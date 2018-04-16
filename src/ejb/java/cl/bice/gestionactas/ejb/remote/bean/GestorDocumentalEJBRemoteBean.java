package cl.bice.gestionactas.ejb.remote.bean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import cl.bice.gestionactas.ejb.dao.DocumentoDAO;
import cl.bice.gestionactas.ejb.dao.TipoCorporacionDAO;
import cl.bice.gestionactas.ejb.entity.DocumentoBO;
import cl.bice.gestionactas.ejb.entity.TipoCorporacionBO;
import cl.bice.gestionactas.ejb.interceptor.EJBInjectorInterceptor;
import cl.bice.gestionactas.ejb.interceptor.Injected;
import cl.bice.gestionactas.ejb.interceptor.WebModuleEntityInterceptor;
import cl.bice.gestionactas.ejb.remote.GestorDocumentalEJBRemote;
import cl.bice.gestionactas.ejb.util.SaveDocument;
import cl.bice.gestionactas.ejb.util.SaveDocumentIDC;
import cl.bice.gestionactas.ejb.vo.DocumentoVO;
import cl.bice.gestionactas.ejb.vo.UserVO;
import cl.neoptera.core.page.PageQueryMapVO;
import cl.neoptera.core.page.PageVO;
import cl.neoptera.ejb.dao.jpa.ServiceContext;

/**
 * Date: 2/10/16 Created by marcelolopez Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

@Stateless(name = "gab_GestorDocumentalEJBRemote", mappedName = "gab_GestorDocumentalEJBRemote")
@Interceptors({ WebModuleEntityInterceptor.class, EJBInjectorInterceptor.class })
public class GestorDocumentalEJBRemoteBean implements GestorDocumentalEJBRemote {
	private static Logger logger = Logger
			.getLogger(GestorDocumentalEJBRemoteBean.class);

	@Injected
	DocumentoDAO documentoDAO;

	@Injected
	TipoCorporacionDAO tipoCorporacionDAO;

	/**
	 * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#actualizarDocumento
	 */
	@Override
	public String actualizarDocumento(String username, String fileName,
			byte[] fileStream, DocumentoVO documentoVO,
			String originalDocumentName, String originalDocumentExtension) {
		DocumentoBO parentDocumentBO = documentoDAO.get(documentoVO
				.getParentDocument().getId());
		DocumentoBO lastDocumentVersionBO = documentoDAO
				.getLastVersionForParentId((documentoVO.getParentDocument()
						.getId()));
		String documentId;
		if (fileStream != null || fileName != null) {
			SaveDocument saveDocument = new SaveDocumentIDC();
			documentId = saveDocument.guardarEnUCM(username, fileName,
					fileStream, documentoVO.getDescripcion());
		} else {
			documentId = parentDocumentBO.getTokenDocumento();
			originalDocumentExtension = lastDocumentVersionBO
					.getOriginalDocumentExtension();
			originalDocumentName = lastDocumentVersionBO
					.getOriginalDocumentBaseName();
		}

		DocumentoBO newUpdatedDocumentoBO = new DocumentoBO(
				documentoVO.getTipoCorporacion(),
				documentoVO.getTipoDocumento(), parentDocumentBO);

		newUpdatedDocumentoBO.setTitulo(documentoVO.getTitulo());
		newUpdatedDocumentoBO.setDescripcion(documentoVO.getDescripcion());
		newUpdatedDocumentoBO.setFechaCreacion(documentoVO.getFechaCreacion());
		// newUpdatedDocumentoBO.setFechaCreacion(documentoVO.getFechaCarga());
		// newUpdatedDocumentoBO.setFechaCreacion(Calendar.getInstance().getTime());
		// newUpdatedDocumentoBO.setFechaCarga(documentoVO.getFechaCarga());
		newUpdatedDocumentoBO.setFechaCarga(Calendar.getInstance().getTime());
		newUpdatedDocumentoBO.setTokenDocumento(documentId);
		newUpdatedDocumentoBO
				.setOriginalDocumentExtension(originalDocumentExtension);
		newUpdatedDocumentoBO.setOriginalDocumentBaseName(originalDocumentName);
		newUpdatedDocumentoBO.setNumSesion(documentoVO.getNumSesion());

		documentoDAO.lockNew();
		documentoDAO.insert(newUpdatedDocumentoBO);
		return documentId;
	}

	/**
	 * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#obtenerDocumentoById(java.lang.Long)
	 */
	@Override
	public DocumentoVO obtenerDocumentoById(Long id) {
		return documentoDAO.get(id).toVO();
	}

	/**
	 * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#obtenerListadoVersiones(cl.bice.gestionactas.ejb.vo.UserVO,
	 *      cl.bice.gestionactas.ejb.vo.DocumentoVO)
	 */
	@Override
	public List<DocumentoVO> obtenerListadoVersiones(UserVO user,
			DocumentoVO document) {
		List<DocumentoVO> resultList = new ArrayList<>();
		for (DocumentoBO docBO : documentoDAO.obtenerListadoVersiones(
				document.getId(), user.getId())) {
			resultList.add(docBO.toVO());
		}
		return resultList;
	}

	/**
	 * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#obtenerDocumentos(cl.neoptera.core.page.PageQueryMapVO)
	 */
	public PageVO<DocumentoVO> obtenerDocumentos(PageQueryMapVO query) {
		List<DocumentoVO> documentoVOs = new ArrayList<>();

		Map<String, Object> mapWhere = query.getQuery();
		Boolean orderByDocumentName = false;

		if (mapWhere == null) {
			throw new NullPointerException(
					"Se esperaba que el mapa de objectos para el where viniera con datos");
		}

		String titulo = (String) mapWhere.get("titulo");
		String descripcion = (String) mapWhere.get("descripcion");
		Long tipoDocumentoId = (Long) mapWhere.get("tipoDocumentoId");
		Long tipoCorporacionId = (Long) mapWhere.get("tipoCorporacionId");
		Long usuarioId = (Long) mapWhere.get("usuario");
		Date periodoInicial = (Date) mapWhere.get("myInitialPeriod");
		Date periodoFinal = (Date) mapWhere.get("myFinalPeriod");
		Integer anno = (Integer) mapWhere.get("anno");
		// Long sesion = (Long) mapWhere.get("sesion");

		// Added Code
		String sesionFull = (String) mapWhere.get("sesion");
		String[] auxSesion = null;
		Long sesion = null;
		if (sesionFull != null && !sesionFull.equals("d")) {
			auxSesion = sesionFull.split("-");
			sesion = Long.decode(auxSesion[0].trim());
		}
		// End

		if (descripcion != null) {
			descripcion = "%" + descripcion.toLowerCase() + "%";
		}
		if (titulo != null) {
			titulo = "%" + titulo.toLowerCase() + "%";
		}

		List<Long> targetIdList = new ArrayList<>();
		Integer cantidad = 0;
		Boolean isFull = false; // cambio del proveedor - agrega linea
		if (periodoFinal != null && periodoInicial != null) {
			cantidad = documentoDAO.countObtenerDocumentosPorPeriodo(true,
					titulo, descripcion, tipoDocumentoId, tipoCorporacionId,
					periodoInicial, periodoFinal, usuarioId);
			if (cantidad > 0) {
				List<Long> objs = documentoDAO.obtenerDocumentosPorPeriodo(
						true, titulo, descripcion, tipoDocumentoId,
						tipoCorporacionId, periodoInicial, periodoFinal,
						usuarioId, query.getFirst(), query.getSize());
				for (Long o : objs) {
					targetIdList.add(o.longValue());
				}
			}
		} else {
			if (anno == null) {
				cantidad = documentoDAO.countFiltrarDocumentosSinAnno(true,
						titulo, descripcion, tipoDocumentoId,
						tipoCorporacionId, usuarioId, sesion);
				if (cantidad > 0) {

					List<BigDecimal> objs = filtrarDocumentosID(titulo,
							descripcion, tipoDocumentoId, tipoCorporacionId,
							sesion, anno, usuarioId, query.getFirst(),
							query.getSize());
					for (int aux = 0; aux < objs.size(); aux++) {
						targetIdList.add(objs.get(aux).longValue());
					}

				}
			} else {
				isFull = true; // Cambio del proveedor - agrega linea

				// Added code
				cantidad = countFiltrarDocumentosSesionID(titulo, descripcion,
						tipoDocumentoId, tipoCorporacionId, sesion, anno,
						usuarioId);

				// end

				if (cantidad > 0) {
					List<BigDecimal> objs = filtrarDocumentosID(titulo,
							descripcion, tipoDocumentoId, tipoCorporacionId,
							sesion, anno, usuarioId, query.getFirst(),
							query.getSize());
					for (int aux = 0; aux < objs.size(); aux++) {
						targetIdList.add(objs.get(aux).longValue());
					}
				}
			}
		}

		if (targetIdList != null && !targetIdList.isEmpty()) {

			List<DocumentoBO> documents = null;

			// si es tipo de corporacion ordenamos por el titulo del documento
			// EJ: 01_XXXX, 02_XXXX, ETC...
			if (tipoCorporacionId != null) {
				TipoCorporacionBO tipoCorporacionBO = tipoCorporacionDAO.obtenerComiteEjecutivoId();
				if (tipoCorporacionBO != null && tipoCorporacionId.intValue() == tipoCorporacionBO.getId().intValue()) {
					orderByDocumentName = true;
				}
			}

			if (orderByDocumentName) {
				documents = documentoDAO
						.obtenerDocByIdsTitAsc(targetIdList);
			} else {
				documents = documentoDAO
						.obtenerDocByIdsFecCreaDesc(targetIdList);
			}

			if (documents != null) {
				for (DocumentoBO doc : documents) {
					DocumentoVO d = doc.toVO();

					Calendar c = Calendar.getInstance();
					c.setTime(d.getFechaCreacion());

					// Added Code
					SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
					String dayMonth = sdf.format(c.getTime()).toUpperCase();

					if (isFull && sesion != null) {
						if (d.getNumSesion().equals(sesion)
								&& c.get(Calendar.YEAR) == anno) {
							if (auxSesion != null)
								if (dayMonth.contains(auxSesion[1].trim())) {
									documentoVOs.add(d);
								} else {
									cantidad--;
								}
							else {
								documentoVOs.add(d);
							}
						} else {
							cantidad--;
						}
					} else {
						if (auxSesion != null)
							if (dayMonth.contains(auxSesion[1].trim())) {
								documentoVOs.add(d);
							} else {
								cantidad--;
							}
						else {
							documentoVOs.add(d);
						}
					}
				}
			}
		}

		PageVO<DocumentoVO> pageVO = new PageVO<>(cantidad, query.getSize());
		pageVO.getData().addAll(documentoVOs);
		return pageVO;
	}

	/**
	 * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#obtenerDocumentoByDocumentId(java.lang.Long)
	 */
	@Override
	public DocumentoVO obtenerDocumentoByDocumentId(Long documentId) {
		DocumentoBO documentoBO = documentoDAO.obtenerDocumentoByDocumentId(
				true, documentId);
		return documentoBO.toVO();
	}

	/**
	 * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#obtenerDocumentosRecientes(cl.bice.gestionactas.ejb.vo.UserVO)
	 */
	@Override
	public List<DocumentoVO> obtenerDocumentosRecientes(UserVO user) {
		List<Integer> targetIdList = documentoDAO
				.getDocumentIdListByLastVersion();
		List<DocumentoVO> returnedListedDocument = new ArrayList<>();
		if (targetIdList != null && !targetIdList.isEmpty()) {
			List<DocumentoBO> documentoBOs = documentoDAO
					.obtenerDocumentosRecientes(true, 10, user.getId(),
							targetIdList);
			List<DocumentoVO> documentoVOs = new ArrayList<>();

			for (DocumentoBO documentoBO : documentoBOs) {
				documentoVOs.add(documentoBO.toVO());
			}
			returnedListedDocument.addAll(documentoVOs);
		}

		// Collections.sort(returnedListedDocument, new sortbyFechaCarga());

		return returnedListedDocument;
	}

	/*
	 * public List<DocumentoVO> obtenerDocumentosRecientes(UserVO user){
	 * List<Integer> targetIdList =
	 * documentoDAO.getDocumentIdListByLastVersion(); if (targetIdList != null
	 * && !targetIdList.isEmpty()) { List<DocumentoBO> documentoBOs =
	 * documentoDAO.obtenerDocumentosRecientes(true, 10, user.getId(),
	 * targetIdList); List<DocumentoVO> documentoVOs = new ArrayList<>();
	 * 
	 * for (DocumentoBO documentoBO : documentoBOs) {
	 * documentoVOs.add(documentoBO.toVO()); } return documentoVOs; } return new
	 * ArrayList<>(); }
	 */

	/**
	 * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#existDocument(cl.bice.gestionactas.ejb.vo.DocumentoVO,
	 *      cl.bice.gestionactas.ejb.vo.DocumentoVO )
	 */
	@Override
	public int existDocument(DocumentoVO documentoVO, DocumentoVO parentDocument) {
		return documentoDAO.getRepeatedTitleCount(documentoVO.getTitulo(),
				parentDocument != null ? parentDocument.getId() : null);
	}

	/**
	 * Permite guardar un documentoBO en la base de datos tentiendo en cuenta
	 * los conceptos de bloqueo
	 * 
	 * @param documentoVO
	 *            Documento que viene desde la capa de presentacion
	 * @param documentId
	 *            id del docuemnto padre
	 * @param originalBaseName
	 *            Nombre del archivo original subido en la capa de presentacion
	 * @param originalExtension
	 *            Extension del archivo original.
	 */
	private void guardaNuevoDocumentoBO(DocumentoVO documentoVO,
			String documentId, String originalBaseName, String originalExtension) {
		DocumentoBO documentoBO = new DocumentoBO(
				documentoVO.getTipoCorporacion(),
				documentoVO.getTipoDocumento(), null);

		documentoBO.setTitulo(documentoVO.getTitulo());
		documentoBO.setDescripcion(documentoVO.getDescripcion());
		documentoBO.setFechaCreacion(documentoVO.getFechaCreacion());
		// documentoBO.setFechaCreacion(documentoVO.getFechaCarga());
		// documentoBO.setFechaCreacion(Calendar.getInstance().getTime());
		// documentoBO.setFechaCarga(documentoVO.getFechaCarga());
		documentoBO.setFechaCarga(Calendar.getInstance().getTime());
		documentoBO.setTokenDocumento(documentId);
		documentoBO.setOriginalDocumentExtension(originalExtension);
		documentoBO.setOriginalDocumentBaseName(originalBaseName);
		documentoBO.setNumSesion(documentoVO.getNumSesion());
		if (documentoBO.getFechaCarga() == null) {
			documentoBO.setFechaCarga(Calendar.getInstance().getTime());
		}
		if (documentoBO.getFechaCreacion() == null) {
			documentoBO.setFechaCreacion(Calendar.getInstance().getTime());
		}

		if (documentoBO.getId() == null) {
			documentoDAO.lockNew();
			documentoDAO.insert(documentoBO);
		} else {
			documentoDAO.lock(documentoBO.getId());
			documentoDAO.update(documentoBO);
		}
	}

	@Override
	public String obtenerToken(String userName, String fileName,
			byte[] fileStream, DocumentoVO documentoVO,
			String originalDocumentName, String originalDocumentExtension) {
		SaveDocument saveDocument = new SaveDocumentIDC();
		String documentId = saveDocument.obtenerToken(userName, fileName,
				fileStream, documentoVO, originalDocumentName,
				originalDocumentExtension);
		System.out
				.println("DOCUMENTO ---------------------------------------- "
						+ documentoVO.toString());
		guardaNuevoDocumentoBO(documentoVO, documentId, originalDocumentName,
				originalDocumentExtension);
		return documentId;
	}

	@Override
	public String crearDocumento(String token, String appName,
			DocumentoVO documentoVO) {
		SaveDocument saveDocument = new SaveDocumentIDC();
		return saveDocument.crearDocumento(token, appName, documentoVO);
	}

	/**
	 * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#bajarDocumento(java.lang.String)
	 */
	@Override
	public DocumentoVO bajarDocumento(String documentId) {
		SaveDocument saveDocument = new SaveDocumentIDC();
		return saveDocument.bajarDocumento(documentId);
	}

	/**
	 * @see cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc#eliminarDocumento
	 */
	@Override
	public String eliminarDocumento(DocumentoVO documentoVO) {
		String documentId = "";
		try {
			List<Long> lista = documentoDAO
					.getDocumentIdListByParent(documentoVO.getParentDocument()
							.getId());
			for (int i = 0; i < lista.size(); i++) {
				logger.info("ID doc: " + lista.get(i));
				documentoDAO.lock(lista.get(i));
				DocumentoBO documentoBO = documentoDAO.getForUpdate(lista
						.get(i));
				documentId = documentoBO.getTokenDocumento();
				documentoBO.setEstado(false);
				// documentId =
				// documentoDAO.updateDocumentFlgEstado(lista.get(i));
				documentoDAO.update(documentoBO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documentId;
	}

	// 03/01/2017
	public List<BigDecimal> filtrarDocumentosID(String titulo,
			String descripcion, Long tipoDocumento, Long tipoCorporacion,
			Long numSesion, Integer year, Long userID, int first, int max) {

		System.out.println("titulo: " + titulo);
		System.out.println("descripcion: " + descripcion);
		System.out.println("tipoDocumento: " + tipoDocumento);
		System.out.println("tipoCorporacion: " + tipoCorporacion);
		System.out.println("numSesion: " + numSesion);
		System.out.println("year: " + year);
		System.out.println("userID: " + userID);
		System.out.println("first: " + first);
		System.out.println("max: " + max);

		EntityManager entityManager = ServiceContext.current().getEM();
		String queryBuild = new String(
				"SELECT DOC_ROW.doc_id FROM (SELECT docum.*, rownum rn FROM ("
						+ " SELECT * FROM doc_documento doc WHERE doc.doc_id IN ( SELECT DISTINCT d.doc_id "
						+ " FROM doc_documento d, udv_userdocumentvisibility dv "
						+ " WHERE d.flg_tipodocumento_id=dv.udv_tipodocumentobo_id "
						+ " AND dv.udv_userbo_id= :userID "
						+ " AND (LOWER(d.nom_titulo) LIKE :titulo or LOWER(d.nom_descripcion) LIKE :descripcion) "
						+ " AND d.cor_version IN (SELECT MAX(document.cor_version) FROM doc_documento document GROUP BY document.doc_parentdocument_id) "
						+ " AND d.doc_id IN (SELECT MAX(docu.doc_id) FROM doc_documento docu GROUP BY docu.doc_parentdocument_id) ");
		if (year != null)
			queryBuild = queryBuild
					+ " AND EXTRACT(YEAR FROM d.fec_fechacreacion)= :year ";
		if (numSesion != null)
			queryBuild = queryBuild + " AND d.num_sesion= :numSesion ";
		if (tipoDocumento != null)
			queryBuild = queryBuild
					+ " AND d.flg_tipodocumento_id= :tipoDocumento ";
		if (tipoCorporacion != null)
			queryBuild = queryBuild
					+ " AND d.doc_tipocorporacion_id= :tipoCorporacion ";
		queryBuild = queryBuild
				+ " AND d.flg_estado= 1) "
				+ " ORDER BY doc.fec_fechacreacion DESC, doc.fec_fechacarga DESC) docum where rownum <=(:first+:max)) DOC_ROW "
				+ " WHERE rn>:first";

		System.out.println("Query: " + queryBuild);

		Query query = entityManager.createNativeQuery(queryBuild);
		query.setParameter("titulo", titulo);
		query.setParameter("descripcion", descripcion);
		if (tipoDocumento != null)
			query.setParameter("tipoDocumento", tipoDocumento);
		if (tipoCorporacion != null)
			query.setParameter("tipoCorporacion", tipoCorporacion);
		if (numSesion != null)
			query.setParameter("numSesion", numSesion);
		if (year != null)
			query.setParameter("year", year);
		query.setParameter("userID", userID);
		query.setParameter("first", first);
		query.setParameter("max", max);
		List<BigDecimal> result = query.getResultList();
		System.out.println("Filtrar ids: " + result);
		return result;
	}

	public Integer countFiltrarDocumentosSesionID(String titulo,
			String descripcion, Long tipoDocumento, Long tipoCorporacion,
			Long numSesion, Integer year, Long userID) {
		EntityManager entityManager = ServiceContext.current().getEM();

		System.out.println("titulo: " + titulo);
		System.out.println("descripcion: " + descripcion);
		System.out.println("tipoDocumento: " + tipoDocumento);
		System.out.println("tipoCorporacion: " + tipoCorporacion);
		System.out.println("numSesion: " + numSesion);
		System.out.println("year: " + year);
		System.out.println("userID: " + userID);

		String queryBuild = new String("SELECT * "
				+ " FROM (SELECT COUNT(doc.doc_parentdocument_id) "
				+ " FROM doc_documento doc, udv_userdocumentvisibility dv"
				+ " WHERE doc.flg_tipodocumento_id=dv.udv_tipodocumentobo_id "
				+ " AND dv.udv_userbo_id = :userID "
				+ " AND (lower(doc.nom_titulo) LIKE :titulo "
				+ " OR lower(doc.nom_descripcion) LIKE :descripcion)"
				+ " AND doc.cor_version IN "
				+ "   (SELECT MAX(doc.cor_version) "
				+ "   FROM doc_documento doc "
				+ "   GROUP BY doc.doc_parentdocument_id " + "   ) "
				+ " AND doc.doc_id IN " + "   (SELECT MAX(doc.doc_id) "
				+ "   FROM doc_documento doc "
				+ "   GROUP BY doc.doc_parentdocument_id " + "    ) ");
		if (year != null)
			queryBuild = queryBuild
					+ " AND extract(YEAR FROM doc.fec_fechacreacion)= :year ";
		if (tipoDocumento != null)
			queryBuild = queryBuild
					+ " AND doc.flg_tipodocumento_id = :tipoDocumento ";
		if (tipoCorporacion != null)
			queryBuild = queryBuild
					+ " AND doc.doc_tipocorporacion_id= :tipoCorporacion ";
		if (numSesion != null)
			queryBuild = queryBuild + " AND doc.num_sesion = :numSesion ";

		queryBuild = queryBuild + " AND doc.flg_estado = 1)";

		System.out.println("Query: " + queryBuild);

		Query query = entityManager.createNativeQuery(queryBuild);
		query.setParameter("titulo", titulo);
		query.setParameter("descripcion", descripcion);
		if (tipoDocumento != null)
			query.setParameter("tipoDocumento", tipoDocumento);
		if (tipoCorporacion != null)
			query.setParameter("tipoCorporacion", tipoCorporacion);
		if (numSesion != null)
			query.setParameter("numSesion", numSesion);
		if (year != null)
			query.setParameter("year", year);
		query.setParameter("userID", userID);
		List<BigDecimal> result = query.getResultList();
		System.out.println("countDocs por sesion: " + result);

		return result.get(0).intValue();
	}

}

class sortbyFechaCarga implements Comparator<DocumentoVO> {
	@Override
	public int compare(DocumentoVO a, DocumentoVO b) {
		return a.getFechaCreacion().after(b.getFechaCreacion()) ? -1 : 1;
	}
}

package cl.bice.gestionactas.web.springmvc.acts.json;

import cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc;
import cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc;
import cl.bice.gestionactas.ejb.vo.DocumentoVO;
import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;
import cl.bice.gestionactas.web.WebUser;
import cl.bice.gestionactas.web.annotation.ContextoWeb;
import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.springmvc.acts.viewvo.ActaViewVO;
import cl.bice.gestionactas.web.springmvc.acts.viewvo.ActasSearchCriteria;
import cl.bice.gestionactas.web.springmvc.common.GenericController;
import cl.bice.gestionactas.web.springmvc.common.PageJsonViewVO;
import cl.bice.gestionactas.web.springmvc.common.PaginatorVO;
import cl.bice.gestionactas.web.springmvc.common.ResultEnum;
import cl.neoptera.core.page.PageQueryMapVO;
import cl.neoptera.core.page.PageVO;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2/10/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

@Controller
@RequestMapping(value = "/main/acts/json")
public class ActsJsonController extends GenericController{
    private static Logger LOGGER = Logger.getLogger(ActsJsonController.class.getName());

    @WebInjected
    GestorDocumentalSvc gestorDocumentalSvc;

    @WebInjected
    ParametrosEstaticosSvc parametrosEstaticosSvc;

    @RequestMapping(value = "/updatedocument", method = RequestMethod.POST,  consumes="multipart/form-data")
    @ResponseBody
    public ResultEnum updateDocument(@RequestPart("document") ActaViewVO actaViewVO,
                                     @RequestPart(value = "file", required = false) MultipartFile file,
                                     @ContextoWeb WebUser webUser) throws IOException {

        DocumentoVO targetDocVO = actaViewVO.toVO(true, parametrosEstaticosSvc);
        targetDocVO.setParentDocument(gestorDocumentalSvc.obtenerDocumentoById(webUser.decript(actaViewVO.getParentDocumentId())));

        String targetName = null;
        String baseName = null;
        String fileExtension = null;
        byte[] targetBytes = null;

        if (gestorDocumentalSvc.existDocument(actaViewVO.toVO(false, null),targetDocVO.getParentDocument()) != 0){
            return ResultEnum.EXISTSTITLE;
        }

        if(file != null){
            targetName = file.getOriginalFilename();
            baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
            fileExtension =  FilenameUtils.getExtension(file.getOriginalFilename());
            targetBytes = file.getBytes();
        }

        gestorDocumentalSvc.actualizarDocumento(webUser.getUsuario().getLoginAD(), targetName, targetBytes, targetDocVO, baseName, fileExtension);

        return ResultEnum.SUCCESS;
    }

    @RequestMapping(value = "/adddocument", method = RequestMethod.POST,  consumes="multipart/form-data")
    @ResponseBody
    public ResultEnum addDocument(@RequestPart("document") ActaViewVO actaViewVO,
                                  @RequestPart("file") MultipartFile file,
                                  @ContextoWeb WebUser webUser) throws IOException {
        LOGGER.info("actaViewVO vo -> " + actaViewVO.toString());
        LOGGER.info("File Name vo -> " + file.getName());
        LOGGER.info("File Contenttype vo -> " + file.getContentType());
        LOGGER.info("File Original Name vo -> " + file.getOriginalFilename());
        System.out.println("actaViewVO vo -> " + actaViewVO.toString());
        System.out.println("File Name vo -> " + file.getName());
        System.out.println("File Contenttype vo -> " + file.getContentType());
        System.out.println("File Original Name vo -> " + file.getOriginalFilename());
        int docQuantity = gestorDocumentalSvc.existDocument(actaViewVO.toVO(false, null), null);
        if (docQuantity != 0){
            return ResultEnum.EXISTSTITLE;
        }
        gestorDocumentalSvc.obtenerToken(webUser.getUsuario().getLoginAD(), file.getOriginalFilename(),
            file.getBytes(),
            actaViewVO.toVO(true,parametrosEstaticosSvc),
            FilenameUtils.getBaseName(file.getOriginalFilename()), FilenameUtils.getExtension(file.getOriginalFilename()));
        return ResultEnum.SUCCESS;
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PageJsonViewVO<ActaViewVO> searchActs(@ContextoWeb WebUser webUser, @RequestBody PaginatorVO<ActasSearchCriteria> paginatorVO){
        PageJsonViewVO<ActaViewVO> pageJsonViewVO = new PageJsonViewVO<>();
        ActasSearchCriteria searchCriteria = paginatorVO.getCriteria();
        Long tipoCorporacionId = null;
        Long tipoDocumentoId = null;
        if (searchCriteria.getCorporacion() != null){
            tipoCorporacionId = webUser.decript(searchCriteria.getCorporacion());
        }
        if (searchCriteria.getTipoDocumento() != null && !searchCriteria.getTipoDocumento().equals("d")){
            tipoDocumentoId = webUser.decript(searchCriteria.getTipoDocumento());
        }

        if(tipoDocumentoId == null){
            pageJsonViewVO.setTotal(0);
            pageJsonViewVO.setRows(null);
            return pageJsonViewVO;
        }

        Map<String, Object> mapWhere = new HashMap<>();
        if (searchCriteria.getTitulo() != null && searchCriteria.getTitulo().length() > 0){
            mapWhere.put("titulo", "%"+searchCriteria.getTitulo()+"%");
            mapWhere.put("descripcion", "%"+searchCriteria.getTitulo()+"%");
        }else{
            mapWhere.put("titulo", "%");
            mapWhere.put("descripcion", "%");
        }
        mapWhere.put("tipoDocumentoId", tipoDocumentoId);
        mapWhere.put("tipoCorporacionId", tipoCorporacionId);
        String sesion = null;
        if (searchCriteria.getSesion() != null && !searchCriteria.getSesion().equals("d")){
        	mapWhere.put("sesion", searchCriteria.getSesion());
        }else{
        	mapWhere.put("sesion", sesion);
        }
        Integer anno = null;
        if (searchCriteria.getAnno() != null && !searchCriteria.getAnno().equals("d")){
            anno = Integer.decode(searchCriteria.getAnno());
        }
        mapWhere.put("anno", anno);
        mapWhere.put("usuario", webUser.getUsuario().getId());

        Map<String, Boolean> sort = null;
        if (paginatorVO.getSort() != null){
            sort = new HashMap<>();
            switch (paginatorVO.getSort()){
                case "titulo":
                    sort.put(paginatorVO.getSort(), paginatorVO.getOrder().equals("asc"));
                    break;
                case "descripcion":
                    sort.put(paginatorVO.getSort(), paginatorVO.getOrder().equals("asc"));
                    break;
                case "fechaCreacion":
                    sort.put(paginatorVO.getSort(), paginatorVO.getOrder().equals("desc"));
                    break;
            }

        }
        LOGGER.info("ActsJsonController .- Map: "+mapWhere);

        PageQueryMapVO queryMapVO = new PageQueryMapVO(paginatorVO.getOffset() / paginatorVO.getLimit(), paginatorVO.getLimit(), mapWhere, sort);
        PageVO<DocumentoVO> voList = gestorDocumentalSvc.obtenerDocumentos(queryMapVO);
        List<ActaViewVO>  actListViewVO = new ArrayList<>();
        for (DocumentoVO documento : voList.getData()){
            ActaViewVO act = new ActaViewVO(documento);
            act.setTokenDocumento(webUser.encript(documento.getTokenDocumento()));
            act.setDocumentId(webUser.encript(documento.getId()));
            actListViewVO.add(act);
        }

        //Collections.sort(actListViewVO, new sortbyFechaCarga());

        pageJsonViewVO.setRows(actListViewVO);
        pageJsonViewVO.setTotal(voList.getTotal());
        return pageJsonViewVO;
    }

    @RequestMapping(value = "/recent", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PageJsonViewVO<ActaViewVO> searchRecentActs(@ContextoWeb WebUser webUser){
        PageJsonViewVO<ActaViewVO> pageJsonViewVO = new PageJsonViewVO<>();
        List<DocumentoVO> voList;
        voList = gestorDocumentalSvc.obtenerDocumentosRecientes(webUser.getUsuario());
        List<ActaViewVO>  actListViewVO = new ArrayList<>();
        for (DocumentoVO documento : voList){
            if (documento != null && documento.getFechaCreacion() != null) {
                ActaViewVO act = new ActaViewVO(documento);
                act.setTokenDocumento(webUser.encript(documento.getTokenDocumento()));
                act.setDocumentId(webUser.encript(documento.getId()));
                actListViewVO.add(act);
            }
        }

        //Collections.sort(actListViewVO, new sortbyFechaCarga());

        pageJsonViewVO.setRows(actListViewVO);
        pageJsonViewVO.setTotal(actListViewVO.size());
        return pageJsonViewVO;
    }

    @RequestMapping(value = "/interact/tipodoc/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TipoDocumentoVO> interactTipoDocumento(@ContextoWeb WebUser user, @PathVariable String id){
        if (id == null || id.isEmpty()){
            throw new NullPointerException("no puede venir el tipo de corporacion nulo");
        }
        Long idCorporacion = user.decript(id);
        List<TipoDocumentoVO> tipoDocumentoVOs = parametrosEstaticosSvc.obtenerTipoDocumentosVisibles(user.getUsuario(), idCorporacion);
        for(int i = 0; i < tipoDocumentoVOs.size() ; i++){
            TipoDocumentoVO tipoDocumentoVO = tipoDocumentoVOs.get(i);
            tipoDocumentoVO.setCiphed(user.encript(tipoDocumentoVO.getId()));
            tipoDocumentoVO.setId(0l);
            tipoDocumentoVOs.set(i, tipoDocumentoVO);
        }
        return tipoDocumentoVOs;
    }

    @RequestMapping(value = "/interact/anno/{idCorporacion}/{idTipoDocumento}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Long> interactYear(@ContextoWeb WebUser user,
                                              @PathVariable("idCorporacion") String idCorporacion,
                                              @PathVariable("idTipoDocumento") String idTipoDocumento){
        if (idCorporacion == null || idCorporacion.isEmpty()){
            throw new NullPointerException("no puede venir el tipo de corporacion vacio");
        }
        if (idTipoDocumento ==null || idTipoDocumento.isEmpty()){
            throw new NullPointerException("no puede venir el tipo de documento vacio");
        }
        Long idCorp = user.decript(idCorporacion);
        Long idTD = user.decript(idTipoDocumento);
        List<Long> listAnno = parametrosEstaticosSvc.obtenerAnnoPorDocumento(user.getUsuario(), idCorp, idTD);
        return listAnno;
    }


    @RequestMapping(value = "/interact/sesion/{idCorporacion}/{idTipoDocumento}/{year}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> interactSession(@ContextoWeb WebUser user,
                                   @PathVariable("idCorporacion") String idCorporacion,
                                   @PathVariable("idTipoDocumento") String idTipoDocumento,
                                   @PathVariable("year") String year){
        if (idCorporacion == null || idCorporacion.isEmpty()){
            throw new NullPointerException("no puede venir el tipo de corporacion vacio");
        }
        if (idTipoDocumento ==null || idTipoDocumento.isEmpty()){
            throw new NullPointerException("no puede venir el tipo de documento vacio");
        }

        if (year == null || year.isEmpty()){
            throw new NullPointerException("el tipo year no puede ser vacio");
        }

        Long idCorp = user.decript(idCorporacion);
        Long idTD = user.decript(idTipoDocumento);
        Long y = Long.decode(year);
        List<String> listAnno = parametrosEstaticosSvc.obtenerSession(user.getUsuario(), idCorp, idTD, y);
        return listAnno;
    }

    @RequestMapping(value="/deletedocument/{id}")
    @ResponseBody
    public ResultEnum deleteDocument(@ContextoWeb WebUser user, @PathVariable String id){
    	String decryptedDocumentId = user.decript(id, false);
    	LOGGER.debug("ID del documento -> " + id);

        String p = "";
        try {
            DocumentoVO documentoVO;
            documentoVO = gestorDocumentalSvc.obtenerDocumentoByDocumentId(new Long(decryptedDocumentId));
            p = gestorDocumentalSvc.eliminarDocumento(documentoVO);
        } catch (NumberFormatException e) {
        	e.printStackTrace();
            LOGGER.error("Error al tratar de eliminar un documento " + e.getMessage());
            return ResultEnum.ERROR;
        }
        return ResultEnum.SUCCESS;
    }
}

class sortbyFechaCarga implements Comparator<ActaViewVO> {
    @Override
    public int compare(ActaViewVO a, ActaViewVO b) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Calendar cal = Calendar.getInstance();
    	Calendar cal2 = Calendar.getInstance();
    	try{
	    	cal.setTime(sdf.parse(a.getFechaCreacion()));
	    	cal2.setTime(sdf.parse(b.getFechaCreacion()));
    	}catch(ParseException pe){
    		pe.printStackTrace();
    	}
        return cal.after(cal2) ? -1 : 1;
    }
}

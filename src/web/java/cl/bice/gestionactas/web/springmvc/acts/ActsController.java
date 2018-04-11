package cl.bice.gestionactas.web.springmvc.acts;

import cl.bice.gestionactas.ejb.svc.GestorDocumentalSvc;
import cl.bice.gestionactas.ejb.svc.ParametrosEstaticosSvc;
import cl.bice.gestionactas.ejb.type.RoleEnum;
import cl.bice.gestionactas.ejb.vo.DocumentoVO;
import cl.bice.gestionactas.ejb.vo.TipoCorporacionVO;
import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;
import cl.bice.gestionactas.ejb.vo.TipoUsuarioVO;
import cl.bice.gestionactas.web.WebUser;
import cl.bice.gestionactas.web.annotation.ContextoWeb;
import cl.bice.gestionactas.web.annotation.WebInjected;
import cl.bice.gestionactas.web.springmvc.acts.viewvo.MultipleDocViewVO;
import cl.bice.gestionactas.web.springmvc.common.GenericController;
import org.apache.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Date: 2/5/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

@Controller
@RequestMapping(value = "/main/acts")
public class ActsController extends GenericController {
    private static Logger LOGGER = Logger.getLogger(ActsController.class.getName());

    @WebInjected
    ParametrosEstaticosSvc parametrosEstaticosSvc;

    @WebInjected
    GestorDocumentalSvc gestorDocumentalSvc;

    public ActsController() {
        mapNav.put("recent", "acts/recents");
        mapNav.put("load", "acts/carga");
        mapNav.put("search", "acts/busqueda");
        mapNav.put("download", "acts/download");
        mapNav.put("edit", "acts/edit");
        mapNav.put("remove", "acts/remove");
        mapNav.put("versions", "acts/versions");
    }
    @RequestMapping(value = "/versions/{encryptedDocId}", method = RequestMethod.GET)
    public ModelAndView showVersions(@ContextoWeb WebUser webUser, @PathVariable String encryptedDocId){
        ModelAndView mav = new ModelAndView(mapNav.get("versions"));
        Long decryptedDocumentId = new Long(webUser.decript(encryptedDocId, false));
        DocumentoVO thisDocument = gestorDocumentalSvc.obtenerDocumentoById(decryptedDocumentId);
        List<DocumentoVO> documentoVOList = gestorDocumentalSvc.obtenerListadoVersiones(webUser.getUsuario(),
                thisDocument.getParentDocument());
        for(DocumentoVO docVO : documentoVOList){
            docVO.setTokenDocumento(webUser.encript(docVO.getId()));
        }
        mav.addObject("versiones", documentoVOList);
        mav.addObject("documento", thisDocument);
        return mav;
    }


    @RequestMapping(value = "/edit/{encryptedDocId}", method = RequestMethod.GET)
    public ModelAndView showEditAct(@ContextoWeb WebUser webUser, @PathVariable String encryptedDocId){
        ModelAndView mav = new ModelAndView(mapNav.get("edit"));
        String decryptedDocumentId = webUser.decript(encryptedDocId, false);
        DocumentoVO documentoVO = gestorDocumentalSvc.obtenerDocumentoById(new Long(decryptedDocumentId));
        mav.addObject("documento", documentoVO);
        Long targetId = documentoVO.getParentDocument() != null ? documentoVO.getParentDocument().getId() : documentoVO.getId();
        mav.addObject("parentDocumentId", webUser.encript(targetId));
        return mav;
    }


    @RequestMapping(value = "/remove/{encryptedDocId}", method = RequestMethod.GET)
    public ModelAndView showRemoveAct(@ContextoWeb WebUser webUser, @PathVariable String encryptedDocId){
        ModelAndView mav = new ModelAndView(mapNav.get("remove"));
        String decryptedDocumentId = webUser.decript(encryptedDocId, false);
        DocumentoVO documentoVO = gestorDocumentalSvc.obtenerDocumentoById(new Long(decryptedDocumentId));
        mav.addObject("documento", documentoVO);
        Long targetId = documentoVO.getParentDocument() != null ? documentoVO.getParentDocument().getId() : documentoVO.getId();
        mav.addObject("parentDocumentId", webUser.encript(targetId));
        return mav;
    }


    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    public ModelAndView showRecentPage(@ContextoWeb WebUser user){
        ModelAndView mav = new ModelAndView(mapNav.get("recent"));
        return mav;
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public ModelAndView showLoadPage(@ContextoWeb WebUser user){
        ModelAndView mav = new ModelAndView(mapNav.get("load"));
        List<TipoUsuarioVO> voTipoUsuarioList = parametrosEstaticosSvc.obtenerTipoUsuarios();
        List<TipoCorporacionVO> voTipoCorporacionList = parametrosEstaticosSvc.obtenerTipoCorporaciones();
        mav.addObject("usuarioType", voTipoUsuarioList);
        Long idTipoCorporacion = voTipoCorporacionList.get(0).getId();
        for(int i=0; i < voTipoCorporacionList.size() ; i++){
            voTipoCorporacionList.get(i).setCiphed(user.encript(voTipoCorporacionList.get(i).getId()));
        }
        mav.addObject("corporacionType", voTipoCorporacionList);
        List<TipoDocumentoVO> voTipoDocumentoList = parametrosEstaticosSvc.obtenerTipoDocumentos(idTipoCorporacion);
        for(int j=0; j <voTipoDocumentoList.size() ; j++){
            voTipoDocumentoList.get(j).setCiphed(user.encript(voTipoDocumentoList.get(j).getId()));
        }
        mav.addObject("documentoType", voTipoDocumentoList);
        return mav;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView showSearchPage(@ContextoWeb WebUser user){
        ModelAndView mav = new ModelAndView(mapNav.get("search"));
        LOGGER.info("showSearchPage ------------------> Usuario" + user.getUsuario());
        List<TipoCorporacionVO> voTipoCorporacionList = parametrosEstaticosSvc.obtenerTipoCorporaciones();
        if (voTipoCorporacionList == null && voTipoCorporacionList.isEmpty()){
            throw new NullPointerException("las corporaciones son nulas, debe existir a lo menos 1");
        }
        Long idTipoCorporacion = voTipoCorporacionList.get(0).getId();
        for(int i=0; i < voTipoCorporacionList.size() ; i++){
            voTipoCorporacionList.get(i).setCiphed(user.encript(voTipoCorporacionList.get(i).getId()));
        }
        //Seleccionamos la primera corporacion
        List<TipoDocumentoVO> voTipoDocumentoList = parametrosEstaticosSvc.obtenerTipoDocumentosVisibles(user.getUsuario(), idTipoCorporacion);
        for(int j=0; j <voTipoDocumentoList.size() ; j++){
            voTipoDocumentoList.get(j).setCiphed(user.encript(voTipoDocumentoList.get(j).getId()));
        }
        mav.addObject("documentoType", voTipoDocumentoList);
        mav.addObject("corporacionType", voTipoCorporacionList);
        GrantedAuthority s = new SimpleGrantedAuthority("ROLE_".concat(RoleEnum.ADMINISTRATOR.getNombre()));
        mav.addObject("canViewEdit", SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(s));
        return mav;
    }

    @RequestMapping(value = "/download/{encryptedDocId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadDocument(@PathVariable String encryptedDocId, @ContextoWeb WebUser webUser) {
        LOGGER.info("showSearchPage --> DOCUMENT ID: " + encryptedDocId);
        String decryptedDocumentId = webUser.decript(encryptedDocId, false);
        DocumentoVO documentoVO = gestorDocumentalSvc.obtenerDocumentoByDocumentId(Long.decode(decryptedDocumentId));
        try{
        byte[] binaryContent = gestorDocumentalSvc.bajarDocumento(documentoVO.getTokenDocumento()).getContent();
        InputStream myInputStream = new ByteArrayInputStream(binaryContent);

        MimetypesFileTypeMap mimeMap = new MimetypesFileTypeMap();
        mimeMap.addMimeTypes("application/pdf pdf");
        String type = mimeMap.getContentType(documentoVO.getOriginalFileName());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + documentoVO.getOriginalFileName() + "\"");
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, type);

        return ResponseEntity.ok()
                .contentLength(binaryContent.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(responseHeaders)
                .body(new InputStreamResource(myInputStream));
        }catch (Exception e){
            return null;
        }
    }




    @RequestMapping(value = "/downloadm", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadMultipleDocument(HttpServletRequest request, @ContextoWeb WebUser webUser) {
        try {
            String m = request.getParameter("id");
            List<String> listDecriptedId = new ArrayList<>();
            for (String encriptedId : m.split(",")) {
                listDecriptedId.add(webUser.decript(encriptedId, false));
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);
            for (String decryptedDocumentId : listDecriptedId) {
                DocumentoVO documentoVO = gestorDocumentalSvc.obtenerDocumentoByDocumentId(Long.parseLong(decryptedDocumentId));
                byte[] binaryContent = gestorDocumentalSvc.bajarDocumento(documentoVO.getTokenDocumento()).getContent();
                ZipEntry entry = new ZipEntry(documentoVO.getOriginalFileName());
                entry.setSize(binaryContent.length);
                zos.putNextEntry(entry);
                zos.write(binaryContent);
                zos.closeEntry();
            }

            zos.close();
            byte[] zippedBytes  = baos.toByteArray();

            LOGGER.info("Preparando descarga: ");
            MimetypesFileTypeMap mimeMap = new MimetypesFileTypeMap();
            mimeMap.addMimeTypes("application/zip");
            String type = mimeMap.getContentType("zip");
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=multipledoc.zip");
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, type);
            InputStream myInputStream = new ByteArrayInputStream(zippedBytes);
            return ResponseEntity.ok()
                    .contentLength(zippedBytes.length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .headers(responseHeaders)
                    .body(new InputStreamResource(myInputStream));
        }
        catch (Exception e){
        	e.printStackTrace();
            LOGGER.info("Error de archivo " + e.getMessage());
        }
        return null;
    }


    @RequestMapping(value = "/downloadmultiple", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadMultipleDocumentAjax(@RequestBody MultipleDocViewVO multipleDoc, @ContextoWeb WebUser webUser) {
        try {
            List<String> listDecriptedId = new ArrayList<>();
            for (String encriptedId : multipleDoc.getDocuments()) {
                listDecriptedId.add(webUser.decript(encriptedId, false));
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

            int i = 0;
            for (String decryptedDocumentId : listDecriptedId) {
                DocumentoVO documentoVO = gestorDocumentalSvc.obtenerDocumentoByDocumentId(Long.decode(decryptedDocumentId));
                byte[] binaryContent = gestorDocumentalSvc.bajarDocumento(documentoVO.getTokenDocumento()).getContent();
                ZipEntry entry = new ZipEntry(documentoVO.getOriginalFileName());
                entry.setSize(binaryContent.length);
                zos.putNextEntry(entry);
                zos.write(binaryContent);
                zos.closeEntry();
            }

            zos.close();
            byte[] zippedBytes  = baos.toByteArray();

            MimetypesFileTypeMap mimeMap = new MimetypesFileTypeMap();
            mimeMap.addMimeTypes("application/zip");
            String type = mimeMap.getContentType("zip");

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=multipledoc.zip");
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, type);

            InputStream myInputStream = new ByteArrayInputStream(zippedBytes);

            return ResponseEntity.ok()
                    .contentLength(zippedBytes.length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .headers(responseHeaders)
                    .body(new InputStreamResource(myInputStream));
        }
        catch (Exception e){
            LOGGER.info("Error de archivo " + e.getMessage());
        }
        return null;
    }
}

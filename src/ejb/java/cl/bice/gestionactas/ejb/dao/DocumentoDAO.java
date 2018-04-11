package cl.bice.gestionactas.ejb.dao;

import cl.bice.gestionactas.ejb.entity.DocumentoBO;
import cl.neoptera.ejb.dao.*;

import java.util.Date;
import java.util.List;

/**
 * Date: 2/16/16
 * Created by Humberto Rojas
 * Birchman Consultores LTDA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

public interface DocumentoDAO extends GenericDAO<Long, DocumentoBO> {
    @GenericQuery(query = "select d from DocumentoBO d, UserDocumentVisibilityBO vd where d.tipoDocumento.id = vd.tipoDocumentoBO.id and vd.userBO.id = :userId order by d.titulo ")
    List<DocumentoBO> listarDocumentos(@GenericWhere(element = "d.estado") Boolean estado, @GenericWhere(element = ":userId") Long userId);

    @GenericQuery(query = "select doc from DocumentoBO  doc order by doc.fechaCreacion desc", max = 1)
    DocumentoBO obtenerDocumentoByDocumentId(@GenericWhere(element = "doc.estado") Boolean estado,
                                             @GenericWhere(element = "doc.id") Long documentId);

    /*
    @GenericQuery(query = "select distinct d.id from DocumentoBO d, UserDocumentVisibilityBO vd " +
            "where d.tipoDocumento.id = vd.tipoDocumentoBO.id and vd.userBO.id = :userId and YEAR(d.fechaCreacion) = :y " +
    		"and d.version in (select max(version) from DocumentoBO doc group by doc.parentDocument.id) " +
            "and d.id in (select max(id) from DocumentoBO doc group by doc.parentDocument.id) " +
            "and (lower(d.titulo) like :titulo or lower(d.descripcion) like :descripcion) order by d.id ")
    List<Long> filtrarDocumentos(@GenericWhere(element = "d.estado") Boolean estado,
                                        @GenericWhere(element = ":titulo", optional = true) String titulo,
                                        @GenericWhere(element = ":descripcion", optional = true) String descripcion,
                                        @GenericWhere(element = "d.tipoDocumento.id", optional = true) Long tipoDocumentoId,
                                        @GenericWhere(element = "d.tipoCorporacion.id", optional = true) Long tipoCorporacionId,
                                        @GenericWhere(element = "d.numSesion", optional = true) Long numSesion,
                                        @GenericWhere(element = ":y", optional = true) Integer anno,
                                        @GenericWhere(element = ":userId") Long userId,
                                        @GenericWhere(element = "*first") int first,
                                        @GenericWhere(element = "*max") int max);
	*/
    
    @GenericQuery(query = "select count(d.parentDocument.id) from DocumentoBO d, UserDocumentVisibilityBO vd where d.tipoDocumento.id = vd.tipoDocumentoBO.id " +
    		"and vd.userBO.id = :userId and YEAR(d.fechaCreacion) = :y and (lower(d.titulo) like :titulo or lower(d.descripcion) like :descripcion) " +
    		"and d.version in (select max(doc.version) from DocumentoBO doc group by doc.parentDocument.id) " +
    		"and d.id in (select max(doc.id) from DocumentoBO doc group by doc.parentDocument.id)" +
    		"and d.numSesion = :numSesion")
    		//"and vd.userBO.id = :userId and YEAR(d.fechaCreacion) = :y and (lower(d.titulo) like :titulo or lower(d.descripcion) like :descripcion) and d.version = 1")
    Integer countFiltrarDocumentosSesion(@GenericWhere(element = "d.estado") Boolean estado,
                                        @GenericWhere(element = ":titulo", optional = true) String titulo,
                                        @GenericWhere(element = ":descripcion", optional = true) String descripcion,
                                        @GenericWhere(element = "d.tipoDocumento.id", optional = true) Long tipoDocumentoId,
                                        @GenericWhere(element = "d.tipoCorporacion.id", optional = true) Long tipoCorporacionId,
                                        @GenericWhere(element = ":userId") Long userId,
                                        @GenericWhere(element = ":numSesion") Long numSesion,
                                        @GenericWhere(element = ":y", optional = true) Integer anno);

    @GenericQuery(query = "select count(d.parentDocument.id) from DocumentoBO d, UserDocumentVisibilityBO vd where d.tipoDocumento.id = vd.tipoDocumentoBO.id " +
            "and vd.userBO.id = :userId and (lower(d.titulo) like :titulo or lower(d.descripcion) like :descripcion) and d.version = 1")
    Integer countFiltrarDocumentosSinAnno(@GenericWhere(element = "d.estado") Boolean estado,
                                   @GenericWhere(element = ":titulo", optional = true) String titulo,
                                   @GenericWhere(element = ":descripcion", optional = true) String descripcion,
                                   @GenericWhere(element = "d.tipoDocumento.id", optional = true) Long tipoDocumentoId,
                                   @GenericWhere(element = "d.tipoCorporacion.id", optional = true) Long tipoCorporacionId,
                                   @GenericWhere(element = ":userId") Long userId,
                                   @GenericWhere(element = "d.numSesion", optional = true) Long numSesion);

    @GenericQuery(query = "select count(distinct d.parentDocument.id) from DocumentoBO d, UserDocumentVisibilityBO vd where d.tipoDocumento.id = vd.tipoDocumentoBO.id and vd.userBO.id = :userId " +
            "and d.fechaCreacion >= :periodoInicial and d.fechaCreacion <= :periodoFinal and (lower(d.titulo) like :titulo or lower(d.descripcion) like :descripcion) and d.version = 1")
    @GenericSort( element = "d.titulo")
    Integer countObtenerDocumentosPorPeriodo(@GenericWhere(element = "d.estado") Boolean estado,
                                             @GenericWhere(element = ":titulo", optional = true) String titulo,
                                             @GenericWhere(element = ":descripcion", optional = true) String descripcion,
                                             @GenericWhere(element = "d.tipoDocumento.id", optional = true) Long tipoDocumentoId,
                                             @GenericWhere(element = "d.tipoCorporacion.id", optional = true) Long tipoCorporacionId,
                                             @GenericWhere(element = ":periodoInicial") Date periodoInicial,
                                             @GenericWhere(element = ":periodoFinal") Date fechaFinal,
                                             @GenericWhere(element = ":userId") Long userId);

    @GenericQuery(query = "select distinct d.parentDocument.id from DocumentoBO d, UserDocumentVisibilityBO vd where d.tipoDocumento.id = vd.tipoDocumentoBO.id and vd.userBO.id = :userId " +
            "and d.fechaCreacion >= :periodoInicial and d.fechaCreacion <= :periodoFinal and (lower(d.titulo) like :titulo or lower(d.descripcion) like :descripcion) order by d.parentDocument.id")
    List<Long> obtenerDocumentosPorPeriodo(@GenericWhere(element = "d.estado") Boolean estado,
                                                  @GenericWhere(element = ":titulo", optional = true) String titulo,
                                                  @GenericWhere(element = ":descripcion", optional = true) String descripcion,
                                                  @GenericWhere(element = "d.tipoDocumento.id", optional = true) Long tipoDocumentoId,
                                                  @GenericWhere(element = "d.tipoCorporacion.id", optional = true) Long tipoCorporacionId,
                                                  @GenericWhere(element = ":periodoInicial") Date periodoInicial,
                                                  @GenericWhere(element = ":periodoFinal") Date fechaFinal,
                                                  @GenericWhere(element = ":userId") Long userId,
                                                  @GenericWhere(element = "*first") int first,
                                                  @GenericWhere(element = "*max") int max);


    @GenericQuery(query = "select d from DocumentoBO d, UserDocumentVisibilityBO vd where d.tipoDocumento.id = vd.tipoDocumentoBO.id " +
            "and vd.userBO.id = :userId and d.id in :idList order by d.fechaCreacion desc")
    List<DocumentoBO> obtenerDocumentosRecientes(@GenericWhere(element = "d.estado") Boolean estado,
                                                 @GenericWhere(element = "*max") Integer max,
                                                 @GenericWhere(element = ":userId") Long userId,
                                                 @GenericWhere(element = ":idList") List<Integer> idList);


    @GenericQuery(query = "select count(*) from DocumentoBO d where d.estado = true")
    Integer getVersionCount(@GenericWhere(element = "d.parentDocument.id") Long parentDocumentId);

    @GenericQuery(query = "select max(id) from DocumentoBO doc where doc.estado = true group by doc.parentDocument")
    List<Integer> getDocumentIdListByLastVersion();

    @GenericQuery(query = "select id from DocumentoBO doc where doc.estado = true and doc.parentDocument.id = :parentId ")
    List<Long> getDocumentIdListByParent(@GenericWhere(element = ":parentId") Long parentDocumentId);

    @GenericQuery(query = "select d from DocumentoBO d, UserDocumentVisibilityBO vd where d.estado = true and d.tipoDocumento.id = vd.tipoDocumentoBO.id " +
            "and vd.userBO.id = :userId and d.parentDocument.id = :parentId order by d.fechaCarga desc")
    List<DocumentoBO> obtenerListadoVersiones(@GenericWhere(element = ":parentId") Long parentDocumentId,
                                              @GenericWhere(element = ":userId") Long userId);


    @GenericQuery(query = "select count(*) from DocumentoBO doc where doc.estado = true")
    Integer getRepeatedTitleCount(@GenericWhere(element = "doc.titulo") String title,
                                  @GenericWhere(element = "doc.parentDocument.id",
                                          optional = true, op = GenericOp.NE) Long parentId);

    @GenericQuery(query = "select doc from DocumentoBO doc where doc.estado = true order by doc.id desc", max = 1)
    DocumentoBO getLastVersionForParentId(@GenericWhere(element = "doc.parentDocument.id") Long parentDocumentId);

    @GenericQuery(query = "select doc.parentDocument.id, max(doc.version) from DocumentoBO doc where doc.parentDocument.id in (:ids) " +
    					  "and doc.estado = true group by doc.parentDocument.id ")
    List<Object> getLastVersionListMaxVersion(@GenericWhere(element = ":ids") List<Long> parentDocumentId);

    @GenericQuery(query = "select d from DocumentoBO d where d.id in(:ids) and d.estado = true order by d.titulo desc")
    List<DocumentoBO> obtenerDocByIdsTitDes(@GenericWhere(element = ":ids") List<Long> ids);
    
    @GenericQuery(query = "select d from DocumentoBO d where d.id in(:ids) and d.estado = true order by d.fechaCreacion desc")
    List<DocumentoBO> obtenerDocByIdsFecCreaDesc(@GenericWhere(element = ":ids") List<Long> ids);

    @GenericQuery(query = "select d from DocumentoBO d where d.id in(:ids) and d.estado = true order by d.titulo asc")
    List<DocumentoBO> obtenerDocByIdsTitAsc(@GenericWhere(element = ":ids") List<Long> ids);


    @GenericQuery(query = "select YEAR(d.fechaCarga) from DocumentoBO d where d.estado = true and d.tipoCorporacion.id = :idCorporacion " +
            "and d.tipoDocumento.id = :idTipoDocumento order by d.fechaCreacion")
    List<Long> obenterAnnoPorTipoDocumento(@GenericWhere(element = ":idCorporacion") Long idCorporacion,
                                           @GenericWhere(element = ":idTipoDocumento") Long idTipoDocumento);

    @GenericQuery(query = "select d from DocumentoBO d where d.estado = true")
    DocumentoBO obtenerDocumentoByParentMaxVersion(@GenericWhere(element = "d.parentDocument.id") Long parentId,
                                                   @GenericWhere(element = "d.version") Integer version);
    
    @GenericQuery(query = "update DocumentoBO set estado = false where estado = true and id = :id commit")
    String updateDocumentFlgEstado(@GenericWhere(element = ":id") Long docId);
}

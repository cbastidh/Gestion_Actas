package cl.bice.gestionactas.ejb.entity;

import cl.bice.gestionactas.ejb.dao.DocumentoDAO;
import cl.bice.gestionactas.ejb.dao.TipoCorporacionDAO;
import cl.bice.gestionactas.ejb.dao.TipoDocumentoDAO;
import cl.bice.gestionactas.ejb.factory.EJBFactory;
import cl.bice.gestionactas.ejb.vo.DocumentoVO;
import cl.bice.gestionactas.ejb.vo.TipoCorporacionVO;
import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;
import cl.neoptera.core.vo.Identified;
import cl.neoptera.ejb.dao.lock.Lock;
import cl.neoptera.ejb.dao.lock.LockListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Date: 2/16/16
 * Created by Humberto Rojas
 * Birchman Consultores LTDA - Chile - Copyright 2016
 * Project: Gestion Actas
 */

@Entity
@Table(name ="doc_documento")
@Lock(id="id", semaphore = Semaphore.class, precedence = 6)
@EntityListeners({LockListener.class})
@SequenceGenerator(sequenceName="cor_sequence", name="IdGen", allocationSize = 1)
public class DocumentoBO implements Identified<Long> {
    @Id
    @GeneratedValue(generator = "IdGen", strategy = GenerationType.SEQUENCE)
    @Column(name = "doc_id")
    private Long id;

    @Column(name = "flg_estado")
    private Boolean estado = true;

    @ManyToOne()
    @JoinColumn(name = "doc_tipocorporacion_id")
    private TipoCorporacionBO tipoCorporacion;

    @ManyToOne()
    @JoinColumn(name = "flg_tipodocumento_id")
    private TipoDocumentoBO tipoDocumento;

    @Column(name = "nom_titulo")
    private String titulo;

    @Column(length=1000, name = "nom_descripcion")
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fec_fechacarga")
    private Date fechaCarga;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fec_fechacreacion")
    private Date fechaCreacion;

    @Column(name = "cod_tokendocumento")
    private String tokenDocumento;

    @Column(name = "nom_originaldocumentbasename")
    private String originalDocumentBaseName;

    @Column(name = "nom_originaldocumentextension")
    private String originalDocumentExtension;

    @Column(name = "cor_version")
    private Integer version;

    @Column(name = "num_sesion")
    private Long numSesion;

    @ManyToOne()
    @JoinColumn(name = "doc_parentdocument_id")
    private DocumentoBO parentDocument;

    @Override
    public Long getId() {
        return id;
    }

    public DocumentoBO(){

    }

    public DocumentoBO(TipoCorporacionVO tipoCorporacion, TipoDocumentoVO tipoDocumento, DocumentoBO parentDocument){
        TipoDocumentoDAO tipoDocumentoDAO = EJBFactory.getBean(TipoDocumentoDAO.class);
        TipoCorporacionDAO tipoCorporacionDAO = EJBFactory.getBean(TipoCorporacionDAO.class);
        DocumentoDAO documentoDAO = EJBFactory.getBean(DocumentoDAO.class);

        this.tipoCorporacion = tipoCorporacionDAO.get(tipoCorporacion.getId());
        this.tipoDocumento = tipoDocumentoDAO.get(tipoDocumento.getId());
        this.parentDocument = parentDocument;

        if(this.parentDocument != null){
            Integer candidateVersion = documentoDAO.getVersionCount(parentDocument.getId());
            this.version = candidateVersion + 1;
        } else {
            this.parentDocument = this;
            this.version = 1;
        }
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public TipoCorporacionBO getTipoCorporacion() {
        return tipoCorporacion;
    }

    public void setTipoCorporacion(TipoCorporacionBO tipoCorporacion) {
        this.tipoCorporacion = tipoCorporacion;
    }

    public TipoDocumentoBO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoBO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTokenDocumento() {
        return tokenDocumento;
    }

    public void setTokenDocumento(String tokenDocumento) {
        this.tokenDocumento = tokenDocumento;
    }

    public String getOriginalDocumentBaseName() {
        return originalDocumentBaseName;
    }

    public void setOriginalDocumentBaseName(String originalDocumentBaseName) {
        this.originalDocumentBaseName = originalDocumentBaseName;
    }

    public String getOriginalDocumentExtension() {
        return originalDocumentExtension;
    }

    public void setOriginalDocumentExtension(String originalDocumentExtension) {
        this.originalDocumentExtension = originalDocumentExtension;
    }

    public Long getNumSesion() {
        return numSesion;
    }

    public void setNumSesion(Long numSesion) {
        this.numSesion = numSesion;
    }

    public DocumentoVO toVO(){
        DocumentoVO targetParentVO = null;
        if(!Objects.equals(this, this.getParentDocument())){
            targetParentVO = this.getParentDocument().toVO();
        }
        DocumentoVO documentoVO = new DocumentoVO(this.id, this.estado, this.tipoCorporacion.toVO(), this.tipoDocumento.toVO(),
                this.titulo, this.descripcion, this.fechaCarga, this.fechaCreacion, this.tokenDocumento,
                this.originalDocumentBaseName, this.originalDocumentExtension, this.version, targetParentVO, numSesion);

        return documentoVO;
    }

    public DocumentoBO getParentDocument() {
        return parentDocument;
    }

    public void setParentDocument(DocumentoBO parentDocument) {
        this.parentDocument = parentDocument;
    }

    @Override
    public String toString() {
        return "DocumentoBO{" +
                "id=" + id +
                ", estado=" + estado +
                ", tipoCorporacion=" + tipoCorporacion +
                ", tipoDocumento=" + tipoDocumento +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCarga=" + fechaCarga +
                ", fechaCreacion=" + fechaCreacion +
                ", tokenDocumento='" + tokenDocumento + '\'' +
                ", originalDocumentBaseName='" + originalDocumentBaseName + '\'' +
                ", originalDocumentExtension='" + originalDocumentExtension + '\'' +
                ", version=" + version +
                ", parentDocument=" + parentDocument +
                '}';
    }
}

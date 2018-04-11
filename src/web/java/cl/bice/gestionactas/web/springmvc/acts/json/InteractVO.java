package cl.bice.gestionactas.web.springmvc.acts.json;

import cl.bice.gestionactas.ejb.vo.TipoDocumentoVO;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 11-06-16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
public class InteractVO implements Serializable {
    List<TipoDocumentoVO> td;
    List<Long> al;

    public InteractVO() {
    }

    public InteractVO(List<TipoDocumentoVO> td, List<Long> al) {
        this.td = td;
        this.al = al;
    }

    public List<TipoDocumentoVO> getTd() {
        return td;
    }

    public void setTd(List<TipoDocumentoVO> td) {
        this.td = td;
    }

    public List<Long> getAl() {
        return al;
    }

    public void setAl(List<Long> al) {
        this.al = al;
    }
}

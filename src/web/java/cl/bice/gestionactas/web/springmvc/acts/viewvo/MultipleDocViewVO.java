package cl.bice.gestionactas.web.springmvc.acts.viewvo;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 16-06-16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
public class MultipleDocViewVO implements Serializable {
    List<String> documents;

    public MultipleDocViewVO() {
    }

    public MultipleDocViewVO(List<String> documents) {
        this.documents = documents;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }
}

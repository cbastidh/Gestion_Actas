package cl.bice.gestionactas.ejb.type;

/**
 * Date: 3/1/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public enum UCMFieldEnum {
    NAME("Name"),
    DESCRIPTION("Description");
    String fieldName;

    UCMFieldEnum(String fieldName){
        this.fieldName = fieldName;
    }

    public String getUCMFieldName(){
        return fieldName;
    }
}

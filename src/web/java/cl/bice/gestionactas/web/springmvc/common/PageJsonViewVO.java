package cl.bice.gestionactas.web.springmvc.common;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class PageJsonViewVO<T> implements Serializable {
    private Integer total;
    private List<T> rows;

    public PageJsonViewVO(Integer total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageJsonViewVO() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}


package cl.bice.gestionactas.web.springmvc.common;

import java.io.Serializable;

/**
 * Date: 2/6/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class PaginatorVO<T extends SearchCriteria> implements Serializable {
    private String order;
    private Integer limit;
    private Integer offset;
    private String sort;
    private T criteria;

    public PaginatorVO() {
    }

    public PaginatorVO(String order, Integer limit, Integer offset, String sort) {
        this.order = order;
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public T getCriteria() {
        return criteria;
    }

    public void setCriteria(T criteria) {
        this.criteria = criteria;
    }

    @Override
    public String toString() {
        return "PaginatorVO{" +
                "order='" + order + '\'' +
                ", limit=" + limit +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                '}';
    }
}

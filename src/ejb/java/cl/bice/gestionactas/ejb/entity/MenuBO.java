package cl.bice.gestionactas.ejb.entity;

import cl.neoptera.core.vo.Identified;
import cl.neoptera.ejb.dao.lock.Lock;

import javax.persistence.*;

/**
 * Date: 05-11-14.
 * Time: 14:41
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2013
 * Project: controlgestion
 */
@Entity
@Table(name = "men_menu")
@Lock(none = true)
@SequenceGenerator(sequenceName="cor_sequence", name="IdGen", allocationSize = 1)
public class MenuBO implements Identified<Long> {

    @Id
    @GeneratedValue(generator = "IdGen", strategy= GenerationType.SEQUENCE)
    @Column(name="men_id")
    Long id;

    @Column(name = "num_order")
    Long order;

    @Column(name = "nom_label")
    String label;

    @Column(name = "nom_bookmark")
    String bookmark;

    @Column(name = "nom_image")
    String image;

    @Column(name = "cod_privilege")
    String privilege;

    @Column(name = "nom_page")
    String page;

    @Column(name = "num_parentid")
    Long parentId;

    @Column(name = "nom_options")
    String options;

    @Column(name = "nom_params")
    String params;

    @Column(name = "flg_estado")
    Boolean status;

    @Column(name = "nom_boostrapicon")
    String boostrapIcon;

    public MenuBO(){

    }


    public MenuBO(Long id, Long order, String label, String bookmark, String image, String privilege, String page, Long parentId, String options, String params) {
        this.id = id;
        this.order = order;
        this.label = label;
        this.bookmark = bookmark;
        this.image = image;
        this.privilege = privilege;
        this.page = page;
        this.parentId = parentId;
        this.options = options;
        this.params = params;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBoostrapIcon() {
        return boostrapIcon;
    }

    public void setBoostrapIcon(String boostrapIcon) {
        this.boostrapIcon = boostrapIcon;
    }

    @Override
    public String toString() {
        return "MenuBO{" +
                "id=" + id +
                ", order=" + order +
                ", label='" + label + '\'' +
                ", bookmark='" + bookmark + '\'' +
                ", image='" + image + '\'' +
                ", privilege='" + privilege + '\'' +
                ", page='" + page + '\'' +
                ", parentId=" + parentId +
                ", options='" + options + '\'' +
                ", params='" + params + '\'' +
                ", status=" + status +
                ", boostrapIcon='" + boostrapIcon + '\'' +
                '}';
    }
}

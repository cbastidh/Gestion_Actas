package cl.bice.gestionactas.ejb.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public class MenuVO implements Serializable, Comparable<MenuVO>{
    private long order;
    private String bookmark;
    private String label;
    private String image;
    private String privilege;
    private String page;
    private String options;
    private String boostrapIcon;
    private List<MenuVO> submenus = new ArrayList<MenuVO>();

    public MenuVO(long order, String bookmark, String options, String label, String image, String page, String privilege, String boostrapIcon) {
        this.order = order;
        this.bookmark = bookmark;
        this.options = options;
        this.label = label;
        this.image = image;
        this.privilege = privilege;
        this.page = page;
        this.boostrapIcon = boostrapIcon;
    }

    public String getLabel() {
        return label;
    }

    public String getBookmark() {
        return bookmark;
    }

    public String getImage() {
        return image;
    }

    public String getPrivilege() {
        return privilege;
    }

    public String getPage() {
        return page;
    }

    public String getOptions() {
        return options;
    }

    public void sort() {
        Collections.sort(submenus);
        for (MenuVO menu: submenus) {
            menu.sort();
        }
    }

    public String getBoostrapIcon() {
        return boostrapIcon;
    }

    public void setBoostrapIcon(String boostrapIcon) {
        this.boostrapIcon = boostrapIcon;
    }

    public List<MenuVO> getSubmenus() {
        return submenus;
    }

    public void addSubmenu(MenuVO submenu) {
        submenus.add(submenu);
    }

    @Override
    public int compareTo(MenuVO o) {
        return Long.valueOf(order).compareTo(o.order);
    }

    @Override
    public String toString() {
        return "MenuVO{" +
                "order=" + order +
                ", bookmark='" + bookmark + '\'' +
                ", label='" + label + '\'' +
                ", image='" + image + '\'' +
                ", privilege='" + privilege + '\'' +
                ", page='" + page + '\'' +
                ", submenus=" + submenus +
                '}';
    }
}
package cl.bice.gestionactas.ejb.type;

import java.util.Set;
import java.util.TreeSet;

/**
 * Date: 2/3/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: Gestion Actas
 */
public enum RoleEnum {
    VISUALIZADOR(true, "VISUALIZADOR"),
    ADMINISTRATOR(true, "ADMINISTRATOR");

    boolean clientRole;
    String nombre;

    RoleEnum(boolean clientRole, String nombre) {
        this.clientRole = clientRole;
        this.nombre = nombre;
    }

    public static RoleEnum[] defaulRoles() {
        return new RoleEnum[] {ADMINISTRATOR, VISUALIZADOR};
    }

    public static Set<RoleEnum> clientRoles() {
        Set<RoleEnum> roles = new TreeSet<RoleEnum>();
        for (RoleEnum role : values()) {
            if (role.clientRole) {
                roles.add(role);
            }
        }
        return roles;
    }

    public static Set<RoleEnum> hostRoles() {
        Set<RoleEnum> roles = new TreeSet<RoleEnum>();
        for (RoleEnum role : values()) {
            if (!role.clientRole) {
                roles.add(role);
            }
        }
        return roles;
    }

    public boolean isClientRole() {
        return clientRole;
    }

    public String getNombre() {
        return nombre;
    }
}

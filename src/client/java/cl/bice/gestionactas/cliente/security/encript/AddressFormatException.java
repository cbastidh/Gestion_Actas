package cl.bice.gestionactas.cliente.security.encript;

/**
 * Date: 4/20/16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
@SuppressWarnings("serial")
public class AddressFormatException extends Exception {
    public AddressFormatException() {
        super();
    }

    public AddressFormatException(String message) {
        super(message);
    }
}
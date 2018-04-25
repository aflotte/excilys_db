package com.excilys.db.exception;

public class DisconnectionFailedExeption  extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -7860148795286916716L;

    @Override
    public String getMessage() {
        return "Une erreur est survenue lors de la déconnection";
    }

}

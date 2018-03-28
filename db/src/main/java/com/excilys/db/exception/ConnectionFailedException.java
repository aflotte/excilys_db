package com.excilys.db.exception;

public class ConnectionFailedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -2563705096214736334L;

    public String getMessage() {
        return "Une erreur est survenue lors de la connection";
    }  
    
}

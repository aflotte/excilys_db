package com.excilys.db.exception;

public class DAOException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -8622847443778245804L;

    /**
     *
     * @param message le message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     *
     * @param message le message
     * @param cause la cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause la cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
}


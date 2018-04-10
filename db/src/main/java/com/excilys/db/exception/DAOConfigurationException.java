package com.excilys.db.exception;

public class DAOConfigurationException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 3033602991349287068L;

    /**
     *
     * @param message le message
     */
    public DAOConfigurationException(String message) {
        super(message);
    }

    /**
     *
     * @param message le message
     * @param cause la cause
     */
    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause la cause
     */
    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }
}

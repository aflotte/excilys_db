package com.excilys.db.exception;

public class DAOConfigurationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3033602991349287068L;

	public DAOConfigurationException( String message ) {
        super( message );
    }

    public DAOConfigurationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DAOConfigurationException( Throwable cause ) {
        super( cause );
    }
}

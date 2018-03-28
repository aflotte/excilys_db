package com.excilys.db.exception;

public class DAOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8622847443778245804L;

	public DAOException( String message ) {
		super( message );
	}

	public DAOException( String message, Throwable cause ) {
		super( message, cause );
	}

	public DAOException( Throwable cause ) {
		super( cause );
	}
}


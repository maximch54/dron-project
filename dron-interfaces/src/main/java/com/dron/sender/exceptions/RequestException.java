package com.dron.sender.exceptions;

public class RequestException extends DronSenderException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestException(String e) {
		super(e);
	}

	public RequestException(String e, Throwable t) {
		super(e, t);
	}

}

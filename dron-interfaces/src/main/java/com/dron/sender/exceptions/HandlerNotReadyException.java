package com.dron.sender.exceptions;

public class HandlerNotReadyException extends DronSenderException {

	private static final long serialVersionUID = 1L;

	public HandlerNotReadyException(String e) {
		super(e);
	}

	public HandlerNotReadyException(String e, Throwable t) {
		super(e, t);
	}

}
package com.dron.sender.exceptions;

public class DronSenderException extends Exception {

    private static final long serialVersionUID = 1L;

    public DronSenderException(String e) {
        super(e);
    }

    public DronSenderException(String e, Throwable t) {
        super(e, t);
    }

}
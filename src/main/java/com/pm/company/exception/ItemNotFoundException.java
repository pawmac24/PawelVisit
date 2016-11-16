package com.pm.company.exception;

/**
 * Created by pmackiewicz on 2015-10-21.
 */
public class ItemNotFoundException extends Exception {
    private static final long serialVersionUID = 100L;

    public ItemNotFoundException(String message) {
        super(message);
    }
}

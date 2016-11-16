package com.pm.company.exception;

/**
 * Created by pmackiewicz on 2015-10-21.
 */
public class ErrorInformation {
    private String errorMessage;

    public ErrorInformation(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

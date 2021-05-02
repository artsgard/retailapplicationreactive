package com.artsgard.retailapplicationreactive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
public class ResourceMandatoryException extends RuntimeException {

    private static final long serialVersionUID = 4755424345335007900L;;

    public ResourceMandatoryException() {
    }

    public ResourceMandatoryException(String message) {
        super(message);
    }

    public ResourceMandatoryException(String message, Throwable cause) {
        super(message, cause);
    }
}

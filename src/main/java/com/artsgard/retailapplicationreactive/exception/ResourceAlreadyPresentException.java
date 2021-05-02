package com.artsgard.retailapplicationreactive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceAlreadyPresentException extends RuntimeException {

    private static final long serialVersionUID = -8176847701775179891L;;

    public ResourceAlreadyPresentException() {
    }

    public ResourceAlreadyPresentException(String message) {
        super(message);
    }

    public ResourceAlreadyPresentException(String message, Throwable cause) {
        super(message, cause);
    }
}
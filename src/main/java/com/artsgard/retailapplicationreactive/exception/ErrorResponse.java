package com.artsgard.retailapplicationreactive.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

/**
 * REST response object for using JSON parser
 */
//@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements Serializable {
    /*
    private static final long serialVersionUID = 6173424894533268920L;

    private String code;

    private String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "[code=" + code + ", message=" + message + "]";
    }


     */
}


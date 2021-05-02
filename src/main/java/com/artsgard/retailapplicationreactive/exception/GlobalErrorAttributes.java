package com.artsgard.retailapplicationreactive.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest requestAttributes,
                                                  ErrorAttributeOptions includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(
                requestAttributes, includeStackTrace);

        final Throwable error = super.getError(requestAttributes);
        if (error instanceof ResourceNotFoundException) {
            final ResourceNotFoundException notFound = (ResourceNotFoundException) error;
            errorAttributes.put("message", notFound.getMessage());
        }
        return errorAttributes;
    }

}
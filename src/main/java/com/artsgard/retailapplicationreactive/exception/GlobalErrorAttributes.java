package com.artsgard.retailapplicationreactive.exception;

import java.rmi.ServerException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        return assembleError(request);
    }

    private Map<String, Object> assembleError(ServerRequest request) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        Throwable error = getError(request);
        if (error instanceof ServerException) {
            ServerException serverError = (ServerException) error;
            errorAttributes.put("status", serverError.detail.getMessage());
            errorAttributes.put("message", serverError.getMessage());
            errorAttributes.put("cause", serverError.getCause());
            errorAttributes.put("data", serverError.getMessage());
            errorAttributes.put("errors", getError(request));
        } else if (error instanceof ResourceNotFoundException) {
            errorAttributes.put("status", HttpStatus.NOT_FOUND);
            errorAttributes.put("class", error.getClass());
            errorAttributes.put("message", error.getMessage());
        } else if (error instanceof ResourceMandatoryException) {
            errorAttributes.put("status", HttpStatus.BAD_REQUEST);
            errorAttributes.put("class", error.getClass());
            errorAttributes.put("message", error.getMessage());
        } else if (error instanceof ResourceAlreadyPresentException) {
            errorAttributes.put("status", HttpStatus.BAD_REQUEST);
            errorAttributes.put("class", error.getClass());
            errorAttributes.put("message", error.getMessage());
        }
        return errorAttributes;
    }

}

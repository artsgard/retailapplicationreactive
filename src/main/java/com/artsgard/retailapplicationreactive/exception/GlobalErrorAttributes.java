package com.artsgard.retailapplicationreactive.exception;

import java.rmi.ServerException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);
        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("message", "username is required");
        //return map;
        return assembleError(request);
    }

    private Map<String, Object> assembleError(ServerRequest request) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        Throwable error = getError(request);
        if (error instanceof ServerException) {
            errorAttributes.put("code", ((ServerException) error).getCause());
            errorAttributes.put("data", error.getMessage());
        } else {
            errorAttributes.put("code", HttpStatus.I_AM_A_TEAPOT);
            errorAttributes.put("data", "I am a tea pot");
        }
        return errorAttributes;
    }

}

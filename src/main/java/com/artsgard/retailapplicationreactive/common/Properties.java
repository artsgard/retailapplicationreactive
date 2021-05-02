package com.artsgard.retailapplicationreactive.common;

import org.springframework.beans.factory.annotation.Value;

public class Properties {

    //@Value("${properties.common.image-path}")
    //public String imagePath;

    @Value("${server.servlet.context-path}")
    public String applicationContext;

    public String getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(String applicationContext) {
        this.applicationContext = applicationContext;
    }
}


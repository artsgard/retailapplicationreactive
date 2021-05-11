package com.artsgard.retailapplicationreactive.config;

import java.util.Locale;

public class MessageInterpolatorImpl implements javax.validation.MessageInterpolator {
    private final javax.validation.MessageInterpolator defaultInterpolator;

    public MessageInterpolatorImpl(javax.validation.MessageInterpolator interpolator) {
        this.defaultInterpolator = interpolator;
    }

    @Override
    public String interpolate(String messageTemplate, Context context) {
        messageTemplate = messageTemplate.toUpperCase();
        System.out.println("<<<<<<<<<<<<<<<<< " + messageTemplate.toString());
        System.out.println("<<<<<<<<<<<<<<<<< " + messageTemplate.isEmpty());
        return defaultInterpolator.interpolate(messageTemplate, context);
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        messageTemplate = messageTemplate.toUpperCase();
        System.out.println("<<<<<<<<<<<<<<<<< " + messageTemplate.toString());
        System.out.println("<<<<<<<<<<<<<<<<< " + messageTemplate.isEmpty());
        return defaultInterpolator.interpolate(messageTemplate, context, locale);
    }
}

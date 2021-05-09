package com.artsgard.retailapplicationreactive.handler;

import com.artsgard.retailapplicationreactive.kafka.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class CompanyRouter {

    @Bean
    public RouterFunction<ServerResponse> kafka(KafkaConsumer consumer) {
        return RouterFunctions.route()
                .GET("/beer/v2/kafka-product", consumer::getPayload)
                .GET("/beer/v2/kafka-products", consumer::getListPayload)
                //.GET("/beer/v2/kafka-stream", consumer::getStream)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> route(CompanyHandler companyHandler) {
        return RouterFunctions.route(GET("/beer/v2/company").and(accept(MediaType.APPLICATION_JSON)), companyHandler::listCompanies)
                .andRoute(GET("/beer/v2/company/{id}").and(accept(MediaType.APPLICATION_JSON)), companyHandler::getCompany)
                .andRoute(POST("/beer/v2/company").and(accept(MediaType.APPLICATION_JSON)).and(contentType(MediaType.APPLICATION_JSON)), companyHandler::createCompany)
                .andRoute(PUT("/beer/v2/company/{id}").and(accept(MediaType.APPLICATION_JSON)).and(contentType(MediaType.APPLICATION_JSON)), companyHandler::updateCompany)
                .andRoute(DELETE("/beer/v2/company/{id}"), companyHandler::deleteCompany);
    }
}

package com.artsgard.retailapplicationreactive.web;

import com.artsgard.retailapplicationreactive.entity.Company;
import com.artsgard.retailapplicationreactive.exception.ValidationRequestHandler;
import com.artsgard.retailapplicationreactive.service.CompanyService;
import reactor.core.publisher.Mono;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class CompanyHandler {

    private final CompanyService companyService;
    private final ValidationRequestHandler validationHandler;

    public CompanyHandler(CompanyService companyService, ValidationRequestHandler validationHandler) {
        this.validationHandler = validationHandler;
        this.companyService = companyService;
    }

    public Mono<ServerResponse> listCompanies(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(companyService.getCompanies(), Company.class));
    }

    public Mono<ServerResponse> getCompany(ServerRequest request) {
        long id = Long.valueOf(request.pathVariable("id"));
        return companyService.getCompanyById(id)
                        .flatMap(comp -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(comp));
    }

    public Mono<ServerResponse> createCompany(ServerRequest request) {

        Mono<Company> body = request.bodyToMono(Company.class);
/*
        Mono<Company> body = validationHandler.requireValidBody(result -> {
            return result.then(Company.class);
        });

*/
        Mono<Company> comp = body.flatMap(companyService::createCompany);

        return comp.flatMap(result -> ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(result));
    }

    public Mono<ServerResponse> updateCompany(ServerRequest request) {
         return companyService.updateCompany(Long.valueOf(request.pathVariable("id")), request.bodyToMono(Company.class))
                         .flatMap(comp -> ServerResponse.ok()
                         .contentType(APPLICATION_JSON)
                         .bodyValue(comp));
    }

    public Mono<ServerResponse> deleteCompany(ServerRequest request) {
        return companyService.deleteCompany(Long.valueOf(request.pathVariable("id"))).then(Mono.empty());
    }

}

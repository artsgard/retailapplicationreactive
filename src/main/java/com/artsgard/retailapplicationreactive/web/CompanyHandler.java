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
        return companyService.getCompanyById(Long.valueOf(request.pathVariable("id")))
                        .flatMap(comp -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(comp));
    }

    public Mono<ServerResponse> createCompany2(ServerRequest request) {
        return request.bodyToMono(Company.class)
                .flatMap(companyService::createCompany)
                    .flatMap(result -> ok()
                    .contentType(APPLICATION_JSON)
                    .bodyValue(result));
    }

    public Mono<ServerResponse> createCompany(ServerRequest request) {
        return validationHandler.requireValidBody(body -> {
            Mono<Company> compMono = body.flatMap(comp -> Mono.just(comp));

            return compMono.flatMap(companyService::createCompany)
                    .flatMap(comp -> ok()
                            .contentType(APPLICATION_JSON)
                            .bodyValue(comp));
        }, request, Company.class);
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

package com.artsgard.retailapplicationreactive.handler;

import com.artsgard.retailapplicationreactive.entity.Company;
import com.artsgard.retailapplicationreactive.exception.ResourceMandatoryException;
import com.artsgard.retailapplicationreactive.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.*;
import com.artsgard.retailapplicationreactive.exception.ResourceNotFoundException;
import reactor.core.publisher.Flux;

@Component
public class CompanyHandler {

    private final CompanyRepository companyRepo;

    public CompanyHandler(CompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    public Mono<ServerResponse> getCompany(ServerRequest request) {
        long id = Long.valueOf(request.pathVariable("id"));
        return companyRepo.findById(id)

                .flatMap(s -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(s))
                .switchIfEmpty(notFound().build());
                //.onErrorResume(e -> Mono.error(new ResourceMandatoryException("name is mandatory", e)));

    }

    public Mono<ServerResponse> getCompany2(ServerRequest request) {
        long id = Long.valueOf(request.pathVariable("id"));
        return companyRepo.findById(id).flatMap(comp -> {
            if (false) {
                return ok()
                        .contentType(APPLICATION_JSON)
                        .body(comp, Company.class);
            } else {
                return Mono.error(new ResourceNotFoundException("No resource found"));
            }
        });
    }

    public Mono<ServerResponse> listCompanies(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(companyRepo.findAll(), Company.class));
    }

    public Mono<ServerResponse> createCompany(ServerRequest request) {
        return request.bodyToMono(Company.class)
                .flatMap(post -> companyRepo.save(post))
                .flatMap(result -> ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(result))
                .onErrorResume(error -> {
                    if(error instanceof RuntimeException) {
                        return status(500).build();
                    }
                    return badRequest().build();
                });
    }

    public Mono<ServerResponse> updateCompany(ServerRequest request) {
         return request.bodyToMono(Company.class)
                .flatMap(p ->
                    companyRepo.findById(Long.parseLong(request.pathVariable("id"))).map(c -> {
                        if (p.getCompanyName() != null) c.setCompanyName(p.getCompanyName());
                        if (p.getCompanyRef() != null) c.setCompanyRef(p.getCompanyRef());
                        if (p.getDescription() != null) c.setDescription(p.getDescription());
                        return  companyRepo.save(c);
                    })).flatMap(r -> ok()
                         .body(r, Company.class))
                 .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> deleteCompany(ServerRequest request) {
        return companyRepo
                .findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(p -> noContent().build(companyRepo.delete(p)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> helloGreeting4(ServerRequest request) {

        return Mono.just("Hello, " + request.queryParam("name").get())
                .onErrorReturn("Hello Stranger")
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s));
    }

    public Mono<ServerResponse> helloGreeting3(ServerRequest request) {

        return Mono.just("Hello, " + request.queryParam("name").get())
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s))
                .onErrorResume(e -> Mono.just("Error " + e.getMessage())
                        .flatMap(s -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(s)));
    }

    public Mono<ServerResponse> helloGreeting(ServerRequest request) {

        return Mono.just("Hello, " + request.queryParam("name").get())
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s))
                .onErrorResume(e -> Mono.just("Error " + e.getMessage())
                        .flatMap(s -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(s)));
    }

    public Mono<ServerResponse> helloGreeting5(ServerRequest request) {

        return ServerResponse.ok()
                .body(sayHello(request)
                        .onErrorResume(e -> Mono.error(new ResourceNotFoundException("name is mandatory", e))), String.class);
    }

    private Mono<String> sayHello(ServerRequest request) {
        return Mono.just("Hello, " + request.queryParam("name").get());
    }

}

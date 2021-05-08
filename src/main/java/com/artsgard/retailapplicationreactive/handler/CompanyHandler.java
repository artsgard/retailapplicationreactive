package com.artsgard.retailapplicationreactive.handler;

import com.artsgard.retailapplicationreactive.entity.Company;
import com.artsgard.retailapplicationreactive.exception.ResourceMandatoryException;
import com.artsgard.retailapplicationreactive.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
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

import java.util.Optional;

@Component
public class CompanyHandler {

    private final CompanyRepository companyRepo;

    public CompanyHandler(CompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    public Mono<ServerResponse> getCompany(ServerRequest request) {
        long id = Long.valueOf(request.pathVariable("id"));
        return companyRepo.findById(id)
                .switchIfEmpty( Mono.error(new ResourceNotFoundException("Application Not Found")))
                .flatMap(s -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(s));
    }

    private Mono<ServerResponse> errorNotFoundMono(){
        return Mono.error(new ResourceNotFoundException("bla bla"));
    }

    public Mono<ServerResponse> getCompany2(ServerRequest request) {
        long id = Long.valueOf(request.pathVariable("id"));
        return companyRepo.findById(id).flatMap(comp -> {
            if (false) {
                return ok()
                        .contentType(APPLICATION_JSON)
                        .body(comp, Company.class);
            } else {
                return Mono.error(new ResourceNotFoundException("No company found with id: " + id));
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
                    })).flatMap(r -> ServerResponse.badRequest()
                         .body(r, Company.class))
                         .onErrorResume(e -> Mono.error(new ResourceNotFoundException("The company is not present", e)));
    }

    public Mono<ServerResponse> deleteCompany(ServerRequest request) {
        return companyRepo
                .findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(p -> noContent().build(companyRepo.delete(p)))
                .onErrorResume(e -> Mono.error(new ResourceNotFoundException("The company is not present", e)));
    }


    // tryout code
    public Mono<ServerResponse> helloGreeting33(ServerRequest request) {

        return Mono.just("Hello, " + request.queryParam("name").get())
                .onErrorReturn("Hello Stranger")
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s));
    }

    public Mono<ServerResponse> helloRequest3(ServerRequest request) {

        return Mono.just("Hello, " + request.queryParam("name").get())
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s))
                .onErrorResume(e -> Mono.just("Error " + e.getMessage())
                        .flatMap(s -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(s)));
    }

    public Mono<ServerResponse> helloRequest(ServerRequest request) {

        return Mono.just("Hello, " + request.queryParam("name").get())
                .onErrorResume(e -> Mono.error(new ResourceNotFoundException("Message is required!", e)))
                .flatMap(s -> ok()
                        .contentType(TEXT_PLAIN)
                        .bodyValue(s));
    }

    public Mono<ServerResponse> helloRequest5(ServerRequest request) {

        return ServerResponse.ok()
                .body(sayHello(request)
                        .onErrorResume(e -> Mono.error(new ResourceNotFoundException("name is mandatory", e))), String.class);
    }

    public Mono<ServerResponse> helloRequest7(ServerRequest request) {
        return Mono.just("Hello, " + request.queryParam("name"))
                .onErrorResume(e -> Mono.error(new ResourceNotFoundException("Message is required!", e)))
                .flatMap(s -> ok()
                        .contentType(TEXT_PLAIN)
                        .bodyValue(s));

    }

    public Mono<ServerResponse> testRequest(ServerRequest request) {
        return Mono.defer(() -> getResponseMessage(request))
                .onErrorResume(e -> Mono.error(new ResourceNotFoundException("Message is required!", e)))
                .flatMap(respText -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(respText));

    }

    private Mono<String> getResponseMessage(ServerRequest request) {
        Optional<String> name = request.queryParam("name");

        return Mono.just("Test " + name.get() + "!");
    }

    private Mono<String> sayHello(ServerRequest request) {
        Optional<String> hello = request.queryParam("name");

        return Mono.just("Test " + hello.get() + "!");

    }

}

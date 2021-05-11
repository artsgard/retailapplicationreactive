package com.artsgard.retailapplicationreactive.exception;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import javax.validation.Validator;
import java.util.function.Function;

@Component
public class ValidationRequestHandler {

    private final Validator validator;

    public ValidationRequestHandler(Validator validator) {
        this.validator = validator;
    }

    public <BODY> Mono<ServerResponse> requireValidBody(
            Function<Mono<BODY>, Mono<ServerResponse>> block,
            ServerRequest request, Class<BODY> bodyClass) {

        return request
                .bodyToMono(bodyClass)
                .flatMap(
                        body -> validator.validate(body).isEmpty()
                                ? block.apply(Mono.just(body))
                                : ServerResponse.unprocessableEntity().build()
                );
    }
}
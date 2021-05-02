package com.artsgard.retailapplicationreactive.kafka;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.artsgard.retailapplicationreactive.entity.Company;
import com.artsgard.retailapplicationreactive.entity.Product;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private Mono<ServerResponse> payload = null;
    private Mono<ServerResponse> listPayload = null;


    @KafkaListener(topics = "product-topic2", groupId = "groupid", containerFactory = "productKafkaListenerContainerFactory")
    public void receive(Product product) {
        LOGGER.info("received payload='{}'", product.toString());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<Product " + product.toString());
        setPayload(Mono.just(product));
        latch.countDown();
    }

    @KafkaListener(topics = "product-list-topic2", groupId = "grouplistid", containerFactory = "productsKafkaListenerContainerFactory")
    //public void receive(ConsumerRecord<?, ?> consumerRecord) {
    public void receiveList(List<Product> products) {
        LOGGER.info("received payload='{}'", products.toString());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<List " + products.toString());
        setListPayload(Flux.fromIterable(products));
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public Mono<ServerResponse> getPayload(ServerRequest request) {
        return payload;
    }

    private void setPayload(Mono<Product> payload) {

        this.payload = payload.flatMap(comp ->
                ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(comp), Company.class))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getListPayload(ServerRequest request) {
        return listPayload;
    }

    private void setListPayload(Flux<Product> listPayload) {

        this.listPayload = ok().contentType(APPLICATION_JSON).body(fromPublisher(listPayload, Product.class));
    }

}

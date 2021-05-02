package com.artsgard.retailapplicationreactive.repository;

import com.artsgard.retailapplicationreactive.entity.Company;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author WillemDragstra
 * https://piotrminkowski.com/2018/10/18/introduction-to-reactive-apis-with-postgres-r2dbc-spring-data-jdbc-and-spring-webflux/
 * https://github.com/mirromutth/r2dbc-mysql
 * https://docs.microsoft.com/es-es/azure/developer/java/spring-framework/configure-spring-data-r2dbc-with-azure-mysql
 *
 */
@Repository
public interface CompanyRepository extends ReactiveCrudRepository<Company, Long> {

    @Query("SELECT * FROM COMPANY")
    Flux<Company> findAllComp();

    @Query("SELECT * FROM COMPANY WHERE ID = :id")
    Mono<Company> findCompById(Long id);
}

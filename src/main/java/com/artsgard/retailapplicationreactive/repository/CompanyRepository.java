package com.artsgard.retailapplicationreactive.repository;

import com.artsgard.retailapplicationreactive.entity.Company;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 *
 * @author WillemDragstra
 *
 */
@Repository
public interface CompanyRepository extends ReactiveCrudRepository<Company, Long> {

    @Query("SELECT * FROM COMPANY WHERE companyRef = :ref")
    Mono<Company> getCompByRef(String ref);

    Mono<Boolean> existsByCompanyRef(String name);
    Mono<Boolean> existsByCompanyName(String name);
    Mono<Company> findCompanyByCompanyRef(String ref);

    @Query("SELECT * FROM COMPANY")
    Flux<Company> findAllComp();

    @Query("SELECT * FROM COMPANY WHERE ID = :id")
    Mono<Company> findCompById(Long id);
}

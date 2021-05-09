package com.artsgard.retailapplicationreactive.service;

import com.artsgard.retailapplicationreactive.entity.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CompanyService {
        Flux<Company> getCompanies();

        Mono<Company> getCompanyById(Long companyId);

        Mono<Company> createCompany(Company company);

        //Mono<Company> createCompany(final Company company);

        Mono<Company> updateCompany(Long companyId, final Mono<Company> companyMono);

        Mono<ServerResponse> deleteCompany(final Long id);
}

package com.artsgard.retailapplicationreactive.service;

import com.artsgard.retailapplicationreactive.entity.Company;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CompanyService {
        Flux<Company> getCompanies();

        Mono<Company> getCompanyById(Long companyId);

        Mono<Company> createCompany(final Mono<Company> companyMono);

        //Mono<Company> createCompany(final Company company);

        Mono<Company> updateCompany(Long companyId, final Mono<Company> companyMono);

        Mono<Void> deleteCompany(final Long id);
}

package com.artsgard.retailapplicationreactive.serviceImpl;

import com.artsgard.retailapplicationreactive.entity.Company;
import com.artsgard.retailapplicationreactive.exception.ResourceAlreadyPresentException;
import com.artsgard.retailapplicationreactive.exception.ResourceNotFoundException;
import com.artsgard.retailapplicationreactive.repository.CompanyRepository;
import com.artsgard.retailapplicationreactive.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepo;

    public Flux<Company> getCompanies() {
        return this.companyRepo.findAll();
    }

    public Mono<Company> getCompanyById(Long id) {
        return this.companyRepo.findById(id)
                .switchIfEmpty( Mono.error(new ResourceNotFoundException("Company Not Found")));
    }

    public Mono<Company> createCompany(Company company) {

        return this.companyRepo.existsByCompanyRef(company.getCompanyRef()).flatMap(exist -> {
            if (exist) {
                return Mono.error(new ResourceAlreadyPresentException("The Product ref allready esists"));
            } else {
                return companyRepo.save(company);
            }

        });

    }

    public Mono<Company> updateCompany(Long id, final Mono<Company> companyMono) {
        return this.companyRepo.findById(id)
                .flatMap(p -> companyMono.map(u -> {
                    p.setCompanyName(u.getCompanyName());
                    p.setCompanyRef(u.getCompanyRef());
                    p.setDescription(u.getDescription());
                    return p;
                }))
                .flatMap(p -> this.companyRepo.save(p))
                .switchIfEmpty( Mono.error(new ResourceNotFoundException("Company Not Found")));
    }

    public Mono<ServerResponse> deleteCompany(final Long id) {

         return companyRepo.findCompById(id).flatMap(comp -> ServerResponse.noContent().build(companyRepo.delete(comp)))
                 .switchIfEmpty( Mono.error(new ResourceNotFoundException("Company Not Found")));
    }

}

package com.artsgard.retailapplicationreactive.serviceImpl;

import com.artsgard.retailapplicationreactive.entity.Company;
import com.artsgard.retailapplicationreactive.repository.CompanyRepository;
import com.artsgard.retailapplicationreactive.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepo;

    public Flux<Company> getCompanies() {
        return this.companyRepo.findAll();
    }

    public Mono<Company> getCompanyById(Long id) {
        return this.companyRepo.findById(id);
    }

    public Mono<Company> createCompany(final Mono<Company> companyMono) {
        return companyMono.flatMap(comp -> this.companyRepo.save(comp));
    }

    public Mono<Company> updateCompany(Long id, final Mono<Company> companyMono) {
        return this.companyRepo.findById(id)
                .flatMap(p -> companyMono.map(u -> {
                    p.setCompanyName(u.getCompanyName());
                    p.setCompanyRef(u.getCompanyRef());
                    p.setDescription(u.getDescription());
                    return p;
                }))
                .flatMap(p -> this.companyRepo.save(p));
    }

    public Mono<Void> deleteCompany(final Long id) {
        return this.companyRepo.deleteById(id);
    }

}

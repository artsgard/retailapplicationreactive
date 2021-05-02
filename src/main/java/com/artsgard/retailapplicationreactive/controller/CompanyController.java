package com.artsgard.retailapplicationreactive.controller;

import com.artsgard.retailapplicationreactive.entity.Company;
import com.artsgard.retailapplicationreactive.service.CompanyService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * willem dragstra
 */
@RestController
@RequestMapping(value = "/beer",  produces = "application/json")
public class CompanyController {

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private CompanyService companyService;

    @GetMapping("/company")
    public Flux<Company> allCompanies() {
        return companyService.getCompanies().take(20);
    }
    
    @GetMapping("/company/{id}")
    public Mono<ResponseEntity<Company>> getProductById(@PathVariable Long id) {
        return this.companyService.getCompanyById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/company", consumes = "application/json")
    public Mono<Company> createCompany(@RequestBody Mono<Company> companyMono) {
        return this.companyService.createCompany(companyMono);
    }
    
    @PutMapping(path = "/company/{id}", produces = "application/json", consumes = "application/json")
    public Mono<Company> updateCompany(@PathVariable Long id, @RequestBody Mono<Company> companyMono) {
        return this.companyService.updateCompany(id, companyMono);
    }
    
     @DeleteMapping(path = "/company/{id}")
     public Mono<Void> deleteCompany(@PathVariable Long id) {
        return this.companyService.deleteCompany(id);
     }

}

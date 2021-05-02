package com.artsgard.retailapplicationreactive.repository;

import com.artsgard.retailapplicationreactive.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author WillemDragstra
 *
 */
@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
   
}

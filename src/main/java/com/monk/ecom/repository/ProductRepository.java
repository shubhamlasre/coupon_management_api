package com.monk.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monk.ecom.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}

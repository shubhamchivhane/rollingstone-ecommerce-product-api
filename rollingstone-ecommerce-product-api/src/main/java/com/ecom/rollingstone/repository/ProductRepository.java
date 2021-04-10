package com.ecom.rollingstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.rollingstone.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
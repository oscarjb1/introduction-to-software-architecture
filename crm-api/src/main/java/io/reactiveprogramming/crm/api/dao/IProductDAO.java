package io.reactiveprogramming.crm.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.reactiveprogramming.crm.entity.Product;

@Repository
public interface IProductDAO extends JpaRepository<Product, Long>{

}

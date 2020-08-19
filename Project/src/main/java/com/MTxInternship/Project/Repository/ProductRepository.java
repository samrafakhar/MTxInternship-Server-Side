package com.MTxInternship.Project.Repository;

import com.MTxInternship.Project.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductID(UUID id);
    Product findByProductName(String id);

    void deleteByProductID(UUID id);

    //void deleteById(UUID id);
}

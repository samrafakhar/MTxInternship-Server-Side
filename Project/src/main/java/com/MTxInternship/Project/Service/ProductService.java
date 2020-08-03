package com.MTxInternship.Project.Service;

import com.MTxInternship.Project.Model.Product;
import com.MTxInternship.Project.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    public Product getProductByID(UUID id) {
        return productRepository.findByProductID(id);
    }
    public void addProduct (Product u) {
        productRepository.save(u);
    }
    public void updateProduct (Product u, UUID id) {
        productRepository.save(u);
    }
    public void deleteProduct (UUID id) {
        productRepository.deleteByProductID(id);
    }
}

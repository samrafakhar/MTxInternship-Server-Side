package com.MTxInternship.Project.API;

import com.MTxInternship.Project.Model.Product;
import com.MTxInternship.Project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.GET, value = "/Products")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @RequestMapping(method= RequestMethod.GET, value ="/Products/{id}")
    public Product getProductByID(@PathVariable UUID id) {
        return productService.getProductByID(id);
    }

    @RequestMapping(method= RequestMethod.POST, value = "/Products")
    public void addProduct (@RequestBody Product u) {
        productService.addProduct(u);
    }

    @RequestMapping(method= RequestMethod.PUT, value ="/Products/{id}")
    public void updateProduct (@RequestBody Product u, @PathVariable UUID id) {
        productService.updateProduct(u, id);
    }

    @RequestMapping(method= RequestMethod.DELETE, value = "/Products/{id}")
    public void deleteProduct (@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
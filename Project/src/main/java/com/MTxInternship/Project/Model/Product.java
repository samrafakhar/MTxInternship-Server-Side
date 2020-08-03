package com.MTxInternship.Project.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID productID;

    private String productName;

    public Product() {
    }

    public Product(UUID productId, String productName) {
        this.productID = productId;
        this.productName = productName;
    }

    public UUID getProductId() {
        return productID;
    }

    public void setProductId(UUID productId) {
        this.productID = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductID() {
        return productID;
    }

    public void setProductID(UUID productID) {
        this.productID = productID;
    }
}

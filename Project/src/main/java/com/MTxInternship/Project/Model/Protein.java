package com.MTxInternship.Project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Protein {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID proteinId;
    private String proteinName;

    @ManyToOne(fetch = FetchType.EAGER)
    ///@JsonIgnore
    private Product product;

    public Protein() {
    }

    public Protein(UUID proteinId, String proteinName, UUID pid) {
        super();
        this.proteinId = proteinId;
        this.proteinName = proteinName;
        this.product=new Product(pid,"");
    }

    public UUID getProteinId() {
        return proteinId;
    }

    public void setProteinId(UUID proteinId) {
        this.proteinId = proteinId;
    }

    public String getProteinName() {
        return proteinName;
    }

    public void setProteinName(String proteinName) {
        this.proteinName = proteinName;
    }

    public Product getProductType() {
        return product;
    }

    public void setProductType(Product productType) {
        this.product = productType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        product = product;
    }
}


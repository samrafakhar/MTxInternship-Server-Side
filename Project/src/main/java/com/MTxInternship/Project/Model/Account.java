package com.MTxInternship.Project.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Account {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID accountID;

    private String name;
    private String type;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    private String phone;
    private String fax;
    private String website;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL )
    @JoinColumn(name = "ShippingAddressID", referencedColumnName = "addID")
    //@JsonIgnore
    private Address shippingAddress;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL )
    @JoinColumn(name = "BillingAddressID", referencedColumnName = "addID")
    //@JsonIgnore
    private Address billingAddress;
    private String businessType;
    private String productType;
    private String proteinType;

    public Account() {
    }

    public Account(UUID ID, String name, String type, String status, UUID ownerID, String phone, String fax, String website, String businessType, String productType, String proteinType, Address shippingAddress, Address billingAddress) {
        super();
        this.accountID=ID;
        this.name = name;
        this.type = type;
        this.status = status;
        this.owner = new User(ownerID,"","","","","",null);
        this.phone = phone;
        this.fax = fax;
        this.website = website;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.businessType = businessType;
        this.productType = productType;
        this.proteinType = proteinType;
    }

    public UUID getAccountID() {
        return accountID;
    }

    public void setAccountID(UUID ID) {
        this.accountID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProteinType() {
        return proteinType;
    }

    public void setProteinType(String proteinType) {
        this.proteinType = proteinType;
    }


}

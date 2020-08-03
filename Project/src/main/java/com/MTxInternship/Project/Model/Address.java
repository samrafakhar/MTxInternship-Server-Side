package com.MTxInternship.Project.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Address")
public class Address {
    @Id
    @Column(name = "addID", columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID addID;

    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zip;
    private String country;

    @OneToOne(fetch = FetchType.LAZY ,mappedBy = "address", cascade = CascadeType.ALL )
    @JsonIgnore
    private User user;

    public Address() { }

    public UUID getAddID() {
        return addID;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public Address(UUID AddID, String street1, String street2, String city, String state, String zip, String country) {
        super();
        this.addID = AddID;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    public Address(Address a) {
        super();
        this.addID = new UUID(8,8);
        this.street1 = a.street1;
        this.street2 = a.street2;
        this.city = a.city;
        this.state = a.state;
        this.zip = a.zip;
        this.country = a.country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAddID(UUID addID) {
        this.addID = addID;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
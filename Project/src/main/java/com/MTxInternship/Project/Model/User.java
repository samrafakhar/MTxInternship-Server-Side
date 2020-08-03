package com.MTxInternship.Project.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="`User`")
public class User {
    @Id
    @GeneratedValue
    private UUID userID;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL )
    @JoinColumn(name = "AddressID", referencedColumnName = "addID")
    //@JsonIgnore
    private Address address;

    public User(){ }

    public User(UUID userID) {
        this.userID = userID;
    }

    public User(UUID userID, String fName, String lName, String password, String email, String phone, Address Address) {
        super();
        this.userID = userID;
        this.firstName = fName;
        this.lastName = lName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = Address;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() { return password; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public Address getAddress() { return address; }

    public void setAddress(Address Address) {
        this.address = Address;
    }
}

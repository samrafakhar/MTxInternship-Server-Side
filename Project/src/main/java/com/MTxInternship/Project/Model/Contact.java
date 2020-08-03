package com.MTxInternship.Project.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Contact {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID contactID;

    private String firstName;
    private String lastName;
    private String title;
    private String email;
    private String phone;
    private String functionalArea;
    private String tradesFor;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinColumn(name = "AddressID", referencedColumnName = "addID")
    private Address address;
    public void setAddress(Address Address) {
        this.address = Address;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Account account;
    public Contact() { }
    public Contact(UUID ID, String firstName, String lastName, String title, String email, String phone,  String functionalArea, Address address, UUID accID, UUID oid, String tradesfor) {
        super();
        this.contactID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.email = email;
        this.phone = phone;
        this.functionalArea = functionalArea;
        this.address = address;
        this.account=new Account(accID,"","","",oid,"","","","","","",null,null);
        this.tradesFor=tradesfor;
    }

    public UUID getContactID() {
        return contactID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFunctionalArea() {
        return functionalArea;
    }

    public void setAccount(Account ccount) {
        account = ccount;
    }

    public Address getAddress() {
        return address;
    }

    public void setContactID(UUID ID) {
        this.contactID = ID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFunctionalArea(String functionalArea) {
        this.functionalArea = functionalArea;
    }

    public Account getAccount() {
        return account;
    }

    public String getTradesFor() {
        return tradesFor;
    }

    public void setTradesFor(String tradesFor) {
        this.tradesFor = tradesFor;
    }
}

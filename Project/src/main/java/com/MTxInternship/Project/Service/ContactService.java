package com.MTxInternship.Project.Service;
import com.MTxInternship.Project.Model.Contact;
import com.MTxInternship.Project.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;
    public List<Contact> getAccountContacts(UUID ID){
        return contactRepository.findAllByAccount_AccountID(ID);
    }
    public Contact getContactByID(UUID id) {
        return contactRepository.findContactByContactID(id);
    }
    public void addContact (Contact u) {
        contactRepository.save(u);
    }
    public void updateContact (Contact u, UUID id) {
        contactRepository.save(u);
    }
    public void deleteContact (UUID id) {
        contactRepository.deleteByContactID(id);
    }
    public void addContactForCloneAccount(Contact c, UUID id)
    {
        Contact newContact=new Contact(id, c.getFirstName(), c.getLastName(), c.getTitle(), c.getEmail(), c.getPhone(), c.getFunctionalArea(), c.getAddress(), id, id, c.getTradesFor());
        contactRepository.save(newContact);
    }
}

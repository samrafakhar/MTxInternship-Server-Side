package com.MTxInternship.Project.API;
import com.MTxInternship.Project.Model.Account;
import com.MTxInternship.Project.Model.Contact;
import com.MTxInternship.Project.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@RestController
public class ContactController {
    @Autowired
    ContactService contactService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.GET, value = "/Contacts/{id}")
    public List<Contact> getAccountContacts(@PathVariable UUID id){
        return contactService.getAccountContacts(id);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.GET, value = "/viewContact/{id}")
    public Contact getContactByID(@PathVariable UUID id){
        return contactService.getContactByID(id);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.POST, value = "/{uid}/addContact/{aid}")
    public void addContact (@RequestBody Contact u, @PathVariable UUID aid, @PathVariable UUID uid) {
        u.setAccount(new Account(aid,"","","",uid,"","","","","","",null,null));
        contactService.addContact(u);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.PUT, value ="/{uid}/{aid}/editContact/{cid}")
    public void updateContact (@RequestBody Contact u, @PathVariable UUID aid, @PathVariable UUID uid, @PathVariable UUID cid) {
        u.setAccount(new Account(aid,"","","",uid,"","","","","","",null,null));
        contactService.updateContact(u, cid);
    }
    @Transactional
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.DELETE, value = "/deleteContact/{id}")
    public void deleteContact (@PathVariable UUID id) {
        contactService.deleteContact(id);
    }
}

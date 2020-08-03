package com.MTxInternship.Project.API;

import com.MTxInternship.Project.Model.Account;
import com.MTxInternship.Project.Model.Address;
import com.MTxInternship.Project.Model.Contact;
import com.MTxInternship.Project.Model.User;
import com.MTxInternship.Project.Service.AccountService;
import com.MTxInternship.Project.Service.ContactService;
import com.MTxInternship.Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;
    @Autowired
    ContactService contactService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.GET, value = "/viewAccount/{id}")
    public Account getAccountByID(@PathVariable UUID id){
        return accountService.getAccountByID(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.GET, value = "/viewAccount/{id}/ProteinTypes")
    public String getAccountProteinTypesByAccountID(@PathVariable UUID id){
        Account a = accountService.getAccountByID(id);
        System.out.println(a.getProteinType());
        return a.getProteinType();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.GET, value = "/Accounts/{id}")
    public List<Account>findAllByOwner(@PathVariable UUID id){
        User u=userService.getUserByID(id);
        return accountService.findAllByOwner(u);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.POST, value = "/addAccount/{id}")
    public void addAccount (@RequestBody Account u, @PathVariable UUID id) {
        System.out.println(u.getType());
        u.setOwner(new User(id));
        accountService.addAccount(u, id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.POST, value = "/cloneAccount/{id}")
    public void cloneAccount (@RequestBody Account u, @PathVariable UUID id) {
        String s = u.getType();

        Account clonedAccount=new Account();

        if(s.equals("vendor") || s.equals("Vendor"))
            clonedAccount.setType("Customer");
        if(s.equals("customer") || s.equals("Customer"))
            clonedAccount.setType("Vendor");

        clonedAccount.setAccountID(new UUID(8,8));
        clonedAccount.setOwner(new User(id));
        clonedAccount.setName(u.getName());
        clonedAccount.setStatus(u.getStatus());
        clonedAccount.setPhone(u.getPhone());
        clonedAccount.setFax(u.getFax());
        clonedAccount.setWebsite(u.getWebsite());
        clonedAccount.setShippingAddress(new Address(u.getShippingAddress()));
        clonedAccount.setBillingAddress(new Address(u.getBillingAddress()));
        clonedAccount.setBusinessType(u.getBusinessType());
        clonedAccount.setProductType(u.getProductType());
        clonedAccount.setProteinType(u.getProteinType());
        accountService.addAccount(clonedAccount, id);
        List<Contact> contacts = contactService.getAccountContacts(id);
        for(Contact contact : contacts)
        {
            UUID acID = clonedAccount.getAccountID();
            contactService.addContactForCloneAccount(contact, acID);

        }

        System.out.println("account current type "+u.getType());
        System.out.println("account cloned to"+clonedAccount.getType());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.PUT, value ="/{uid}/editAccount/{id}")
    public void updateAccount (@RequestBody Account u, @PathVariable UUID uid) {
        u.setOwner(new User(uid, "","","","","",null));
        accountService.updateAccount(u, uid);
    }

    @Transactional
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method= RequestMethod.DELETE, value = "/deleteAccount/{id}")
    public void deleteAccount (@PathVariable UUID id) {
        accountService.deleteAccount(id);
    }
}

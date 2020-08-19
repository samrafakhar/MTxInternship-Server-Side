package com.MTxInternship.Project.Service;


import com.MTxInternship.Project.Model.Account;
import com.MTxInternship.Project.Model.User;
import com.MTxInternship.Project.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAccounts(){
        List<Account> accounts = new ArrayList<>();
        Iterable<Account> accountsAll = accountRepository.findAll();
        for (Account account : accountsAll) {
            accounts.add(account);
        }
        return accounts;
        //return accountRepository.findAll();
    }

    @PostConstruct
    public void something(){

    }
    public Account getAccountByID(UUID id) {
        return accountRepository.findByAccountID(id);
    }
    public void addAccount (Account u, UUID id) {
        accountRepository.save(u);
    }
    public void updateAccount (Account u, UUID id) { accountRepository.save(u); }
    public void deleteAccount (UUID id) {
        accountRepository.deleteAccountByAccountID(id);
    }

    public List<Account> findAllByOwner(User user){
        Iterable<Account> accountsAll = accountRepository.findAllByOwner(user);
        //for (Account account : accountsAll) {
            //System.out.println(account.getAccountID());
        //}
        return accountRepository.findAllByOwner(user);
    }
}

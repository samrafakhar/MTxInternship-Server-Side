package com.MTxInternship.Project.Repository;

import com.MTxInternship.Project.Model.Account;
import com.MTxInternship.Project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAllByOwner(User user);

    Account findByAccountID (UUID id);

    void deleteAccountByAccountID(UUID id);


    List<Account> findAllByName (String name);
    //Account getOne(UUID id);
}

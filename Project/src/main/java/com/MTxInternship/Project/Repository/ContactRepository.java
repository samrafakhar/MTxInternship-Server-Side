package com.MTxInternship.Project.Repository;

import com.MTxInternship.Project.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findAllByAccount_AccountID(UUID id);

    void deleteByContactID(UUID id);

    Contact findContactByContactID(UUID id);
}

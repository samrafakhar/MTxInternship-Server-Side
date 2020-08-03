package com.MTxInternship.Project.Repository;

import com.MTxInternship.Project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByFirstName(String name);
    public User findByEmail(String email);
    public User findByEmailAndPassword(String email, String password);

    User findByUserID(UUID id);

    void deleteByUserID(UUID id);
}

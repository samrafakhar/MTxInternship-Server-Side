package com.MTxInternship.Project.Service;

import com.MTxInternship.Project.Model.Account;
import com.MTxInternship.Project.Repository.UserRepository;
import com.MTxInternship.Project.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User getUserByID(UUID id) {
        return userRepository.findByUserID(id);
    }
    public void addUser (User u) {
        userRepository.save(u);
    }
    public void updateUser (User newUser, UUID id) {
        userRepository.save(newUser);
    }
    public void deleteUser (UUID id) {
        userRepository.deleteByUserID(id);
    }
    public List<User> getUserByFirstName(String name) {
        return userRepository.findByFirstName(name);
    }

    public User getUserByEmailID(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserByEmailAndPassword(String email, String pwd){
        return userRepository.findByEmailAndPassword(email, pwd);
    }
}

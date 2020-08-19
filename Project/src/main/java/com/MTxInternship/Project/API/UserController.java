package com.MTxInternship.Project.API;

import com.MTxInternship.Project.Model.User;
import com.MTxInternship.Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
        @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @ResponseBody
    public User loginUser(@RequestBody User user) throws Exception {
        String email=user.getEmail();
        String password=user.getPassword();

        User u=null;
        if(email!=null&&password!=null) {
            u=userService.getUserByEmailAndPassword(email, password);
        }
        if(u ==null)
            throw new Exception("email or password incorrect");
        else {
            return u;
        }
    }
    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/Users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @RequestMapping(method= RequestMethod.GET, value ="/Users/{id}")
    public User getUserByID(@PathVariable UUID id) {
        return userService.getUserByID(id);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.POST, value = "/register")
    @ResponseBody
    public void addUser (@RequestBody User u) throws Exception {
        String email=u.getEmail();
        if(email!=null) {
            User userWithEmail = userService.getUserByEmailID(email);
            if (userWithEmail == null) {
                userService.addUser(u);
            }
            else
                throw new Exception("already registered");
        }
    }
    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.PUT, value="/updateUser/{id}")
    public void updateUser(@RequestBody User u, @PathVariable UUID id) {
        userService.updateUser(u, id);
    }
    @RequestMapping(method= RequestMethod.DELETE, value = "/Users/{id}")
    public void deleteUser (@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}

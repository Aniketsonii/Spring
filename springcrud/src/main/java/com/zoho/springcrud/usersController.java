package com.zoho.springcrud;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class usersController {

    @Autowired
    private usersService usersService; 

    @RequestMapping("/users")
    public List<users> getAllUsers() {
        return usersService.getAllUsers();
        
    }

    @RequestMapping("/users/{id}")
    public users getUsers(@PathVariable Integer id) {
        return usersService.getUsers(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public users addUsers(@RequestBody users users){
       return usersService.addUsers(users);


    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public users updateUsers(@RequestBody users users, @PathVariable Integer id){
        return usersService.updateUsers(id, users);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    public Map<String, String> deleteUsers(@PathVariable Integer id){
        usersService.deleteUsers(id);
        Map<String,String> res = new HashMap<String, String>();
        res.put("message", "Record Deleted Successfully");
        return res;
    }
}

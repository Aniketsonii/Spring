package com.zoho.springcrud;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.validation.Valid;

import org.apache.logging.log4j.LogBuilder;
import org.hibernate.internal.util.xml.ErrorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.log4j2.Log4J2LoggingSystem;
import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
// @Component
public class usersController {

    Logger logger = Logger.getLogger(usersController.class.getName());

    @Autowired
    private usersService usersService; 

    public Integer get=100;
    public Integer getusr=159;
    public Integer postusr=189;
    public Integer putusr=76;
    public Integer deleteuser=19;

    @RequestMapping("/users")
    public List<users> getAllUsers() {
        get+=1;
        return usersService.getAllUsers();
        
    }

    @RequestMapping("/users/{id}")
    public Optional<users> getUsers(@PathVariable Integer id) throws UserNotFoundException {
        getusr+=1;
        return usersService.getUsers(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public users addUsers(@RequestBody @Valid users users) throws ConstraintViolationException {
    try{
        postusr+=1;
        return usersService.addUsers(users);
    }catch(ConstraintViolationException e){
        logger.log(Level.WARNING, "Duplicate Users Found");
        throw new ConstraintViolationException("Duplicate Entries Found");
    }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public users updateUsers(@RequestBody @Valid users users, @PathVariable Integer id)throws UserNotFoundException{
        putusr+=1;
        return usersService.updateUsers(id, users);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    public Map<String, String> deleteUsers(@PathVariable Integer id) throws UserNotFoundException{
        deleteuser+=1;
        usersService.deleteUsers(id);
        Map<String,String> res = new HashMap<String, String>();
        res.put("message", "Record Deleted Successfully");
        return res;
    }

    @Scheduled(cron = " 0 15 19 * * ?")
    @RequestMapping(method = RequestMethod.GET,value = "/sendmail")
    public String sendmail() {
        String msg = "No of GET Requests: "+get+"\nNo of GET/User Requests: "+getusr+"\nNo of POST Requests: "+postusr+"\nNo of PUT Requests: "+putusr+"\nNo of DELETE Requests: "+deleteuser+"\nTotal Number of CRUD API calls: ";

        usersService.sendMail("aniketsoni760@gmail.com", "Test Subject", msg);

        return "emailsent";
    }
}

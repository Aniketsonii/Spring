package com.zoho.springcrud;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class usersService {

    private List<users>user = new ArrayList<>(Arrays.asList(
                    new users("aniket","Aniket@gmail.com","DXC"),
                    new users("aadarsh","Aadarsh@gmail.com","DXC"),
                    new users("soheb","soheb@gmail.com","DXC")
        ));
    
    public List<users> getAllUsers(){
        return user;
    }

    public users getUsers(Integer id) {
         return user.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }

    public users addUsers(users users) {
        users usr = new users(users.getName(), users.getEmail(), users.getTeam()); 
        System.out.println(
            users
        );
        user.add(usr);
        return usr;
    }

    public users updateUsers(Integer id, users users) {
        users usr = new users(users.getName(), users.getEmail(), users.getTeam());
        for(int i =0;i<user.size();i++){
            users u = user.get(i);
            if(u.getId().equals(id)){
                user.set(i,usr);
                // return;
            }
        }
        return usr;
    }

    public void deleteUsers(Integer id) {
        user.removeIf(t-> 
        {
            return t.getId().equals(id);
        });
        // return rtr;
    }
}

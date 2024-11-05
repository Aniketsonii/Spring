package com.zoho.springcrud;

import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class usersService {

    @Autowired
    private usersRepository usersRepository;

    // private List<users>user = new ArrayList<>(Arrays.asList(
    //                 new users("aniket","Aniket@gmail.com","DXC"),
    //                 new users("aadarsh","Aadarsh@gmail.com","DXC"),
    //                 new users("soheb","soheb@gmail.com","DXC")
    //     ));
    
    public List<users> getAllUsers(){
        List<users> user = new ArrayList<>();
        usersRepository.findAll()
        .forEach(user::add);
        return user;
    }

    public Optional<users> getUsers(Integer id) {
        //  return user.stream().filter(t -> t.getId().equals(id)).findFirst().get();
        return usersRepository.findById(id);
    }

    public users addUsers(users users) {
        // users usr = new users(users.getName(), users.getEmail(), users.getTeam()); 
        // System.out.println(
        //     users
        // );
        // user.add(usr);
        // return usr;
        users.setId(Math.abs((int) new Date().getTime() + (int) (Math.random() * 500)));
        return usersRepository.save(users);
    }

    public users updateUsers(Integer id, users users) {
        // users usr = new users(users.getName(), users.getEmail(), users.getTeam());
        // for(int i =0;i<user.size();i++){
        //     users u = user.get(i);
        //     if(u.getId().equals(id)){
        //         user.set(i,usr);
        //         // return;
        //     }
        // }
        // return usr;
        users.setId(id);
        return usersRepository.save(users);
    }

    public void deleteUsers(Integer id) {
        // user.removeIf(t-> 
        // {
        //     return t.getId().equals(id);
        // });
        // return rtr;
        usersRepository.deleteById(id);
    }
}

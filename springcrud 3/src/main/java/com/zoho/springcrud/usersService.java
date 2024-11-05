package com.zoho.springcrud;

import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





@Service
public class usersService {

    @Autowired
    private usersRepository usersRepository;
    
    public List<users> getAllUsers(){
        List<users> user = new ArrayList<>();
        usersRepository.findAll()
        .forEach(user::add);
        return user;
    }

    public Optional<users> getUsers(Integer id) throws UserNotFoundException {
        //  return user.stream().filter(t -> t.getId().equals(id)).findFirst().get();
        Optional<users> user = usersRepository.findById(id);
        if (user.isPresent()){
            return user;
        }else{
            throw new UserNotFoundException("User not found with id: "+id);
        }
        
        // return usersRepository.findById(id);
    }

    public users addUsers(users users) throws ConstraintViolationException {
        // users usr = new users(users.getName(), users.getEmail(), users.getTeam()); 
        // System.out.println(
        //     users
        // );
        // user.add(usr);
        // return usr;
            users.setId(Math.abs((int) new Date().getTime() + (int) (Math.random() * 500)));
        // System.out.println(users);
            return usersRepository.save(users);
    }

    public users updateUsers(Integer id, users users) throws UserNotFoundException{
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
        Optional<users> user = usersRepository.findById(id);
        if (user.isPresent()){
            return usersRepository.save(users);
        }else{
            throw new UserNotFoundException("Cannot find user with : "+id+", Invalid Update Request");
        }
    }

    public void deleteUsers(Integer id) throws UserNotFoundException {
        // user.removeIf(t-> 
        // {
        //     return t.getId().equals(id);
        // });
        // return rtr;
        Optional<users> user = usersRepository.findById(id);
        if (user.isPresent()){
            usersRepository.deleteById(id);
        }else{
            throw new UserNotFoundException("Cannot find user with : "+id+", Invalid Delete Request");
        }
    }

    private JavaMailSender javaMailSender;

    public usersService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String toEmail, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("hadyshah1234@gmail.com");

        // mailMessage.setTo("Aniket.soni.760@gmail.com");
        // mailMessage.setSubject("hello from spring");
        // mailMessage.setText("okay bhai chal raha hai");

        // mailMessage.setFrom("hadyshah1234@gmail.com");

        javaMailSender.send(mailMessage);
    }
}

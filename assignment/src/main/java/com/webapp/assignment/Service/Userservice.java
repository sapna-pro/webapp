package com.webapp.assignment.Service;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.webapp.assignment.Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.webapp.assignment.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class Userservice{

    @Autowired
    private UserRepository userRepository;



    public Userservice(){

       // this.userRepository = userRepository;
    }

    public User findUserByEmail(String emailid) {

        return userRepository.findByEmailid(emailid);
    }

    public boolean userExist(String emailid){
        if(userRepository.findByEmailid(emailid) == null){
            return false;
        }else {
            return true;
        }
    }

    public void saveUser(User user) {

        userRepository.save(user);
    }


    public User AuthenticateUser(String emailid,String pass) throws UsernameNotFoundException{

        User user = userRepository.findByEmailid(emailid);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User logged_user=null;

        if(userExist(emailid)){
            System.out.println("user found");

            if(encoder.matches(pass, user.getPassword())){
                logged_user = user;
                System.out.println("user returen");
                return logged_user;
            }

        }else{
            logged_user = null;
            System.out.println("null user returen");
            return logged_user;
        }
        System.out.println("returen statement excecute");
        return logged_user;
//        User user = userRepository.findByEmailid(emailid);
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        if(user == null){
//            user = null;
//            return user;
//        }else{
//            if(encoder.matches(pass, user.getPassword())){
//                return user;
//            }
//        }
//        return user;
    }

    public boolean user_exist(String email){
        if(userExist(email))
            return true;
        return false;
    }


}

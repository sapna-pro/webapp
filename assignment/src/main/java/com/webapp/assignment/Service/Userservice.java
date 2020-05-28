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

        if(user == null){
            user = null;
            return user;
        }else{
            if(encoder.matches(pass, user.getPassword())){
                return user;
            }
        }
        return user;
    }


}

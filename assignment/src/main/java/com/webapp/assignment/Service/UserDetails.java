package com.webapp.assignment.Service;

import com.webapp.assignment.Entity.User;
import com.webapp.assignment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmailid(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return (org.springframework.security.core.userdetails.UserDetails) new MyUserPrincipal(user);
    }
}


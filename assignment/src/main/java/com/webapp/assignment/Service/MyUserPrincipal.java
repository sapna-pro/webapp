package com.webapp.assignment.Service;

import com.webapp.assignment.Entity.User;

public class MyUserPrincipal extends UserDetails {

    private User user;

    public MyUserPrincipal(User user) {
        this.user = user;
    }
}

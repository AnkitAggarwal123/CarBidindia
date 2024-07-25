package com.carbid.demo.service;

import com.carbid.demo.model.User;
import com.carbid.demo.repo.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class customUserService implements UserDetailsService {

    @Autowired
    IUser iUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user1 = iUser.findByEmail(username);

        if(user1 == null){
            throw new UsernameNotFoundException("User does not exist");
        }

        return user1;
    }
}

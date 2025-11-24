package com.carbid.demo.service;

import com.carbid.demo.model.ApproveUser;
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

        ApproveUser approveUser1 = iUser.findByEmail(username);

        if(approveUser1 == null){
            throw new UsernameNotFoundException("User does not exist");
        }

        return approveUser1;
    }
}

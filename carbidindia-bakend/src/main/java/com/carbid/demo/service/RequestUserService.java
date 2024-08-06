package com.carbid.demo.service;

import com.carbid.demo.dto.UserDto;
import com.carbid.demo.model.RequestUser;
import com.carbid.demo.model.User;
import com.carbid.demo.repo.IRequestUser;
import com.carbid.demo.repo.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RequestUserService {

    @Autowired
    IRequestUser iRequestUser;

    @Autowired
    IUser iUser;

    public String createUser(UserDto userDto){

        if (iUser.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        if(iRequestUser.existsByEmail(userDto.getEmail())){
            throw new IllegalArgumentException("Email is already in use");
        }

        RequestUser requestUser = new RequestUser();
        requestUser.setName(userDto.getName());
        requestUser.setEmail(userDto.getEmail());
        requestUser.setLocation(userDto.getLocation());
        requestUser.setPhoneNumber(userDto.getPhoneNumber());
        requestUser.setPassword(passwordEncoder().encode(userDto.getPassword()));
        requestUser.setRole("USER");
        iRequestUser.save(requestUser);
        return "User added successfully";
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }





}

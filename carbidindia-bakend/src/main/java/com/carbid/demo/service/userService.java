package com.carbid.demo.service;

import com.carbid.demo.dto.UserDto;
import com.carbid.demo.model.User;
import com.carbid.demo.repo.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {

    @Autowired
    IUser iUser;

    public String createUser(UserDto userDto) {
        if (iUser.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setLocation(userDto.getLocation());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder().encode(userDto.getPassword()));
        user.setRole("USER");
        iUser.save(user);

        return "User added successfully";
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<User> getAllUser() {
        return iUser.findAll();
    }
}

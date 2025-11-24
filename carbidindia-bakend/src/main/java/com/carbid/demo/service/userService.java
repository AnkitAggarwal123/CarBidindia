package com.carbid.demo.service;

import com.carbid.demo.model.ApproveUser;
import com.carbid.demo.model.RequestUser;
import com.carbid.demo.repo.IRequestUser;
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

    @Autowired
    IRequestUser iRequestUser;


    public String approverUser(Long userId) {

        RequestUser requestUser = iRequestUser.findById(userId).orElseThrow(()-> new RuntimeException("user not found"));

        if(iUser.existsByEmail(requestUser.getEmail())){
            throw new RuntimeException("User already exist");
        }


        ApproveUser approveUser = new ApproveUser();
        approveUser.setEmail(requestUser.getEmail());
        approveUser.setName(requestUser.getName());
        approveUser.setPassword(requestUser.getPassword());
        approveUser.setLocation(requestUser.getLocation());
        approveUser.setRole(requestUser.getRole());
        approveUser.setPhoneNumber(requestUser.getPhoneNumber());
        iUser.save(approveUser);
        iRequestUser.delete(requestUser);
        return "user approved successfully";
    }


    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<ApproveUser> getAllUser() {
        return iUser.findAll();
    }


    public List<RequestUser> getAllRequested() {
        return iRequestUser.findAll();
    }
}

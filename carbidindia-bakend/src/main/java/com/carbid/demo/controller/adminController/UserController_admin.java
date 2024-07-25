package com.carbid.demo.controller.adminController;

import com.carbid.demo.model.User;
import com.carbid.demo.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class UserController_admin {


    @Autowired
    userService userSer;

    @GetMapping("/allusers")
    public List<User> allUser(){
        return userSer.getAllUser();
    }
}

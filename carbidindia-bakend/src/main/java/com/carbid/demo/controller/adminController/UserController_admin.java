package com.carbid.demo.controller.adminController;

import com.carbid.demo.model.RequestUser;
import com.carbid.demo.model.User;
import com.carbid.demo.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/approve/user/{userId}")
    public ResponseEntity<?> approveUser(@PathVariable Long userId){

        try{
            String str = userSer.approverUser(userId);
            return ResponseEntity.ok(str);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something wne wrong");
        }
    }

    @GetMapping("/requested/user")
    public List<RequestUser> getAllRequested(){
        return userSer.getAllRequested();
    }
}

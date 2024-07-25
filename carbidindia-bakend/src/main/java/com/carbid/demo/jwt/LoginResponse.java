package com.carbid.demo.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String jwtToken;

    private String username;
    private List<String> roles;

    private String name;

    private Long id;

    public LoginResponse(String username, List<String> roles, String jwtToken, String name, Long id) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
        this.name = name;
        this.id = id;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}



package com.carbid.demo.controller.genric;

import com.carbid.demo.dto.UserDto;
import com.carbid.demo.jwt.JwtUtils;
import com.carbid.demo.jwt.LoginRequest;
import com.carbid.demo.jwt.LoginResponse;
import com.carbid.demo.model.ApproveUser;
import com.carbid.demo.service.RequestUserService;
import com.carbid.demo.service.customUserService;
import com.carbid.demo.service.userService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController_genric {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    JwtUtils jwtUtils;


    @Autowired
    userService userSer;

    @Autowired
    customUserService customUser;


    private final RequestUserService requestUserService;

    @PostMapping("/create/user")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
        String message = requestUserService.createUser(userDto);
        System.out.println("Response Message: " + message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken, ((ApproveUser) userDetails).getName(),((ApproveUser) userDetails).getId());

        return ResponseEntity.ok(response);
    }



    @PostMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestBody Map<String, String> tokenRequest) {
        String token = tokenRequest.get("token");
        if (!jwtUtils.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired or invalid");
        }

        String username = jwtUtils.getUserNameFromJwtToken(token);
        UserDetails userDetails = customUser.loadUserByUsername(username);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(
                token,
                userDetails.getUsername(),
                roles,
                ((ApproveUser) userDetails).getName(),
                ((ApproveUser) userDetails).getId()
        );

        return ResponseEntity.ok(response);

    }
}

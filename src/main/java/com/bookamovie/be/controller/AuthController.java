package com.bookamovie.be.controller;

import com.bookamovie.be.constraint.ValidPassword;
import com.bookamovie.be.entity.Role;
import com.bookamovie.be.entity.User;
import com.bookamovie.be.mapper.ApiMapper;
import com.bookamovie.be.repository.RoleRepository;
import com.bookamovie.be.repository.UserRepository;
import com.bookamovie.be.service.JwtService;
import com.bookamovie.be.service.UserService;
import com.bookamovie.be.view.UserRequest;
import com.bookamovie.be.view.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final ApiMapper apiMapper;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    @Validated
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) throws Exception {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new Exception("Username is already taken!");
        }
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setSurname(userRequest.getSurname());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Role roles = roleRepository.findByName("ROLE_USER").orElseThrow();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticateUser(@RequestBody UserRequest userRequest) throws Exception {
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(userRequest.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userRequest.getUsername(), userRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        val token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(apiMapper.userResponse(token));
    }
}

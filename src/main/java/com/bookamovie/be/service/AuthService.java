package com.bookamovie.be.service;

import com.bookamovie.be.entity.Role;
import com.bookamovie.be.entity.User;
import com.bookamovie.be.repository.RoleRepository;
import com.bookamovie.be.repository.UserRepository;
import com.bookamovie.be.view.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public User registerUser(UserRequest userRequest) throws Exception{
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
        return user;
    }

    public String authenticateUser(UserRequest userRequest) throws Exception{
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(userRequest.getUsername());
            val authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userRequest.getUsername(), userRequest.getPassword()
            ));
            SecurityContext sc = SecurityContextHolder.createEmptyContext();
            sc.setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        return jwtService.generateToken(userDetails);
    }
}

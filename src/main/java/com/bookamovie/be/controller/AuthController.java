package com.bookamovie.be.controller;

import com.bookamovie.be.mapper.ApiMapper;
import com.bookamovie.be.service.AuthService;
import com.bookamovie.be.view.UserRequest;
import com.bookamovie.be.view.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ApiMapper apiMapper;
    private final AuthService authService;

    @PostMapping("/register")
    @Validated
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) throws Exception {
        authService.registerUser(userRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticateUser(@RequestBody UserRequest userRequest) throws Exception {
        val token = authService.authenticateUser(userRequest);
        return ResponseEntity.ok(apiMapper.userResponse(token));
    }
}

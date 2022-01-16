package com.bookamovie.be.service;

import com.bookamovie.be.entity.User;
import com.bookamovie.be.mapper.UserMapper;
import com.bookamovie.be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();
        return userMapper.userDetails(user);
    }

    public User getUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        val user = getUserByUsername(authentication.getName());
        val id = user.getId();
        return userRepository.findById(id).orElseThrow();
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow();
    }
}

package com.bookamovie.be.configuration;

import com.bookamovie.be.entity.Role;
import com.bookamovie.be.entity.User;
import com.bookamovie.be.repository.RoleRepository;
import com.bookamovie.be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initRoles(){
        return args -> {
            log.info("Preloading " + roleRepository.save(new Role("ROLE_ADMIN")));
            log.info("Preloading " + roleRepository.save(new Role("ROLE_USER")));
        };
    }
}

package com.bookamovie.be.configuration;

import com.bookamovie.be.entity.Role;
import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.User;
import com.bookamovie.be.repository.RoleRepository;
import com.bookamovie.be.repository.SeatRepository;
import com.bookamovie.be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final RoleRepository roleRepository;
    private final SeatRepository seatRepository;

    @Bean
    CommandLineRunner initRoles(){
        return args -> {
            log.info("Preloading " + roleRepository.save(new Role("ROLE_ADMIN")));
            log.info("Preloading " + roleRepository.save(new Role("ROLE_USER")));
        };
    }

   @Bean
   CommandLineRunner initSeats(){
        val letters = Arrays.asList("A", "B", "C", "D", "E");
        val numbers = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");

        return args -> {
            letters.forEach(letter -> numbers.forEach(number ->
                    log.info("Preloading " + seatRepository.save(new Seat(letter + number)))));
        };
   }
}

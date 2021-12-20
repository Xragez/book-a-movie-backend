package com.bookamovie.be.configuration;

import com.bookamovie.be.entity.Role;
import com.bookamovie.be.repository.RoleRepository;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initRoles(RoleRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Role("ROLE_ADMIN")));
            log.info("Preloading " + repository.save(new Role("ROLE_USER")));
        };
    }
}

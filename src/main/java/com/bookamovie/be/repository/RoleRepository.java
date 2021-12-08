package com.bookamovie.be.repository;

import com.bookamovie.be.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role>findByName(String name);
}

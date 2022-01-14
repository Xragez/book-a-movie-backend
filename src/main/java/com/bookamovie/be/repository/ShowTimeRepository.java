package com.bookamovie.be.repository;

import com.bookamovie.be.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    Optional<List<ShowTime>> findByDate(LocalDate date);
}

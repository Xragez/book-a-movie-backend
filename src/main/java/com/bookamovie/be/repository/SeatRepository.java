package com.bookamovie.be.repository;

import com.bookamovie.be.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findByName(String name);
}

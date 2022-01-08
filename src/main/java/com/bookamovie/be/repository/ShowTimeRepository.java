package com.bookamovie.be.repository;

import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.view.ShowTimeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    List<ShowTime> findByMovieId(String movieId);
    List<ShowTime> findByDate(LocalDate date);
    Optional<ShowTime> findById(Long id);
    ShowTime save(ShowTimeRequest entity);
}

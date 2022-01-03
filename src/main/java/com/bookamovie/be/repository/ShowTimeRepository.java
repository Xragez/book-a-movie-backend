package com.bookamovie.be.repository;

import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.view.ShowTimeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    ShowTime findByMovieId(String movieId);
    Optional<ShowTime> findById(Long id);
    ShowTime save(ShowTimeRequest entity);
}

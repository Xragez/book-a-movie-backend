package com.bookamovie.be.repository;

import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.view.ShowTimeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    ShowTime findByMovieId(String movieId);
    ShowTime save(ShowTimeRequest entity);
}

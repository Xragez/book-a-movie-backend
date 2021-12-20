package com.bookamovie.be.repository;

import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.model.ShowTimeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    ShowTime save(ShowTimeRequest entity);
}

package com.bookamovie.be.service;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.repository.ShowTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShowtimeService {

    private final ShowTimeRepository showTimeRepository;

    public List<Seat> getTakenSeats(Long showTimeId){
        val showtime = showTimeRepository.findById(showTimeId).orElseThrow();
        val seats = new ArrayList<Seat>();
        showtime.getTickets().forEach(ticket -> seats.addAll(ticket.getSeats()));
        return seats;
    }
}

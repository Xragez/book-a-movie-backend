package com.bookamovie.be.controller;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.mapper.ShowTimeMapper;
import com.bookamovie.be.model.ShowTimeResponse;
import com.bookamovie.be.repository.ShowTimeRepository;
import com.bookamovie.be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final ShowTimeRepository showTimeRepository;
    private final UserRepository userRepository;
    private final ShowTimeMapper showTimeMapper;

    @GetMapping("/showtime/{id}")
    public ResponseEntity<ShowTimeResponse> getShowTIme(@PathVariable Long id) {
        val response = showTimeRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(showTimeMapper.showTimeResponse(response), HttpStatus.OK);
    }

    @GetMapping("/showtime/taken-seats/{id}")
    public ResponseEntity<List<Seat>> getShowTimeTakenSeats(@PathVariable Long id) {
        val showtime = showTimeRepository.getById(id);
        val tickets = showtime.getTickets();
        val seats = new ArrayList<Seat>();
        tickets.forEach(ticket -> seats.addAll(ticket.getSeats()));

        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<List<Ticket>> getUserTickets(@PathVariable Long id) {
        val user = userRepository.findById(id).orElseThrow();
        val tickets = user.getTickets();
        return new ResponseEntity<>(List.copyOf(tickets), HttpStatus.OK);
    }
}

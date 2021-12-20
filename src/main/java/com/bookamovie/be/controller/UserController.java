package com.bookamovie.be.controller;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.mapper.ApiMapper;
import com.bookamovie.be.repository.TicketRepository;
import com.bookamovie.be.view.ShowTimeResponse;
import com.bookamovie.be.repository.ShowTimeRepository;
import com.bookamovie.be.repository.UserRepository;
import com.bookamovie.be.view.TicketRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final ShowTimeRepository showTimeRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final ApiMapper apiMapper;

    @GetMapping("/showtime/{id}")
    public ResponseEntity<ShowTimeResponse> getShowTIme(@PathVariable Long id) {
        val response = showTimeRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(apiMapper.showTimeResponse(response), HttpStatus.OK);
    }

    @GetMapping("/showtime/taken-seats/{id}")
    public ResponseEntity<List<Seat>> getShowTimeTakenSeats(@PathVariable Long id) {
        val showtime = showTimeRepository.getById(id);
        val tickets = showtime.getTickets();
        val seats = new ArrayList<Seat>();
        tickets.forEach(ticket -> seats.addAll(ticket.getSeats()));

        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> addTicket(@RequestBody TicketRequest ticketRequest){
        val showTime = showTimeRepository.findById(ticketRequest.getShowTimeId()).orElseThrow();
        val user = userRepository.findById(ticketRequest.getUserId()).orElseThrow();
        val ticket = apiMapper.ticket(ticketRequest, showTime, user);
        ticketRepository.save(ticket);

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<List<Ticket>> getUserTickets(@PathVariable Long id) {
        val user = userRepository.findById(id).orElseThrow();
        val tickets = user.getTickets();
        return new ResponseEntity<>(List.copyOf(tickets), HttpStatus.OK);
    }
}

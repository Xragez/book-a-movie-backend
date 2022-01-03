package com.bookamovie.be.controller;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.mapper.ApiMapper;
import com.bookamovie.be.repository.SeatRepository;
import com.bookamovie.be.repository.ShowTimeRepository;
import com.bookamovie.be.repository.TicketRepository;
import com.bookamovie.be.repository.UserRepository;
import com.bookamovie.be.service.ShowtimeService;
import com.bookamovie.be.view.ShowTimeResponse;
import com.bookamovie.be.view.TicketRequest;
import com.bookamovie.be.view.TicketResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final ShowTimeRepository showTimeRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final ShowtimeService showtimeService;
    private final ApiMapper apiMapper;

    @GetMapping("/showtime/{id}")
    public ResponseEntity<ShowTimeResponse> getShowTime(@PathVariable Long id) {
        val response = showTimeRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(apiMapper.showTimeResponse(response), HttpStatus.OK);
    }

    @GetMapping("/showtime")
    public ResponseEntity<ShowTimeResponse> getShowTimeByMovieId(@RequestParam String movieId) {
        val response = showTimeRepository.findByMovieId(movieId);
        return new ResponseEntity<>(apiMapper.showTimeResponse(response), HttpStatus.OK);
    }

    @GetMapping("/showtime/taken-seats")
    public ResponseEntity<List<Seat>> getShowTimeTakenSeats(@RequestParam Long showTimeId) {
        val response = showtimeService.getTakenSeats(showTimeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> addTicket(@RequestBody TicketRequest ticketRequest) {
        val seats = new HashSet<Seat>();
        ticketRequest.getSeats().forEach(seat -> seats.add(seatRepository.findByName(seat.getName())));
        for(val seat: seats){
            if(showtimeService.getTakenSeats(ticketRequest.getShowTimeId()).contains(seat)){
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        val showTime = showTimeRepository.findById(ticketRequest.getShowTimeId()).orElseThrow();
        val user = userRepository.findById(ticketRequest.getUserId()).orElseThrow();
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShowTime(showTime);
        ticket.setSeats(seats);
        ticketRepository.save(ticket);

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketResponse>> getUserTickets(@RequestParam Long userId) {
        val user = userRepository.findById(userId).orElseThrow();
        val tickets = apiMapper.ticketsResponse(user.getTickets());
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}

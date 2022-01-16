package com.bookamovie.be.controller;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.mapper.ApiMapper;
import com.bookamovie.be.repository.SeatRepository;
import com.bookamovie.be.service.ShowtimeService;
import com.bookamovie.be.service.TicketService;
import com.bookamovie.be.service.UserService;
import com.bookamovie.be.view.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SeatRepository seatRepository;
    private final ShowtimeService showtimeService;
    private final TicketService ticketService;
    private final ApiMapper apiMapper;

    @GetMapping("/showtime/{id}")
    public ResponseEntity<ShowTimeResponse> getShowTime(@PathVariable Long id) {
        val response = showtimeService.getShowTime(id);
        return new ResponseEntity<>(apiMapper.showTimeResponse(response), HttpStatus.OK);
    }

    @GetMapping("/showtime")
    public ResponseEntity<List<ShowTimeResponse>> getShowTimesByMovieId(@RequestParam(required = false) Long movieId,
                                                                        @RequestParam(required = false) String date) {
        val showTimes = showtimeService.getShowTimesByMovieIdOrDate(movieId, date);
        return new ResponseEntity<>(apiMapper.showTimeResponse(showTimes), HttpStatus.OK);
    }

    @GetMapping("/showtime/hours")
    public ResponseEntity<ShowTimesResponse> getShowTimeHours(@RequestParam String date,
                                                              @RequestParam(required = false) Long movieId) {

        val result = showtimeService.getShowTimeHours(movieId, date);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/showtime/taken-seats")
    public ResponseEntity<List<Seat>> getShowTimeTakenSeats(@RequestParam Long showTimeId) {
        val response = showtimeService.getTakenSeats(showTimeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> addTicket(@RequestBody TicketRequest ticketRequest) throws Exception {
        val ticket = ticketService.addTicket(ticketRequest);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping("/tickets")
    public ResponseEntity<TicketsResponse> getUserTickets() {
        val user = userService.getUserById();
        return new ResponseEntity<>(apiMapper.ticketsResponse(user), HttpStatus.OK);
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketView> getTicketById(@PathVariable Long id) {
        val ticket = ticketService.getTicketById(id);
        return new ResponseEntity<>(apiMapper.ticketView(ticket), HttpStatus.OK);
    }

    @GetMapping("/seats")
    public ResponseEntity<SeatsResponse> getSeats() {
        return new ResponseEntity<>(apiMapper.seatsResponse(seatRepository.findAll()), HttpStatus.OK);
    }
}

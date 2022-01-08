package com.bookamovie.be.controller;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.mapper.ApiMapper;
import com.bookamovie.be.repository.SeatRepository;
import com.bookamovie.be.repository.ShowTimeRepository;
import com.bookamovie.be.repository.TicketRepository;
import com.bookamovie.be.repository.UserRepository;
import com.bookamovie.be.service.ShowtimeService;
import com.bookamovie.be.view.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/showtime/{id}")
    public ResponseEntity<ShowTimeResponse> getShowTime(@PathVariable Long id) {
        val response = showTimeRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(apiMapper.showTimeResponse(response), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/showtime")
    public ResponseEntity<List<ShowTimeResponse>> getShowTimesByMovieId(@RequestParam(required = false) String movieId,
                                                                       @RequestParam(required = false) String date) {
        val localDate = LocalDate.parse(date);
        List<ShowTime> showTimes = showTimeRepository.findAll();
        if (movieId != null) {
            showTimes = showTimes.stream().filter(showTime -> showTime.getMovieId().equals(movieId)).collect(Collectors.toList());
        }
        if (date != null) {
            showTimes = showTimes.stream().filter(showTime -> showTime.getDate().equals(localDate)).collect(Collectors.toList());
        }
        return new ResponseEntity<>(apiMapper.showTimeResponse(showTimes), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/showtime/hours")
    public ResponseEntity<ShowTimesResponse> getShowTimeHours(@RequestParam(required = false) String date){
        val localDate = LocalDate.parse(date);
        List<ShowTime> showTimes = showTimeRepository.findByDate(localDate);
        val showTimesMap = new HashMap<ShowTimeMovieData, List<ShowTimeHour>>();
        for (val showTime: showTimes){
            List<ShowTimeHour> temp = new ArrayList<>();
            val movieData = apiMapper.showTimeMovieData(showTime.getMovieId(), showTime.getMovieTitle());
            if(showTimesMap.containsKey(movieData)){
                temp = showTimesMap.get(movieData);
            }
            temp.add(apiMapper.showTimeHour(showTime.getId(), showTime.getTime()));
            showTimesMap.put(movieData, temp);
        }
        return new ResponseEntity<>(apiMapper.showTimesResponse(showTimesMap), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/showtime/taken-seats")
    public ResponseEntity<List<Seat>> getShowTimeTakenSeats(@RequestParam Long showTimeId) {
        val response = showtimeService.getTakenSeats(showTimeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/tickets")
    public ResponseEntity<Ticket> addTicket(@RequestBody TicketRequest ticketRequest) throws Exception {
        val seats = new HashSet<Seat>();
        ticketRequest.getSeats().forEach(seat -> seats.add(seatRepository.findByName(seat.getName())));
        for (val seat : seats) {
            if (showtimeService.getTakenSeats(ticketRequest.getShowTimeId()).contains(seat)) {
                throw new Exception("At least one chosen seat is not available.");
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

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketResponse>> getUserTickets(@RequestParam Long userId) {
        val user = userRepository.findById(userId).orElseThrow();
        val tickets = apiMapper.ticketsResponse(user.getTickets());
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketResponse> getTicketsByCode(@PathVariable Long id) {
        val ticket = ticketRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(apiMapper.ticketResponse(ticket), HttpStatus.OK);
    }

}

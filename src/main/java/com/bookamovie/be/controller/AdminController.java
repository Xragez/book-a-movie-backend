package com.bookamovie.be.controller;

import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.mapper.ApiMapper;
import com.bookamovie.be.service.TicketService;
import com.bookamovie.be.view.ShowTimeRequest;
import com.bookamovie.be.repository.ShowTimeRepository;
import com.bookamovie.be.view.TicketsResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ShowTimeRepository showTimeRepository;
    private final TicketService ticketService;
    private final ApiMapper apiMapper;

    @PostMapping("/showtime")
    public ResponseEntity<ShowTime> newShowTime(@RequestBody ShowTimeRequest showTimeRequest) {
        val result = showTimeRepository.save(apiMapper.showTime(showTimeRequest));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/showtime/{id}")
    public ResponseEntity<String> deleteShowTime(@PathVariable Long id){
        showTimeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/tickets/{code}")
    public ResponseEntity<TicketsResponse> getTicketsByCode(@PathVariable String code) {
        val tickets = ticketService.getAllTickets()
                .stream().filter(ticket -> ticket.getCode().contains(code))
                .collect(Collectors.toList());
        return new ResponseEntity<>(apiMapper.ticketsResponse(tickets), HttpStatus.OK);
    }
}

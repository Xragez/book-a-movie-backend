package com.bookamovie.be.service;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.repository.SeatRepository;
import com.bookamovie.be.repository.ShowTimeRepository;
import com.bookamovie.be.repository.TicketRepository;
import com.bookamovie.be.view.TicketRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class TicketService {

    private final ShowtimeService showtimeService;
    private final SeatRepository seatRepository;
    private final ShowTimeRepository showTimeRepository;
    private final UserService userService;
    private final TicketRepository ticketRepository;

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow();
    }

    public Ticket addTicket(TicketRequest ticketRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        val user = userService.getUserByUsername(authentication.getName());
        val seats = new HashSet<Seat>();
        ticketRequest.getSeats().forEach(seat -> seats.add(seatRepository.findByName(seat).orElseThrow()));
        for (val seat : seats) {
            if (showtimeService.getTakenSeats(ticketRequest.getShowTimeId()).contains(seat)) {
                throw new Exception("At least one chosen seat is not available.");
            }
        }
        val showTime = showTimeRepository.findById(ticketRequest.getShowTimeId()).orElseThrow();
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShowTime(showTime);
        ticket.setSeats(seats);
        ticketRepository.save(ticket);
        return ticket;
    }
}

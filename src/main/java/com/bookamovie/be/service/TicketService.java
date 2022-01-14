package com.bookamovie.be.service;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.repository.SeatRepository;
import com.bookamovie.be.repository.ShowTimeRepository;
import com.bookamovie.be.repository.TicketRepository;
import com.bookamovie.be.repository.UserRepository;
import com.bookamovie.be.view.TicketRequest;
import com.bookamovie.be.view.TicketView;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TicketService {

    private final ShowtimeService showtimeService;
    private final SeatRepository seatRepository;
    private final ShowTimeRepository showTimeRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public Ticket getTicketById(Long id){
        return ticketRepository.findById(id).orElseThrow();
    }

    public Ticket addTicket(TicketRequest ticketRequest) throws Exception{
        val seats = new HashSet<Seat>();
        ticketRequest.getSeats().forEach(seat -> seats.add(seatRepository.findByName(seat).orElseThrow()));
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
        return ticket;
    }
}

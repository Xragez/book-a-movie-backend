package com.bookamovie.be.mapper;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.entity.User;
import com.bookamovie.be.view.ShowTimeRequest;
import com.bookamovie.be.view.ShowTimeResponse;
import com.bookamovie.be.view.TicketRequest;
import com.bookamovie.be.view.TicketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ApiMapper {
    ShowTimeResponse showTimeResponse(ShowTime showTime);

    ShowTime showTime(ShowTimeRequest showTimeRequest);

    TicketResponse ticketResponse(Ticket ticket);

    List<TicketResponse> ticketsResponse(Set<Ticket> tickets);
}

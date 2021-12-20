package com.bookamovie.be.mapper;

import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.entity.User;
import com.bookamovie.be.view.ShowTimeRequest;
import com.bookamovie.be.view.ShowTimeResponse;
import com.bookamovie.be.view.TicketRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApiMapper {
    ShowTimeResponse showTimeResponse(ShowTime showTime);

    ShowTime showTime(ShowTimeRequest showTimeRequest);

    @Mapping(target = "id", ignore = true)
    Ticket ticket(TicketRequest ticketRequest, ShowTime showTime, User user);

}

package com.bookamovie.be.mapper;

import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.view.*;
import lombok.val;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ApiMapper {
    List<ShowTimeResponse> showTimeResponse(List<ShowTime> showTime);

    ShowTimeResponse showTimeResponse(ShowTime showTime);

    ShowTime showTime(ShowTimeRequest showTimeRequest);

    TicketResponse ticketResponse(Ticket ticket);

    ShowTimesResponse showTimesResponse(Map<ShowTimeMovieData, List<ShowTimeHour>> showTimes);

    default List<ShowTimeHours> map(Map<ShowTimeMovieData, List<ShowTimeHour>> value) {
        val result = new ArrayList<ShowTimeHours>();
        for (val element : value.entrySet()) {
            result.add(showTimeHours(element.getKey().getMovieId(), element.getKey().getMovieTitle(), element.getValue()));
        }
        return result;
    }

    ShowTimeMovieData showTimeMovieData(String movieId, String movieTitle);

    ShowTimeHours showTimeHours(String movieId, String movieTitle, List<ShowTimeHour> hours);

    ShowTimeHour showTimeHour(Long showTimeId, String hour);

    List<TicketResponse> ticketsResponse(Set<Ticket> tickets);
}

package com.bookamovie.be.mapper;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.entity.User;
import com.bookamovie.be.view.*;
import lombok.val;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface ApiMapper {

    UserResponse userResponse(String token);

    List<ShowTimeResponse> showTimeResponse(List<ShowTime> showTime);

    ShowTimeResponse showTimeResponse(ShowTime showTime);

    ShowTime showTime(ShowTimeRequest showTimeRequest);

    TicketView ticketView(Ticket ticket);

    ShowTimesResponse showTimesResponse(Map<ShowTimeMovieData, List<ShowTimeHour>> showTimes);

    default ShowTimesResponse showTimesResponse(List<ShowTimeHours> showTimes){
        val result = new ShowTimesResponse();
        result.setShowTimes(showTimes);
        return result;
    }

    default List<ShowTimeHours> map(Map<ShowTimeMovieData, List<ShowTimeHour>> value) {
        val result = new ArrayList<ShowTimeHours>();
        for (val element : value.entrySet()) {
            result.add(showTimeHours(element.getKey().getMovieId(), element.getKey().getMovieTitle(), element.getValue()));
        }
        return result;
    }

    ShowTimeMovieData showTimeMovieData(Long movieId, String movieTitle);

    ShowTimeHours showTimeHours(Long movieId, String movieTitle, List<ShowTimeHour> hours);

    ShowTimeHour showTimeHour(Long showTimeId, String hour);

    TicketsResponse ticketsResponse(User user);

    default TicketsResponse ticketsResponse(List<Ticket> tickets){
        val result = new TicketsResponse();
        result.setTickets(ticketViewList(tickets));
        return result;
    }

    default SeatsResponse seatsResponse(List<Seat> seats){
        val result = new SeatsResponse();
        result.setSeats(seatViewList(seats));
        return result;
    }

    @Mapping(target = "userData.firstName", source = "user.firstName")
    @Mapping(target = "userData.surname", source = "user.surname")
    List<TicketView> ticketViewList(List<Ticket> tickets);

    List<SeatVIew> seatViewList(List<Seat> seats);

    SeatVIew seatView(Seat seat);
}

package com.bookamovie.be.mapper;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.entity.Ticket;
import com.bookamovie.be.entity.User;
import com.bookamovie.be.view.*;
import lombok.val;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ApiMapper {

    UserResponse userResponse(String token);

    List<ShowTimeResponse> showTimeResponse(List<ShowTime> showTime);

    ShowTimeResponse showTimeResponse(ShowTime showTime);

    ShowTime showTime(ShowTimeRequest showTimeRequest);

    TicketView ticketResponse(Ticket ticket);

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

    ShowTimeMovieData showTimeMovieData(String movieId, String movieTitle);

    ShowTimeHours showTimeHours(String movieId, String movieTitle, List<ShowTimeHour> hours);

    ShowTimeHour showTimeHour(Long showTimeId, String hour);

    TicektsResponse ticketsResponse(User user);

    default SeatsResponse seatsResponse(List<Seat> seats){
        val result = new SeatsResponse();
        result.setSeats(seatViewList(seats));
        return result;
    }

    List<SeatVIew> seatViewList(List<Seat> seats);

    SeatVIew seatView(Seat seat);
}

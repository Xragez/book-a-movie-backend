package com.bookamovie.be.view;

import com.bookamovie.be.entity.Seat;
import lombok.Data;

import java.util.List;

@Data
public class TicketView {
    private UserData userData;
    private List<SeatVIew> seats;
    private ShowTimeResponse showTime;
    private String code;
}

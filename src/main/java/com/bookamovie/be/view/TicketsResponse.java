package com.bookamovie.be.view;

import com.bookamovie.be.entity.Seat;
import lombok.Data;

import java.util.List;

@Data
public class TicketsResponse {
    private List<Seat> seats;
    private ShowTimeResponse showTime;
}

package com.bookamovie.be.view;

import lombok.Data;

import java.util.List;

@Data
public class TicketRequest {

    private Long userId;
    private List<SeatVIew> seats;
    private Long showTimeId;
}
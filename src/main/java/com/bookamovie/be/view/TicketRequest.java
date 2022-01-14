package com.bookamovie.be.view;

import lombok.Data;

import java.util.List;

@Data
public class TicketRequest {

    private Long userId;
    private List<String> seats;
    private Long showTimeId;
}

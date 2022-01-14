package com.bookamovie.be.view;

import lombok.Data;

import java.util.List;

@Data
public class TicektsResponse {
    private UserData userData;
    private List<TicketView> tickets;
}

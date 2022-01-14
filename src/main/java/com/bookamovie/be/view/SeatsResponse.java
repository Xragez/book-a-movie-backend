package com.bookamovie.be.view;

import lombok.Data;

import java.util.List;

@Data
public class SeatsResponse {
    private List<SeatVIew> seats;
}

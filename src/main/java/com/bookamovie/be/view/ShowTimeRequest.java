package com.bookamovie.be.view;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ShowTimeRequest {
    private String movieId;
    private String movieTitle;
    private LocalDate date;
    private String time;
}

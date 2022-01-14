package com.bookamovie.be.view;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ShowTimeResponse {
    private String id;
    private String movieId;
    private String movieTitle;
    private LocalDate date;
    private String time;
}

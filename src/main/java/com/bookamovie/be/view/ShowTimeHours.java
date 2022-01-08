package com.bookamovie.be.view;

import lombok.Data;

import java.util.List;

@Data
public class ShowTimeHours {
    private String movieId;
    private String movieTitle;
    private List<ShowTimeHour> hours;
}

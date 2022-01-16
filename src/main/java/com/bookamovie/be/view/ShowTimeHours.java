package com.bookamovie.be.view;

import lombok.Data;

import java.util.List;

@Data
public class ShowTimeHours {
    private Long movieId;
    private String movieTitle;
    private List<ShowTimeHour> hours;
}

package com.bookamovie.be.view;

import lombok.Data;

import java.util.Date;

@Data
public class ShowTimeResponse {
    private String movieId;
    private Date date;
    private Date time;
}

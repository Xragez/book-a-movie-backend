package com.bookamovie.be.model;

import lombok.Data;

import java.util.Date;

@Data
public class ShowTimeResponse {
    private String movieId;
    private Date date;
    private Date time;
}

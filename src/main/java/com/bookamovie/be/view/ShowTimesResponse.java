package com.bookamovie.be.view;

import lombok.Data;

import java.util.List;

@Data
public class ShowTimesResponse {
    private List<ShowTimeHours> showTimes;
}

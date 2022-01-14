package com.bookamovie.be.service;

import com.bookamovie.be.entity.Seat;
import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.mapper.ApiMapper;
import com.bookamovie.be.repository.ShowTimeRepository;
import com.bookamovie.be.view.ShowTimeHour;
import com.bookamovie.be.view.ShowTimeMovieData;
import com.bookamovie.be.view.ShowTimeResponse;
import com.bookamovie.be.view.ShowTimesResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShowtimeService {

    private final ShowTimeRepository showTimeRepository;
    private final ApiMapper apiMapper;

    public List<ShowTime> getShowTimesByMovieIdOrDate(String movieId, String date){
        val localDate = LocalDate.parse(date);
        List<ShowTime> showTimes = showTimeRepository.findAll();
        if (movieId != null) {
            showTimes = showTimes.stream().filter(showTime -> showTime.getMovieId().equals(movieId)).collect(Collectors.toList());
        }
        if (date != null) {
            showTimes = showTimes.stream().filter(showTime -> showTime.getDate().equals(localDate)).collect(Collectors.toList());
        }
        return showTimes;
    }

    public ShowTime getShowTime(Long id){
        return showTimeRepository.findById(id).orElseThrow();
    }

    public List<Seat> getTakenSeats(Long showTimeId){
        val showtime = showTimeRepository.findById(showTimeId).orElseThrow();
        val seats = new ArrayList<Seat>();
        showtime.getTickets().forEach(ticket -> seats.addAll(ticket.getSeats()));
        return seats;
    }

    public ShowTimesResponse getShowTimeHours(String movieId, String date){
        val localDate = LocalDate.parse(date);
        List<ShowTime> showTimes = showTimeRepository.findByDate(localDate).orElseThrow();
        val showTimesMap = new HashMap<ShowTimeMovieData, List<ShowTimeHour>>();
        for (val showTime : showTimes) {
            List<ShowTimeHour> temp = new ArrayList<>();
            val movieData = apiMapper.showTimeMovieData(showTime.getMovieId(), showTime.getMovieTitle());
            if (showTimesMap.containsKey(movieData)) {
                temp = showTimesMap.get(movieData);
            }
            temp.add(apiMapper.showTimeHour(showTime.getId(), showTime.getTime()));
            showTimesMap.put(movieData, temp);
        }

        ShowTimesResponse result;

        if (movieId != null) {
            result = apiMapper.showTimesResponse(apiMapper.showTimesResponse(showTimesMap)
                    .getShowTimes().stream().filter(showTimeHours -> showTimeHours.getMovieId().equals(movieId))
                    .collect(Collectors.toList()));
        } else {
            result = apiMapper.showTimesResponse(showTimesMap);
        }

        return result;
    }
}

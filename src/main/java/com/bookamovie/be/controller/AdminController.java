package com.bookamovie.be.controller;

import com.bookamovie.be.entity.ShowTime;
import com.bookamovie.be.model.ShowTimeRequest;
import com.bookamovie.be.repository.ShowTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ShowTimeRepository showTimeRepository;

    @PostMapping("showtime")
    public ResponseEntity<ShowTime> newShowTime(@RequestBody ShowTimeRequest showTimeRequest) {
        val result = showTimeRepository.save(showTimeRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("showtime/{id}")
    public ResponseEntity<String> deleteShowTime(@PathVariable Long id){
        showTimeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
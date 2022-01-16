package com.bookamovie.be.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "showtimes")
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long movieId;

    @Column(nullable = false)
    private String movieTitle;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String time;

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL)
    private Set<Ticket> tickets;
}

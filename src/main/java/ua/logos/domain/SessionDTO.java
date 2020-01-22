package ua.logos.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.logos.entity.FilmEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor

public class SessionDTO {

    private Long id;

    private LocalTime time;

    private LocalDate date;

    private FilmEntity film;

    private String linkSession;

    private String nameCinema;

}

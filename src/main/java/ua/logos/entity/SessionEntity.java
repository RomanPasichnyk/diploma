package ua.logos.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "sessions")
public class SessionEntity extends BaseEntity {

    @Column(columnDefinition = "TIME")
    private LocalTime time;

    @Column(columnDefinition = "DATE")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private FilmEntity film;

    @Column(name = "link_session")
    private String linkSession;

    @Column(name = "name_cinema")
    private String nameCinema;

}

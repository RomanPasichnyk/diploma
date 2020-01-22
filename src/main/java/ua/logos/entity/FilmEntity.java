package ua.logos.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.logos.enums.GenreEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "films")
public class FilmEntity extends BaseEntity {

    @Column(name = "film_name", length = 150, nullable = false)
    private String filmName;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "language", length = 40, nullable = false)
    private String language;

    @Column(name = "release_film", nullable = false, columnDefinition = "DATE")
    private LocalDate releaseFilm;

    @Column(name = "start_rental", nullable = false, columnDefinition = "DATE")
    private LocalDate startRental;

    @Column(name = "end_rental", nullable = false, columnDefinition = "DATE")
    private LocalDate endRental;

    @Column(name = "image")
    private String image;

    @Column(name = "link_trailer")
    private String linkTrailer;

    @Enumerated
    @ElementCollection(targetClass = GenreEnum.class)
    private List<GenreEnum> genre;

    @Column(name = "duration", nullable = false)
    private int duration;

}

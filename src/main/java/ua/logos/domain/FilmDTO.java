package ua.logos.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.logos.enums.GenreEnum;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class FilmDTO {

    private Long id;

    @NotNull(message = "Field 'FilmName' can`t be null")
    private String filmName;

    @NotNull(message = "Field 'OriginalName' can`t be null")
    private String originalName;

    @NotNull(message = "Field 'Language' can`t be null")
    private String language;

    private LocalDate releaseFilm;

    private LocalDate startRental;

    private LocalDate endRental;

    private List<GenreEnum> genre;

    private String image;

    private int duration;

    private String linkTrailer;

}

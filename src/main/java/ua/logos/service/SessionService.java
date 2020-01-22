package ua.logos.service;

import ua.logos.domain.SessionDTO;

import java.time.LocalDate;
import java.util.List;

public interface SessionService {

    void saveSession(SessionDTO sessionDTO);

    List<SessionDTO> getAllSession();

    SessionDTO findSessionById(Long id);

    List<SessionDTO> getSessionByFilmId(Long id);

    List<SessionDTO> getSessionByFilmIdAndDate(Long id, LocalDate date);

    void deleteSessionById(Long id);

    List<SessionDTO> getSessionByFilmIdAndDateAndNameCinema(Long id, LocalDate date, String nameCinema);

}

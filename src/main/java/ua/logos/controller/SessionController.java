package ua.logos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.logos.domain.SessionDTO;
import ua.logos.service.SessionService;

import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.sql.Array;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public ResponseEntity<?> saveSession(@RequestBody SessionDTO sessionDTO) {
        System.out.println(sessionDTO.getTime());
        sessionService.saveSession(sessionDTO);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllSession() {
        List<SessionDTO> sessionDTOS = sessionService.getAllSession();
        return new ResponseEntity<List<SessionDTO>>(sessionDTOS, HttpStatus.OK);
    }

    @GetMapping("search/{filmId}")
    public ResponseEntity<?> getSessionByFilmId(
            @PathVariable("filmId") Long id
    ) {
        List<SessionDTO> sessionDTOS = sessionService.getSessionByFilmId(id);
        Set<LocalDate> sessionDates = new HashSet<>();
        for (SessionDTO sessionDTO : sessionDTOS
        ) {
            sessionDates.add(sessionDTO.getDate());
        }

        return new ResponseEntity<Set<LocalDate>>(sessionDates, HttpStatus.OK);
    }

    @GetMapping("searchtime/{filmId}")
    public ResponseEntity<?> getSessionByFilmIdAndDate(
            @PathVariable("filmId") Long id,
            @RequestParam("date") String date
    ) {

        String[] datee = date.split(",");
        LocalDate normDate = LocalDate.of(Integer.valueOf(datee[0]), Integer.valueOf(datee[1]), Integer.valueOf(datee[2]));
        List<SessionDTO> sessionDTOS = sessionService.getSessionByFilmIdAndDate(id, normDate);
        return new ResponseEntity<List<SessionDTO>>(sessionDTOS, HttpStatus.OK);
    }

    @DeleteMapping("{sessionId}")
    public ResponseEntity<?> deleteFilm(@PathVariable("sessionId") Long id) {
        sessionService.deleteSessionById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("find")
    public ResponseEntity<?>getSessionById(
            @RequestParam("sessionId") Long id
    ) {
        SessionDTO sessionDTO = sessionService.findSessionById(id);
        return new ResponseEntity<SessionDTO>(sessionDTO, HttpStatus.OK);
    }

    @GetMapping("searchByAll")
    public ResponseEntity<?> getSessionByFilmIdAndDateAndNameCinema(
            @RequestParam("filmId") Long filmId,
            @RequestParam("date") String date,
            @RequestParam("nameCinema") String nameCinema
    ){
        LocalDate normDate = null;
        if(date.equals("now")) {
            normDate = LocalDate.now();
        } else {
            String[] datee = date.split("-");
            normDate = LocalDate.of(Integer.valueOf(datee[0]), Integer.valueOf(datee[1]), Integer.valueOf(datee[2]));
        }
        List<SessionDTO> sessionDTOS = sessionService.getSessionByFilmIdAndDateAndNameCinema(filmId, normDate, nameCinema);
        return new ResponseEntity<List<SessionDTO>>(sessionDTOS, HttpStatus.OK);
    }
}

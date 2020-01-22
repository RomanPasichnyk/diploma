package ua.logos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.logos.domain.FilmDTO;
import ua.logos.domain.UserDTO;
import ua.logos.service.FileStorageService;
import ua.logos.service.FilmService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> addFilm(@RequestBody FilmDTO filmDTO) {
        System.out.println(filmDTO);
        filmService.saveFilm(filmDTO);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllFilms() {
        List<FilmDTO> filmDTOS = filmService.getAllFilms();
        return new ResponseEntity<List<FilmDTO>>(filmDTOS, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<?> getFilmById(
            @RequestParam("id") Long id) {
        FilmDTO filmDTO = filmService.findFilmById(id);
        return new ResponseEntity<FilmDTO>(filmDTO, HttpStatus.OK);
    }

    @DeleteMapping("{filmId}")
    public ResponseEntity<?> deleteFilm(@PathVariable("filmId") Long id) {
        filmService.deleteFilmById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("{filmId}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable("filmId") Long id,
            @RequestParam("file") MultipartFile file
    ) {
        System.out.println(file.getOriginalFilename());
        fileStorageService.storeFile(file, "film_id_" + id + getFileExtension(file));
        filmService.addImageToFilm("film_id_" + id + getFileExtension(file), id);
        System.out.println("film_id_" + id + getFileExtension(file));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("find")
    public ResponseEntity<?> getFilmByName(@RequestParam("filmName") String filmName) {
        FilmDTO filmDTO = filmService.findFilmByName(filmName);
        return new ResponseEntity<FilmDTO>(filmDTO, HttpStatus.OK);
    }

    @GetMapping("image/{filmId}")
    public ResponseEntity<?> getImage(
            @PathVariable("filmId") Long id,
            HttpServletRequest request
    ) {
        FilmDTO filmDTO = filmService.findFilmById(id);
        String fileName = filmDTO.getImage();
        Resource resource = fileStorageService.loadFile(fileName);

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }


}

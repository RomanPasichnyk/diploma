package ua.logos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.logos.domain.FilmDTO;
import ua.logos.entity.FilmEntity;
import ua.logos.exception.ResourceNotFoundException;
import ua.logos.repository.FilmRepository;
import ua.logos.service.FilmService;
import ua.logos.utils.ObjectMapperUtils;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ObjectMapperUtils modelMapper;

    @Override
    public void saveFilm(FilmDTO filmDTO) {
        FilmEntity filmEntity = modelMapper.map(filmDTO, FilmEntity.class);
        filmRepository.save(filmEntity);
    }

    @Override
    public List<FilmDTO> getAllFilms() {
        List<FilmEntity> filmEntities = filmRepository.findAll();
        List<FilmDTO> filmDTOS = modelMapper.mapAll(filmEntities, FilmDTO.class);
        return filmDTOS;
    }

    @Override
    public FilmDTO findFilmById(Long id) {
        FilmEntity filmEntity = filmRepository.findById(id).get();
        FilmDTO filmDTO = modelMapper.map(filmEntity, FilmDTO.class);
        return filmDTO;
    }

    @Override
    public void deleteFilmById(Long id) {
        FilmEntity filmEntity = filmRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Could not delete film with id[" + id + "]. Not Found!")
        );
        filmRepository.deleteById(id);
    }

    @Override
    public void addImageToFilm(String image, Long id) {
        FilmEntity filmEntity = filmRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Film with id[" + id + "] not found!")
        );
        filmEntity.setImage(image);
        filmRepository.save(filmEntity);
    }

    @Override
    public FilmDTO findFilmByName(String name) {
        FilmEntity filmEntity = filmRepository.findByFilmName(name);
        FilmDTO filmDTO = modelMapper.map(filmEntity, FilmDTO.class);
        return filmDTO;
    }
}

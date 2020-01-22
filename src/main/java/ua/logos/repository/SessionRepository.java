package ua.logos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.logos.entity.SessionEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    List<SessionEntity> findByFilmId(Long id);

    List<SessionEntity> findByFilmIdAndDate(Long id, LocalDate date);

    List<SessionEntity> findByFilmIdAndDateAndNameCinema(Long id, LocalDate date, String nameCinema);

}

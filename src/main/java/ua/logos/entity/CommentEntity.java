package ua.logos.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @Column(name = "comment", nullable = false, length = 150)
    private String comment;

    @Column(name = "comment_time", nullable = false, columnDefinition = "TIME")
    private LocalTime commentsTime;

    @Column(name = "comment_date", nullable = false, columnDefinition = "DATE")
    private LocalDate commentsDate;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private FilmEntity film;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}

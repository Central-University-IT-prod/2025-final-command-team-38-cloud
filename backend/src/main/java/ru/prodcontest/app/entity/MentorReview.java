package ru.prodcontest.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "mentor_reviews")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Отзыв о менторе")
public class MentorReview {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Уникальный идентификатор отзыва", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    private UUID id;

    @ManyToOne
    @Schema(description = "Автор отзыва", requiredMode = Schema.RequiredMode.REQUIRED)
    private Student author;

    @ManyToOne
    @Schema(description = "Ментор, о котором отзыв", requiredMode = Schema.RequiredMode.REQUIRED)
    private Mentor mentor;

    @Schema(description = "Рейтинг", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private int rating;

    @Schema(description = "Комментарий", example = "Отличный ментор, рекомендую!", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String comment;
}
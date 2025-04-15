package ru.prodcontest.app.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table
@Schema(description = "Запрос на добавление ментора")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorRequest {
    @Schema(description = "Статус запроса", example = "WAIT")
    public enum Status {
        @Schema(description = "Активный")
        ACTIVE,
        @Schema(description = "Ожидает одобрения")
        WAIT,
        @Schema(description = "Отменен")
        CANCELED,
        @Schema(description = "Не найден")
        NOT_FOUND
    }

    @Enumerated(EnumType.ORDINAL)
    @Schema(description = "Статус запроса", example = "WAIT")
    @Builder.Default
    private Status status = Status.WAIT;

    @Id
    @Schema(description = "Уникальный идентификатор запроса", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Schema(description = "Фотография ментора (base64)", maxLength = 206400)
    @Size(max = 206400)
    private String photo;

    @Schema(description = "Имя ментора", minLength = 2, maxLength = 30, example = "Иван")
    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;

    @Schema(description = "Фамилия ментора", minLength = 2, maxLength = 30, example = "Иванов")
    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;

    @Schema(description = "Возраст ментора", minimum = "16", maximum = "100", example = "30")
    @Range(min = 16, max = 100)
    private int age;

    @Schema(description = "Стек технологий ментора", example = "[\"Java\", \"Spring\", \"PostgreSQL\"]")
    @ElementCollection
    private Set<@NotBlank @Length(min = 2, max = 50) String> stack;

    @Schema(description = "Email ментора", example = "ivan.ivanov@example.com")
    @Email
    private String email;

    @Schema(description = "Telegram ментора. Должен начинаться с '@' и иметь минимум 5 символов после него.", example = "@ivan_mentor")
    @Pattern(regexp = "^@.{5,}$", message = "Telegram должен начинаться с символа '@' и иметь минимум 5 символов после него")
    private String telegram;

    @Schema(description = "Ссылки на ресурсы ментора", example = "[\"linkedin.com/in/ivan\", \"github.com/ivan\"]")
    @ElementCollection
    private Set<@NotBlank String> resources;

    @Schema(description = "Биография ментора", minLength = 150, maxLength = 1500, example = "Опыт работы в IT более 5 лет...")
    @Size(min = 150, max = 1500)
    private String bio;

    @Schema(description = "Опыт работы (в годах)", minimum = "0", maximum = "100", example = "5")
    @Range(min = 0, max = 100)
    private int experience;

    @Schema(description = "Стоимость часа работы", minimum = "0", example = "1000")
    @PositiveOrZero
    private int costPerHour;

    @Schema(description = "Активность ментора", example = "true")
    @Builder.Default
    private boolean isActive = true;

    @Schema(description = "Рейтинг ментора", example = "4.5")
    private double rating;

    public Mentor asMentor() {
        return Mentor.builder()
                .id(id)
                .photo(photo)
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .stack(Set.copyOf(stack))
                .email(email)
                .telegram(telegram)
                .resources(Set.copyOf(resources))
                .bio(bio)
                .experience(experience)
                .costPerHour(costPerHour)
                .isActive(isActive)
                .rating(rating)
                .build();
    }
}
package ru.prodcontest.app.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Информация о менторе") // Добавлено общее описание
public class Mentor {
    @Id
    @Schema(description = "Уникальный идентификатор ментора", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
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

    @Schema(description = "Стек технологий ментора", example = "[\"Java\", \"Spring\", \"MySQL\"]")
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

    @Schema(description = "Биография ментора", minLength = 16, maxLength = 1500, example = "Опыт работы в IT более 5 лет...")
    @Size(min = 16, max = 1500)
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

    @Override
    public String toString() {
        return "Mentor{" +
                "id=" + id +
                '}';
    }

    public MentorRequest asRequest() {
        return MentorRequest.builder()
                .status(MentorRequest.Status.WAIT)
                .id(id)
                .photo(photo)
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .stack(new TreeSet<>(stack))
                .email(email)
                .telegram(telegram)
                .resources(new TreeSet<>(resources))
                .bio(bio)
                .experience(experience)
                .costPerHour(costPerHour)
                .isActive(isActive)
                .rating(rating)
                .build();
    }
}
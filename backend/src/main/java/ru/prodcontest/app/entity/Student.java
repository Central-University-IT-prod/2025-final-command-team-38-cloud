package ru.prodcontest.app.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
@Schema(description = "Сущность студента")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Уникальный идентификатор студента", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    private UUID uuid;

    @Length(min = 2, max = 30)
    @Schema(description = "Имя студента", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @Length(min = 2, max = 30)
    @Schema(description = "Фамилия студента", example = "Иванов", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Schema(description = "Email студента", example = "ivan@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Телеграм студента", example = "@ivanov", requiredMode = Schema.RequiredMode.REQUIRED)
    private String telegram;

    @Schema(description = "Наличие опыта", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean hasExperience;

    @ElementCollection
    @Schema(description = "Стек технологий", example = "[\"Java\", \"Spring\"]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Set<String> stack;
}
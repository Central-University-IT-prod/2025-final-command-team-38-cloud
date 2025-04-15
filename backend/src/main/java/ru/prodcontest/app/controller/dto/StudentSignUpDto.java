package ru.prodcontest.app.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Schema(description = "DTO для регистрации студента")
@Data
@AllArgsConstructor
public class StudentSignUpDto {
    @Length(min = 2, max = 30)
    @Schema(description = "Имя студента", example = "Петр")
    private String firstName;

    @Length(min = 2, max = 30)
    @Schema(description = "Фамилия студента", example = "Петров")
    private String lastName;

    @Email
    @NotNull
    @Schema(description = "Email студента", example = "petr@example.com")
    private String email;

    @NotNull
    @Schema(description = "Телеграм студента", example = "@petrov")
    @Pattern(regexp = "^@.{5,}$", message = "Строка должна начинаться с символа '@' и быть больше 5 символов")
    private String telegram;

    @Schema(description = "Наличие опыта", example = "true")
    private boolean hasExperience;

    @Schema(description = "Стек технологий", example = "[\"Python\", \"Django\"]")
    private Set<@NotBlank @Length(min = 2, max = 50) String> stack;
}
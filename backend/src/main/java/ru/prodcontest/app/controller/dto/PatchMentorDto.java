package ru.prodcontest.app.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

@Data
@Schema(description = "DTO для частичного обновления информации о менторе")
public class PatchMentorDto {

    @Schema(description = "Фото ментора (Base64 encoded string)", maxLength = 206400, example = "base64encodedimage...")
    @Size(max = 206400, message = "Фотография не должна превышать 206400 символов")
    private String photo;

    @Schema(description = "Имя ментора", minLength = 2, maxLength = 30, example = "Иван")
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов")
    private String firstName;

    @Schema(description = "Фамилия ментора", minLength = 2, maxLength = 30, example = "Иванов")
    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 символов")
    private String lastName;

    @Schema(description = "Возраст ментора", minimum = "16", maximum = "100", example = "25")
    @Range(min = 16, max = 100, message = "Возраст должен быть от 16 до 100 лет")
    private Integer age;

    @Schema(description = "Telegram никнейм ментора", example = "@ivanov_ivan")
    private String telegram;

    @Schema(description = "Набор ресурсов (ссылок) ментора", example = "[\"https://example.com\", \"https://example.org\"]")
    private Set<@NotBlank(message = "Ресурс не может быть пустым") String> resources;

    @Schema(description = "Биография ментора", minLength = 150, maxLength = 1500, example = "Я опытный ментор...")
    @Size(min = 150, max = 1500, message = "Биография должна содержать от 150 до 1500 символов")
    private String bio;

    @Schema(description = "Опыт работы ментора (в годах)", minimum = "0", maximum = "100", example = "5")
    @Range(min = 0, max = 100, message = "Опыт работы должен быть от 0 до 100 лет")
    private Integer experience;

    @Schema(description = "Стоимость часа работы ментора", minimum = "0", example = "1000")
    @PositiveOrZero(message = "Стоимость часа должна быть положительной или равна нулю")
    private Integer costPerHour;
}
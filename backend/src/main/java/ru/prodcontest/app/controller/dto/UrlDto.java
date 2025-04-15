package ru.prodcontest.app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для представления URL")
public class UrlDto {

    @Schema(description = "URL", example = "https://example.com")
    private String url;
}
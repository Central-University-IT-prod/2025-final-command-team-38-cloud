package ru.prodcontest.app.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO для обновления статуса активности")
public class ActiveDto {
    @Schema(description = "Статус активности", example = "true")
    private boolean isActive;
}
package ru.prodcontest.app.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "DTO для подтверждения запроса ментора")
public class ApproveDto {

    @Schema(description = "Идентификатор запроса ментора", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    private UUID mentorRequestId;

    @Schema(description = "Признак подтверждения", example = "true")
    private boolean approve;
}
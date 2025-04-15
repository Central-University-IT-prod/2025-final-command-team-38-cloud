package ru.prodcontest.app.controller.dto.statsDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Статистика запросов на доступ ментора")
public class MentorRequestAccess {

    @Schema(description = "Общее количество запросов на доступ", example = "200")
    long request;

    @Schema(description = "Количество менторов, получивших доступ", example = "150")
    long mentor;

    @Schema(description = "Конверсия запросов в предоставленный доступ (менторы / запросы)", example = "0.75")
    public double getConversion() {
        return (double) mentor / (request != 0 ? request : 1);
    }
}
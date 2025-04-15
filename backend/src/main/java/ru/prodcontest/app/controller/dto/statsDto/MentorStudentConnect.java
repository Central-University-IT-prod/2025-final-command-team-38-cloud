package ru.prodcontest.app.controller.dto.statsDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Статистика соединений между менторами и студентами")
public class MentorStudentConnect {

    @Schema(description = "Общее количество соединений", example = "500")
    long connectionCount;

    @Schema(description = "Количество активных соединений в данный момент", example = "200")
    long nowConnected;

    @Schema(description = "Количество соединений, которые были активны в прошлом", example = "100")
    long wasConnected;

    @Schema(description = "Общая конверсия соединений ((активные + бывшие) / общее количество)", example = "0.6")
    public double getConversion() {
        return (double) (wasConnected + nowConnected) / (connectionCount != 0 ? connectionCount : 1);
    }
}
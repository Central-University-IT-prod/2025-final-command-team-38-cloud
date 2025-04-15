package ru.prodcontest.app.controller.dto.statsDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Статистика активности студентов и менторов")
public class Activity {

    @Schema(description = "Общее количество студентов", example = "1000")
    long studentCount;

    @Schema(description = "Количество студентов, проявивших активность", example = "500")
    long studentActivityCount;

    @Schema(description = "Общее количество менторов", example = "100")
    long mentorCount;

    @Schema(description = "Количество менторов, проявивших активность", example = "75")
    long mentorActivityCount;

    @Schema(description = "Конверсия студентов (активные / общее количество)", example = "0.5")
    public double getStudentConversion() {
        return (double) studentActivityCount / (studentCount != 0 ? studentCount : 1);
    }

    @Schema(description = "Конверсия менторов (активные / общее количество)", example = "0.75")
    public double getMentorConversion() {
        return (double) mentorActivityCount / (mentorCount != 0 ? mentorCount : 1);
    }
}
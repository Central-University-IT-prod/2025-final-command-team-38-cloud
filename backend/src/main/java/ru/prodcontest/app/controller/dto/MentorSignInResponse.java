package ru.prodcontest.app.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.prodcontest.app.entity.MentorRequest;

@Data
@Schema(description = "Ответ при входе ментора")
public class MentorSignInResponse {

    @Schema(description = "Статус запроса на вход", example = "ACTIVE")
    private MentorRequest.Status status = MentorRequest.Status.ACTIVE;

    @Schema(description = "Токен авторизации", example = "REDACTED")
    private String token;

    public MentorSignInResponse(MentorRequest.Status status) {
        this.status = status;
    }

    public MentorSignInResponse(String token) {
        this.token = token;
    }
}
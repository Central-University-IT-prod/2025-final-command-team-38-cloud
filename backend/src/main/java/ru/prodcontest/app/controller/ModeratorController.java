package ru.prodcontest.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.prodcontest.app.ChunkRequest;
import ru.prodcontest.app.controller.dto.ApproveDto;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.MentorRequest;
import ru.prodcontest.app.entity.Moderator;
import ru.prodcontest.app.service.interfaces.MentorService;
import ru.prodcontest.app.service.interfaces.ModeratorService;

@Tag(name = "6. ModeratorController: Действия модераторов")
@RestController
@RequestMapping("/api/moderator")
@CrossOrigin
public class ModeratorController {
    @Autowired
    private ModeratorService moderatorService;
    @Autowired
    private MentorService mentorService;

    @PostMapping("/approve")
    @Operation(summary = "Одобрение или отклонение запроса ментора", description = "Позволяет модератору одобрить или отклонить запрос на регистрацию ментора")
    @ApiResponse(responseCode = "200", description = "Запрос успешно обработан")
    @ApiResponse(responseCode = "403", description = "Доступ запрещен (неверный логин модератора)")
    @ApiResponse(responseCode = "404", description = "Запрос или модератор не найден")
    public Mentor approve(
            @Parameter(description = "Данные для одобрения/отклонения запроса", required = true)
            @RequestBody @Validated ApproveDto approveDto,
            @Parameter(description = "Логин модератора", required = true)
            @RequestHeader("Authorization") String email) {
        return moderatorService.approve(
                moderatorService.getByLogin(email),
                mentorService.getRequest(approveDto.getMentorRequestId()),
                approveDto.isApprove()
        );
    }

    @GetMapping("/requests")
    @Operation(summary = "Получение списка запросов на регистрацию", description = "Возвращает список запросов на регистрацию менторов с пагинацией")
    @ApiResponse(responseCode = "200", description = "Список запросов успешно получен")
    @ApiResponse(responseCode = "403", description = "Доступ запрещен (неверный логин модератора)")
    public Iterable<MentorRequest> getRequests(
            @Parameter(description = "Логин модератора", required = true)
            @RequestHeader("Authorization") String login,
            @Parameter(description = "Лимит количества результатов (по умолчанию 5)")
            @RequestParam(defaultValue = "5", required = false) int limit,
            @Parameter(description = "Смещение (по умолчанию 0)")
            @RequestParam(defaultValue = "0", required = false) int offset) {
        moderatorService.getByLogin(login);
        return moderatorService.waitingRequests(new ChunkRequest(offset, limit));
    }
    @GetMapping
    public Iterable<Moderator> getModerators(){
        return moderatorService.getAll();
    }
}
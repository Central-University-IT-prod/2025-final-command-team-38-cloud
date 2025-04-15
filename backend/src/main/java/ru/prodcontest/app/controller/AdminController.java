package ru.prodcontest.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.prodcontest.app.controller.dto.UrlDto;
import ru.prodcontest.app.entity.Connection;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.Moderator;
import ru.prodcontest.app.service.interfaces.AdminService;
import ru.prodcontest.app.service.interfaces.ConnectionService;
import ru.prodcontest.app.service.interfaces.MentorService;
import ru.prodcontest.app.service.interfaces.ModeratorService;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "5. AdminController: Действия администратора")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MentorService mentorService;
    @Autowired
    private ModeratorService moderatorService;
//    @Autowired
//    private ConnectionService connectionService;

    @PostMapping("/createModerator")
    @Operation(summary = "Создание модератора", description = "Создает нового модератора")
    @ApiResponse(responseCode = "200", description = "Модератор успешно создан")
    @ApiResponse(responseCode = "403", description = "Доступ запрещен (неверный пароль администратора)")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    public Moderator addModerator(
            @Parameter(description = "Данные модератора", required = true)
            @RequestBody Moderator moderator,
            @Parameter(description = "Токен администратора", required = true)
            @RequestHeader("Authorization") String adminPassword) {
        adminService.adminOrException(adminPassword);
        moderatorService.register(moderator);
        return moderator;
    }

    @DeleteMapping("/deleteModerator/{moderatorId}")
    @Operation(summary = "Удаление модератора", description = "Удаляет модератора по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Модератор успешно удален")
    @ApiResponse(responseCode = "403", description = "Доступ запрещен (неверный пароль администратора)")
    @ApiResponse(responseCode = "404", description = "Модератор не найден")
    public void deleteModerator(
            @Parameter(description = "Идентификатор модератора", required = true)
            @PathVariable UUID moderatorId,
            @Parameter(description = "Токен администратора", required = true)
            @RequestHeader("Authorization") String adminPassword) {
        adminService.adminOrException(adminPassword);
        moderatorService.delete(moderatorId);
    }

    @PostMapping("/createMentor")
    @Operation(summary = "Создание ментора", description = "Создает нового ментора")
    @ApiResponse(responseCode = "200", description = "Ментор успешно создан")
    @ApiResponse(responseCode = "403", description = "Доступ запрещен (неверный пароль администратора)")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    public void createMentor(
            @Parameter(description = "Данные ментора", required = true)
            @RequestBody @Validated Mentor mentor,
            @Parameter(description = "Токен администратора", required = true)
            @RequestHeader("Authorization") String adminPassword) {
        adminService.adminOrException(adminPassword);
        mentorService.add(mentor);
    }

    @DeleteMapping("/deleteMentor/{mentorId}")
    @Operation(summary = "Удаление ментора", description = "Удаляет ментора по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Ментор успешно удален")
    @ApiResponse(responseCode = "403", description = "Доступ запрещен (неверный пароль администратора)")
    @ApiResponse(responseCode = "404", description = "Ментор не найден")
    public void delMentor(
            @Parameter(description = "Идентификатор ментора", required = true)
            @PathVariable UUID mentorId,
            @Parameter(description = "Токен администратора", required = true)
            @RequestHeader("Authorization") String adminPassword) {
        adminService.adminOrException(adminPassword);
        mentorService.del(mentorId);
    }

    @GetMapping("/getModerators")
    @Operation(summary = "Получить список модераторов", description = "Возвращает список всех модераторов. Требуется администраторский пароль в заголовке Authorization.")
    public Iterable<Moderator> getModerators(
            @Parameter(description = "Администраторский пароль (Authorization header)", required = true)
            @RequestHeader("Authorization") String adminPassword) {
        adminService.adminOrException(adminPassword);
        return moderatorService.getAll();
    }
//    @PostMapping("/{connectionId}/setUrl")
//    void connection(@RequestHeader("Authorization") String adminPassword, @PathVariable UUID connectionId, @RequestBody UrlDto urlDto) {
//        adminService.adminOrException(adminPassword);
//        Connection connection = connectionService.findConnection(connectionId);
//        connection.setUrl(urlDto.getUrl());
//        connectionService.add(connection);
//    }
//    @GetMapping("/{connectionId}/getUrl")
//    UrlDto connection(@RequestHeader("Authorization") String adminPassword, @PathVariable UUID connectionId) {
//        adminService.adminOrException(adminPassword);
//        Connection connection = connectionService.findConnection(connectionId);
//        return new UrlDto(connection.getUrl());
//    }
}

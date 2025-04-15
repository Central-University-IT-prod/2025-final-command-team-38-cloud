package ru.prodcontest.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.prodcontest.app.ChunkRequest;
import ru.prodcontest.app.entity.Connection;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.MentorReview;
import ru.prodcontest.app.entity.Student;
import ru.prodcontest.app.service.interfaces.ConnectionService;
import ru.prodcontest.app.service.interfaces.MentorReviewService;
import ru.prodcontest.app.service.interfaces.MentorService;
import ru.prodcontest.app.service.interfaces.StudentService;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "4. ReviewController: Действия с отзывами на менторов")
@RequestMapping("/api/review")
@CrossOrigin
public class ReviewController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private MentorService mentorService;
    @Autowired
    private MentorReviewService mentorReviewService;

    @PostMapping("/{mentorId}")
    @Operation(summary = "Добавление отзыва на ментора", description = "Позволяет студенту добавить отзыв на ментора")
    @ApiResponse(responseCode = "200", description = "Отзыв успешно добавлен")
    @ApiResponse(responseCode = "403", description = "Доступ запрещен (нет активной связи с ментором)")
    @ApiResponse(responseCode = "404", description = "Ментор или студент не найден")
    private void postReview(
            @Parameter(description = "Токен авторизации студента", required = true)
            @RequestHeader("Authorization") String studentToken,
            @Parameter(description = "Идентификатор ментора", required = true)
            @PathVariable UUID mentorId,
            @Parameter(description = "Рейтинг от 1 до 5", required = true)
            @RequestParam int rating,
            @Parameter(description = "Комментарий к отзыву (опционально)")
            @RequestParam(required = false) String comment) {
        Student student = studentService.getByToken(studentToken);
        Mentor mentor = mentorService.get(mentorId);
        Connection connection = connectionService.findConnection(mentor, student);
        if (connection.getStatus() != Connection.Status.ACTIVE && connection.getStatus() != Connection.Status.STOP) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        mentorReviewService.postReview(student, mentor, rating, comment);
    }

    @GetMapping("/{mentorId}")
    @Operation(summary = "Получение отзывов на ментора", description = "Возвращает список отзывов на ментора с пагинацией")
    @ApiResponse(responseCode = "200", description = "Список отзывов успешно получен")
    @ApiResponse(responseCode = "404", description = "Ментор не найден")
    private List<MentorReview> getReviews(
            @Parameter(description = "Идентификатор ментора", required = true)
            @PathVariable UUID mentorId,
            @Parameter(description = "Смещение (по умолчанию 0)")
            @RequestParam(required = false, defaultValue = "0") int offset,
            @Parameter(description = "Лимит количества результатов (по умолчанию 5)")
            @RequestParam(required = false, defaultValue = "5") int limit) {
        Mentor mentor = mentorService.get(mentorId);
        return mentorReviewService.getReviews(mentor, new ChunkRequest(offset, limit));
    }
}
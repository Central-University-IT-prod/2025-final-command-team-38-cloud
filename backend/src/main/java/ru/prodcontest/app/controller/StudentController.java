package ru.prodcontest.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.prodcontest.app.controller.dto.SignInDto;
import ru.prodcontest.app.controller.dto.StudentSignUpDto;
import ru.prodcontest.app.entity.Connection;
import ru.prodcontest.app.entity.Student;
import ru.prodcontest.app.repository.ConnectionRep;
import ru.prodcontest.app.repository.MentorRep;
import ru.prodcontest.app.repository.StudentRep;
import ru.prodcontest.app.service.interfaces.ConnectionService;
import ru.prodcontest.app.service.interfaces.MentorService;
import ru.prodcontest.app.service.interfaces.StudentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/student")
@Tag(name = "1. StudentController: Действия со студентами")
@CrossOrigin()
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private MentorService mentorService;

    @PostMapping("/sign-up")
    @Operation(summary = "Регистрация студента", description = "Регистрирует нового студента в системе")
    @ApiResponse(responseCode = "200", description = "Успешная регистрация")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    public Student signUp(@RequestBody StudentSignUpDto signInDto) {
        return studentService.add(signInDto);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Авторизация студента", description = "Авторизует студента в системе")
    @ApiResponse(responseCode = "200", description = "Успешная авторизация")
    @ApiResponse(responseCode = "404", description = "Студент не найден")
    public Student signIn(@RequestBody SignInDto student) {
        return studentService.getByToken(student.getEmail());
    }

    @GetMapping("/{studentId}")
    @Operation(summary = "Получение информации о студенте", description = "Возвращает информацию о студенте по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Информация о студенте найдена")
    @ApiResponse(responseCode = "404", description = "Студент не найден")
    public Student getStudent(
            @Parameter(description = "Идентификатор студента", required = true)
            @PathVariable UUID studentId) {
        return studentService.get(studentId);
    }

    @PostMapping("/connectionRequest/{mentorId}")
    @Operation(summary = "Запрос на подключение к ментору", description = "Отправляет запрос на подключение к ментору")
    @ApiResponse(responseCode = "200", description = "Запрос успешно отправлен")
    @ApiResponse(responseCode = "404", description = "Ментор или студент не найден")
    public void sendMentorConnectionRequest(
            @Parameter(description = "Идентификатор ментора", required = true)
            @PathVariable UUID mentorId,
            @Parameter(description = "Токен авторизации студента", required = true)
            @RequestHeader("Authorization") String token) {
        Student student = studentService.getByToken(token);
        connectionService.add(new Connection(
                mentorService.get(mentorId),
                student));
    }
}
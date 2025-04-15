package ru.prodcontest.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.prodcontest.app.entity.Connection;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.Student;
import ru.prodcontest.app.service.interfaces.ConnectionService;
import ru.prodcontest.app.service.interfaces.MentorService;
import ru.prodcontest.app.service.interfaces.StudentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/connection")
@Tag(name = "3. ConnectionController: Действия со связями студент-ментор")
@CrossOrigin()
public class ConnectionController {
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private MentorService mentorService;
    @Autowired
    private StudentService studentService;

    @PostMapping("/create/{mentorId}")
    @Operation(
            summary = "Создание связи между студентом и ментором",
            description = "Создает связь между студентом и ментором по ID ментора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Связь успешно создана"),
                    @ApiResponse(responseCode = "404", description = "Ментор не найден")
            }
    )
    Connection createConnection(
            @Parameter(description = "Токен доступа студента", required = true)
            @RequestHeader(name = "Authorization", required = false) String email,
            @Parameter(description = "ID ментора", required = true)
            @PathVariable UUID mentorId) {
        Student student = studentService.getByToken(email);
        Mentor mentor = mentorService.get(mentorId);
        return connectionService.add(new Connection(mentor, student));
    }

    @PostMapping("/mentor/stop/{studentId}")
    @Operation(
            summary = "Остановка связи ментором",
            description = "Останавливает связь между ментором и студентом по ID студента",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Связь успешно остановлена"),
                    @ApiResponse(responseCode = "404", description = "Студент или ментор не найдены")
            }
    )
    Connection stopConnection(
            @Parameter(description = "Токен доступа ментора", required = true)
            @RequestHeader(name = "Authorization", required = false) String email,
            @Parameter(description = "ID студента", required = true)
            @PathVariable UUID studentId) {
        Mentor mentor = mentorService.getByToken(email);
        Student student = studentService.get(studentId);
        Connection connection = connectionService.findConnection(mentor, student);
        connectionService.stop(connection);
        return connection;
    }

    @PostMapping("/mentor/cancel/{studentId}")
    @Operation(
            summary = "Отмена связи ментором",
            description = "Отменяет связь между ментором и студентом по ID студента",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Связь успешно отменена"),
                    @ApiResponse(responseCode = "404", description = "Студент или ментор не найдены")
            }
    )
    Connection cancelConnectionMentor(
            @Parameter(description = "Токен доступа ментора", required = true)
            @RequestHeader(name = "Authorization", required = false) String email,
            @Parameter(description = "ID студента", required = true)
            @PathVariable UUID studentId) {
        Mentor mentor = mentorService.getByToken(email);
        Student student = studentService.get(studentId);
        Connection connection = connectionService.findConnection(mentor, student);
        connectionService.cancel(connection);
        return connection;
    }

    @PostMapping("/student/cancel/{mentorId}")
    Connection cancelConnectionStudent(@RequestHeader(name = "Authorization", required = false) String email,
                                       @PathVariable UUID mentorId) {
        Student student = studentService.getByToken(email);
        Mentor mentor = mentorService.get(mentorId);
        Connection connection = connectionService.findConnection(mentor, student);
        connectionService.cancel(connection);
        return connection;
    }

    @PostMapping("/mentor/approve/{studentId}")
    @Operation(
            summary = "Одобрение связи ментором",
            description = "Одобряет связь между ментором и студентом по ID студента",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Связь успешно одобрена"),
                    @ApiResponse(responseCode = "404", description = "Студент или ментор не найдены")
            }
    )
    Connection approveConnection(
            @Parameter(description = "Токен доступа ментора", required = true)
            @RequestHeader(name = "Authorization", required = false) String email,
            @Parameter(description = "ID студента", required = true)
            @PathVariable UUID studentId) {
        Mentor mentor = mentorService.getByToken(email);
        Student student = studentService.get(studentId);
        Connection connection = connectionService.findConnection(mentor, student);
        connectionService.approve(connection);
        return connection;
    }

    @GetMapping("/mentor/myRequests")
    @Operation(
            summary = "Получение запросов ментора",
            description = "Получает список запросов к ментору с указанными статусами",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список запросов успешно получен"),
                    @ApiResponse(responseCode = "404", description = "Ментор не найден")
            }
    )
    Iterable<Connection> getMyRequestsMentor(
            @Parameter(description = "Токен доступа ментора", required = true)
            @RequestHeader(name = "Authorization", required = false) String email,
            @Parameter(description = "Список статусов запросов (например, WAITING, ACTIVE, CANCELED).  Можно указать несколько значений.")
            @RequestParam List<Connection.Status> statuses) {
        Mentor mentor = mentorService.getByToken(email);
        return connectionService.getByMentorAndStatus(mentor, statuses);
    }

    @GetMapping("/student/myRequests")
    @Operation(
            summary = "Получение запросов студента",
            description = "Получает список запросов от студента с указанными статусами",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список запросов успешно получен"),
                    @ApiResponse(responseCode = "404", description = "Студент не найден")
            }
    )
    Iterable<Connection> getMyRequestsStudent(
            @Parameter(description = "Токен доступа студента", required = true)
            @RequestHeader(name = "Authorization", required = false) String email,
            @Parameter(description = "Список статусов запросов (например WAIT, ACTIVE). Можно указать несколько значений.")
            @RequestParam List<Connection.Status> statuses) {
        Student student = studentService.getByToken(email);
        return connectionService.getByStudentAndStatus(student, statuses);
    }
    @GetMapping("/mentor/myRequests/all")
    @Operation(
            summary = "Получение запросов ментора",
            description = "Получает список запросов к ментору с указанными статусами",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список запросов успешно получен"),
                    @ApiResponse(responseCode = "404", description = "Ментор не найден")
            }
    )
    Iterable<Connection> getMyRequestsMentor(
            @Parameter(description = "Токен доступа ментора", required = true)
            @RequestHeader(name = "Authorization", required = false) String email) {
        Mentor mentor = mentorService.getByToken(email);
        return connectionService.getByMentorAndStatus(mentor, List.of(Connection.Status.REQUEST));
    }

    @GetMapping("/student/myRequests/all")
    @Operation(
            summary = "Получение запросов студента",
            description = "Получает список запросов от студента с указанными статусами",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список запросов успешно получен"),
                    @ApiResponse(responseCode = "404", description = "Студент не найден")
            }
    )
    Iterable<Connection> getMyRequestsStudent(
            @Parameter(description = "Токен доступа студента", required = true)
            @RequestHeader(name = "Authorization", required = false) String email) {
        Student student = studentService.getByToken(email);
        return connectionService.getByStudentAndStatus(student, List.of(Connection.Status.REQUEST));
    }
    @GetMapping("/get/{studentId}/{mentorId}")
    @Operation(summary = "Получить соединение между студентом и ментором", description = "Возвращает информацию о соединении между указанным студентом и ментором.")
    @ApiResponse(responseCode = "200", description = "Успешно найдено соединение")
    public Connection connection(
            @Parameter(description = "ID студента", required = true) @PathVariable UUID studentId,
            @Parameter(description = "ID ментора", required = true) @PathVariable UUID mentorId) {
        Mentor mentor = mentorService.get(mentorId);
        Student student = studentService.get(studentId);
        return connectionService.findConnection(mentor, student);
    }

    @GetMapping("/{connectionId}")
    @Operation(summary = "Получить соединение по ID", description = "Возвращает информацию о соединении по указанному ID.")
    @ApiResponse(responseCode = "200", description = "Успешно найдено соединение")
    public Connection connection(
            @Parameter(description = "ID соединения", required = true) @PathVariable UUID connectionId) {
        return connectionService.findConnection(connectionId);
    }
}

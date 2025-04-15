package ru.prodcontest.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.prodcontest.app.ChunkRequest;
import ru.prodcontest.app.controller.dto.ActiveDto;
import ru.prodcontest.app.controller.dto.MentorSignInResponse;
import ru.prodcontest.app.controller.dto.PatchMentorDto;
import ru.prodcontest.app.controller.dto.SignInDto;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.MentorRequest;
import ru.prodcontest.app.service.interfaces.MentorService;

import java.util.Set;
import java.util.UUID;

@RestController
@Tag(name = "2. MentorController: Действия со связями студент-ментор")
@RequestMapping("/api/mentor")
@CrossOrigin()
public class MentorController {
    @Autowired
    private MentorService mentorService;

    @PostMapping("/sign-in")
    @Operation(summary = "Авторизация ментора", description = "Авторизует ментора в системе")
    @ApiResponse(responseCode = "200", description = "Успешная авторизация")
    @ApiResponse(responseCode = "404", description = "Ментор не найден")
    public MentorSignInResponse signIn(@RequestBody SignInDto signInDto) {
        if (mentorService.existsByEmail(signInDto.getEmail()))
            return new MentorSignInResponse(signInDto.getEmail());
        else {
            MentorRequest mentorRequest = mentorService.getRequestByEmailOrNull(signInDto.getEmail());
            if (mentorRequest == null) {
                return new MentorSignInResponse(MentorRequest.Status.NOT_FOUND);
            } else {
                return new MentorSignInResponse(mentorRequest.getStatus());
            }
        }
    }

    @PostMapping
    @Operation(summary = "Добавление запроса ментора", description = "Добавляет запрос на регистрацию ментора")
    @ApiResponse(responseCode = "200", description = "Запрос успешно добавлен")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    public MentorRequest addMentorRequest(@RequestBody MentorRequest mentor) {
        return mentorService.addRequest(mentor);
    }

    @GetMapping("/{mentorId}")
    @Operation(summary = "Получение информации о менторе", description = "Возвращает информацию о менторе по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Информация о менторе найдена")
    @ApiResponse(responseCode = "404", description = "Ментор не найден")
    public Mentor getMentor(
            @Parameter(description = "Идентификатор ментора", required = true)
            @PathVariable UUID mentorId) {
        return mentorService.get(mentorId);
    }

    @GetMapping("/get/{email}")
    @Operation(summary = "Получение информации о менторе по email", description = "Возвращает информацию о менторе по его email")
    @ApiResponse(responseCode = "200", description = "Информация о менторе найдена")
    @ApiResponse(responseCode = "404", description = "Ментор не найден")
    public Mentor getMentor(
            @Parameter(description = "Email ментора", required = true)
            @PathVariable String email) {
        return mentorService.getByEmail(email);
    }

    @GetMapping("/request/{mentorId}")
    @Operation(summary = "Получение запроса ментора по идентификатору", description = "Возвращает запрос на регистрацию ментора по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Запрос найден")
    @ApiResponse(responseCode = "404", description = "Запрос не найден")
    public MentorRequest getRequestMentor(
            @Parameter(description = "Идентификатор запроса ментора", required = true)
            @PathVariable UUID mentorId) {
        return mentorService.getRequest(mentorId);
    }

    @GetMapping("/request/get")
    @Operation(summary = "Получение запроса ментора по email", description = "Возвращает запрос на регистрацию ментора по его email")
    @ApiResponse(responseCode = "200", description = "Запрос найден")
    @ApiResponse(responseCode = "404", description = "Запрос не найден")
    public MentorRequest getRequestMentor(
            @Parameter(description = "Email ментора", required = true)
            @PathVariable String email) {
        return mentorService.getRequestByEmail(email);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех менторов", description = "Возвращает список всех зарегистрированных менторов")
    @ApiResponse(responseCode = "200", description = "Список менторов успешно получен")
    public Iterable<Mentor> getMentorAll() {
        return mentorService.getAll();
    }

    @GetMapping
    @Operation(summary = "Поиск менторов по параметрам", description = "Возвращает список менторов, соответствующих заданным параметрам")
    @ApiResponse(responseCode = "200", description = "Список менторов успешно получен")
    public Iterable<Mentor> getMentor(
            @Parameter(description = "Стек технологий (опционально)")
            @RequestParam(required = false) @Validated Set<@NotBlank @Length(min = 2, max = 50) String> stack,
            @Parameter(description = "Минимальная стоимость")
            @RequestParam(required = false) Integer minCost,
            @Parameter(description = "Максимальная стоимость")
            @RequestParam(required = false) Integer maxCost,
            @Parameter(description = "Лимит количества результатов")
            @RequestParam(defaultValue = "5", required = false) int limit,
            @Parameter(description = "Смещение)")
            @RequestParam(defaultValue = "0", required = false) int offset) {
        return mentorService.getWithStack(stack, minCost, maxCost, new ChunkRequest(offset, limit));
    }

    @PostMapping("/active")
    @Operation(summary = "Установка активности ментора", description = "Устанавливает активность ментора (активен/неактивен)")
    @ApiResponse(responseCode = "200", description = "Активность успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Ментор не найден")
    public Mentor setActive(
            @Parameter(description = "Email ментора", required = true)
            @RequestHeader("Authorization") String email,
            @Parameter(description = "Объект с флагом активности", required = true)
            @RequestBody ActiveDto activeDto) {
        return mentorService.setActive(mentorService.getByEmail(email), activeDto.isActive());
    }

    @PutMapping
    @Operation(summary = "Редактирование профиля ментора", description = "Позволяет ментору изменять свой профиль")
    @ApiResponse(responseCode = "200", description = "Профиль ментора обновлен")
    @ApiResponse(responseCode = "404", description = "Ментор не найден")
    public void patchMentor(@Parameter(description = "Токен ментора", required = true)
                            @RequestHeader("Authorization") String email,
                            @Parameter(description = "Объект с полями для изменения", required = true)
                            @RequestBody PatchMentorDto patchMentorDto) {
        MentorRequest mentor = mentorService.getByEmail(email).asRequest();
        if (patchMentorDto.getPhoto() != null) {
            mentor.setPhoto(patchMentorDto.getPhoto());
        }
        if (patchMentorDto.getFirstName() != null) {
            mentor.setFirstName(patchMentorDto.getFirstName());
        }
        if (patchMentorDto.getLastName() != null) {
            mentor.setLastName(patchMentorDto.getLastName());
        }
        if (patchMentorDto.getAge() != null) {
            mentor.setAge(patchMentorDto.getAge());
        }
        if (patchMentorDto.getTelegram() != null) {
            mentor.setTelegram(patchMentorDto.getTelegram());
        }
        if (patchMentorDto.getResources() != null) {
            mentor.setResources(patchMentorDto.getResources());
        }
        if (patchMentorDto.getBio() != null) {
            mentor.setBio(patchMentorDto.getBio());
        }
        if (patchMentorDto.getExperience() != null) {
            mentor.setExperience(patchMentorDto.getExperience());
        }
        if (patchMentorDto.getCostPerHour() != null) {
            mentor.setCostPerHour(patchMentorDto.getCostPerHour());
        }
        mentorService.addRequest(mentor);
    }
}
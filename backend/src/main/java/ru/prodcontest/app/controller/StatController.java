package ru.prodcontest.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.prodcontest.app.controller.dto.statsDto.Activity;
import ru.prodcontest.app.controller.dto.statsDto.MentorRequestAccess;
import ru.prodcontest.app.controller.dto.statsDto.MentorStudentConnect;
import ru.prodcontest.app.service.interfaces.AdminService;
import ru.prodcontest.app.service.interfaces.MentorService;
import ru.prodcontest.app.service.interfaces.StatService;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/stat")
@Tag(name = "7. StatController: Получение админом аналитики")
@CrossOrigin
public class StatController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StatService statService;
    @Autowired
    private MentorService mentorService;

    @GetMapping("/activity")
    Activity activity(@RequestHeader("Authorization") String adminPassword) {
        adminService.adminOrException(adminPassword);
        return statService.mentorStudentConnectByMentor();
    }

    @GetMapping("/activity/add/{mentorId}")
    MentorStudentConnect activityPost(@RequestHeader(value = "Authorization", required = false) String adminPassword,
                                      @PathVariable UUID mentorId) {
        adminService.adminOrException(adminPassword);
        return statService.mentorStudentConnectByMentor(mentorService.get(mentorId));
    }

    @GetMapping("/mentorRequestAccess")
    MentorRequestAccess mentorRequestAccess(@RequestHeader("Authorization") String adminPassword) {
        adminService.adminOrException(adminPassword);
        return statService.mentorRequestAccess();
    }

    @GetMapping("/mentorStudentConnect")
    MentorStudentConnect mentorStudentConnect(@RequestHeader("Authorization") String adminPassword) {
        adminService.adminOrException(adminPassword);
        return statService.mentorStudentConnect();
    }
}

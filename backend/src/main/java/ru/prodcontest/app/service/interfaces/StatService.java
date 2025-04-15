package ru.prodcontest.app.service.interfaces;

import org.springframework.stereotype.Service;
import ru.prodcontest.app.controller.dto.statsDto.Activity;
import ru.prodcontest.app.controller.dto.statsDto.MentorRequestAccess;
import ru.prodcontest.app.controller.dto.statsDto.MentorStudentConnect;
import ru.prodcontest.app.entity.Mentor;

@Service
public interface StatService {
    Activity mentorStudentConnectByMentor();
    MentorRequestAccess mentorRequestAccess();
    MentorStudentConnect mentorStudentConnect();
    MentorStudentConnect mentorStudentConnectByMentor(Mentor mentor);
}
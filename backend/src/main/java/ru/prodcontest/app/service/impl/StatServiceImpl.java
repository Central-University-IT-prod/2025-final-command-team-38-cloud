package ru.prodcontest.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prodcontest.app.controller.dto.statsDto.Activity;
import ru.prodcontest.app.controller.dto.statsDto.MentorRequestAccess;
import ru.prodcontest.app.controller.dto.statsDto.MentorStudentConnect;
import ru.prodcontest.app.entity.Connection;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.repository.ConnectionRep;
import ru.prodcontest.app.repository.MentorRep;
import ru.prodcontest.app.repository.MentorRequestRep;
import ru.prodcontest.app.repository.StudentRep;
import ru.prodcontest.app.service.interfaces.StatService;

import java.util.List;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private MentorRep mentorRep;
    @Autowired
    private StudentRep studentRep;
    @Autowired
    private MentorRequestRep mentorRequestRep;
    @Autowired
    private ConnectionRep connectionRep;

    @Override
    public Activity mentorStudentConnectByMentor() {
        return Activity.builder()
                .mentorCount(mentorRep.count())
                .mentorActivityCount(connectionRep.countMentorActive())
                .studentCount(studentRep.count())
                .studentActivityCount(connectionRep.countStudentActive())
                .build();
    }

    @Override
    public MentorRequestAccess mentorRequestAccess() {
        return MentorRequestAccess.builder()
                .request(mentorRequestRep.count())
                .mentor(mentorRep.count())
                .build();
    }

    @Override
    public MentorStudentConnect mentorStudentConnect() {
        return MentorStudentConnect.builder()
                .connectionCount(connectionRep.count())
                .nowConnected(connectionRep.countByStatusIn(List.of(Connection.Status.ACTIVE)))
                .wasConnected(connectionRep.countByStatusIn(List.of(Connection.Status.STOP)))
                .build();
    }

    @Override
    public MentorStudentConnect mentorStudentConnectByMentor(Mentor mentor) {
        return MentorStudentConnect.builder()
                .connectionCount(connectionRep.countByMentor(mentor))
                .nowConnected(connectionRep.countByMentorIsAndStatusIn(mentor, List.of(Connection.Status.ACTIVE)))
                .wasConnected(connectionRep.countByMentorIsAndStatusIn(mentor, List.of(Connection.Status.STOP)))
                .build();
    }
}

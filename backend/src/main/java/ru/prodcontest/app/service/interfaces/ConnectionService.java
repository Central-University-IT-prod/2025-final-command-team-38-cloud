package ru.prodcontest.app.service.interfaces;

import org.springframework.stereotype.Service;
import ru.prodcontest.app.entity.Connection;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.Student;

import java.util.List;
import java.util.UUID;

@Service
public interface ConnectionService {
    Connection add(Connection newConnection);
    void approve(Connection connection);
    void stop(Connection connection);
    void cancel(Connection connection);
    Connection findConnection(Mentor mentor, Student student);
    Connection findConnection(UUID connectionId);
    Iterable<Connection> getByMentorAndStatus(Mentor mentor, List<Connection.Status> statusList);
    Iterable<Connection> getByStudentAndStatus(Student student, List<Connection.Status> statusList);
}

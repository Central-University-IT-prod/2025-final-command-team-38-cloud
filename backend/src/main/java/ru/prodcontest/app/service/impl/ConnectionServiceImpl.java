package ru.prodcontest.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prodcontest.app.entity.Connection;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.Student;
import ru.prodcontest.app.repository.ConnectionRep;
import ru.prodcontest.app.service.interfaces.ConnectionService;

import java.util.List;
import java.util.UUID;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    ConnectionRep connectionRep;

    @Override
    public Connection add(Connection newConnection) {
        if (connectionRep.existsByMentorAndStudent(newConnection.getMentor(), newConnection.getStudent()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        return connectionRep.save(newConnection);
    }

    @Override
    public void approve(Connection connection) {
        if (connection.getStatus() != Connection.Status.REQUEST)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        connection.setStatus(Connection.Status.ACTIVE);
        connectionRep.save(connection);
    }

    @Override
    public void stop(Connection connection) {
        if (connection.getStatus() != Connection.Status.ACTIVE)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        connection.setStatus(Connection.Status.STOP);
        connectionRep.save(connection);
    }

    @Override
    public void cancel(Connection connection) {
        if (connection.getStatus() != Connection.Status.REQUEST)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        connection.setStatus(Connection.Status.CANCEL);
        connectionRep.save(connection);
    }

    @Override
    public Connection findConnection(Mentor mentor, Student student) {
        return connectionRep.findByMentorAndStudent(mentor, student).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Connection findConnection(UUID connectionId) {
        return connectionRep.findById(connectionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Iterable<Connection> getByMentorAndStatus(Mentor mentor, List<Connection.Status> statusList) {
        return connectionRep.findByMentorIsAndStatusIn(mentor, statusList);
    }

    @Override
    public Iterable<Connection> getByStudentAndStatus(Student student, List<Connection.Status> statusList) {
        return connectionRep.findByStudentIsAndStatusIn(student, statusList);
    }
}

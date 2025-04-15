package ru.prodcontest.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ru.prodcontest.app.controller.dto.StudentSignUpDto;
import ru.prodcontest.app.entity.Student;
import ru.prodcontest.app.repository.StudentRep;
import ru.prodcontest.app.service.interfaces.StudentService;

import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRep studentRep;

    @Override
    public Student get(UUID id) {
        return studentRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Student getByToken(String token) {
        return studentRep.findByEmail(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Student add(StudentSignUpDto signInDto) {
        if (studentRep.existsByEmail(signInDto.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        Student student = new Student();
        student.setFirstName(signInDto.getFirstName());
        student.setLastName(signInDto.getLastName());
        student.setEmail(signInDto.getEmail());
        student.setTelegram(signInDto.getTelegram());
        student.setStack(signInDto.getStack());
        studentRep.save(student);
        return student;
    }

    @Override
    public boolean existsByEmail(String email) {
        return studentRep.existsByEmail(email);
    }
}

package ru.prodcontest.app.service.interfaces;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import ru.prodcontest.app.controller.dto.StudentSignUpDto;
import ru.prodcontest.app.entity.Student;

import java.util.UUID;

@Service
public interface StudentService {
    Student get(UUID id);
    Student getByToken(String token);

    Student add(StudentSignUpDto signInDto);

    boolean existsByEmail(String email);
}

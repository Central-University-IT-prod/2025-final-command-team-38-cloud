package ru.prodcontest.app.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prodcontest.app.entity.Student;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRep extends CrudRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);

    boolean existsByEmail(@Email @NotNull String email);
}

package ru.prodcontest.app.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prodcontest.app.entity.MentorRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MentorRequestRep extends CrudRepository<MentorRequest, UUID> {
    boolean existsByEmail(String email);
    Optional<MentorRequest> findByEmail(String email);
    List<MentorRequest> findByStatus(Pageable pageable, MentorRequest.Status status);
}

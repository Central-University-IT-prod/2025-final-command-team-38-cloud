package ru.prodcontest.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prodcontest.app.entity.Moderator;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModeratorRep extends CrudRepository<Moderator, UUID> {
    Optional<Moderator> findByLogin(String login);
    boolean existsByLogin(String login);
}

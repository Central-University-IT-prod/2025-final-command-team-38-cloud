package ru.prodcontest.app.repository;

import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prodcontest.app.entity.Mentor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface MentorRep extends CrudRepository<Mentor, UUID> {
    @Query(value = "SELECT m FROM Mentor m " +
            "WHERE m.isActive IS TRUE AND " +
            ":minCost IS NULL OR :minCost <= m.costPerHour AND " +
            ":maxCost IS NULL OR :maxCost >= m.costPerHour")
    List<Mentor> getByQuery(Integer minCost, Integer maxCost, Pageable pageable);

    @Query(value = "SELECT m FROM Mentor m " +
            "INNER JOIN m.stack s " +
            "WHERE m.isActive IS TRUE AND " +
            ":minCost IS NULL OR :minCost <= m.costPerHour AND " +
            ":maxCost IS NULL OR :maxCost >= m.costPerHour AND s IN (:stack)")
    List<Mentor> getByQuery(Set<String> stack, Integer minCost, Integer maxCost, Pageable pageable);

    Optional<Mentor> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByTelegram(@Pattern(regexp = "^@.{5,}$", message = "Telegram должен начинаться с символа '@' и иметь минимум 5 символов после него") String telegram);
}

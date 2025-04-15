package ru.prodcontest.app.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.MentorReview;

import java.util.List;
import java.util.UUID;

@Repository
public interface MentorReviewRep extends CrudRepository<MentorReview, UUID> {
    List<MentorReview> findByMentor(Mentor mentor, Pageable pageable);
    int countByMentor(Mentor mentor);
}

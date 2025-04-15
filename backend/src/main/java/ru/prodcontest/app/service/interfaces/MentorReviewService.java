package ru.prodcontest.app.service.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.MentorReview;
import ru.prodcontest.app.entity.Student;

import java.util.List;

@Service
public interface MentorReviewService {
    void postReview(Student student, Mentor mentor, int rating, String comment);
    List<MentorReview> getReviews(Mentor mentor, Pageable pageable);
}

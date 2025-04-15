package ru.prodcontest.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.MentorReview;
import ru.prodcontest.app.entity.Student;
import ru.prodcontest.app.repository.MentorReviewRep;
import ru.prodcontest.app.service.interfaces.MentorReviewService;
import ru.prodcontest.app.service.interfaces.MentorService;

import java.util.List;
import java.util.UUID;

@Service
public class MentorReviewServiceImpl implements MentorReviewService {
    @Autowired
    private MentorReviewRep mentorReviewRep;
    @Autowired
    private MentorService mentorService;

    @Override
    public void postReview(Student student, Mentor mentor, int rating, String comment) {
        int reviewCount = mentorReviewRep.countByMentor(mentor);
        mentorReviewRep.save(new MentorReview(UUID.randomUUID(), student, mentor, rating, comment));
        mentor.setRating((mentor.getRating() * reviewCount + rating) / (reviewCount + 1));
        mentorService.save(mentor);
    }

    @Override
    public List<MentorReview> getReviews(Mentor mentor, Pageable pageable) {
        return mentorReviewRep.findByMentor(mentor, pageable);
    }
}

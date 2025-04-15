package ru.prodcontest.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.MentorRequest;
import ru.prodcontest.app.repository.MentorRep;
import ru.prodcontest.app.repository.MentorRequestRep;
import ru.prodcontest.app.service.interfaces.MentorService;

import java.util.Set;
import java.util.UUID;

@Service
public class MentorServiceImpl implements MentorService {
    @Autowired
    private MentorRep mentorRep;
    @Autowired
    private MentorRequestRep mentorRequestRep;

    @Override
    public MentorRequest addRequest(MentorRequest mentor) {
        return mentorRequestRep.save(mentor);
    }

    @Override
    public void delRequest(UUID uuid) {
        mentorRequestRep.deleteById(uuid);
    }

    @Override
    public Mentor add(Mentor mentor) {
        if (mentorRequestRep.existsByEmail(mentor.getEmail()) || mentorRep.existsByEmail(mentor.getEmail()) || mentorRep.existsByTelegram(mentor.getTelegram()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        return mentorRep.save(mentor);
    }

    @Override
    public void save(Mentor mentor) {
        mentorRep.save(mentor);
    }

    @Override
    public void del(UUID uuid) {
        mentorRep.deleteById(uuid);
    }

    @Override
    public Mentor get(UUID uuid) {
        return mentorRep.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public MentorRequest getRequest(UUID uuid) {
        return mentorRequestRep.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mentor setActive(Mentor mentor, boolean isActive) {
        mentor.setActive(isActive);
        mentorRep.save(mentor);
        return mentor;
    }

    @Override
    public Iterable<Mentor> getWithStack(Set<String> stack, Integer minCost, Integer maxCost, Pageable pageable) {
        if (stack == null) {
            return mentorRep.getByQuery(minCost, maxCost, pageable);
        } else {
            return mentorRep.getByQuery(stack, minCost, maxCost, pageable);
        }
    }

    @Override
    public Iterable<Mentor> getAll() {
        return mentorRep.findAll();
    }

    @Override
    public Mentor getByEmail(String email) {
        return mentorRep.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public MentorRequest getRequestByEmail(String email) {
        return mentorRequestRep.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public MentorRequest getRequestByEmailOrNull(String email) {
        return mentorRequestRep.findByEmail(email).orElse(null);
    }

    @Override
    public Mentor getByToken(String token) {
        return mentorRep.findByEmail(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public boolean existsByEmail(String email) {
        return mentorRep.existsByEmail(email);
    }

    @Override
    public Mentor requestToMentor(MentorRequest mentorRequest) {
        return Mentor.builder().age(mentorRequest.getAge())
                .bio(mentorRequest.getBio())
                .stack(mentorRequest.getStack())
                .firstName(mentorRequest.getFirstName())
                .lastName(mentorRequest.getLastName())
                .photo(mentorRequest.getPhoto())
                .costPerHour(mentorRequest.getCostPerHour())
                .experience(mentorRequest.getExperience())
                .email(mentorRequest.getEmail())
                .telegram(mentorRequest.getTelegram())
                .isActive(true)
                .resources(mentorRequest.getResources())
                .build();
    }
}

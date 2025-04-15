package ru.prodcontest.app.service.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.MentorRequest;

import java.util.Set;
import java.util.UUID;

@Service
public interface MentorService {
    Mentor add(Mentor mentor);
    void save(Mentor mentor);
    MentorRequest addRequest(MentorRequest mentor);
    void del(UUID uuid);
    void delRequest(UUID uuid);
    Mentor get(UUID uuid);
    MentorRequest getRequest(UUID uuid);
    Mentor setActive(Mentor mentor, boolean isActive);
    Iterable<Mentor> getAll();
    Mentor getByEmail(String email);
    MentorRequest getRequestByEmail(String email);
    MentorRequest getRequestByEmailOrNull(String email);
    Mentor getByToken(String token);
    Iterable<Mentor> getWithStack(Set<String> stack, Integer minCost, Integer maxCost, Pageable pageable);
    boolean existsByEmail(String email);
    Mentor requestToMentor(MentorRequest mentorRequest);
}

package ru.prodcontest.app.service.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.MentorRequest;
import ru.prodcontest.app.entity.Moderator;

import java.util.UUID;

@Service
public interface ModeratorService {
    Moderator getById(UUID id);
    Moderator getByLogin(String login);
    void register(Moderator moderator);
    void delete(UUID id);

    Mentor approve(Moderator moderator, MentorRequest request, boolean approved);
    Mentor approveWithoutModerator(MentorRequest request, boolean approved);
    Iterable<MentorRequest> waitingRequests(Pageable pageable);
    Iterable<Moderator> getAll();
}

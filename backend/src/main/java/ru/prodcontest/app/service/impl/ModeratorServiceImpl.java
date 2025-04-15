package ru.prodcontest.app.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.MentorRequest;
import ru.prodcontest.app.entity.Moderator;
import ru.prodcontest.app.repository.MentorRep;
import ru.prodcontest.app.repository.MentorRequestRep;
import ru.prodcontest.app.repository.ModeratorRep;
import ru.prodcontest.app.service.interfaces.ModeratorService;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class ModeratorServiceImpl implements ModeratorService {
    @Autowired
    private ModeratorRep moderatorRep;
    @Autowired
    private MentorRequestRep mentorRequestRep;
    @Autowired
    private MentorRep mentorRep;

    @Override
    public Moderator getById(UUID id) {
        return moderatorRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Moderator getByLogin(String login) {
        return moderatorRep.findByLogin(login).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Не нашли модератора"));
    }

    @Override
    public void register(Moderator moderator) {
        if (moderatorRep.existsByLogin(moderator.getLogin())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        moderatorRep.save(moderator);
    }

    @Override
    public void delete(UUID id) {
        moderatorRep.deleteById(id);
    }

    @Override
    public Mentor approve(Moderator moderator, MentorRequest request, boolean approved) {
        if (approved) {
            request.setStatus(MentorRequest.Status.ACTIVE);
            mentorRequestRep.save(request);
            Mentor mentor = request.asMentor();
            mentorRep.save(mentor);
            return mentor;
        } else {
            request.setStatus(MentorRequest.Status.CANCELED);
            mentorRequestRep.save(request);
            return null;
        }
    }

    @Override
    public Mentor approveWithoutModerator(MentorRequest request, boolean approved) {
        if (approved) {
            mentorRequestRep.delete(request);
//            return mentorRep.save(request); // TODO
            return null;
        } else {
            mentorRequestRep.save(request);
            return null;
        }
    }

    @Override
    public Iterable<MentorRequest> waitingRequests(Pageable pageable) {
        return mentorRequestRep.findByStatus(pageable, MentorRequest.Status.WAIT);
    }

    @Override
    public Iterable<Moderator> getAll() {
        return moderatorRep.findAll();
    }
}

package ru.prodcontest.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prodcontest.app.entity.Connection;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConnectionRep extends CrudRepository<Connection, UUID> {
    Optional<Connection> findByMentorAndStudent(Mentor mentor, Student student);
    boolean existsByMentorAndStudent(Mentor mentor, Student student);
    Iterable<Connection> findByMentorIsAndStatusIn(Mentor mentor, List<Connection.Status> statusList);
    Iterable<Connection> findByStudentIsAndStatusIn(Student student, List<Connection.Status> statusList);
    int countByStatusIn(List<Connection.Status> statusList);
    int countByMentorIsAndStatusIn(Mentor mentor, List<Connection.Status> statusList);
    @Query("SELECT COUNT(*) FROM Connection c WHERE c.status = 2 GROUP BY c.mentor")
    int countMentorActive();
    @Query("SELECT COUNT(*) FROM Connection c WHERE c.status = 2 GROUP BY c.student")
    int countStudentActive();
    long countByMentor(Mentor mentor);
    @Query("SELECT COUNT(*) FROM Connection c WHERE c.status = 2 and c.mentor = :mentor GROUP BY c.student")
    int countStudentActiveByMentor(Mentor mentor);
}

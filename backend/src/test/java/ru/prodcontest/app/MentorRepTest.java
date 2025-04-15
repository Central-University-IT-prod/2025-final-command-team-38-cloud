package ru.prodcontest.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.prodcontest.app.entity.Mentor;  // Замените на ваш фактический путь к классу
import ru.prodcontest.app.repository.MentorRep; // Замените на ваш фактический путь к классу

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MentorRepTest {
    private static final Logger log = LoggerFactory.getLogger(MentorRepTest.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MentorRep mentorRep;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE mentor CASCADE");
        // Создаем объект Mentor и заполняем обязательные поля
        Mentor mentor = new Mentor();

        mentor.setPhoto("base64EncodedPhotoData"); // Замените на реальные данные фото
        mentor.setFirstName("John");
        mentor.setLastName("Doe");
        mentor.setAge(30);  // Возраст от 16 до 100

        Set<String> stack = new HashSet<>();
        stack.add("Java");
        stack.add("Spring");
        mentor.setStack(stack);

        mentor.setEmail("john.doe@example.com");
        mentor.setTelegram("@johndoe"); // Замените на реальный Telegram

        Set<String> resources = new HashSet<>();
        resources.add("https://example.com/resource1");
        resources.add("https://example.com/resource2");
        mentor.setResources(resources);

        mentor.setBio("Experienced Java and Spring mentor. oiga[0erjag-erognv0aerjg[0aehrfg0ahwr0ghap0['egap0'gje0-fjg]iadjgf]poaejr-aje9-gja]-erjasdfasdfasdfasdfasdfasfdasdfasddfasfdsfasasdfasdfasdfasdf");
        mentor.setExperience(5); // Опыт от 0 до 100
        mentor.setCostPerHour(100); // Положительное или 0 значение
        mentor.setActive(true); // По умолчанию true, но лучше указать явно

        mentorRep.save(mentor);

        // Создаем объект Mentor и заполняем обязательные поля
        mentor = new Mentor();

        mentor.setPhoto("base64EncodedPhotoData"); // Замените на реальные данные фото
        mentor.setFirstName("John");
        mentor.setLastName("Doe");
        mentor.setAge(30);  // Возраст от 16 до 100

        stack = new HashSet<>();
        mentor.setStack(stack);

        mentor.setEmail("john.get@example.com");
        mentor.setTelegram("@johnget"); // Замените на реальный Telegram

        resources = new HashSet<>();
        resources.add("https://example.com/resource1");
        resources.add("https://example.com/resource2");
        mentor.setResources(resources);

        mentor.setBio("Experienced Java and Spring mentor. oiga[0erjag-erognv0aerjg[0aehrfg0ahwr0ghap0['egap0'gje0-fjg]iadjgf]poaejr-aje9-gja]-erjasdfasdfasdfasdfasdfasfdasdfasddfasfdsfasasdfasdfasdfasdf");
        mentor.setExperience(5); // Опыт от 0 до 100
        mentor.setCostPerHour(100); // Положительное или 0 значение
        mentor.setActive(true); // По умолчанию true, но лучше указать явно

        mentorRep.save(mentor);
    }

    @Test
    void getByQuery_shouldReturnMentorsWithMatchingStackAndCost() {
        Set<String> stack = new HashSet<>();
        stack.add("Java");
        Integer minCost = 90;
        Integer maxCost = 160;

        Iterable<Mentor> mentors = mentorRep.getByQuery(stack, minCost, maxCost, Pageable.unpaged());
        List<Mentor> mentorList = (List<Mentor>) mentors; // Преобразование в List для удобства

        assertEquals(1, mentorList.size());
        assertEquals("john.doe@example.com", mentorList.get(0).getEmail()); // Проверяем, что вернулся нужный ментор
    }

    @Test
    void getByQuery_shouldReturnEmptyList_whenNoMentorsMatch() {
        Set<String> stack = new HashSet<>();
        stack.add("C++"); // Нет менторов с таким стеком
        Integer minCost = 200;
        Integer maxCost = 250;

        Iterable<Mentor> mentors = mentorRep.getByQuery(stack, minCost, maxCost, Pageable.unpaged());
        List<Mentor> mentorList = (List<Mentor>) mentors;

        assertTrue(mentorList.isEmpty());
    }

    @Test
    void getByQuery_shouldReturnMentors_whenMinCostIsNull() {
        Set<String> stack = new HashSet<>();
        stack.add("Spring");
        Integer minCost = null;
        Integer maxCost = 250;

        Iterable<Mentor> mentors = mentorRep.getByQuery(stack, minCost, maxCost, Pageable.unpaged());
        List<Mentor> mentorList = (List<Mentor>) mentors;

        assertEquals(1, mentorList.size());
        assertEquals("john.doe@example.com", mentorList.get(0).getEmail());
    }

    @Test
    void getByQuery_shouldReturnMentors_whenMaxCostIsNull() {
        Set<String> stack = new HashSet<>();
        stack.add("Java");
        Integer minCost = 50;
        Integer maxCost = null;

        Iterable<Mentor> mentors = mentorRep.getByQuery(stack, minCost, maxCost, Pageable.unpaged());
        List<Mentor> mentorList = (List<Mentor>) mentors;

        assertEquals(1, mentorList.size());
        assertEquals("john.doe@example.com", mentorList.get(0).getEmail());
    }
    @Test
    void getByQuery_emptyStack() {
        Set<String> stack = new HashSet<>();
        Integer minCost = null;
        Integer maxCost = null;

        Iterable<Mentor> mentors = mentorRep.getByQuery(minCost, maxCost, Pageable.unpaged());
        List<Mentor> mentorList = new ArrayList<>();
        mentors.forEach(mentorList::add);

        log.info("list " + mentorList.toString());
        assertEquals(2, mentorList.size());
        assertEquals("john.doe@example.com", mentorList.get(0).getEmail());
    }
    @Test
    void getByQuery_nullStack() {
        Set<String> stack = null;
        Integer minCost = null;
        Integer maxCost = null;

        Iterable<Mentor> mentors = mentorRep.getByQuery(minCost, maxCost, Pageable.unpaged());
        List<Mentor> mentorList = (List<Mentor>) mentors;

        assertEquals(2, mentorList.size());
        assertEquals("john.doe@example.com", mentorList.get(0).getEmail());
    }

    @Test
    void findByEmail_shouldReturnMentor_whenEmailExists() {
        Optional<Mentor> mentor = mentorRep.findByEmail("john.doe@example.com");
        assertTrue(mentor.isPresent());
        assertEquals("john.doe@example.com", mentor.get().getEmail());
    }

    @Test
    void findByEmail_shouldReturnEmptyOptional_whenEmailDoesNotExist() {
        Optional<Mentor> mentor = mentorRep.findByEmail("nonexistent@example.com");
        assertTrue(mentor.isEmpty());
    }

    @Test
    void existsByEmail_shouldReturnTrue_whenEmailExists() {
        boolean exists = mentorRep.existsByEmail("john.doe@example.com");
        assertTrue(exists);
    }

    @Test
    void existsByEmail_shouldReturnFalse_whenEmailDoesNotExist() {
        boolean exists = mentorRep.existsByEmail("nonexistent@example.com");
        assertFalse(exists);
    }
}
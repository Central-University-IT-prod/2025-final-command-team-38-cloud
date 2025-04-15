package ru.prodcontest.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.Student;
import ru.prodcontest.app.repository.ConnectionRep;
import ru.prodcontest.app.repository.MentorRep;
import ru.prodcontest.app.repository.StudentRep;

import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConnectionTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Mentor MENTOR;
    private static Mentor getMentor() {
        MENTOR = new Mentor();
        MENTOR.setFirstName("Mentor");
        MENTOR.setLastName("Mentorov");
        MENTOR.setAge(20);
        MENTOR.setStack(Set.of("Java", "Spring"));
        MENTOR.setEmail("mentor@mail.ru");
        MENTOR.setTelegram("@rofls88");
        MENTOR.setResources(Set.of("https://t.me/rofls88"));
        MENTOR.setBio("string".repeat(50));
        return MENTOR;
    }

    private static Student STUDENT;
    private static Student getStudent() {
        STUDENT = new Student();
        STUDENT.setFirstName("Student");
        STUDENT.setLastName("Studentov");
        STUDENT.setEmail("student@mail.ru");
        STUDENT.setTelegram("@rofls88");
        STUDENT.setStack(Set.of("Java", "Spring"));
        return STUDENT;
    }

    @Autowired
    private MentorRep mentorRep;
    @Autowired
    private StudentRep studentRep;
    @Autowired
    private ConnectionRep connectionRep;

    private void recreate() {
        connectionRep.deleteAll();
        mentorRep.deleteAll();
        mentorRep.save(getMentor());
        studentRep.deleteAll();
        studentRep.save(getStudent());
    }

    private void createConnection() throws Exception {
        recreate();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/connection/create/{mentorId}", MENTOR.getId())
                        .header("Authorization", STUDENT.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(1)
    public void testMentorApproveConnection() throws Exception {
        createConnection();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/connection/mentor/approve/{studentId}", STUDENT.getUuid())
                        .header("Authorization", MENTOR.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    public void testStudentCancelConnection() throws Exception {
        createConnection();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/connection/student/cancel/{mentorId}", MENTOR.getId())
                        .header("Authorization", STUDENT.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(3)
    public void testMentorStopConnection() throws Exception {
        testMentorApproveConnection();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/connection/mentor/stop/{studentId}", STUDENT.getUuid())
                        .header("Authorization", MENTOR.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    public void testMentorCancelConnection() throws Exception {
        createConnection();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/connection/mentor/cancel/{studentId}", STUDENT.getUuid())
                        .header("Authorization", MENTOR.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(5)
    public void testGetStudentMyRequests() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/connection/student/myRequests?statuses=REQUEST,CANCEL,ACTIVE,STOP")
                        .header("Authorization", STUDENT.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(6)
    public void testGetMentorMyRequests() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/connection/mentor/myRequests?statuses=REQUEST,CANCEL,ACTIVE,STOP")
                        .header("Authorization", MENTOR.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}

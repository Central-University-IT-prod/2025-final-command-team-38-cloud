package ru.prodcontest.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.prodcontest.app.controller.dto.SignInDto;
import ru.prodcontest.app.controller.dto.StudentSignUpDto;
import ru.prodcontest.app.entity.Student;
import ru.prodcontest.app.repository.StudentRep;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Student student;
    @Autowired
    private StudentRep studentRep;

    @Test
    @Order(1)
    public void setUp() {
        studentRep.deleteAll();
    }

    @Test
    @Order(2)
    public void testStudentSignUp() throws Exception {
        // Request body for sign-up
        String requestBody = objectMapper.writeValueAsString(
                new StudentSignUpDto("Петр", "Петров", "petr@example.com", "@petrov", true, Set.of("Python", "Django"))
        );

        // Perform the POST request to /api/student/sign-up
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/student/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent); // Adjust assertion based on the actual response

        student = objectMapper.readValue(responseContent, Student.class);
    }

    @Test
    @Order(3)
    public void testStudentSignIn() throws Exception {
        // Request body for sign-in
        String requestBody = objectMapper.writeValueAsString(
                new SignInDto("petr@example.com", "password123") // Use the email you signed up with
        );

        // Perform the POST request to /api/student/sign-in
        mockMvc.perform(MockMvcRequestBuilders.post("/api/student/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    public void testGetStudentById_Success() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/student/" + student.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Student actualResponse = objectMapper.readValue(responseContent, Student.class);

        assertEquals(objectMapper.writeValueAsString(student), objectMapper.writeValueAsString(actualResponse));
    }

    @Test
    public void testGetStudentById_NotFound() throws Exception {
        UUID nonExistingStudentId = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/" + nonExistingStudentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound()); // Expect 404 Not Found
    }
}
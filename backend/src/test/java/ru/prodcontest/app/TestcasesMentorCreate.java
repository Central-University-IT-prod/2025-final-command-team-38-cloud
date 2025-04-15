package ru.prodcontest.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestcasesMentorCreate {
    private static final Logger log = LoggerFactory.getLogger(TestcasesMentorCreate.class);
    @Autowired
    private MockMvc mockMvc;
    static List<String> ids = new LinkedList<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Test
    @Order(1)
    public void cleanupDatabase() {
        jdbcTemplate.execute("TRUNCATE TABLE mentor_request CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE moderator CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE mentor CASCADE;");
    }
    @Test
    @Order(2)
    public void successCreateRequest() throws Exception {
        String json = """
                {
                  "photo": "https://example.com/image.jpg",
                  "firstName": "John",
                  "lastName": "Doe",
                  "age": 25,
                  "stack": [
                    "Java",
                    "Spring Boot",
                    "PostgreSQL"
                  ],
                  "email": "john.doe@example.com",
                  "telegram": "@johndoe",
                  "resources": [
                    "https://example.com/resource1",
                    "https://example.com/resource2"
                  ],
                  "bio": "The quick brown fox jumps over the lazy dog. A wise old owl lived in an oak. Never gonna give 
                  you up, never gonna let you down, never gonna run around and desert you. This is a sentence with 200 characters!",
                  "experience": 5,
                  "costPerHour": 50
                }
               \s""".replace("\n", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mentor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        json = """
                {
                  "photo": "https://example.com/image.jpg",
                  "firstName": "John",
                  "lastName": "Doe1",
                  "age": 25,
                  "stack": [
                    "C++",
                    "Spring Boot",
                    "PostgreSQL"
                  ],
                  "email": "john.doe1@example.com",
                  "telegram": "@johndoe1",
                  "resources": [
                    "https://example.com/resource1",
                    "https://example.com/resource2"
                  ],
                  "bio": "The quick brown fox jumps over the lazy dog. A wise old owl lived in an oak. Never gonna give 
                  you up, never gonna let you down, never gonna run around and desert you. This is a sentence with 200 characters!",
                  "experience": 5,
                  "costPerHour": 50
                }
               \s""".replace("\n", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mentor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        json = """
                {
                  "photo": "https://example.com/image.jpg",
                  "firstName": "John",
                  "lastName": "Doe2",
                  "age": 25,
                  "stack": [
                  ],
                  "email": "john.doe2@example.com",
                  "telegram": "@johndoe2",
                  "resources": [
                    "https://example.com/resource1",
                    "https://example.com/resource2"
                  ],
                  "bio": "The quick brown fox jumps over the lazy dog. A wise old owl lived in an oak. Never gonna give 
                  you up, never gonna let you down, never gonna run around and desert you. This is a sentence with 200 characters!",
                  "experience": 5,
                  "costPerHour": 50
                }
               \s""".replace("\n", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mentor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
    @Test
    @Order(3)
    public void successCreateModer() throws Exception {
        String json = """
                {
                  "login": "login",
                  "password": "password"
                }
               \s""".replace("\n", ""); // password not use
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/createModerator")
                        .header("Authorization", "hesoyam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
    @Test
    @Order(4)
    public void successGetRequests() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/moderator/requests")
                        .header("Authorization", "login"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        if (jsonNode.isArray()) {
            assertEquals(3, jsonNode.size());
            for (JsonNode node : jsonNode) {
                ids.add(node.get("id").toPrettyString());
                log.info(ids.toString());
            }
            log.info(ids.toString());
        } else {
            throw new AssertionError("Expected a JSON array");
        }
    }
    @Test
    @Order(5)
    public void successApprove() throws Exception {
        log.info(ids.toString());
        String json = """
                {
                  "mentorRequestId": %s,
                  "approve": true
                }
               \s""".formatted(ids.get(0)).replace("\n", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/moderator/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "login"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/mentor")
                        .header("Authorization", "string"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        log.info(jsonNode.toPrettyString());
        assertEquals(1, jsonNode.size());
    }
    @Test
    @Order(6)
    public void successNotApprove() throws Exception {
        String json = """
                {
                  "mentorRequestId": %s,
                  "approve": false
                }
               \s""".formatted(ids.get(1)).replace("\n", "");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/moderator/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "login"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
    }
}

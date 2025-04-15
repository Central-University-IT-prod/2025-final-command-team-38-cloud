package ru.prodcontest.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.prodcontest.app.entity.Mentor;
import ru.prodcontest.app.entity.Moderator;
import ru.prodcontest.app.repository.ConnectionRep;
import ru.prodcontest.app.repository.MentorRep;
import ru.prodcontest.app.repository.MentorRequestRep;
import ru.prodcontest.app.repository.ModeratorRep;
import ru.prodcontest.app.service.interfaces.MentorService;
import ru.prodcontest.app.service.interfaces.ModeratorService;

import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class MentorEditTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ModeratorService moderatorService;
    @Autowired
    private MentorService mentorService;

    private static Mentor MENTOR;
    @Autowired
    private ModeratorRep moderatorRep;
    @Autowired
    private MentorRep mentorRep;
    @Autowired
    private ConnectionRep connectionRep;
    @Autowired
    private MentorRequestRep mentorRequestRep;

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

    @Test
    public void test() throws Exception {
        moderatorRep.deleteAll();
        connectionRep.deleteAll();
        mentorRep.deleteAll();
        mentorRequestRep.deleteAll();

        Moderator moderator = new Moderator();
        moderator.setLogin("moder");
        moderatorService.register(moderator);

        mentorService.save(getMentor());
        moderatorService.approve(moderator, MENTOR.asRequest(), true);

        mvc.perform(MockMvcRequestBuilders.get("/api/mentor/get/mentor@mail.ru"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Mentor"));

        mvc.perform(MockMvcRequestBuilders.put("/api/mentor")
                        .header("Authorization", "mentor@mail.ru")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"First name\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.post("/api/moderator/approve")
                .header("Authorization", "moder")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mentorRequestId\": \"%s\", \"approve\": true}".formatted(MENTOR.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/api/mentor/get/mentor@mail.ru"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("First name"));
    }
}

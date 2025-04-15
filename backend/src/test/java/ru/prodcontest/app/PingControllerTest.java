package ru.prodcontest.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PingControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void pingShouldReturnPong() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/ping"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("pong"));
	}
}
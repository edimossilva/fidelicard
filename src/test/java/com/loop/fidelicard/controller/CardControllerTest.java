package com.loop.fidelicard.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.loop.fidelicard.service.CardService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CardControllerTest {

	@Autowired
	private CardController cardController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CardService cardService;

	@Test
	public void contexLoads() throws Exception {
		assertThat(cardController).isNotNull();
	}

	@Test
	public void cardGet() throws Exception {

		mockMvc.perform(get("/card").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string("Hello"));
	}
}

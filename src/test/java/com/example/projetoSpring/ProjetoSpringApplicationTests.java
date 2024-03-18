package com.example.projetoSpring;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.service.MarcaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjetoSpringApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MarcaService service;

	@Test
	void contextLoads() {
	}

	@Test
	void case1() throws Exception{

		Marca marca = new Marca();

		marca.setNome("BYD");

		mockMvc.perform(post("/marcas")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(marca)))
				.andExpect(status().isOk());

	}

	@Test
	void case2() throws Exception{

		Usuario usuario = new Usuario("Lucas Wallace", "lucasWallace@teste.com", "12345678");

		mockMvc.perform(post("/api/auth/signup")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(usuario)))
				.andExpect(status().isOk());

	}

}

package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.model.Tema;
import br.com.aceleramaker.blogpessoal.service.TemaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TemaController.class)
@Import({TemaControllerTest.MockConfig.class, br.com.aceleramaker.blogpessoal.config.SecurityConfigTest.class})
class TemaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TemaService temaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Tema tema;

    @BeforeEach
    void setUp() {
        tema = new Tema();
        tema.setId(1L);
        tema.setDescricao("Tema Teste");
    }

    @Test
    void deveCriarTemaComSucesso() throws Exception {
        when(temaService.salvarTema(Mockito.any(Tema.class))).thenReturn(tema);

        mockMvc.perform(post("/api/temas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tema)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value(tema.getDescricao()));
    }

    @Test
    void deveAlterarTemaComSucesso() throws Exception {
        when(temaService.alterarTema(Mockito.any(Tema.class))).thenReturn(tema);

        mockMvc.perform(put("/api/temas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tema)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value(tema.getDescricao()));
    }

    @Test
    void deveDeletarTemaComSucesso() throws Exception {
        mockMvc.perform(delete("/api/temas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveBuscarTodosOsTemas() throws Exception {
        when(temaService.buscarTemas()).thenReturn(List.of(tema));

        mockMvc.perform(get("/api/temas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public TemaService temaService() {
            return Mockito.mock(TemaService.class);
        }
    }
}

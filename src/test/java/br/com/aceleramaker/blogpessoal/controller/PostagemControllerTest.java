package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.dto.PostagemDTO;
import br.com.aceleramaker.blogpessoal.model.Postagem;
import br.com.aceleramaker.blogpessoal.service.PostagemService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostagemController.class)
@Import({PostagemControllerTest.MockConfig.class, br.com.aceleramaker.blogpessoal.config.SecurityConfigTest.class})
class PostagemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostagemService postagemService;

    @Autowired
    private ObjectMapper objectMapper;

    private PostagemDTO postagemDTO;
    private Postagem postagem;

    @BeforeEach
    void setUp() {
        postagemDTO = new PostagemDTO();
        postagemDTO.setTitulo("Título Teste");
        postagemDTO.setTexto("Texto Teste");
        postagemDTO.setUsuarioId(1L);
        postagemDTO.setTemaId(1L);

        postagem = new Postagem();
        postagem.setId(1L);
        postagem.setTitulo(postagemDTO.getTitulo());
        postagem.setTexto(postagemDTO.getTexto());
        postagem.setData(LocalDateTime.now());
    }

    @Test
    void deveCriarPostagemComSucesso() throws Exception {
        when(postagemService.salvarPostagem(Mockito.any(PostagemDTO.class))).thenReturn(postagem);

        mockMvc.perform(post("/api/postagens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postagemDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value(postagem.getTitulo()));
    }

    @Test
    void deveAlterarPostagemComSucesso() throws Exception {
        when(postagemService.alterarPostagem(Mockito.eq(1L), Mockito.any(PostagemDTO.class))).thenReturn(postagem);

        mockMvc.perform(put("/api/postagens/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postagemDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value(postagem.getTitulo()));
    }

    @Test
    void deveDeletarPostagemComSucesso() throws Exception {
        mockMvc.perform(delete("/api/postagens/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveBuscarTodasPostagens() throws Exception {
        when(postagemService.buscarPostagens()).thenReturn(List.of(postagem));

        mockMvc.perform(get("/api/postagens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void deveBuscarPostagensComFiltro() throws Exception {
        when(postagemService.buscarPostagensComFiltro(1L, 1L)).thenReturn(List.of(postagem));

        mockMvc.perform(get("/api/postagens/filtro")
                        .param("autor", "1")
                        .param("tema", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public PostagemService postagemService() {
            return Mockito.mock(PostagemService.class);
        }
    }
}

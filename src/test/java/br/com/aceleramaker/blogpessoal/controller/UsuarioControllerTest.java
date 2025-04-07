package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.config.SecurityConfigTest;
import br.com.aceleramaker.blogpessoal.dto.UsuarioLoginDTO;
import br.com.aceleramaker.blogpessoal.model.Usuario;
import br.com.aceleramaker.blogpessoal.security.JwtSecurity;
import br.com.aceleramaker.blogpessoal.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@Import({UsuarioControllerTest.MockConfig.class, SecurityConfigTest.class})
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtSecurity jwtSecurity;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;
    private UsuarioLoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Maria");
        usuario.setUsuario("maria@email.com");
        usuario.setSenha("123456");

        loginDTO = new UsuarioLoginDTO();
        loginDTO.setUsuario("maria@email.com");
        loginDTO.setSenha("123456");
    }

    @Test
    void deveCriarUsuarioComSucesso() throws Exception {
        when(usuarioService.salvarUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(usuario.getNome()));
    }

    @Test
    void deveAlterarUsuarioComSucesso() throws Exception {
        when(usuarioService.alterarUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario").value(usuario.getUsuario()));
    }

    @Test
    void deveDeletarUsuarioComSucesso() throws Exception {
        doNothing().when(usuarioService).deletarUsuario(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveAutenticarUsuarioComSucesso() throws Exception {
        when(usuarioService.autenticarUsuario(any(UsuarioLoginDTO.class))).thenReturn(usuario);
        when(jwtSecurity.gerarTokenJwt(usuario.getUsuario())).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Bearer fake-jwt-token"));
    }

    @Test
    void deveFalharAutenticacaoComCredenciaisInvalidas() throws Exception {
        when(usuarioService.autenticarUsuario(any(UsuarioLoginDTO.class))).thenReturn(null);

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Usuário ou senha inválidos"));
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UsuarioService usuarioService() {
            return Mockito.mock(UsuarioService.class);
        }

        @Bean
        public JwtSecurity jwtSecurity() {
            return Mockito.mock(JwtSecurity.class);
        }

        @Bean
        public AuthenticationManager authManager() {
            return Mockito.mock(AuthenticationManager.class);
        }
    }
}

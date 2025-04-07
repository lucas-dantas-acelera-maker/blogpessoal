package br.com.aceleramaker.blogpessoal.service;

import br.com.aceleramaker.blogpessoal.dto.UsuarioLoginDTO;
import br.com.aceleramaker.blogpessoal.model.Usuario;
import br.com.aceleramaker.blogpessoal.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setUsuario("joao@email.com");
        usuario.setSenha("senha123");
    }

    @Test
    void testSalvarUsuario() {
        when(passwordEncoder.encode("senha123")).thenReturn("senhaCriptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        Usuario resultado = usuarioService.salvarUsuario(usuario);

        assertEquals("senhaCriptografada", resultado.getSenha());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testAlterarUsuarioComIdValido() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        Usuario resultado = usuarioService.alterarUsuario(usuario);

        assertEquals(usuario, resultado);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testAlterarUsuarioSemId() {
        usuario.setId(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.alterarUsuario(usuario));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("ID do usuário não informado"));
    }

    @Test
    void testAlterarUsuarioComIdInexistente() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.alterarUsuario(usuario));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Usuário não encontrado"));
    }

    @Test
    void testDeletarUsuarioComIdValido() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        usuarioService.deletarUsuario(1L);

        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void testDeletarUsuarioComIdInexistente() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.deletarUsuario(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Usuário não encontrado"));
    }

    @Test
    void testAutenticarUsuarioComCredenciaisValidas() {
        UsuarioLoginDTO loginDTO = new UsuarioLoginDTO();
        loginDTO.setUsuario("joao@email.com");
        loginDTO.setSenha("senha123");

        usuario.setSenha("senhaCriptografada");

        when(usuarioRepository.findByUsuario("joao@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha123", "senhaCriptografada")).thenReturn(true);

        Usuario resultado = usuarioService.autenticarUsuario(loginDTO);

        assertEquals(usuario, resultado);
    }

    @Test
    void testAutenticarUsuarioInexistente() {
        UsuarioLoginDTO loginDTO = new UsuarioLoginDTO();
        loginDTO.setUsuario("naoexiste@email.com");
        loginDTO.setSenha("qualquer");

        when(usuarioRepository.findByUsuario("naoexiste@email.com")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.autenticarUsuario(loginDTO));

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
    }

    @Test
    void testAutenticarUsuarioComSenhaInvalida() {
        UsuarioLoginDTO loginDTO = new UsuarioLoginDTO();
        loginDTO.setUsuario("joao@email.com");
        loginDTO.setSenha("senhaErrada");

        usuario.setSenha("senhaCriptografada");

        when(usuarioRepository.findByUsuario("joao@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaErrada", "senhaCriptografada")).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.autenticarUsuario(loginDTO));

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
    }
}

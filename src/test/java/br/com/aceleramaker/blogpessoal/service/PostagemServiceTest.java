package br.com.aceleramaker.blogpessoal.service;

import br.com.aceleramaker.blogpessoal.dto.PostagemDTO;
import br.com.aceleramaker.blogpessoal.model.Postagem;
import br.com.aceleramaker.blogpessoal.model.Tema;
import br.com.aceleramaker.blogpessoal.model.Usuario;
import br.com.aceleramaker.blogpessoal.repository.PostagemRepository;
import br.com.aceleramaker.blogpessoal.repository.TemaRepository;
import br.com.aceleramaker.blogpessoal.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostagemServiceTest {

    @InjectMocks
    private PostagemService postagemService;

    @Mock
    private PostagemRepository postagemRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TemaRepository temaRepository;

    private Usuario usuario;
    private Tema tema;
    private PostagemDTO postagemDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Autor");

        tema = new Tema();
        tema.setId(1L);
        tema.setDescricao("Tecnologia");

        postagemDTO = new PostagemDTO();
        postagemDTO.setTitulo("Título Teste");
        postagemDTO.setTexto("Conteúdo de teste");
        postagemDTO.setUsuarioId(1L);
        postagemDTO.setTemaId(1L);
    }

    @Test
    void testSalvarPostagem_Sucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));
        when(postagemRepository.save(any(Postagem.class))).thenAnswer(i -> i.getArgument(0));

        Postagem resultado = postagemService.salvarPostagem(postagemDTO);

        assertEquals("Título Teste", resultado.getTitulo());
        assertEquals("Conteúdo de teste", resultado.getTexto());
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(tema, resultado.getTema());
        verify(postagemRepository, times(1)).save(any(Postagem.class));
    }

    @Test
    void testSalvarPostagem_UsuarioNaoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                postagemService.salvarPostagem(postagemDTO));

        assertEquals("404 NOT_FOUND \"Usuário não encontrado pelo ID fornecido\"", ex.getMessage());
    }

    @Test
    void testSalvarPostagem_TemaNaoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(temaRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                postagemService.salvarPostagem(postagemDTO));

        assertEquals("404 NOT_FOUND \"Tema não encontrado pelo ID fornecido\"", ex.getMessage());
    }

    @Test
    void testAlterarPostagem_Sucesso() {
        Postagem postagem = new Postagem();
        postagem.setId(1L);
        postagem.setTitulo("Antigo");

        when(postagemRepository.findById(1L)).thenReturn(Optional.of(postagem));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));
        when(postagemRepository.save(any(Postagem.class))).thenAnswer(i -> i.getArgument(0));

        Postagem resultado = postagemService.alterarPostagem(1L, postagemDTO);

        assertEquals("Título Teste", resultado.getTitulo());
        assertEquals("Conteúdo de teste", resultado.getTexto());
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(tema, resultado.getTema());
    }

    @Test
    void testDeletarPostagem_Sucesso() {
        when(postagemRepository.existsById(1L)).thenReturn(true);

        postagemService.deletarPostagem(1L);

        verify(postagemRepository).deleteById(1L);
    }

    @Test
    void testDeletarPostagem_NaoEncontrada() {
        when(postagemRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                postagemService.deletarPostagem(1L));

        assertEquals("404 NOT_FOUND \"Postagem não encontrada pelo ID fornecido\"", ex.getMessage());
    }

    @Test
    void testBuscarPostagemPorId_Encontrada() {
        Postagem postagem = new Postagem();
        postagem.setId(1L);

        when(postagemRepository.findById(1L)).thenReturn(Optional.of(postagem));

        Postagem resultado = postagemService.buscarPostagemPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void testBuscarPostagemPorId_NaoEncontrada() {
        when(postagemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> postagemService.buscarPostagemPorId(1L));
    }

    @Test
    void testBuscarPostagensComFiltro() {
        Postagem postagem = new Postagem();
        postagem.setUsuario(usuario);
        postagem.setTema(tema);

        when(postagemRepository.findAll()).thenReturn(List.of(postagem));

        List<Postagem> resultado = postagemService.buscarPostagensComFiltro(1L, 1L);

        assertEquals(1, resultado.size());
        assertEquals(usuario, resultado.get(0).getUsuario());
        assertEquals(tema, resultado.get(0).getTema());
    }
}

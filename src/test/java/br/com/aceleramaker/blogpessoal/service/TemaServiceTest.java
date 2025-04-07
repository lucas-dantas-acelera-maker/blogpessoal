package br.com.aceleramaker.blogpessoal.service;

import br.com.aceleramaker.blogpessoal.model.Tema;
import br.com.aceleramaker.blogpessoal.repository.TemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TemaServiceTest {

    @InjectMocks
    private TemaService temaService;

    @Mock
    private TemaRepository temaRepository;

    private Tema tema;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tema = new Tema();
        tema.setId(1L);
        tema.setDescricao("Tecnologia");
    }

    @Test
    void testSalvarTema() {
        when(temaRepository.save(tema)).thenReturn(tema);

        Tema resultado = temaService.salvarTema(tema);

        assertEquals("Tecnologia", resultado.getDescricao());
        verify(temaRepository).save(tema);
    }

    @Test
    void testAlterarTemaComIdValido() {
        when(temaRepository.existsById(1L)).thenReturn(true);
        when(temaRepository.save(tema)).thenReturn(tema);

        Tema resultado = temaService.alterarTema(tema);

        assertEquals(tema, resultado);
        verify(temaRepository).save(tema);
    }

    @Test
    void testAlterarTemaSemId() {
        tema.setId(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> temaService.alterarTema(tema));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("ID do tema não informado"));
    }

    @Test
    void testAlterarTemaComIdInexistente() {
        when(temaRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> temaService.alterarTema(tema));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Tema não encontrado"));
    }

    @Test
    void testDeletarTemaComIdValido() {
        when(temaRepository.existsById(1L)).thenReturn(true);

        temaService.deletarTema(1L);

        verify(temaRepository).deleteById(1L);
    }

    @Test
    void testDeletarTemaComIdInexistente() {
        when(temaRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> temaService.deletarTema(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Tema não encontrado"));
    }

    @Test
    void testBuscarTemas() {
        List<Tema> lista = List.of(tema);
        when(temaRepository.findAll()).thenReturn(lista);

        List<Tema> resultado = temaService.buscarTemas();

        assertEquals(1, resultado.size());
        assertEquals("Tecnologia", resultado.get(0).getDescricao());
    }
}

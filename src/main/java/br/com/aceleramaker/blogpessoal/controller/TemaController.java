package br.com.aceleramaker.blogpessoal.controller;

import br.com.aceleramaker.blogpessoal.model.Tema;
import br.com.aceleramaker.blogpessoal.service.TemaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/temas")
public class TemaController {
    private final TemaService temaService;

    public TemaController(TemaService temaService) {
        this.temaService = temaService;
    }

    @PostMapping
    public ResponseEntity<Tema> criarTema(@Valid @RequestBody Tema tema) {
        Tema novoTema = temaService.salvarTema(tema);
        return new ResponseEntity<>(novoTema, HttpStatus.CREATED);
    }
}

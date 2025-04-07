package br.com.aceleramaker.blogpessoal.service;

import br.com.aceleramaker.blogpessoal.model.Tema;
import br.com.aceleramaker.blogpessoal.repository.TemaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TemaService {
    private final TemaRepository temaRepository;

    public TemaService(TemaRepository temaRepository) {
        this.temaRepository = temaRepository;
    }

    public Tema salvarTema(Tema tema) {
        return temaRepository.save(tema);
    }

    public Tema alterarTema(Tema tema) {
        if (tema.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do tema não informado.");
        }

        if (!temaRepository.existsById(tema.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema não encontrado com o ID fornecido.");
        }

        return temaRepository.save(tema);
    }
}

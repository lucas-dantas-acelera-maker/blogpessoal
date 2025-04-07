package br.com.aceleramaker.blogpessoal.service;

import br.com.aceleramaker.blogpessoal.model.Tema;
import br.com.aceleramaker.blogpessoal.repository.TemaRepository;
import org.springframework.stereotype.Service;

@Service
public class TemaService {
    private final TemaRepository temaRepository;

    public TemaService(TemaRepository temaRepository) {
        this.temaRepository = temaRepository;
    }

    public Tema salvarTema(Tema tema) {
        return temaRepository.save(tema);
    }
}

package br.com.aceleramaker.blogpessoal.repository;

import br.com.aceleramaker.blogpessoal.model.Postagem;
import org.springframework.data.repository.CrudRepository;

public interface PostagemRepository extends CrudRepository<Postagem, Long> {
}

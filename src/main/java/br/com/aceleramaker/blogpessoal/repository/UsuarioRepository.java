package br.com.aceleramaker.blogpessoal.repository;

import br.com.aceleramaker.blogpessoal.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}

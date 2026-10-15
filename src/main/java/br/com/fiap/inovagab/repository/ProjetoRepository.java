package br.com.fiap.inovagab.repository;

import br.com.fiap.inovagab.model.Projeto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjetoRepository extends MongoRepository<Projeto, String> {
}
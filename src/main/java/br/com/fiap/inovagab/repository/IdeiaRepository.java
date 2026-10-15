package br.com.fiap.inovagab.repository;

import br.com.fiap.inovagab.model.Ideia;
import br.com.fiap.inovagab.model.StatusIdeia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IdeiaRepository extends MongoRepository<Ideia, String> {

    List<Ideia> findByOperadorIdOrderByDataDesc(String operadorId);

    List<Ideia> findByStatusOrderByDataDesc(StatusIdeia status);

    List<Ideia> findByEstrategiaId(String estrategiaId);

    List<Ideia> findAllByOrderByDataDesc();

    List<Ideia> findByNotaIAIsNotNullOrderByNotaIADesc();
}

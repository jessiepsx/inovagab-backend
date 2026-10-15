package br.com.fiap.inovagab.repository;

import br.com.fiap.inovagab.model.Resultado;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResultadoRepository extends MongoRepository<Resultado, String> {

    List<Resultado> findByProjetoId(String projetoId);
}
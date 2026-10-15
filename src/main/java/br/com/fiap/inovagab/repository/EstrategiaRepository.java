package br.com.fiap.inovagab.repository;

import br.com.fiap.inovagab.model.Estrategia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EstrategiaRepository extends MongoRepository<Estrategia, String> {

    List<Estrategia> findByAtivaTrue();

    List<Estrategia> findAllByOrderByDataDesc();
}

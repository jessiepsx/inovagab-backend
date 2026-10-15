package br.com.fiap.inovagab.repository;

import br.com.fiap.inovagab.model.Auditoria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditoriaRepository extends MongoRepository<Auditoria, String> {
    List<Auditoria> findTop50ByOrderByDataDesc();
}

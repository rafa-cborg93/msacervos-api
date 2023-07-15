package br.cborg.msacervos.repository;

import br.cborg.msacervos.entity.Acervo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcervoRepository extends JpaRepository<Acervo, Long> {
    Optional<Acervo> findByIsbn(Integer isbn);
}

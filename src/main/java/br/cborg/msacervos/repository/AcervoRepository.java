package br.cborg.msacervos.repository;

import br.cborg.msacervos.entity.Acervo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcervoRepository extends JpaRepository<Acervo, Long> {
    Optional<Acervo> findByIsbn(String isbn);
    List<Acervo> findAcervoListByIsbnAndNumeroChamada(String isbn, String numeroChamada);
    List<Acervo> findAcervoListByIsbn(String isbn);
    List<Acervo> findAcervoListByNumeroChamada(String numeroChamada);
}

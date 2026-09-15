package br.com.campusgigs.contratacao.repository;

import br.com.campusgigs.contratacao.model.Contratacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratacaoRepository extends JpaRepository<Contratacao, Long> {
}

package br.com.coamo.marcelo.atividade.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.Classificacao;
import br.com.coamo.marcelo.atividade.models.ProcessoEntrada;

@Repository
public interface ClassificacaoRepository  extends JpaRepository<Classificacao, Long> {
	Optional<Classificacao> findByProcesso(ProcessoEntrada processo);
}
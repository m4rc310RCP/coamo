package br.com.coamo.marcelo.atividade.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.Entrega;
import br.com.coamo.marcelo.atividade.models.Pessoa;
import br.com.coamo.marcelo.atividade.models.ProcessoEntrada;

@Repository
public interface EntregaRepository  extends JpaRepository<Entrega, Long> {
	List<Entrega> findAllByProcesso(ProcessoEntrada processo);
	Optional<Entrega> findByProcessoAndPessoa(ProcessoEntrada processo, Pessoa pessoa);
}
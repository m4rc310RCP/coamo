package br.com.coamo.marcelo.atividade.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.Pesagem;
import br.com.coamo.marcelo.atividade.models.ProcessoEntrada;

@Repository
public interface PesagemRepository  extends JpaRepository<Pesagem, Long> {
	List<Pesagem> findAllByProcesso(ProcessoEntrada processo);

	int countByProcesso(ProcessoEntrada processo);
}
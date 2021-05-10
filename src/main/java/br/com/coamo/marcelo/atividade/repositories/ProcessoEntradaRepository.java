package br.com.coamo.marcelo.atividade.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.ProcessoEntrada;

@Repository
public interface ProcessoEntradaRepository  extends JpaRepository<ProcessoEntrada, Long> {
	Optional<ProcessoEntrada> findByPlacaAndNotasPesagemNull(String placa);
	List<ProcessoEntrada> findAllByNotasPesagemNull();
}
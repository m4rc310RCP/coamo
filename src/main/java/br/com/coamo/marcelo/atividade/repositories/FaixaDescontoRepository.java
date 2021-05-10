package br.com.coamo.marcelo.atividade.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.FaixaDesconto;
import br.com.coamo.marcelo.atividade.models.ItemClassificacao;

@Repository
public interface FaixaDescontoRepository  extends JpaRepository<FaixaDesconto, Long> {
	List<FaixaDesconto> findAllByItemClassificacao(ItemClassificacao item);
}
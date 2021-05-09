package br.com.coamo.marcelo.atividade.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.ItemClassificacao;
import br.com.coamo.marcelo.atividade.models.Produto;

@Repository
public interface ItemClassificacaoRepository  extends JpaRepository<ItemClassificacao, Long> {
	List<ItemClassificacao> findAllByProduto(Produto produto);
}
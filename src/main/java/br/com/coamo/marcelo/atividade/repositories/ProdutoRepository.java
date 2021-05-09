package br.com.coamo.marcelo.atividade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.Produto;

@Repository
public interface ProdutoRepository  extends JpaRepository<Produto, Long> {
}
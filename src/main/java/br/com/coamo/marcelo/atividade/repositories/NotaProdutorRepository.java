package br.com.coamo.marcelo.atividade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.NotaProdutor;

@Repository
public interface NotaProdutorRepository  extends JpaRepository<NotaProdutor, Long> {
	
}
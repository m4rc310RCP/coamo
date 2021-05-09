package br.com.coamo.marcelo.atividade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.Cooperado;

@Repository
public interface CooperadoRepository  extends JpaRepository<Cooperado, Long> {
	Cooperado findByMatricula(String matricula);
}
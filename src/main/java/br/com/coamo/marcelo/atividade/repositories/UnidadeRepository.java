package br.com.coamo.marcelo.atividade.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.Unidade;

@Repository
public interface UnidadeRepository  extends JpaRepository<Unidade, Long> {

	Optional<Unidade> findByCodigo(int codigo);

	List<Unidade> findAllByMae(Unidade unidade);

//	Optional<Unidade> findByCodigo(Long codigo);
}
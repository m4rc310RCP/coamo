package br.com.coamo.marcelo.atividade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.Pessoa;
import br.com.coamo.marcelo.atividade.models.PessoaFisica;

@Repository
public interface PessoaRepository  extends JpaRepository<Pessoa, Long> {
	public PessoaFisica findByCpf(String cpf);
}

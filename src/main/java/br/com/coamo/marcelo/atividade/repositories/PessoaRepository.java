package br.com.coamo.marcelo.atividade.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.coamo.marcelo.atividade.models.Pessoa;
import br.com.coamo.marcelo.atividade.models.PessoaFisica;
import br.com.coamo.marcelo.atividade.models.PessoaJuridica;

@Repository
public interface PessoaRepository  extends JpaRepository<Pessoa, Long> {
	List<PessoaFisica> findAllByNome(String nome);
	Optional<PessoaFisica> findByCpf(String cpf);
	Optional<PessoaJuridica> findByCnpj(String cnpj);
	List<PessoaFisica> findPfByNomeContainingIgnoreCase(String nome);
	List<PessoaJuridica> findPjByNomeContainingIgnoreCase(String nome);
}
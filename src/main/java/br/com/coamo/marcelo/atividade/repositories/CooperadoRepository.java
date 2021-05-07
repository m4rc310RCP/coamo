package br.com.coamo.marcelo.atividade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.coamo.marcelo.atividade.models.Cooperado;
import br.com.coamo.marcelo.atividade.models.CooperadoPessoaFisica;
import br.com.coamo.marcelo.atividade.models.CooperadoPessoaJuridica;

//@Repository
public interface CooperadoRepository  extends JpaRepository<Cooperado, Long> {
	public CooperadoPessoaFisica findPFByMatricula(String matricula);
	public CooperadoPessoaJuridica findPJByMatricula(String matricula);
	
//	public CooperadoPessoaFisica findByMatriculaOrPessoaCpf(String matricula, String cpf);
//	public CooperadoPessoaJuridica findByMatriculaOrCnpj(String matricula, String cnpj);
}

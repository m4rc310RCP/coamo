package br.com.coamo.marcelo.atividade.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.coamo.marcelo.atividade.models.PessoaFisica;
import br.com.coamo.marcelo.atividade.models.PessoaJuridica;
import br.com.coamo.marcelo.atividade.models.Unidade;
import br.com.coamo.marcelo.atividade.repositories.PessoaRepository;
import br.com.coamo.marcelo.atividade.repositories.UnidadeRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@Service
@GraphQLApi
public class CoamoService extends MService{
	
	
	
	@Autowired
	private UnidadeRepository unidadeRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	
	
//	Ações para manipular Pessoa ================================
	
	@GraphQLQuery
	public List<PessoaFisica> listPessoasFisica() {
		return pessoaRepository.findAllByNome("");
	}
	

	@GraphQLQuery
	public PessoaFisica getPessoaFisica(String cpf) {
		return pessoaRepository.findByCpf(cpf);
	}
	
	@GraphQLQuery
	public PessoaJuridica getPessoaJuridica(String cnpj) {
		return pessoaRepository.findByCnpj(cnpj);
	}
	
	
	
	
	@GraphQLMutation
	public PessoaJuridica cadastroPessoaJuridica(PessoaJuridica pessoaJuridica) {
		cloneObject("id", "findById", pessoaRepository, pessoaJuridica);
		cloneObject("cpf", "findByCpf", pessoaRepository, pessoaJuridica);
		return pessoaRepository.save(pessoaJuridica);
	}
	@GraphQLMutation
	public PessoaFisica cadastroPessoaFisica(PessoaFisica pessoaFisica) {
		cloneObject("id", "findById", pessoaRepository, pessoaFisica);
		cloneObject("cpf", "findByCpf", pessoaRepository, pessoaFisica);
		return pessoaRepository.save(pessoaFisica);
	}
	
	
//	FIM Ações para manipular Pessoa ================================
	
//	Ações para manipular Cooperado ================================
//	FIM Ações para manipular Cooperado ================================
	
//	Ações para manipular Unidades ================================
	@GraphQLQuery
	public Unidade getUnidade(int codigo) {
		return unidadeRepository.findByCodigo(codigo).orElse(null);
	}
	
	@GraphQLQuery
	public List<Unidade> listUnidades(){
		return unidadeRepository.findAll();
	}
	
	@GraphQLQuery
	public List<Unidade> unidadesFilhas(@GraphQLContext Unidade unidade){
		return unidadeRepository.findAllByMae(unidade);
	}
	
	@GraphQLMutation
	public Unidade adicionarUnidadeFilha(int codigoUnidadeMae, int codigoUnidadeFilha) {
		Unidade mae = unidadeRepository.findByCodigo(codigoUnidadeMae).orElseThrow(null);
		Unidade filha = unidadeRepository.findByCodigo(codigoUnidadeFilha).orElseThrow(null);
		
		filha.setMae(mae);
		unidadeRepository.save(filha);
		
		return mae;
	}
	
	@GraphQLMutation
	public boolean removeUnidade(Long id) {
		boolean exists = unidadeRepository.existsById(id);
		unidadeRepository.deleteById(id);
		return exists;
	}
	
	@GraphQLMutation
	public Unidade setUnidade(Unidade unidade) {
		cloneObject("codigo", "findByCodigo", unidadeRepository, unidade);
		return unidadeRepository.saveAndFlush(unidade);
	}
	
//	Unidades ================================
}

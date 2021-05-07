package br.com.coamo.marcelo.atividade.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.coamo.marcelo.atividade.models.CooperadoPessoaFisica;
import br.com.coamo.marcelo.atividade.models.PessoaFisica;
import br.com.coamo.marcelo.atividade.repositories.CooperadoRepository;
import br.com.coamo.marcelo.atividade.repositories.PessoaRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@Service
@GraphQLApi
public class CoamoService extends MService{
	
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private CooperadoRepository cooperadoRepository;
	
	
	@GraphQLMutation
	public CooperadoPessoaFisica cooperadoPF(CooperadoPessoaFisica cooperado) {
		cloneObject("id", "findById", cooperadoRepository, cooperado);
		cloneObject("matricula", "findPFByMatricula", cooperadoRepository, cooperado);
		
		return cooperadoRepository.save(cooperado);
	}
	
	
	@GraphQLMutation
	public PessoaFisica pessoaFisica(PessoaFisica pessoaFisica) {
		cloneObject("id", "findById", pessoaRepository, pessoaFisica);
		cloneObject("cpf", "findByCpf", pessoaRepository, pessoaFisica);
		
		return pessoaRepository.save(pessoaFisica);
	}
	
	
	@GraphQLQuery
	public Date getDate() {
		return new Date();
	}
}

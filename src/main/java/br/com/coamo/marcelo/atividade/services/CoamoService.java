package br.com.coamo.marcelo.atividade.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.coamo.marcelo.atividade.models.Cooperado;
import br.com.coamo.marcelo.atividade.models.ItemClassificacao;
import br.com.coamo.marcelo.atividade.models.Pessoa;
import br.com.coamo.marcelo.atividade.models.PessoaFisica;
import br.com.coamo.marcelo.atividade.models.PessoaJuridica;
import br.com.coamo.marcelo.atividade.models.Produto;
import br.com.coamo.marcelo.atividade.models.Unidade;
import br.com.coamo.marcelo.atividade.repositories.CooperadoRepository;
import br.com.coamo.marcelo.atividade.repositories.ItemClassificacaoRepository;
import br.com.coamo.marcelo.atividade.repositories.PessoaRepository;
import br.com.coamo.marcelo.atividade.repositories.ProdutoRepository;
import br.com.coamo.marcelo.atividade.repositories.UnidadeRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@Service
@GraphQLApi
public class CoamoService extends MService {

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private CooperadoRepository cooperadoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ItemClassificacaoRepository itemClassificacaoRepository;

//	Ações para manipular Pessoa ================================

	@GraphQLQuery
	public List<PessoaFisica> listPessoasFisica() {
		return pessoaRepository.findByNomeContainingIgnoreCase("");
	}

	@GraphQLQuery
	public PessoaFisica getPessoaFisica(String cpf) {
		return pessoaRepository.findByCpf(cpf).get();
	}

	@GraphQLQuery
	public PessoaJuridica getPessoaJuridica(String cnpj) {
		return pessoaRepository.findByCnpj(cnpj).orElse(null);
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

//	Ações para manipular Produto ================================
	
	@GraphQLMutation
	public Produto cadastroProduto(Produto produto) {
		cloneObject("id", "findById", produtoRepository, produto);
		return produtoRepository.save(produto);
	}
	
	@GraphQLQuery
	public List<ItemClassificacao> listItensClassificacao(){
		return itemClassificacaoRepository.findAll();
	}
	
	
	@GraphQLMutation
	public ItemClassificacao cadastroItemClassificacao(ItemClassificacao item) {
		cloneObject("id", "findById", itemClassificacaoRepository, item);
		return itemClassificacaoRepository.save(item);
	}
	
	@GraphQLQuery
	public List<Produto> listProdutos(){
		return produtoRepository.findAll();
	}
	
	
	
//	FiM Ações para manipular Produto ================================
//	Ações para manipular Cooperado ================================

	@GraphQLQuery
	public PessoaFisica titularPF(@GraphQLContext Cooperado cooperado) {
		try {
			return (PessoaFisica) cooperado.getComponentes().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	@GraphQLQuery
	public PessoaJuridica titularPJ(@GraphQLContext Cooperado cooperado) {
		try {
			return (PessoaJuridica) cooperado.getComponentes().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@GraphQLQuery
	public Boolean conjunta(@GraphQLContext Cooperado cooperado) {
		try {
			return cooperado.getComponentes().size() > 1;
		} catch (Exception e) {
			return false;
		}
	}

	@GraphQLMutation
	public Cooperado adicionarComponenteMatricula(String matricula, String cpf) {

		Cooperado cooperado = cooperadoRepository.findByMatricula(matricula).orElseThrow(null);
		PessoaFisica pf = pessoaRepository.findByCpf(cpf).orElseThrow(null);

		List<Pessoa> componentes = cooperado.getComponentes();
		componentes = componentes == null ? new ArrayList<>() : componentes;
		componentes.add(pf);

		return cooperadoRepository.save(cooperado);
	}

	@GraphQLMutation
	public Cooperado cadastroCooperado(String matricula, String cpf) {
		PessoaFisica pf = pessoaRepository.findByCpf(cpf).orElseThrow(null);
		Cooperado cooperado = cooperadoRepository.findByMatricula(matricula).orElse(new Cooperado());
		cooperado.setMatricula(matricula);
		cooperado.setComponentes(Arrays.asList(pf));

		return cooperadoRepository.save(cooperado);
	}
	
	@GraphQLMutation
	public Cooperado cadastroCooperadoPJ(String matricula, String cnpj) {
		PessoaJuridica pj = pessoaRepository.findByCnpj(cnpj).orElseThrow(null);
		Cooperado cooperado = cooperadoRepository.findByMatricula(matricula).orElse(new Cooperado());
		cooperado.setMatricula(matricula);
		cooperado.setComponentes(Arrays.asList(pj));
		
		return cooperadoRepository.save(cooperado);
	}

	@GraphQLQuery
	public List<Cooperado> cooperados() {
		return cooperadoRepository.findAll();
	}

//	FIM Ações para manipular Cooperado ================================

//	Ações para manipular Unidades ================================
	@GraphQLQuery
	public Unidade getUnidade(int codigo) {
		return unidadeRepository.findByCodigo(codigo).orElse(null);
	}

	@GraphQLQuery
	public List<Unidade> listUnidades() {
		return unidadeRepository.findAll();
	}

	@GraphQLQuery
	public List<Unidade> unidadesFilhas(@GraphQLContext Unidade unidade) {
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

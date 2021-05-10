package br.com.coamo.marcelo.atividade.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.coamo.marcelo.atividade.models.Classificacao;
import br.com.coamo.marcelo.atividade.models.Cooperado;
import br.com.coamo.marcelo.atividade.models.Entrega;
import br.com.coamo.marcelo.atividade.models.FaixaDesconto;
import br.com.coamo.marcelo.atividade.models.ItemClassificacao;
import br.com.coamo.marcelo.atividade.models.NotaProdutor;
import br.com.coamo.marcelo.atividade.models.Pesagem;
import br.com.coamo.marcelo.atividade.models.Pessoa;
import br.com.coamo.marcelo.atividade.models.PessoaFisica;
import br.com.coamo.marcelo.atividade.models.PessoaJuridica;
import br.com.coamo.marcelo.atividade.models.ProcessoEntrada;
import br.com.coamo.marcelo.atividade.models.Produto;
import br.com.coamo.marcelo.atividade.models.Unidade;
import br.com.coamo.marcelo.atividade.repositories.ClassificacaoRepository;
import br.com.coamo.marcelo.atividade.repositories.CooperadoRepository;
import br.com.coamo.marcelo.atividade.repositories.EntregaRepository;
import br.com.coamo.marcelo.atividade.repositories.FaixaDescontoRepository;
import br.com.coamo.marcelo.atividade.repositories.ItemClassificacaoRepository;
import br.com.coamo.marcelo.atividade.repositories.PesagemRepository;
import br.com.coamo.marcelo.atividade.repositories.PessoaRepository;
import br.com.coamo.marcelo.atividade.repositories.ProcessoEntradaRepository;
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

	@Autowired
	private FaixaDescontoRepository faixaDescontoRepository;

	@Autowired
	private ProcessoEntradaRepository processoEntradaRepository;

	@Autowired
	private EntregaRepository entregaRepository;

	@Autowired
	private PesagemRepository pesagemRepository;
	
	@Autowired
	private ClassificacaoRepository classificacaoRepository;

	@GraphQLMutation(name = "processo_addNotaProdutor")
	public ProcessoEntrada adicionarNotaProdutor(String placa, String cpf, NotaProdutor nota) {
		ProcessoEntrada processo = processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa)
				.orElseThrow(() -> getException("Processo não existe"));
		Pessoa pessoa = pessoaRepository.findByCpf(cpf).orElseThrow(() -> getException("Pessoa fisica nao encontrada"));

		Optional<Entrega> data = entregaRepository.findByProcessoAndPessoa(processo, pessoa);
		if (data.isPresent()) {
			Entrega entrega = data.get();
			entrega.setNotaProdutor(nota);
			entregaRepository.save(entrega);
		}
		return processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa).orElseThrow(null);
	}

	@GraphQLQuery(name = "list_processos_pendentes")
	public List<ProcessoEntrada> processosPendentes() {
		return processoEntradaRepository.findAllByNotasPesagemNull();
	}

	@GraphQLQuery(name = "pesagens")
	public List<Pesagem> pesagens(@GraphQLContext ProcessoEntrada processo) {
		return pesagemRepository.findAllByProcesso(processo);
	}

	@GraphQLQuery(name = "processoPesagemFinalizado")
	public boolean processoPesagemFinalizado(@GraphQLContext ProcessoEntrada processo) {
		return pesagemRepository.countByProcesso(processo) >= 2;
	}

	@GraphQLMutation(name = "processo_informarMotorista")
	public ProcessoEntrada informarMotorista(String placa, PessoaFisica motorista) {
		ProcessoEntrada processo = processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa)
				.orElseThrow(() -> new UnsupportedOperationException("Processo não existe"));
		
		processo.setMotorista(motorista);
		
		return processoEntradaRepository.save(processo);
	}
	
	@GraphQLMutation(name = "processo_pesagem")
	public ProcessoEntrada pesarCarga(String placa, BigDecimal peso) {
		ProcessoEntrada processo = processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa)
				.orElseThrow(() -> new UnsupportedOperationException("Processo não existe"));

		if (pesagensFinalizadas(processo)) {
			exception("Processo de Pesagem finalizado!");
		}

		Pesagem pesagem = new Pesagem();
		pesagem.setPeso(peso);
		pesagem.setProcesso(processo);

		pesagemRepository.save(pesagem);

		if (pesagensFinalizadas(processo)) {
			processarPesagem(processo);
		}

		return processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa)
				.orElseThrow(() -> getException("Erro Processo pesagem"));
	}

	private void processarPesagem(ProcessoEntrada processo) {
		List<Pesagem> pesagens = pesagemRepository.findAllByProcesso(processo);
		BigDecimal peso1 = pesagens.get(0).getPeso();
		BigDecimal peso2 = pesagens.get(1).getPeso();

		BigDecimal peso = peso1.subtract(peso2);

		entregaRepository.findAllByProcesso(processo).forEach(entrega -> {
			BigDecimal percentual = entrega.getPercentual();
			entrega.setQuantidade(peso.multiply(percentual));
			entregaRepository.save(entrega);
		});
	}

	private boolean pesagensFinalizadas(ProcessoEntrada processo) {
		return pesagemRepository.countByProcesso(processo) == 2;
	}

	@GraphQLMutation(name = "processo_desmembrarCarga")
	public ProcessoEntrada desmembrarCarga(String placa, String matricula, String cpf, BigDecimal percentual) {
		ProcessoEntrada processo = processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa)
				.orElseThrow(() -> new UnsupportedOperationException("Processo não existe"));

		Cooperado cooperado = cooperadoRepository.findByMatricula(matricula)
				.orElseThrow(() -> new UnsupportedOperationException("Cooperado não localizado"));

		Pessoa titular = cpf.isEmpty() ? cooperado.getComponentes().get(0)
				: pessoaRepository.findByCpf(cpf)
						.orElseThrow(() -> new UnsupportedOperationException("Pessoa física não localizada"));

		Entrega entrega = entregaRepository.findByProcessoAndPessoa(processo, titular).orElse(new Entrega());
		entrega.setPercentual(percentual);
		entrega.setPessoa(titular);
		entrega.setProcesso(processo);

		entregaRepository.save(entrega);

		return processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa).orElseThrow(null);
	}

	@GraphQLQuery
	public List<Entrega> rateios(@GraphQLContext ProcessoEntrada processo) {
		return entregaRepository.findAllByProcesso(processo);
	}

	@GraphQLMutation(name = "processo_setProdutor")
	public ProcessoEntrada setProdutorToProcesso(String placa, String matricula, String cpf) {
		ProcessoEntrada processo = processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa)
				.orElseThrow(() -> new UnsupportedOperationException("Processo não existe"));
		Cooperado cooperado = cooperadoRepository.findByMatricula(matricula)
				.orElseThrow(() -> new UnsupportedOperationException("Cooperado não localizado"));

		Pessoa titular = cpf.isEmpty() ? cooperado.getComponentes().get(0)
				: pessoaRepository.findByCpf(cpf)
						.orElseThrow(() -> new UnsupportedOperationException("Pessoa física não localizada"));

		Entrega entrega = entregaRepository.findByProcessoAndPessoa(processo, titular).orElse(new Entrega());
		entrega.setPessoa(titular);
		entrega.setProcesso(processo);
		entrega.setPercentual(BigDecimal.ONE);

		entregaRepository.save(entrega);

		return processoEntradaRepository.save(processo);

	}

	@GraphQLMutation(name = "processo_abertura")
	public ProcessoEntrada entrada(String placa, int codigoUnidade) {

		ProcessoEntrada processo = processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa).orElse(null);
		if (processo != null) {
			throw new UnsupportedOperationException("Processo aberto para a placa informada");
		}
		
		Unidade unidade = unidadeRepository.findByCodigo(codigoUnidade).orElseThrow(()->getException("Unidade nao existe"));
		
		processo = new ProcessoEntrada();
		processo.setUnidade(unidade);
		processo.setPlaca(placa);
		processo.setRateios(new ArrayList<Entrega>());

		return processoEntradaRepository.save(processo);
	}
	
	@GraphQLQuery
	public Classificacao classificacao(@GraphQLContext ProcessoEntrada processo) {
		return classificacaoRepository.findByProcesso(processo).orElse(null);
	}

	@GraphQLMutation(name = "processo_setProduto")
	public ProcessoEntrada setProduto(String placa, Long idProduto) {

		Produto produto = produtoRepository.findById(idProduto)
				.orElseThrow(() -> new UnsupportedOperationException("Produto não cadastrado"));
		
		ProcessoEntrada processo = processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa).orElseThrow(()-> getException("Processo não existe"));
		processo.setProduto(produto);
		
		Classificacao c = classificacaoRepository.findByProcesso(processo).orElse(new Classificacao());
		c.setDescricao(produto.getDescricao());
		c.setProcesso(processo);
		
		List<ItemClassificacao> itensClassificacao = itemClassificacaoRepository.findAllByProduto(produto);
		
		for (ItemClassificacao i : itensClassificacao) {
			
			ItemClassificacao item = new ItemClassificacao();
			item.setFaixasDescontos(i.getFaixasDescontos());
			item.setDescricao(i.getDescricao());
			item.setProduto(produto);
			
			itensClassificacao.add(item);
		};

		
		c.setItensClassificacao(itensClassificacao);
		
		classificacaoRepository.save(c);
		
		
		return processoEntradaRepository.save(processo);

//		Optional<ProcessoEntrada> data = processoEntradaRepository.findByPlacaAndNotasPesagemNull(placa);
//		if (data.isPresent()) {
//			ProcessoEntrada processo = data.get();
//			processo.setProduto(produto);
//			
//			Classificacao classificacao = classificacaoRepository.findByProcesso(processo).orElse(new Classificacao());
//			classificacao.setDescricao(produto.getDescricao());
//			classificacao.setProcesso(processo);
//			classificacao.setItensClassificacao(new ArrayList<>());
//			
//			produto.getItensClassificacao().forEach(i->{
//				ItemClassificacao item = new ItemClassificacao();
//				item.setFaixasDescontos(i.getFaixasDescontos());
//				
//				item.setDescricao(i.getDescricao());
//				item.setProduto(produto);
//				
//				classificacao.getItensClassificacao().add(item);
//			});
//			
//			classificacaoRepository.save(classificacao);
//			
//			return processoEntradaRepository.save(processo);
//		}

//		throw new UnsupportedOperationException("Não há aberto para a placa informada");

	}

//	Ações para manipular Pessoa ================================

	@GraphQLQuery(name = "list_PF")
	public List<PessoaFisica> listPessoasFisica() {
		return pessoaRepository.findPfByNomeContainingIgnoreCase("");
	}

	@GraphQLQuery(name = "list_PJ")
	public List<PessoaJuridica> listPessoasJuridica() {
		return pessoaRepository.findPjByNomeContainingIgnoreCase("");
	}

	@GraphQLQuery(name = "get_PF")
	public PessoaFisica getPessoaFisica(String cpf) {
		return pessoaRepository.findByCpf(cpf).get();
	}

	@GraphQLQuery(name = "get_PJ")
	public PessoaJuridica getPessoaJuridica(String cnpj) {
		return pessoaRepository.findByCnpj(cnpj).orElse(null);
	}

	@GraphQLMutation(name = "add_PJ")
	public PessoaJuridica cadastroPessoaJuridica(PessoaJuridica pessoaJuridica) {
		cloneObject("id", "findById", pessoaRepository, pessoaJuridica);
		cloneObject("cpf", "findByCpf", pessoaRepository, pessoaJuridica);
		return pessoaRepository.save(pessoaJuridica);
	}

	@GraphQLMutation(name = "add_PF")
	public PessoaFisica cadastroPessoaFisica(PessoaFisica pessoaFisica) {
		cloneObject("id", "findById", pessoaRepository, pessoaFisica);
		cloneObject("cpf", "findByCpf", pessoaRepository, pessoaFisica);
		return pessoaRepository.save(pessoaFisica);
	}

//	FIM Ações para manipular Pessoa ================================

//	Ações para manipular Produto ================================

	@GraphQLMutation(name = "add_produto")
	public Produto cadastroProduto(Produto produto) {
		cloneObject("id", "findById", produtoRepository, produto);
		return produtoRepository.save(produto);
	}

	@GraphQLMutation(name = "add_faixaDesconto")
	public FaixaDesconto cadastroFaixaDesconto(FaixaDesconto faixa) {
		cloneObject("id", "findById", faixaDescontoRepository, faixa);
		return faixaDescontoRepository.save(faixa);
	}

	@GraphQLQuery(name = "faixasDescontos")
	public List<FaixaDesconto> faixasDescontos(@GraphQLContext ItemClassificacao item) {
		return faixaDescontoRepository.findAllByItemClassificacao(item);
	}

	@GraphQLQuery(name = "list_faixasDescontos")
	public List<FaixaDesconto> listFaixasDescontos() {
		return faixaDescontoRepository.findAll();
	}

	@GraphQLQuery(name = "list_itensClassificacao")
	public List<ItemClassificacao> listItensClassificacao() {
		return itemClassificacaoRepository.findAll();
	}

	@GraphQLQuery(name = "itensClassificacao")
	public List<ItemClassificacao> itensClassificacao(@GraphQLContext Produto produto) {
		return itemClassificacaoRepository.findAllByProduto(produto);
	}

	@GraphQLMutation(name = "add_itemClassificacao")
	public ItemClassificacao cadastroItemClassificacao(ItemClassificacao item) {
		cloneObject("id", "findById", itemClassificacaoRepository, item);
		return itemClassificacaoRepository.save(item);
	}

	@GraphQLQuery(name = "list_produtos")
	public List<Produto> listProdutos() {
		return produtoRepository.findAll();
	}

//	FiM Ações para manipular Produto ================================
//	Ações para manipular Cooperado ================================

	@GraphQLQuery(name = "titularPF")
	public PessoaFisica titularPF(@GraphQLContext Cooperado cooperado) {
		try {
			return (PessoaFisica) cooperado.getComponentes().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@GraphQLQuery(name = "titularPJ")
	public PessoaJuridica titularPJ(@GraphQLContext Cooperado cooperado) {
		try {
			return (PessoaJuridica) cooperado.getComponentes().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@GraphQLQuery(name = "conjunta")
	public Boolean conjunta(@GraphQLContext Cooperado cooperado) {
		try {
			return cooperado.getComponentes().size() > 1;
		} catch (Exception e) {
			return false;
		}
	}

	@GraphQLMutation(name = "add_componentParaMatricula")
	public Cooperado adicionarComponenteMatricula(String matricula, String cpf) {

		Cooperado cooperado = cooperadoRepository.findByMatricula(matricula).orElseThrow(null);
		PessoaFisica pf = pessoaRepository.findByCpf(cpf).orElseThrow(null);

		List<Pessoa> componentes = cooperado.getComponentes();
		componentes = componentes == null ? new ArrayList<>() : componentes;
		componentes.add(pf);

		return cooperadoRepository.save(cooperado);
	}

	@GraphQLMutation(name = "add_cooperado")
	public Cooperado cadastroCooperado(String matricula, String cpf) {
		PessoaFisica pf = pessoaRepository.findByCpf(cpf).orElseThrow(null);
		Cooperado cooperado = cooperadoRepository.findByMatricula(matricula).orElse(new Cooperado());
		cooperado.setMatricula(matricula);
		cooperado.setComponentes(Arrays.asList(pf));

		return cooperadoRepository.save(cooperado);
	}

	@GraphQLMutation(name = "add_cooperadoPJ")
	public Cooperado cadastroCooperadoPJ(String matricula, String cnpj) {
		PessoaJuridica pj = pessoaRepository.findByCnpj(cnpj).orElseThrow(null);
		Cooperado cooperado = cooperadoRepository.findByMatricula(matricula).orElse(new Cooperado());
		cooperado.setMatricula(matricula);
		cooperado.setComponentes(Arrays.asList(pj));

		return cooperadoRepository.save(cooperado);
	}

	@GraphQLQuery(name = "list_cooperados")
	public List<Cooperado> cooperados() {
		return cooperadoRepository.findAll();
	}

//	FIM Ações para manipular Cooperado ================================

//	Ações para manipular Unidades ================================
	@GraphQLQuery(name = "get_unidade")
	public Unidade getUnidade(int codigo) {
		return unidadeRepository.findByCodigo(codigo).orElse(null);
	}

	@GraphQLQuery(name = "list_unidades")
	public List<Unidade> listUnidades() {
		return unidadeRepository.findAll();
	}

	@GraphQLQuery(name = "list_unidadesFilhas")
	public List<Unidade> unidadesFilhas(@GraphQLContext Unidade unidade) {
		return unidadeRepository.findAllByMae(unidade);
	}

	@GraphQLMutation(name = "add_unidadeFilha")
	public Unidade adicionarUnidadeFilha(int codigoUnidadeMae, int codigoUnidadeFilha) {
		Unidade mae = unidadeRepository.findByCodigo(codigoUnidadeMae).orElseThrow(null);
		Unidade filha = unidadeRepository.findByCodigo(codigoUnidadeFilha).orElseThrow(null);

		filha.setMae(mae);
		unidadeRepository.save(filha);

		return mae;
	}

	@GraphQLMutation(name = "remove_unidade")
	public boolean removeUnidade(Long id) {
		boolean exists = unidadeRepository.existsById(id);
		unidadeRepository.deleteById(id);
		return exists;
	}

	@GraphQLMutation(name = "add_unidade")
	public Unidade setUnidade(Unidade unidade) {
		cloneObject("codigo", "findByCodigo", unidadeRepository, unidade);
		return unidadeRepository.saveAndFlush(unidade);
	}
//	Unidades ================================

	private void exception(String message) {
		throw new UnsupportedOperationException(message);
	}

	private final UnsupportedOperationException getException(String message) {
		return new UnsupportedOperationException(message);
	}
}

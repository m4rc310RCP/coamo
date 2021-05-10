package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity(name="Processo_entrada")
@Getter @Setter
public class ProcessoEntrada implements Serializable {

	private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;
    
    private String placa;
    
    @OneToOne(cascade = CascadeType.ALL)
    private PessoaFisica motorista;
    
    @Transient
    private boolean finalizado;
    
    @Transient
    private boolean processoPesagemFinalizado;
    
    @OneToMany
    private List<NotaPesagem> notasPesagem;
    
//    @OneToMany(cascade = CascadeType.ALL)
    @Transient
    private List<Entrega> rateios;
    
    
    @Transient
    private List<Pesagem> pesagens;
    
    
    @ManyToOne
    private Produto produto;
    
    @Transient
    private Classificacao classificacao;
    
    
//    @JoinColumn(name="fk_Unidade_id")
    @OneToOne
    private Unidade unidade;


}

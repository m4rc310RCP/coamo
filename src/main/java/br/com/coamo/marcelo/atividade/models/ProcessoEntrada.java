package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

//@Entity(name="Processo_entrada")
@Getter @Setter
public class ProcessoEntrada implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;
    private int numero;
    @OneToMany(mappedBy="processoEntrada")
    private Set<Entrega> entrega;
    @ManyToOne
    @JoinColumn(name="fk_Pesagem_Pesagem_PK")
    private Pesagem pesagem;
    @ManyToOne
    @JoinColumn(name="fk_Classificacao_id")
    private Classificacao classificacao;
    @ManyToOne
    @JoinColumn(name="fk_Unidade_id")
    private Unidade unidade;


}

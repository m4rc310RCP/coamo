package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter @Setter
public class Classificacao implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;
    private String descricao;
    @OneToMany(mappedBy="classificacao")
    private Set<ItemClassificacao> itemClassificacao;
    @OneToMany(mappedBy="classificacao")
    private Set<ProcessoEntrada> processoEntrada;


}

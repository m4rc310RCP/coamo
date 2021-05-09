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

//@Entity(name="faixa_desconto")
@Getter @Setter
public class FaixaDesconto implements Serializable {

   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
//    @Id
    @Column(name="faixa_desconto_PK", nullable=false)
    private Long faixaDescontoPk;
    
    private double inicio;
    private double fim;
    private double percentual;
    @OneToMany(mappedBy="faixaDesconto")
    private Set<ItemClassificacao> itemClassificacao;

  

}

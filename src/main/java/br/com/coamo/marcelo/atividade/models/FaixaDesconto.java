package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity(name="faixa_desconto")
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
//    @Column(name="faixa_desconto_PK", nullable=false)
//    private Long faixaDescontoPk;
    
    private BigDecimal inicio;
    private BigDecimal fim;
    private BigDecimal percentual;
    
    @OneToOne()
    private ItemClassificacao itemClassificacao;
    
//    @OneToMany
//    private List<ItemClassificacao> itemClassificacao;

  

}

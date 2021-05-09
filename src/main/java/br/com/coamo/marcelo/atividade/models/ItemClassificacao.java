package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity(name="Item_classificacao")
@Getter @Setter
public class ItemClassificacao implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;
    
    private String descricao;
    private double percentual;
    
    @ManyToOne
    private Produto produto;
    
    
//    @ManyToOne
//    @JoinColumn(name="fk_faixa_desconto_faixa_desconto_PK")
//    private FaixaDesconto faixaDesconto;
//    @ManyToOne
//    @JoinColumn(name="fk_Classificacao_id")
//    private Classificacao classificacao;


}

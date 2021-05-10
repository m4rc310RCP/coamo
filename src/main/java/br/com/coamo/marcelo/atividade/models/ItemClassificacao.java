package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

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
    
    @ManyToOne
    private Produto produto;
    
    
    @Transient
    private List<FaixaDesconto> faixasDescontos;
    
    
//    @ManyToOne
//    @JoinColumn(name="fk_Classificacao_id")
//    private Classificacao classificacao;


}

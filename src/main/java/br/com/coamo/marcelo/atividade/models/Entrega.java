package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Entrega implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal percentual;
    private BigDecimal quantidade;
    @Column(name="taxa_recepcao")
    private BigDecimal taxaRecepcao;
    
    @OneToOne
    private ProcessoEntrada processo;
    
    @OneToOne
    private Pessoa pessoa;
    
    @OneToOne(cascade = CascadeType.ALL)
    private NotaProdutor notaProdutor;
//    
//    @ManyToOne
//    @JoinColumn(name="fk_Cooperado_id")
//    private Cooperado cooperado;
//    
//    @ManyToOne
//    @JoinColumn(name="fk_Processo_entrada_id")
//    private ProcessoEntrada processoEntrada;


}

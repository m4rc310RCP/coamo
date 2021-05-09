package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter @Setter
public class Entrega implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double percentual;
    private double quantidade;
    @Column(name="taxa_recepcao")
    private double taxaRecepcao;
    
    @ManyToOne
    @JoinColumn(name="fk_Nota_produtor_id")
    private NotaProdutor notaProdutor;
    
    @ManyToOne
    @JoinColumn(name="fk_Cooperado_id")
    private Cooperado cooperado;
    
    @ManyToOne
    @JoinColumn(name="fk_Processo_entrada_id")
    private ProcessoEntrada processoEntrada;


}

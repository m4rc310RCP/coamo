package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity(name="Nota_produtor")
@Getter @Setter
public class NotaProdutor implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;
    
    private int numero;
    private int serie;
    
    
    @Column(name="data_emissao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;
    
    @Column(name="data_vencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    

   
}

package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

//@Entity(name="Nota_produtor")
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
    private LocalDateTime dataEmissao;
    @Column(name="data_vencimento")
    private LocalDate dataVencimento;
    @OneToMany(mappedBy="notaProdutor")
    private Set<Entrega> entrega;

   
}

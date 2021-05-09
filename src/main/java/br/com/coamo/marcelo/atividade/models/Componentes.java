package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Setter;

@Entity
@Setter
public class Componentes implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne()
    private Cooperado cooperado;
    
    @ManyToOne
    private PessoaFisica pessoaFisica;
}

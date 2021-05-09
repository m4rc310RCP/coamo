package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter @Setter
public class Socios implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne
    @JoinColumn(name="fk_Pessoa_juridica_fk_Cooperado_id")
    private PessoaJuridica pessoaJuridica;
    @ManyToOne
    @JoinColumn(name="fk_Pessoa_fisica_fk_Cooperado_id")
    private PessoaFisica pessoaFisica;

}

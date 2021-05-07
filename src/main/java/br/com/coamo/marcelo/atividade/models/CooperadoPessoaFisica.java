package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CooperadoPessoaFisica extends Cooperado  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String matricula;
	
	@Transient
	private PessoaFisica pessoa;
	
	@OneToMany(mappedBy = "id")
	private List<PessoaFisica> componentes;
}

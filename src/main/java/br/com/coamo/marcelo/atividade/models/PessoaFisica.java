package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PessoaFisica extends Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String cpf;
	
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	private String rg;
	

}

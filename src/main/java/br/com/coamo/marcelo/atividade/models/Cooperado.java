package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public class Cooperado  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String matricula;
	
	@Transient
	private boolean individual;
}

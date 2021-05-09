package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter @Setter
public class Pesagem implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Id
    @Column(name="Pesagem_PK", nullable=false)
    private int pesagemPk;
    @Column(name="primeiro_peso")
    private double primeiroPeso;
    @Column(name="segundo_peso")
    private double segundoPeso;
    @OneToMany(mappedBy="pesagem")
    private Set<ProcessoEntrada> processoEntrada;

}

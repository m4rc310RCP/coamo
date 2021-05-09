package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Unidade implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private int codigo;
    
    @ManyToOne
    private Unidade mae;
    
    @Transient
    private List<Unidade> unidadesFilhas;
//    
//    @OneToMany(mappedBy="unidade")
//    private Set<ProcessoEntrada> processosEntrada;
    
    @ManyToOne()
    private Endereco endereco;

    
}

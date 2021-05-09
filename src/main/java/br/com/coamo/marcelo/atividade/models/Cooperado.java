package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Cooperado implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;
    
    private String matricula;
    
    @OneToMany(mappedBy = "cooperado")
    private List<Componentes> componentes;
    
//    @OneToMany(mappedBy="cooperado")
//    private Set<Entrega> entrega;
    
//    @OneToMany(mappedBy="cooperado")
//    private Set<PessoaFisica> pessoaFisica;
//    
//    @OneToMany(mappedBy="cooperado")
//    private Set<PessoaJuridica> pessoaJuridica;


}

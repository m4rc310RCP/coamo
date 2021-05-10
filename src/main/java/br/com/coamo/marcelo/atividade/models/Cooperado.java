package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
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
    
    @Transient
    private PessoaFisica titularPF;
    
    @Transient
    private PessoaJuridica titularPJ;
    
    @Transient
    private Boolean conjunta;
    
    @OneToMany()
    private List<Pessoa> componentes;
    
//    @Transient
//    private List<Entrega> entregas;
    
//    @OneToMany(mappedBy="cooperado")
//    private Set<PessoaFisica> pessoaFisica;
//    
//    @OneToMany(mappedBy="cooperado")
//    private Set<PessoaJuridica> pessoaJuridica;


}

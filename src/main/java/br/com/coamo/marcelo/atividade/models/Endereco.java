package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Endereco implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;
    
    private String logradouro;
    private String municipio;
    private String uf;
//    
//    @OneToMany(mappedBy="endereco")
//    private Set<PessoaFisica> pessoaFisica;
//    
//    @OneToMany(mappedBy="endereco")
//    private Set<PessoaJuridica> pessoaJuridica;
//    
//    @OneToMany(mappedBy="endereco")
//    private Set<Unidade> unidade;

}

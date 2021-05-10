package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

//@Entity(name="estado_civil")
@Getter @Setter
public class EstadoCivil implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
//    @Id
    @Column(name="estado_civil_PK", nullable=false)
    private int estadoCivilPk;
    
    private String descricao;
    private String sigla;
    @OneToMany(mappedBy="estadoCivil")
    private Set<PessoaFisica> pessoaFisica;

    

}

package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name="Pessoa_fisica")
public class PessoaFisica extends Pessoa implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

    @Column(name="data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    
   
//    @Column(unique = true)
    private String cpf;
    private String rg;
    
//    @OneToMany()
//    private List<Componentes> componentes;
//    @ManyToOne
//    @JoinColumn(name="fk_estado_civil_estado_civil_PK")
//    private EstadoCivil estadoCivil;
//    @ManyToOne
//    @JoinColumn(name="fk_Cooperado_id")
//    private Cooperado cooperado;
//    @ManyToOne
//    @JoinColumn(name="fk_Endereco_id")
//    private Endereco endereco;
//    @OneToMany(mappedBy="pessoaFisica")
//    private Set<Socios> socios;


}

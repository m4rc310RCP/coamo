package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="Pessoa_juridica")
@Getter @Setter
public class PessoaJuridica extends Pessoa implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String cnpj;
    
    @Column(name="inscricao_junta_comercial")
    private String inscricaoJuntaComercial;
    
   
    
//    @ManyToOne
//    @JoinColumn(name="fk_Cooperado_id")
//    private Cooperado cooperado;
//    @ManyToOne
//    @JoinColumn(name="fk_Endereco_id")
//    private Endereco endereco;
//    @OneToMany(mappedBy="pessoaJuridica")
//    private Set<Socios> socios;


}

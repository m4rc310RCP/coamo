package br.com.coamo.marcelo.atividade.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    
    @ManyToOne
    private Endereco endereco;
    
    @OneToMany()
    private List<PessoaFisica> socios;


}

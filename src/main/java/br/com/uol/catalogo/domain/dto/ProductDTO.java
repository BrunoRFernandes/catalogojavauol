package br.com.uol.catalogo.domain.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForBigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

public class ProductDTO {
	
	private Long id;

	@NotEmpty(message = "O Campo Nome é Obrigatório")
	@Length(min = 3, message = "O Nome deve conter no minímo 3 caracteres")
	private String nome;

	@NotEmpty(message = "O Campo Descrição é Obrigatório")
	private String descricao;

	@DecimalMin(value = "0.1", message = "O valor deve ser maior que 0")
	private double preco;

	public ProductDTO() {}
	
	public ProductDTO(Long id, String nome, String descricao, double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	

}

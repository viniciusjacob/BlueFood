package com.bluefood.domain.pagamento;

public enum StatusPagamento {

	Autorizado("Autorizado"),
	NaoAutorizado("N�o autorizado pela institui��o financeira"),
	CartaoInvalido("Cart�o inv�lido ou bloqueado");
	
	String descricao;
	
	private StatusPagamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}

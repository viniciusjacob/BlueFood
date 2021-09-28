package com.bluefood.domain.pagamento;

import javax.validation.constraints.Pattern;

public class DadosCartao {

	@Pattern(regexp = "\\d{16}", message = "O numero de cart�o � invalido")
	private String numCartao;
	
	public String getNumCartao() {
		return numCartao;
	}
	
	public void setNumCartao(String numCartao) {
		this.numCartao = numCartao;
	}
}

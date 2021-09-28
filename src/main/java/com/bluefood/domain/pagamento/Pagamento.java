package com.bluefood.domain.pagamento;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bluefood.domain.pedido.Pedido;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "pagamento")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pagamento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@OneToOne
	private Pedido pedido;

	@NotNull
	private LocalDateTime data;

	@Column(name = "num_cartao_final")
	@NotNull
	@Size(min = 4, max = 4)
	private String numCartaoFinal;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private BandeiraCartao bandeiraCartao;

	public void definirNumeroEBandeira(String numCartao) {
		numCartaoFinal = numCartao.substring(12);
		bandeiraCartao = getBandeira(numCartao);
	}

	private BandeiraCartao getBandeira(String numCartao) {

		if (numCartao.endsWith("0000")) {

			return BandeiraCartao.AMEX;
		}

		if (numCartao.endsWith("1111")) {

			return BandeiraCartao.ELO;
		}
		
		if (numCartao.endsWith("2222")) {

			return BandeiraCartao.MASTER;
		}
		
		return BandeiraCartao.VISA;
	}
}

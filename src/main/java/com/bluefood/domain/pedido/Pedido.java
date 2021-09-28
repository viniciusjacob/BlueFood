package com.bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bluefood.domain.cliente.Cliente;
import com.bluefood.domain.restaurante.Restaurante;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {
	
	public enum Status {
		
		Producao(1, "Pedido esta sendo preparado", false),
		Entrega(2, "Pedido saiu para entrega", false),
		Concluido(3, "Pedido entregue", true);
		
		private Status(int ordem, String descricao, boolean ultimo) {
			
			this.ordem = ordem;
			this.descricao = descricao;
			this.ultimo = ultimo;
		}
		
		int ordem;
		String descricao;
		boolean ultimo;
		
		public int getOrdem() {
			return ordem;
		}
		
		public String getDescricao() {
			return descricao;
		}
		
		public boolean isUltimo() {
			return ultimo;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private LocalDateTime data;
	
	@NotNull
	private Status status;
	
	@NotNull
	@ManyToOne
	private Cliente cliente;
	
	@NotNull
	@ManyToOne
	private Restaurante restaurante;
	
	@NotNull
	private BigDecimal subtotal;
	
	@NotNull
	private BigDecimal total;
	
	@NotNull
	@Column(name = "taxa_entrega")
	private BigDecimal taxaEntrega;
	
	@OneToMany(mappedBy = "id.pedido", fetch = FetchType.EAGER)
	private Set<ItemPedido> itens;
	
	public String getFormattedId() {
		
		return String.format("#%04d", id);
	}
}

package com.bluefood.domain.restaurante;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bluefood.domain.usario.Usuario;
import com.bluefood.infrastructure.web.validator.UploadConstraint;
import com.bluefood.util.FileType;
import com.bluefood.util.StringUtils;

import org.springframework.web.multipart.MultipartFile;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "restaurante")
public class Restaurante extends Usuario {

    @NotBlank(message = "O CNPJ não pode ficar vazio")
    @Pattern(regexp = "[0-9]{14}", message = "O CNPJ possui formato inválido")
    @Column(length = 14)
    private String cnpj;

    @Size(max = 80)
	private String logotipo;

    @UploadConstraint(acceptedTypes = { FileType.PNG, FileType.JPG })
	private transient MultipartFile logotipoFile;

    @NotNull(message = "A taxa de entrega não pode ficar vazia")
    @Min(0)
    @Max(99)
    private BigDecimal taxaEntrega;

    @NotNull(message = "O tempo de entrega não pode ficar vazio")
    @Min(0)
	@Max(120)
	private Integer tempoEntregabase;

    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "restaurante_has_categoria", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "categoria_restaurante_id"))
	@Size(min = 1, message = "selecione pelo menos uma categoria")
	@ToString.Exclude
	private Set<CategoriaRestaurante> categorias = new HashSet<>(0);

    @OneToMany(mappedBy = "restaurante")
	private Set<ItemCardapio> itensCardapio = new HashSet<>(0);

    public void setLogotipoFileName() {

		if (getId() == null) {

			throw new IllegalStateException("É preciso primeiro gravar o registro");
		}

		this.logotipo = String.format("%04d-logo.%S", getId(),
				FileType.of(logotipoFile.getContentType()).getExtension());
	}

	public String getCategoriaAsText() {
		Set<String> strings = new LinkedHashSet<>();
		for (CategoriaRestaurante categoria : categorias) {

			strings.add(categoria.getNome());
		}

		return StringUtils.concatenation(strings);
	}

	public Integer calcularTempoEntrega(String cep) {
		int soma = 0;

		for (char c : cep.toCharArray()) {
			int v = Character.getNumericValue(c);

			if (v > 0) {
				soma += v;
			}
		}

		soma /= 2;

		return tempoEntregabase + soma;
	}
}

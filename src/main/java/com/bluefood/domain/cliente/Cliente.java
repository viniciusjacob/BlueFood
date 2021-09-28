package com.bluefood.domain.cliente;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.bluefood.domain.usario.Usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "cliente")
public class Cliente extends Usuario{
    
    @NotBlank(message = "O CPF não pode ficar vazio")
    @Pattern(regexp = "[0-9]{11}", message = "O CPF possui formato inválido")
    @Column(length = 11)
    private String cpf;

    @NotBlank(message = "O CEP não pode ficar vazio")
    @Pattern(regexp = "[0-9]{8}", message = "O CEP possui formato inválido")
    @Column(length = 8)
    private String cep;
}

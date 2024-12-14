package br.ufsm.poli.csi.lauren.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UsuarioCriacao {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @Email(message = "Email inválido")
    private String email;
    @NotBlank(message = "Senha é obrigatória")
    @Length(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    private String senha;
}

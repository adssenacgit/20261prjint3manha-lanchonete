package br.com.senac.prjint3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @Schema(example = "Administrador")
        @NotBlank(message = "O nome do usuário é obrigatório.")
        @Size(max = 300, message = "O nome do usuário deve ter no máximo 300 caracteres.")
        String nome,

        @Schema(example = "admin")
        @NotBlank(message = "O login é obrigatório.")
        @Size(max = 45, message = "O login deve ter no máximo 45 caracteres.")
        String login,

        @Schema(example = "123456")
        @NotBlank(message = "A senha é obrigatória.")
        @Size(max = 200, message = "A senha deve ter no máximo 200 caracteres.")
        String senha,

        @Schema(example = "1", description = "-1 apagado, 0 inativo, 1 ativo")
        Integer status
) {
}

package br.com.senac.prjint3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
        @Schema(example = "Bebidas")
        @NotBlank(message = "O nome da categoria é obrigatório.")
        @Size(max = 200, message = "O nome da categoria deve ter no máximo 200 caracteres.")
        String nome,

        @Schema(example = "1", description = "-1 apagado, 0 inativo, 1 ativo")
        Integer status
) {
}

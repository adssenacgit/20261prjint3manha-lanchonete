package br.com.senac.prjint3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoRequest(
        @Schema(example = "Café Especial")
        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres.")
        String nome,

        @Schema(example = "Café torrado e moído")
        @NotBlank(message = "A descrição do produto é obrigatória.")
        String descricao,

        @Schema(example = "25.90")
        @NotNull(message = "O preço do produto é obrigatório.")
        @DecimalMin(value = "0.00", inclusive = true, message = "O preço não pode ser negativo.")
        BigDecimal preco,

        @Schema(example = "https://exemplo.com/cafe.jpg")
        @NotBlank(message = "A URL da imagem é obrigatória.")
        @Size(max = 255, message = "A URL da imagem deve ter no máximo 255 caracteres.")
        String imagemUrl,

        @Schema(example = "1", description = "-1 apagado, 0 inativo, 1 ativo")
        Integer status,

        @Schema(example = "1")
        @NotNull(message = "O id da categoria é obrigatório.")
        Integer categoriaId
) {
}

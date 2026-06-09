package br.com.senac.prjint3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemPedidoRequest(
        @Schema(example = "2")
        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser pelo menos 1.")
        Integer quantidade,

        @Schema(example = "25.90")
        @NotNull(message = "O preço unitário é obrigatório.")
        @DecimalMin(value = "0.00", inclusive = true, message = "O preço unitário não pode ser negativo.")
        BigDecimal precoUnitario,

        @Schema(example = "51.80")
        @NotNull(message = "O subtotal é obrigatório.")
        @DecimalMin(value = "0.00", inclusive = true, message = "O subtotal não pode ser negativo.")
        BigDecimal subtotal,

        @Schema(example = "1", description = "-1 apagado, 0 inativo, 1 ativo")
        Integer status,

        @Schema(example = "1")
        @NotNull(message = "O id do pedido é obrigatório.")
        Integer pedidoId,

        @Schema(example = "1")
        @NotNull(message = "O id do produto é obrigatório.")
        Integer produtoId
) {
}

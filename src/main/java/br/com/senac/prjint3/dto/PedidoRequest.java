package br.com.senac.prjint3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoRequest(
        @Schema(example = "1001")
        @NotNull(message = "O número do pedido é obrigatório.")
        Integer numero,

        @Schema(example = "2026-06-03T10:30:00")
        @NotNull(message = "A data do pedido é obrigatória.")
        LocalDateTime data,

        @Schema(example = "51.80")
        @NotNull(message = "O total do pedido é obrigatório.")
        @DecimalMin(value = "0.00", inclusive = true, message = "O total do pedido não pode ser negativo.")
        BigDecimal total,

        @Schema(example = "1", description = "-1 apagado, 0 inativo, 1 ativo")
        Integer status
) {
}

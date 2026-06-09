package br.com.senac.prjint3.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponse(
        Integer id,
        Integer numero,
        LocalDateTime data,
        BigDecimal total,
        Integer status
) {
}

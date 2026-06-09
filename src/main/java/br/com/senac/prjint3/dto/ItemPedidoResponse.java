package br.com.senac.prjint3.dto;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Integer id,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal,
        Integer status,
        Integer pedidoId,
        Integer pedidoNumero,
        Integer produtoId,
        String produtoNome
) {
}

package br.com.senac.prjint3.dto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Integer id,
        String nome,
        String descricao,
        BigDecimal preco,
        String imagemUrl,
        Integer status,
        Integer categoriaId,
        String categoriaNome
) {
}

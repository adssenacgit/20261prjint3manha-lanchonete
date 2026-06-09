package br.com.senac.prjint3.dto;

public record UsuarioResponse(
        Integer id,
        String nome,
        String login,
        Integer status
) {
}

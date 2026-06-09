package br.com.senac.prjint3.domain;

public final class RegistroStatus {

    public static final int EXCLUIDO = -1;
    public static final int INATIVO = 0;
    public static final int ATIVO = 1;

    private RegistroStatus() {
    }

    public static int novoOuPadrao(Integer status) {
        return status == null ? ATIVO : validar(status);
    }

    public static int validar(Integer status) {
        if (status == null) {
            throw new IllegalArgumentException("O status deve ser informado.");
        }

        if (status != EXCLUIDO && status != INATIVO && status != ATIVO) {
            throw new IllegalArgumentException("Status inválido. Use -1 para apagado, 0 para inativo ou 1 para ativo.");
        }

        return status;
    }
}

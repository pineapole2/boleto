package com.math.boleto.fornecedor.util;

public class BoletoUtils {

    private BoletoUtils() {}

    public static String sanitizeLinhaDigitavel(String linha) {
        if (linha == null) return null;
        return linha.replaceAll("[^0-9]", "");
    }

    public static String linhaDigitavelParaCodigoBarras(String linhaDigitavel) {

        String linha = sanitizeLinhaDigitavel(linhaDigitavel);

        if (linha == null) {
            throw new IllegalArgumentException("Linha digitável inválida");
        }

        if (linha.length() == 47) {
            return boletoBancarioParaCodigoBarras(linha);
        }

        if (linha.length() == 48) {
            return boletoArrecadacaoParaCodigoBarras(linha);
        }

        throw new IllegalArgumentException(
                "Linha digitável com tamanho inválido: " + linha.length()
        );
    }

    private static String boletoBancarioParaCodigoBarras(String linha47) {

        String bancoMoeda = linha47.substring(0, 4);
        String dvGeral = linha47.substring(32, 33);
        String fatorValor = linha47.substring(33, 47);

        String campoLivre =
                linha47.substring(4, 9) +
                linha47.substring(10, 20) +
                linha47.substring(21, 31);

        return bancoMoeda
                + dvGeral
                + fatorValor
                + campoLivre;
    }

    private static String boletoArrecadacaoParaCodigoBarras(String linha48) {

        StringBuilder codigoBarras = new StringBuilder(44);

        for (int i = 0; i < 4; i++) {
            int inicio = i * 12;
            int fim = inicio + 11;
            codigoBarras.append(linha48, inicio, fim);
        }

        return codigoBarras.toString();
    }
}

package com.math.boleto.fornecedor.util;

public class BoletoUtils {

    private BoletoUtils() {}

    public static String sanitizeLinhaDigitavel(String linha) {
        if (linha == null) return null;
        return linha.replaceAll("[^0-9]", "");
    }

    public static String linhaDigitavelParaCodigoBarras(String linhaDigitavel) {

        String linha47 = sanitizeLinhaDigitavel(linhaDigitavel);

        if (linha47 == null || linha47.length() != 47) {
            throw new IllegalArgumentException("Linha digitável inválida");
        }

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
}

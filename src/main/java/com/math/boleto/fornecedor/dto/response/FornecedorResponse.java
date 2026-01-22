package com.math.boleto.fornecedor.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FornecedorResponse {

    private Long id;
    private String fornecedor;
    private Long nf;
    private LocalDate dataEmissao;
    private LocalDate vencimento;
    private BigDecimal valor;
    private String parcelas;
    private boolean pago;
    private String obs;

    private String linhaDigitavel;
}

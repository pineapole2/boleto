package com.math.boleto.fornecedor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fornecedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fornecedor;
    private Long nf;
    private LocalDate dataEmissao;
    private LocalDate vencimento;
    private BigDecimal valor;
    private String parcelas;
    private boolean pago;
    private String obs;
    
    @Column(length = 60)
    private String linhaDigitavel;

    @Column(length = 60)
    private String codigoBarras;
}

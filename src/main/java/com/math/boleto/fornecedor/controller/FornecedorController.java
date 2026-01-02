package com.math.boleto.fornecedor.controller;

import com.math.boleto.fornecedor.dto.response.FornecedorResponse;
import com.math.boleto.fornecedor.service.FornecedorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponse>> findAll(
            @RequestParam(required = false) String fornecedor,
            @RequestParam(required = false) Long nf,
            @RequestParam(required = false) BigDecimal valor,
            @RequestParam(required = false) String parcelas,
            @RequestParam(required = false) String obs,
            @RequestParam(required = false) Boolean pago,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate vencimentoDe,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate vencimentoAte
    ) {
        return ResponseEntity.ok(
                fornecedorService.findWithFilters(
                        fornecedor, nf, valor, parcelas, obs, pago,
                        vencimentoDe, vencimentoAte
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> findById(@PathVariable Long id){
        return ResponseEntity
                .ok(fornecedorService.findById(id));
    }

}

package com.math.boleto.fornecedor.service;

import com.math.boleto.fornecedor.dto.response.FornecedorResponse;
import com.math.boleto.fornecedor.entity.Fornecedor;
import com.math.boleto.fornecedor.repository.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<FornecedorResponse> findWithFilters(
            String fornecedor,
            Long nf,
            BigDecimal valor,
            String parcelas,
            String obs,
            Boolean pago,
            LocalDate vencimentoDe,
            LocalDate vencimentoAte
    ) {
        return fornecedorRepository.findAll()
                .stream()
                .filter(f -> matchesFilters(
                        f, fornecedor, nf, valor, parcelas,
                        obs, pago, vencimentoDe, vencimentoAte
                ))
                .map(this::toResponse)
                .toList();
    }

    public FornecedorResponse findById(Long id){
        Fornecedor fornecedor = findFornecedorById(id);
        return toResponse(fornecedor);
    }

    private Fornecedor findFornecedorById(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Fornecedor não encontrado")
                );
    }

    private boolean matchesFilters(
            Fornecedor f,
            String fornecedor,
            Long nf,
            BigDecimal valor,
            String parcelas,
            String obs,
            Boolean pago,
            LocalDate vencimentoDe,
            LocalDate vencimentoAte
    ) {
        boolean ok = true;

        if (fornecedor != null && !fornecedor.isBlank())
            ok &= f.getFornecedor() != null &&
                    f.getFornecedor().toLowerCase().contains(fornecedor.toLowerCase());

        if (nf != null)
            ok &= nf.equals(f.getNf());

        if (valor != null)
            ok &= f.getValor() != null &&
                    f.getValor().compareTo(valor) == 0;

        if (parcelas != null && !parcelas.isBlank())
            ok &= f.getParcelas() != null &&
                    f.getParcelas().toLowerCase().contains(parcelas.toLowerCase());

        if (obs != null && !obs.isBlank())
            ok &= f.getObs() != null &&
                    f.getObs().toLowerCase().contains(obs.toLowerCase());

        if (pago != null)
            ok &= f.isPago() == pago;

        if (vencimentoDe != null)
            ok &= f.getVencimento() != null &&
                    !f.getVencimento().isBefore(vencimentoDe);

        if (vencimentoAte != null)
            ok &= f.getVencimento() != null &&
                    !f.getVencimento().isAfter(vencimentoAte);

        return ok;
    }

    private FornecedorResponse toResponse(Fornecedor f) {
        return FornecedorResponse.builder()
                .id(f.getId())
                .fornecedor(f.getFornecedor())
                .nf(f.getNf())
                .dataEmissao(f.getDataEmissao())
                .vencimento(f.getVencimento())
                .valor(f.getValor())
                .parcelas(f.getParcelas())
                .pago(f.isPago())
                .obs(f.getObs())
                .build();
    }
}

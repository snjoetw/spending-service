package com.sj.spending.transaction.controller;

import com.sj.spending.logic.data.ImportedTransaction;
import com.sj.spending.transaction.data.entity.ImportedTransactionEntity;
import com.sj.spending.transaction.data.repo.ImportedTransactionRepository;
import com.sj.spending.transaction.data.dto.ImportedTransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/importedTransactions")
public class ImportedTransactionV1Controller {

    private final ImportedTransactionRepository importedTransactionRepository;

    @Autowired
    public ImportedTransactionV1Controller(ImportedTransactionRepository importedTransactionRepository) {
        this.importedTransactionRepository = importedTransactionRepository;
    }

    @RequestMapping(value = "/{importedTransactionId}", method = RequestMethod.GET)
    public ImportedTransactionDto findImportedTransaction(@PathVariable(value = "importedTransactionId") long importedTransactionId) {
        return importedTransactionRepository.findById(importedTransactionId)
                .map(t -> ImportedTransactionDto.from(t))
                .orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ImportedTransactionDto> findImportedTransactions() {
        return importedTransactionRepository.findAll().stream()
                .map(t -> ImportedTransactionDto.from(t))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.PUT)
    public ImportedTransactionDto updateImportedTransaction(@RequestBody ImportedTransactionDto transaction) {
        ImportedTransactionEntity entity = importedTransactionRepository.findById(transaction.getImportedTransactionId()).get();
        ImportedTransaction.copy(transaction, entity);


        ImportedTransactionEntity saved = importedTransactionRepository.save(entity);
        return ImportedTransactionDto.from(saved);
    }

}

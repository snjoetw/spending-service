package com.sj.spending.transaction.data.repo;

import com.sj.spending.transaction.data.entity.ImportedTransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface ImportedTransactionRepository extends CrudRepository<ImportedTransactionEntity, Long> {

    @Override
    ImportedTransactionEntity save(ImportedTransactionEntity importedTransaction);

    @Override
    void delete(ImportedTransactionEntity importedTransaction);

    @Override
    boolean existsById(Long importedTransactionId);

    boolean existsBySourceTransactionId(Long sourceTransactionId);

    @Override
    @Query("SELECT t FROM ImportedTransactionEntity t ORDER BY transactionDate DESC, name, amount")
    Collection<ImportedTransactionEntity> findAll();

    @Override
    Optional<ImportedTransactionEntity> findById(Long importedTransactionId);

}

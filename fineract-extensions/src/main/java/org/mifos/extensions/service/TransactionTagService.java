package org.mifos.extensions.service;

import org.mifos.extensions.dto.TransactionTagDto;

import java.util.List;
import java.util.Optional;

public interface TransactionTagService {
    List<TransactionTagDto> findAll();
    Optional<TransactionTagDto> findById(Long id);
    TransactionTagDto create(TransactionTagDto dto);
    TransactionTagDto update(Long id, TransactionTagDto dto);
    void delete(Long id);

    List<TransactionTagDto> findByTransactionId(Long transactionId);
    void addTagToTransaction(Long transactionId, Long tagId);
    void removeTagFromTransaction(Long transactionId, Long tagId);
}

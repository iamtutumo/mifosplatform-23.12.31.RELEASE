package org.mifos.extensions.service;

import org.mifos.extensions.dto.TransactionCategoryDto;

import java.util.List;
import java.util.Optional;

public interface TransactionCategoryService {
    List<TransactionCategoryDto> findAll();
    Optional<TransactionCategoryDto> findById(Long id);
    TransactionCategoryDto create(TransactionCategoryDto dto);
    TransactionCategoryDto update(Long id, TransactionCategoryDto dto);
    void delete(Long id);
}

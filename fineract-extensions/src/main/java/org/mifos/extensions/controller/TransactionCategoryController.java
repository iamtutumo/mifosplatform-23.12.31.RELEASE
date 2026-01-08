package org.mifos.extensions.controller;

import org.mifos.extensions.service.TransactionCategoryService;
import org.mifos.extensions.dto.TransactionCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactioncategories")
public class TransactionCategoryController {

    private final TransactionCategoryService service;

    public TransactionCategoryController(TransactionCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TransactionCategoryDto>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<TransactionCategoryDto> create(@RequestBody TransactionCategoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionCategoryDto> get(@PathVariable Long id) {
        return ResponseEntity.of(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionCategoryDto> update(@PathVariable Long id, @RequestBody TransactionCategoryDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

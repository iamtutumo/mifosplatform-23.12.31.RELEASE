package org.mifos.extensions.controller;

import org.mifos.extensions.dto.TransactionTagDto;
import org.mifos.extensions.service.TransactionTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactiontags")
public class TransactionTagController {

    private final TransactionTagService service;

    public TransactionTagController(TransactionTagService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<TransactionTagDto>> list() { return ResponseEntity.ok(service.findAll()); }

    @PostMapping
    public ResponseEntity<TransactionTagDto> create(@RequestBody TransactionTagDto dto) { return ResponseEntity.ok(service.create(dto)); }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionTagDto> get(@PathVariable Long id) { return ResponseEntity.of(service.findById(id)); }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionTagDto> update(@PathVariable Long id, @RequestBody TransactionTagDto dto) { return ResponseEntity.ok(service.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/transaction/{txId}")
    public ResponseEntity<List<TransactionTagDto>> tagsForTransaction(@PathVariable("txId") Long txId) {
        return ResponseEntity.ok(service.findByTransactionId(txId));
    }

    @PostMapping("/transaction/{txId}/{tagId}")
    public ResponseEntity<Void> addTagToTransaction(@PathVariable Long txId, @PathVariable Long tagId) {
        service.addTagToTransaction(txId, tagId); return ResponseEntity.ok().build();
    }

    @DeleteMapping("/transaction/{txId}/{tagId}")
    public ResponseEntity<Void> removeTagFromTransaction(@PathVariable Long txId, @PathVariable Long tagId) {
        service.removeTagFromTransaction(txId, tagId); return ResponseEntity.noContent().build();
    }
}

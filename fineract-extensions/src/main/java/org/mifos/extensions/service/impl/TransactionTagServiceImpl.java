package org.mifos.extensions.service.impl;

import org.mifos.extensions.dto.TransactionTagDto;
import org.mifos.extensions.service.TransactionTagService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionTagServiceImpl implements TransactionTagService {

    private final JdbcTemplate jdbcTemplate;

    public TransactionTagServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TransactionTagDto> findAll() {
        try {
            return jdbcTemplate.query("SELECT id, name, description, color_code FROM m_transaction_tag",
                    (rs, rowNum) -> {
                        TransactionTagDto d = new TransactionTagDto();
                        d.setId(rs.getLong("id"));
                        d.setName(rs.getString("name"));
                        d.setDescription(rs.getString("description"));
                        d.setColorCode(rs.getString("color_code"));
                        return d;
                    });
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public Optional<TransactionTagDto> findById(Long id) {
        try {
            TransactionTagDto d = jdbcTemplate.queryForObject("SELECT id, name, description, color_code FROM m_transaction_tag WHERE id = ?",
                    new Object[]{id}, (rs, rowNum) -> {
                        TransactionTagDto t = new TransactionTagDto();
                        t.setId(rs.getLong("id"));
                        t.setName(rs.getString("name"));
                        t.setDescription(rs.getString("description"));
                        t.setColorCode(rs.getString("color_code"));
                        return t;
                    });
            return Optional.ofNullable(d);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public TransactionTagDto create(TransactionTagDto dto) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO m_transaction_tag (name, description, color_code) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, dto.getName());
                ps.setString(2, dto.getDescription());
                ps.setString(3, dto.getColorCode());
                return ps;
            }, keyHolder);
            Number id = keyHolder.getKey();
            if (id != null) dto.setId(id.longValue());
        } catch (Exception ignored) {}
        return dto;
    }

    @Override
    public TransactionTagDto update(Long id, TransactionTagDto dto) {
        try {
            jdbcTemplate.update("UPDATE m_transaction_tag SET name = ?, description = ?, color_code = ? WHERE id = ?",
                    dto.getName(), dto.getDescription(), dto.getColorCode(), id);
            dto.setId(id);
        } catch (Exception ignored) {}
        return dto;
    }

    @Override
    public void delete(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM m_transaction_tags WHERE tag_id = ?", id);
            jdbcTemplate.update("DELETE FROM m_transaction_tag WHERE id = ?", id);
        } catch (Exception ignored) {}
    }

    @Override
    public List<TransactionTagDto> findByTransactionId(Long transactionId) {
        try {
            return jdbcTemplate.query("SELECT t.id, t.name, t.description, t.color_code FROM m_transaction_tag t JOIN m_transaction_tags tt ON t.id = tt.tag_id WHERE tt.transaction_id = ?",
                    new Object[]{transactionId}, (rs, rowNum) -> {
                        TransactionTagDto d = new TransactionTagDto();
                        d.setId(rs.getLong("id"));
                        d.setName(rs.getString("name"));
                        d.setDescription(rs.getString("description"));
                        d.setColorCode(rs.getString("color_code"));
                        return d;
                    });
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void addTagToTransaction(Long transactionId, Long tagId) {
        try {
            jdbcTemplate.update("INSERT IGNORE INTO m_transaction_tags (transaction_id, tag_id) VALUES (?, ?)", transactionId, tagId);
        } catch (Exception ignored) {}
    }

    @Override
    public void removeTagFromTransaction(Long transactionId, Long tagId) {
        try {
            jdbcTemplate.update("DELETE FROM m_transaction_tags WHERE transaction_id = ? AND tag_id = ?", transactionId, tagId);
        } catch (Exception ignored) {}
    }
}

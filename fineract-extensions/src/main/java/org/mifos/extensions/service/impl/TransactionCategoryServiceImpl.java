package org.mifos.extensions.service.impl;

import org.mifos.extensions.dto.TransactionCategoryDto;
import org.mifos.extensions.service.TransactionCategoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionCategoryServiceImpl implements TransactionCategoryService {

    private final JdbcTemplate jdbcTemplate;

    public TransactionCategoryServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TransactionCategoryDto> findAll() {
        try {
            return jdbcTemplate.query("SELECT id, name, description, is_system, is_active FROM m_transaction_category",
                    (rs, rowNum) -> {
                        TransactionCategoryDto d = new TransactionCategoryDto();
                        d.setId(rs.getLong("id"));
                        d.setName(rs.getString("name"));
                        d.setDescription(rs.getString("description"));
                        d.setIsSystem(rs.getBoolean("is_system"));
                        d.setIsActive(rs.getBoolean("is_active"));
                        return d;
                    });
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public Optional<TransactionCategoryDto> findById(Long id) {
        try {
            TransactionCategoryDto d = jdbcTemplate.queryForObject("SELECT id, name, description, is_system, is_active FROM m_transaction_category WHERE id = ?",
                    new Object[]{id}, (rs, rowNum) -> {
                        TransactionCategoryDto t = new TransactionCategoryDto();
                        t.setId(rs.getLong("id"));
                        t.setName(rs.getString("name"));
                        t.setDescription(rs.getString("description"));
                        t.setIsSystem(rs.getBoolean("is_system"));
                        t.setIsActive(rs.getBoolean("is_active"));
                        return t;
                    });
            return Optional.ofNullable(d);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public TransactionCategoryDto create(TransactionCategoryDto dto) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO m_transaction_category (name, description, is_system, is_active) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, dto.getName());
                ps.setString(2, dto.getDescription());
                ps.setBoolean(3, dto.getIsSystem() == null ? false : dto.getIsSystem());
                ps.setBoolean(4, dto.getIsActive() == null ? true : dto.getIsActive());
                return ps;
            }, keyHolder);
            
            Number generatedId = keyHolder.getKey();
            if (generatedId != null) {
                dto.setId(generatedId.longValue());
            }
        } catch (Exception ignored) {}
        return dto;
    }

    @Override
    public TransactionCategoryDto update(Long id, TransactionCategoryDto dto) {
        try {
            jdbcTemplate.update("UPDATE m_transaction_category SET name = ?, description = ?, is_system = ?, is_active = ? WHERE id = ?",
                    dto.getName(), dto.getDescription(), dto.getIsSystem(), dto.getIsActive(), id);
        } catch (Exception ignored) {}
        return dto;
    }

    @Override
    public void delete(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM m_transaction_category WHERE id = ?", id);
        } catch (Exception ignored) {}
    }
}

package com.sailorham.stcs.databaseSeminar.domain.sql.service;

import com.sailorham.stcs.databaseSeminar.common.exception.ServiceException;
import com.sailorham.stcs.databaseSeminar.common.exception.ServiceExceptionCode;
import com.sailorham.stcs.databaseSeminar.domain.sql.dto.SqlRequest;
import com.sailorham.stcs.databaseSeminar.domain.sql.dto.SqlResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SqlExecutorService {

    private final JdbcTemplate jdbcTemplate;

    // TODO: 읽기 전용 유저가 실행할 수 있도록 수정해야함
    public SqlResponse execute(SqlRequest request) {

        String sql = request.sql().trim();
        log.info("Execute sql: {}", sql);

        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

            List<String> columns = extractColumns(rows);

            return new SqlResponse(sql, rows.size(), columns, rows);

        } catch (DataAccessException e) {
            log.error("SQL Execution Failed: {}", e.getMessage());
            throw new ServiceException(ServiceExceptionCode.INVALID_SQL_SYNTAX);
        }
    }

    private List<String> extractColumns(List<Map<String, Object>> rows) {

        if (rows.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(rows.getFirst().keySet());
    }
}

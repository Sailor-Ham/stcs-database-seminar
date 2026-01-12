package com.sailorham.stcs.databaseSeminar.domain.sql.service;

import com.sailorham.stcs.databaseSeminar.common.exception.ServiceException;
import com.sailorham.stcs.databaseSeminar.common.exception.ServiceExceptionCode;
import com.sailorham.stcs.databaseSeminar.domain.sql.dto.ColumnSchemaResponse;
import com.sailorham.stcs.databaseSeminar.domain.sql.dto.SqlRequest;
import com.sailorham.stcs.databaseSeminar.domain.sql.dto.SqlResponse;
import com.sailorham.stcs.databaseSeminar.domain.sql.dto.TableSchemaResponse;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SqlExecutorService {

    private final JdbcTemplate jdbcTemplate;

    public SqlExecutorService(@Qualifier("readOnlyJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    public List<TableSchemaResponse> getDatabaseSchema() {

        List<TableSchemaResponse> tableList = new ArrayList<>();

        try (
            Connection connection =
                Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()
        ) {

            DatabaseMetaData metaData = connection.getMetaData();
            String catalog = connection.getCatalog();

            try (ResultSet tables = metaData.getTables(catalog, null, "%", new String[]{"TABLE"})) {

                while (tables.next()) {

                    String tableName = tables.getString("TABLE_NAME");
                    String tableComment = tables.getString("REMARKS");

                    List<String> pkColumns = new ArrayList<>();

                    try (ResultSet pks = metaData.getPrimaryKeys(catalog, null, tableName)) {
                        while (pks.next()) {
                            pkColumns.add(pks.getString("COLUMN_NAME"));
                        }
                    }

                    List<ColumnSchemaResponse> columnList = new ArrayList<>();

                    try (ResultSet columns = metaData.getColumns(catalog, null, tableName, null)) {

                        while (columns.next()) {

                            String columnName = columns.getString("COLUMN_NAME");
                            String typeName = columns.getString("TYPE_NAME");
                            int columnSize = columns.getInt("COLUMN_SIZE");
                            int decimalDigits = columns.getInt("DECIMAL_DIGITS");
                            String isNullable = columns.getString("IS_NULLABLE");

                            String fullType = formatColumnType(typeName, columnSize, decimalDigits);

                            columnList.add(new ColumnSchemaResponse(
                                columnName,
                                fullType,
                                pkColumns.contains(columnName),
                                "YES".equals(isNullable)
                            ));
                        }
                    }

                    tableList.add(new TableSchemaResponse(
                        tableName,
                        tableComment != null ? tableComment : "",
                        columnList
                    ));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to fetch database schema", e);
            throw new ServiceException(ServiceExceptionCode.SCHEMA_RETRIEVAL_FAILED);
        }

        return tableList;
    }

    private List<String> extractColumns(List<Map<String, Object>> rows) {

        if (rows.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(rows.getFirst().keySet());
    }

    private String formatColumnType(String typeName, int size, int digits) {

        String upperType = typeName.toUpperCase();

        if (upperType.equals("DECIMAL") || upperType.equals("NUMERIC")) {
            return upperType + "(" + size + "," + digits + ")";
        } else if (upperType.equals("VARCHAR") || upperType.equals("CHAR")) {
            return upperType + "(" + size + ")";
        } else {
            return upperType;
        }
    }
}

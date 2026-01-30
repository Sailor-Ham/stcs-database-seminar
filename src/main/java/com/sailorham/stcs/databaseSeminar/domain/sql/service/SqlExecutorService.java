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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
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

        Map<String, String> tableAliasMap = extractTableAliases(sql);

        try {
            return jdbcTemplate.query(sql, rs -> {

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                Map<String, Integer> labelCounts = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {

                    String label = metaData.getColumnLabel(i);
                    labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
                }

                List<String> columns = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {

                    String label = metaData.getColumnLabel(i);
                    String originTableName = metaData.getTableName(i);

                    String displayTableName = originTableName;

                    if (originTableName != null &&
                        tableAliasMap.containsKey(originTableName.toUpperCase())
                    ) {
                        displayTableName = tableAliasMap.get(originTableName.toUpperCase());
                    }

                    if (labelCounts.get(label) > 1 &&
                        displayTableName != null &&
                        !displayTableName.isEmpty()
                    ) {
                        columns.add(displayTableName + "." + label);
                    } else {
                        columns.add(label);
                    }
                }

                List<List<Object>> rows = new ArrayList<>();
                while (rs.next()) {

                    List<Object> row = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(rs.getObject(i));
                    }

                    rows.add(row);
                }

                return SqlResponse.of(sql, columns, rows);
            });
        } catch (BadSqlGrammarException e) {
            log.info("Bad SQL Grammar: {}", e.getMessage());
            throw new ServiceException(
                ServiceExceptionCode.INVALID_SQL_SYNTAX,
                Objects.requireNonNull(e.getSQLException()).getMessage()
            );
        } catch (DataAccessException e) {
            log.error("SQL Execution Failed: {}", e.getMessage());
            throw new ServiceException(ServiceExceptionCode.SQL_EXECUTION_FAILED);
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

    private Map<String, String> extractTableAliases(String sql) {

        Map<String, String> tableAliasMap = new HashMap<>();

        try {

            Statement statement = CCJSqlParserUtil.parse(sql);

            if (statement instanceof Select select) {
                if (select.getSelectBody() instanceof PlainSelect plainSelect) {

                    if (plainSelect.getFromItem() instanceof Table table) {
                        if (table.getAlias() != null) {
                            tableAliasMap
                                .put(table.getName().toUpperCase(), table.getAlias().getName());
                        }
                    }

                    if (plainSelect.getJoins() != null) {
                        for (var join : plainSelect.getJoins()) {
                            if (join.getRightItem() instanceof Table table) {
                                if (table.getAlias() != null) {
                                    tableAliasMap
                                        .put(table.getName().toUpperCase(),
                                            table.getAlias().getName());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("SQL parsing failed for Alias extraction. Fallback to default behavior.", e);
        }

        return tableAliasMap;
    }
}

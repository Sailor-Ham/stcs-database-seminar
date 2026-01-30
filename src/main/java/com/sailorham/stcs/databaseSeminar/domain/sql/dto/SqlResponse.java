package com.sailorham.stcs.databaseSeminar.domain.sql.dto;

import java.util.Collections;
import java.util.List;

public record SqlResponse(
    String executedSql,
    int rowCount,
    List<String> columns,
    List<List<Object>> rows
) {

    public static SqlResponse of(
        String executedSql,
        List<String> columns,
        List<List<Object>> rows
    ) {

        List<String> safeColumns = (columns != null) ? columns : Collections.emptyList();
        List<List<Object>> safeRows = (rows != null) ? rows : Collections.emptyList();

        return new SqlResponse(
            executedSql,
            safeRows.size(),
            safeColumns,
            safeRows
        );
    }
}

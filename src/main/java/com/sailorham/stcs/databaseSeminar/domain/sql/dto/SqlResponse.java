package com.sailorham.stcs.databaseSeminar.domain.sql.dto;

import java.util.List;
import java.util.Map;

public record SqlResponse(
    String executedSql,
    int rowCount,
    List<String> columns,
    List<Map<String, Object>> rows
) {

}

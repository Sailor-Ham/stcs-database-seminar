package com.sailorham.stcs.databaseSeminar.domain.sql.dto;

import java.util.List;

public record TableSchemaResponse(
    String tableName,
    String description,
    List<ColumnSchemaResponse> columns
) {

}

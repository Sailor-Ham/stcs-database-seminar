package com.sailorham.stcs.databaseSeminar.domain.sql.dto;

public record ColumnSchemaResponse(
    String name,
    String type,
    boolean primary,
    boolean nullable
) {

}

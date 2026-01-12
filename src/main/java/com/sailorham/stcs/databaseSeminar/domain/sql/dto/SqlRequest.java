package com.sailorham.stcs.databaseSeminar.domain.sql.dto;

import jakarta.validation.constraints.NotBlank;

public record SqlRequest(

    @NotBlank(message = "SQL 쿼리를 입력해주세요.")
    String sql
) {

}

package com.sailorham.stcs.databaseSeminar.domain.sql.controller;

import com.sailorham.stcs.databaseSeminar.common.response.ApiResponse;
import com.sailorham.stcs.databaseSeminar.domain.sql.dto.SqlRequest;
import com.sailorham.stcs.databaseSeminar.domain.sql.dto.SqlResponse;
import com.sailorham.stcs.databaseSeminar.domain.sql.service.SqlExecutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stcs/sql")
public class SqlExecutorController {

    private final SqlExecutorService sqlExecutorService;

    @PostMapping("/execute")
    public ApiResponse<SqlResponse> execute(@RequestBody @Valid SqlRequest request) {
        return ApiResponse.success(sqlExecutorService.execute(request));
    }
}

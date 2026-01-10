package com.sailorham.stcs.databaseSeminar.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    boolean success;
    T data;
    ErrorResponse error;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .success(true)
            .data(data)
            .build();
    }

    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(
        ErrorResponse errorResponse,
        HttpStatus status
    ) {
        return ResponseEntity.status(status)
            .body(ApiResponse.<T>builder()
                .success(false)
                .error(errorResponse)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(
        HttpStatus status,
        String errorCode,
        String errorMessage
    ) {
        return ResponseEntity.status(status)
            .body(ApiResponse.<T>builder()
                .success(false)
                .error(ErrorResponse.of(errorCode, errorMessage))
                .build());
    }
}

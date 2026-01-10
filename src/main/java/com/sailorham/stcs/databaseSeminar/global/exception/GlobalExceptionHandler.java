package com.sailorham.stcs.databaseSeminar.global.exception;

import com.sailorham.stcs.databaseSeminar.common.exception.ServiceException;
import com.sailorham.stcs.databaseSeminar.common.exception.ServiceExceptionCode;
import com.sailorham.stcs.databaseSeminar.common.response.ApiResponse;
import com.sailorham.stcs.databaseSeminar.common.response.ErrorResponse;
import com.sailorham.stcs.databaseSeminar.common.response.ErrorResponse.ValidationError;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
        MethodArgumentNotValidException exception
    ) {

        List<ValidationError> validationErrors =
            exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .toList();

        log.warn("Validation Error: {}", validationErrors);

        ErrorResponse errorResponse = ErrorResponse.of(
            "VALIDATION_ERROR",
            "입력값이 올바르지 않습니다.",
            validationErrors
        );

        return ApiResponse.error(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleServiceException(ServiceException exception) {

        ServiceExceptionCode code = exception.getExceptionCode();

        log.warn("Service Exception: [{}]: {}", code.name(), exception.getMessage());

        return ApiResponse.error(code.getStatus(), code.name(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {

        log.error("Unhandled Exception: ", exception);

        return ApiResponse.error(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "SERVER_ERROR",
            "서버 내부 오류가 발생했습니다."
        );
    }
}

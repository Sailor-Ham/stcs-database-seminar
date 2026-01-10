package com.sailorham.stcs.databaseSeminar.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    String errorCode,
    String errorMessage,

    @JsonInclude(Include.NON_EMPTY)
    List<ValidationError> errors
) {

    public static ErrorResponse of(String errorCode, String errorMessage) {
        return new ErrorResponse(errorCode, errorMessage, null);
    }

    public static ErrorResponse of(
        String errorCode,
        String errorMessage,
        List<ValidationError> errors
    ) {
        return new ErrorResponse(errorCode, errorMessage, errors);
    }

    public record ValidationError(String field, String message) {

        public static ValidationError of(String field, String message) {
            return new ValidationError(field, message);
        }
    }
}

package com.sailorham.stcs.databaseSeminar.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ServiceExceptionCode {

    NOT_FOUND_STUDENT(HttpStatus.NOT_FOUND, "학생을 찾을 수 없습니다."),
    ;

    final HttpStatus status;
    final String description;
}

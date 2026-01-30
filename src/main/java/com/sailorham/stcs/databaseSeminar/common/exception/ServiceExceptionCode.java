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

    INVALID_DEPARTMENT_BUDGET(HttpStatus.BAD_REQUEST, "학과 예산은 0보다 커야 합니다."),

    INVALID_COURSE_CREDITS(HttpStatus.BAD_REQUEST, "강의 학점은 0보다 커야 합니다."),

    INVALID_INSTRUCTOR_SALARY(HttpStatus.BAD_REQUEST, "교수의 연봉은 29,000을 초과해야 합니다."),

    INVALID_SECTION_SEMESTER(HttpStatus.BAD_REQUEST,
        "학기는 Spring, Summer, Fall, Winter 중 하나여야 합니다."),

    INVALID_SECTION_YEAR(HttpStatus.BAD_REQUEST, "개설 년도는 1701년 초과, 2100년 미만이어야 합니다."),

    INVALID_STUDENT_TOT_CRED(HttpStatus.BAD_REQUEST, "총 이수 학점은 0 이상이어야 합니다."),

    INVALID_TIME_FORMAT(HttpStatus.BAD_REQUEST, "시간은 0~23, 분은 0~59 사이여야 합니다."),

    INVALID_SQL_SYNTAX(HttpStatus.BAD_REQUEST, "SQL 문법이 올바르지 않거나 실행할 수 없는 쿼리입니다."),
    SQL_EXECUTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "SQL 실행 중 오류가 발생했습니다."),

    SCHEMA_RETRIEVAL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 스키마 정보를 가져오는데 실패했습니다."),
    ;

    final HttpStatus status;
    final String description;
}

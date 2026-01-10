package com.sailorham.stcs.databaseSeminar.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceException extends RuntimeException {

    private final ServiceExceptionCode exceptionCode;

    public ServiceException(ServiceExceptionCode exceptionCode) {
        super(exceptionCode.getDescription());
        this.exceptionCode = exceptionCode;
    }

    public ServiceException(ServiceExceptionCode exceptionCode, String detailMessage) {
        super(exceptionCode.getDescription() + " (" + detailMessage + ")");
        this.exceptionCode = exceptionCode;
    }

    public ServiceException(ServiceExceptionCode exceptionCode, Throwable cause) {
        super(exceptionCode.getDescription(), cause);
        this.exceptionCode = exceptionCode;
    }
}

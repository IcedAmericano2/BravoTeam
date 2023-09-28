package com.mju.management.domain.model.Exception;

import com.mju.management.domain.model.Exception.ExceptionList;

public class InvalidDateRangeException  extends RuntimeException  {
    private final ExceptionList exceptionList;

    public InvalidDateRangeException(ExceptionList exceptionList) {
        super(exceptionList.getMessage());
        this.exceptionList = exceptionList;
    }

    public ExceptionList getExceptionList() {
        return exceptionList;
    }
}

package com.mju.management.global.model.Exception;

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

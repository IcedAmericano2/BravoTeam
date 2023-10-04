package com.mju.management.global.service;

import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.InvalidDateRangeException;
import com.mju.management.global.model.Exception.NonExistentException;
import com.mju.management.global.model.Result.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionService {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult unknown(Exception e){
        log.error("unknown exception", e);
        return responseService.getFailResult(ExceptionList.UNKNOWN.getCode(), ExceptionList.UNKNOWN.getMessage());
    }

    @ExceptionHandler({NonExistentException.class})
    protected CommonResult handleCustom(NonExistentException e) {
        log.error("non existent exception", e);
        ExceptionList exceptionList = e.getExceptionList();
        return responseService.getFailResult(exceptionList.getCode(), exceptionList.getMessage());
    }

    @ExceptionHandler({InvalidDateRangeException.class})
    protected CommonResult invalidDateRangeException(InvalidDateRangeException e) {
        log.error("invalid date range exception", e);
        ExceptionList exceptionList = e.getExceptionList();
        return responseService.getFailResult(exceptionList.getCode(), exceptionList.getMessage());
    }

}

package com.legion.picturebackend.exception;

import com.legion.picturebackend.common.BaseResponse;
import com.legion.picturebackend.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * RestControllerAdvcice是环绕切点，
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * here as we import RestControllerAdvice, so whenevener we capture a BusinessException, we call the handler
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionhandler(BusinessException e) {
        log.error("BusinessException", e);
        /**
         * return our own encapsulated result to the frontend, protect the backend privacy
         */
        return ResultUtils.error(e.getCode(), e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "system mistake");
    }
}

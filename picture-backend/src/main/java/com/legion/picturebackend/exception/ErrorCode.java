package com.legion.picturebackend.exception;

import lombok.Getter;
//auto enerate getter functions
@Getter
public enum ErrorCode {

    /*
    our error code is designed Aline with http response:
        400: bad request
        401: no auth
        403: forbidden
        404: not exist/ no resource
      1. more digits for future development
     */
    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "Request parameter error"),
    NOT_LOGIN_ERROR(40100, "Not logged in"),
    NO_AUTH_ERROR(40101, "No permission"),
    NOT_FOUND_ERROR(40400, "Requested data does not exist"),
    FORBIDDEN_ERROR(40300, "Access forbidden"),
    SYSTEM_ERROR(50000, "Internal system error"),
    OPERATION_ERROR(50001, "Operation failed");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}

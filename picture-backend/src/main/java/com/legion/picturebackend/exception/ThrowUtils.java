package com.legion.picturebackend.exception;

import org.apache.logging.log4j.message.Message;

/**
 * throwif : exception handler utils class
 */
public class ThrowUtils {
    /**
     *
     * @param condition
     * @param e exception
     */
    public static void throwIf(boolean condition,RuntimeException e) {
        if(condition) {
            throw e;
        }
    }

    /**
     *
     * @param condition
     * @param errorCode Errorcode
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     *
     * @param condition
     * @param errorCode
     * @param message
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }

}

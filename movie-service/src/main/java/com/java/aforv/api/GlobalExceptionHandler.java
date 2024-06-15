package com.java.aforv.api;

import com.java.aforv.exception.InvalidDataException;
import com.java.aforv.exception.NotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Getter
    static  class Error {
        private final String reason;
        private final String message;

        Error(String reason, String message) {
            this.reason = reason;
            this.message = message;
        }
    }
    //400 error
    @ExceptionHandler({InvalidDataException.class, HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidDataException(Throwable th) {
        log.warn(th.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.getReasonPhrase(), th.getMessage());

    }
    //404 error
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNotFoundException(NotFoundException ex) {
        log.warn(ex.getMessage());
        return  new Error(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    }

    //defaultExceptionHandler
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error HandleUnknownException(Exception ex) {
        log.error(ex.getMessage());
        return  new Error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
    }
}

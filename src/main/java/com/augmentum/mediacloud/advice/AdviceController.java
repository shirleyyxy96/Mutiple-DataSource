package com.augmentum.mediacloud.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.augmentum.mediacloud.Application;
import com.augmentum.mediacloud.exception.BusinessException;

/**
 * 
 * This is a class by handle exception with controller.
 *
 */
@ControllerAdvice(basePackageClasses = Application.class)
public class AdviceController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Object>("Failed...." + ex.getMessage(), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.valueOf(statusCode);
    }
}

package com.augmentum.mediacloud.exception;

/**
 * This is a exception using in controller.
 */

public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    public BusinessException(String name) {
        super(name);
    }
}

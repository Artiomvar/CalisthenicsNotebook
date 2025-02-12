package com.example.fitnessapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A custom exception for demonstration.
 */
public class MyCustomException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(MyCustomException.class);

    public MyCustomException(String message) {
        super(message);
        // Log at creation time (optional).
        logger.error("MyCustomException created: {}", message);
    }

    public MyCustomException(String message, Throwable cause) {
        super(message, cause);
        logger.error("MyCustomException created: {}, cause: {}", message, cause.toString());
    }
}

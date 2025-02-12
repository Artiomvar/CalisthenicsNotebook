package com.example.fitnessapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;

/**
 * A global exception handler that deals with DB exceptions (DataAccessException).
 */
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * This method handles any Spring DataAccessException
     * (e.g. when a query fails or DB is unreachable).
     */
    @ExceptionHandler(DataAccessException.class)
    public String handleDatabaseException(DataAccessException ex, Model model) {
        logger.error("Database Exception caught in @ControllerAdvice: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", "A database error occurred: " + ex.getMessage());
        return "error-db"; // Our custom Thymeleaf page
    }

    /**
     * Optional: If you'd like to handle all other uncaught exceptions here,
     * you can do:
     *
     * @ExceptionHandler(Exception.class)
     * public String handleOtherException(Exception ex, Model model) {
     *     logger.error("Unhandled exception: {}", ex.getMessage(), ex);
     *     model.addAttribute("errorMessage", "An error occurred: " + ex.getMessage());
     *     return "error-other";
     * }
     */
}

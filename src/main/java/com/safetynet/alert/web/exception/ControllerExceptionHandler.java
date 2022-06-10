package com.safetynet.alert.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * ControllerExceptionHandler. class that handle
 * exception.
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private	static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * handleConstraintViolationException. Method that handle
     * a constraint violation exception
     *
     * @param ex a ConstraintViolationException
     * @param request a request
     *
     * @return an error message response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(
        ConstraintViolationException ex,
        WebRequest request) {

        log.error("Constraint violation exception msg:{}", ex.getMessage());

        ErrorMessage message = new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }



    /**
     * BadRequestException. Method that handle
     * a bad request.
     *
     * @param ex a BadRequestException
     * @param request a request
     *
     * @return an 400 response status
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> badRequestException(BadRequestException ex, WebRequest request) {

        log.error("BadRequest exception msg:{}", ex.getMessage());

        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * NoSuchElementException. Method that handle
     * a not exit element.
     *
     * @param ex a NoSuchElementException
     * @param request a request
     *
     * @return an 404 response status
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> noSuchElementException(NoSuchElementException ex, WebRequest request) {

        log.error("NoSuchElement exception msg:{}", ex.getMessage());

        ErrorMessage message = new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

}
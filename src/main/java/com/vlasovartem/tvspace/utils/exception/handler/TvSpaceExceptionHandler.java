package com.vlasovartem.tvspace.utils.exception.handler;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.vlasovartem.tvspace.utils.ResponseEntityExceptionCreator;
import com.vlasovartem.tvspace.utils.exception.EntityValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.vlasovartem.tvspace.utils.ResponseEntityExceptionCreator.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Created by artemvlasov on 08/12/15.
 */
@ControllerAdvice
public class TvSpaceExceptionHandler {

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity validatorHandler(EntityValidationException ex) {
        if(ex.getObjectNode() != null) {
            return create(NOT_ACCEPTABLE, ex.getMessage(), ex.getObjectNode());
        } else {
            return create(NOT_ACCEPTABLE, ex.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception ex) {
        return ResponseEntity.status(FORBIDDEN).body(JsonNodeFactory.instance.objectNode().put("error", ex
                .getMessage()));
    }
}

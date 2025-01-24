package com.hackathon.inditex.Exceptions;

import com.hackathon.inditex.DTO.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCenterCreationDataException.class)
    public ResponseEntity<MessageResponse> invalidCenterDataHandler(InvalidCenterCreationDataException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(CenterNotExistsException.class)
    public ResponseEntity<MessageResponse> centerNotExistsHandler(CenterNotExistsException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidCenterUpdateDataException.class)
    public ResponseEntity<MessageResponse> invalidCenterUpdateDataHandler(InvalidCenterUpdateDataException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }

}

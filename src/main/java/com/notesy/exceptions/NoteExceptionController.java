package com.notesy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.notesy.responses.ErrorResponse;
import com.notesy.utility.ResponseErrorUtils;

@RestControllerAdvice
public class NoteExceptionController {

	@ExceptionHandler(value = NoteNotFoundException.class)
	public ResponseEntity<ErrorResponse> noteNotFoundException(NoteNotFoundException exp) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
				ResponseErrorUtils.NOTE_NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(value = NoteNotPersistedException.class)
	public ResponseEntity<ErrorResponse> noteNotPersistedException(NoteNotPersistedException exp) {
		return ResponseEntity.badRequest().body(
				new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseErrorUtils.NOTE_PERSIST_ERROR));
	}

}

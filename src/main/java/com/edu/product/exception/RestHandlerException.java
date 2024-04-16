package com.edu.product.exception;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebInputException;

import com.edu.product.model.dto.SubErrorDto;
import com.edu.product.model.response.ErrorRes;

import reactor.core.publisher.Mono;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestHandlerException {

	/*
	 * Resource path not implemented
	 */
	@ExceptionHandler(NoResourceFoundException.class)
	public Mono<ResponseEntity<ErrorRes>> handleNoResourceFound(NoResourceFoundException ex) {
		return Mono.just(ErrorRes.builder().code(HttpStatus.NOT_FOUND.value()).message("resource path not implemented").build())
			   .map(error -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
	}

	/* 
	 * Method not implemented in a path implemented
	 */
	@ExceptionHandler(MethodNotAllowedException.class)
	public Mono<ResponseEntity<ErrorRes>> handleHttpRequestMethodNotSupported(MethodNotAllowedException ex) {
		return Mono.just(ErrorRes.builder().code(HttpStatus.BAD_REQUEST.value()).message("method not implemented").build())
			   .map(error -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error));
	}

	/*
	 * Mismatched type
	 */
	@ExceptionHandler(ServerWebInputException.class)
	public Mono<ResponseEntity<ErrorRes>> handleServerWebInput(ServerWebInputException ex) {
		return Mono.just(ErrorRes.builder().code(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).build())
			   .map(error -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error));
	}

	/*
	 * Constraints violated
	 */
	@ExceptionHandler(WebExchangeBindException.class)
	public Mono<ResponseEntity<ErrorRes>> handleMethodArgumentNotValid(WebExchangeBindException ex) {
		List<SubErrorDto> errors = ex.getFieldErrors().stream().map(e -> SubErrorDto.builder().field(e.getField())
				.value(e.getRejectedValue()).message(e.getDefaultMessage()).build()).toList();
		return Mono.just(ErrorRes.builder().code(HttpStatus.BAD_REQUEST.value()).message("validation erros").subErrors(errors).build())
			   .map(error -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error));
	}

	/*
	 * Resource not found
	 */
	@ExceptionHandler(NotFoundResourceException.class)
	public Mono<ResponseEntity<ErrorRes>> handleNotFoundResource(NotFoundResourceException ex) {
		return Mono.just(ErrorRes.builder().code(HttpStatus.NOT_FOUND.value()).message(ex.getMessage()).build())
			   .map(error -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
	}

	/*
	 * Generic error 
	 */
	@ExceptionHandler(Exception.class)
	public Mono<ResponseEntity<ErrorRes>> handleException(Exception ex) {
		return Mono.just(ErrorRes.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage()).build())
			   .map(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error));
	}

}

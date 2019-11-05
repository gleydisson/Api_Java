package com.example.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// Mensagem de erro para usuario
		String messageUser = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		
		// Mensagem de erro para desenvolvedor
		String messagedeveloper = Optional.ofNullable(ex.getCause()).orElse(ex).toString();
		
		List<Erro> erros = Arrays.asList(new Erro(messageUser, messagedeveloper));
		return handleExceptionInternal(ex, new Erro(messageUser, messagedeveloper), headers, HttpStatus.BAD_REQUEST, request);
	}
	// captura metodos invalidos que sao enviados pelo usuario - ex: name=null
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, status, request);
	}
	
	// Serve para retornar 404 quando tenta remover pessoa que nao existe
	@ExceptionHandler({ org.springframework.dao.EmptyResultDataAccessException.class })
	//@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> EmptyResultDataAccessException(org.springframework.dao.EmptyResultDataAccessException ex, WebRequest request) {
		
		// Mensagem de erro para usuario
        String messageUser = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		// Mensagem de erro para desenvolvedor
		String messagedeveloper = ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(messageUser, messagedeveloper));
		
		return handleExceptionInternal(ex, new Erro(messageUser, messagedeveloper), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}
	
	// Trata Erro de codigo da categoria ou pessoa invalido na insercao do lancamento - status 400
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		
		// Mensagem de erro para usuario
        String messageUser = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		// Mensagem de erro para desenvolvedor
		String messagedeveloper = ExceptionUtils.getRootCauseMessage(ex);
		
		List<Erro> erros = Arrays.asList(new Erro(messageUser, messagedeveloper));
		
		return handleExceptionInternal(ex, new Erro(messageUser, messagedeveloper), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
	}
	
	// BindingResult = Lista de todos os erros
	private List<Erro> criarListaDeErros(BindingResult bindingResult){
		List<Erro> erros = new ArrayList<>();
		
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
		String messageUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
		String messageDeveloper = fieldError.toString();
		erros.add(new Erro(messageUser, messageDeveloper));
		}
		return erros;
	}
	
	public static class Erro {
		
		private String messsageUser;
		private String messageDeveloper;
		
		public Erro(String messsageUser, String messageDeveloper) {
			
			this.messsageUser = messsageUser;
			this.messageDeveloper = messageDeveloper;
		}

		public String getMesssageUser() {
			return messsageUser;
		}

		public String getMessageDeveloper() {
			return messageDeveloper;
		}
		
		
		
	}
}

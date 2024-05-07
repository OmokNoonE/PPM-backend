package org.omoknoone.ppm.common.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.TransactionRolledbackException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.tools.UnsupportedPointcutPrimitiveException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final Environment environment;

    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class, HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class, EntityNotFoundException.class,
            MethodArgumentTypeMismatchException.class, ServletRequestBindingException.class,
            NullPointerException.class, IllegalArgumentException.class, AccessDeniedException.class,
            ConstraintViolationException.class, OptimisticLockException.class, HttpMessageNotWritableException.class,
            BeanCreationException.class, NoSuchBeanDefinitionException.class, BeanInitializationException.class,
            BeanInstantiationException.class, UnsupportedPointcutPrimitiveException.class, TransactionSystemException.class,
            CannotCreateTransactionException.class, TransactionException.class, Exception.class
    })
    public ResponseEntity<Map<Integer, String>> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        return buildResponseEntity(ex);
    }

    private ResponseEntity<Map<Integer, String>> buildResponseEntity(Exception ex) {
        HttpStatus status;
        String propertyKey;

        // 예외 유형에 따라 status 및 propertyKey 결정하는 로직
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            propertyKey = environment.getProperty("exception.controller.httpMediaTypeNotSupported");
        } else if (ex instanceof HttpMessageNotReadableException) {
            status = HttpStatus.BAD_REQUEST;
            propertyKey = environment.getProperty("exception.controller.httpMessageNotReadable");
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
            propertyKey = environment.getProperty("exception.controller.httpRequestMethodNotSupported");
        } else if (ex instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            propertyKey = environment.getProperty("exception.data.entityNotFound");
        } else if (ex instanceof MethodArgumentTypeMismatchException || ex instanceof ServletRequestBindingException) {
            status = HttpStatus.BAD_REQUEST;
            propertyKey = environment.getProperty("exception.controller.methodArgumentTypeMismatch");
        } else if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            propertyKey = environment.getProperty("exception.service.illegalArgument");
        } else if (ex instanceof NullPointerException) {
            status = HttpStatus.BAD_REQUEST;
            propertyKey = environment.getProperty("exception.service.nullPointer");
        } else if (ex instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            propertyKey = environment.getProperty("exception.controller.accessDenied");
        } else if (ex instanceof ConstraintViolationException) {
            status = HttpStatus.BAD_REQUEST;
            propertyKey = environment.getProperty("exception.data.constraintViolation");
        } else if (ex instanceof OptimisticLockException) {
            status = HttpStatus.CONFLICT;
            propertyKey = environment.getProperty("exception.data.optimisticLockingFailure");
        } else if (ex instanceof HttpMessageNotWritableException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            propertyKey = environment.getProperty("exception.data.httpMessageNotWritable");
        } else if (ex instanceof BeanCreationException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            propertyKey = environment.getProperty("exception.bean.beanCreation");
        } else if (ex instanceof BeanInitializationException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            propertyKey = environment.getProperty("exception.bean.beanInitialization");
        } else if (ex instanceof BeanInstantiationException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            propertyKey = environment.getProperty("exception.bean.beanInstantiation");
        } else if (ex instanceof NoSuchBeanDefinitionException) {
            status = HttpStatus.NOT_FOUND;
            propertyKey = environment.getProperty("exception.bean.noSuchBeanDefinition");
        } else if (ex instanceof UnsupportedPointcutPrimitiveException) {
            status = HttpStatus.BAD_REQUEST;
            propertyKey = environment.getProperty("exception.aop.aspectJExpressionPointcut");
        } else if (ex instanceof TransactionSystemException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            propertyKey = environment.getProperty("exception.system.transactionSystemError");
        } else if (ex instanceof CannotCreateTransactionException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            propertyKey = environment.getProperty("exception.system.cannotCreateTransaction");
        } else if (ex instanceof TransactionRolledbackException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            propertyKey = environment.getProperty("exception.system.rollbackError");
        } else if (ex instanceof TransactionException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            propertyKey = environment.getProperty("exception.system.transactionError");
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            propertyKey = environment.getProperty("exception.system.generalError");
        }

        Map<Integer, String> responseMessage = new HashMap<>();
        responseMessage.put(status.value(), propertyKey);

        return new ResponseEntity<>(responseMessage, status);
    }
}
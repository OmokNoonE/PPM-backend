package org.omoknoone.ppm.common.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.Getter;
import org.aspectj.weaver.tools.UnsupportedPointcutPrimitiveException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ExceptionMapper {
    private static ExceptionMapper INSTANCE;
    private final Map<Class<? extends Exception>, ExceptionInfo> exceptionMap;
    private final Environment environment;

    private ExceptionMapper(Environment environment) {
        this.environment = environment;
        this.exceptionMap = new HashMap<>();
        initExceptionMap();
    }

    public static synchronized ExceptionMapper getInstance(Environment environment) {
        if (INSTANCE == null) {
            INSTANCE = new ExceptionMapper(environment);
        }
        return INSTANCE;
    }

    private void initExceptionMap() {

        // Controller exceptions
        exceptionMap.put(HttpMediaTypeNotSupportedException.class, new ExceptionInfo(HttpStatus.UNSUPPORTED_MEDIA_TYPE, environment.getProperty("exception.controller.httpMediaTypeNotSupported")));
        exceptionMap.put(HttpMessageNotReadableException.class, new ExceptionInfo(HttpStatus.BAD_REQUEST, environment.getProperty("exception.controller.httpMessageNotReadable")));
        exceptionMap.put(HttpRequestMethodNotSupportedException.class, new ExceptionInfo(HttpStatus.METHOD_NOT_ALLOWED, environment.getProperty("exception.controller.httpRequestMethodNotSupported")));
        exceptionMap.put(MethodArgumentTypeMismatchException.class, new ExceptionInfo(HttpStatus.BAD_REQUEST, environment.getProperty("exception.controller.methodArgumentTypeMismatch")));
        exceptionMap.put(AccessDeniedException.class, new ExceptionInfo(HttpStatus.FORBIDDEN, environment.getProperty("exception.controller.accessDenied")));

        // Service exceptions
        exceptionMap.put(NullPointerException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.service.nullPointer")));
        exceptionMap.put(IllegalArgumentException.class, new ExceptionInfo(HttpStatus.BAD_REQUEST, environment.getProperty("exception.service.illegalArgument")));

        // Data exceptions
        exceptionMap.put(EntityNotFoundException.class, new ExceptionInfo(HttpStatus.NOT_FOUND, environment.getProperty("exception.data.entityNotFound")));
        exceptionMap.put(ConstraintViolationException.class, new ExceptionInfo(HttpStatus.BAD_REQUEST, environment.getProperty("exception.data.constraintViolation")));
        exceptionMap.put(OptimisticLockException.class, new ExceptionInfo(HttpStatus.CONFLICT, environment.getProperty("exception.data.optimisticLockingFailure")));
        exceptionMap.put(HttpMessageNotWritableException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.data.httpMessageNotWritable")));
        exceptionMap.put(DataAccessException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.data.databaseAccessError")));

        // Bean exceptions
        exceptionMap.put(BeanCreationException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.bean.beanCreation")));
        exceptionMap.put(NoSuchBeanDefinitionException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.bean.noSuchBeanDefinition")));
        exceptionMap.put(BeanInitializationException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.bean.beanInitialization")));
        exceptionMap.put(BeanInstantiationException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.bean.beanInstantiation")));

        // AOP exceptions
        exceptionMap.put(UnsupportedPointcutPrimitiveException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.aop.aspectJExpressionPointcut")));

        // System exceptions
        exceptionMap.put(TransactionSystemException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.system.transactionSystemError")));
        exceptionMap.put(CannotCreateTransactionException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.system.cannotCreateTransaction")));
        exceptionMap.put(TransactionException.class, new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.system.transactionError")));
    }

    protected ExceptionInfo getExceptionInfo(Exception ex) {
        return exceptionMap.getOrDefault(ex.getClass(),
                new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("exception.system.generalError")));
    }
}
package com.qk365.ocr.exception;

import com.qk365.ocr.model.dto.R;
import com.qk365.ocr.model.dto.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Configuration
@RestControllerAdvice(annotations = RestController.class)
@ConditionalOnWebApplication
public class RestControllerExceptionHandler {

    private static void logError(String name, Throwable e) {
        log.error("{}", name, e);
    }

    private static void logWarn(String name, Throwable e) {
        log.warn("{}", name, e);
    }

    @ExceptionHandler(Throwable.class)
    public R<Void> handleThrowable(Throwable e) {
        logError("Throwable", e);
        return R.fail(ResultCodeEnum.FAILURE);
    }

    @ExceptionHandler(Exception.class)
    public R<Void> handleMissingServletRequestParameterException(Exception e) {
        logError("Exception", e);
        return R.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logWarn("MethodArgumentNotValidException", e);
        return R.fail(ResultCodeEnum.PARAM_ERROR.getCode(),
                ExceptionHelper.firstErrorMessage(e.getBindingResult()));
    }

    @ExceptionHandler(BindException.class)
    public R<Void> handBindException(BindException e) {
        logWarn("BindException", e);
        return R
                .fail(ResultCodeEnum.PARAM_ERROR.getCode(), ExceptionHelper.firstErrorMessage(e.getBindingResult()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<Void> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        logWarn("MissingServletRequestParameterException", e);
        return R.fail(ResultCodeEnum.PARAM_ERROR.getCode(), String.format("参数%s未传", e.getParameterName()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R<Void> handleConstraintViolationException(ConstraintViolationException e) {
        logWarn("ConstraintViolationException", e);
        return R.fail(ResultCodeEnum.PARAM_ERROR.getCode(),
                ExceptionHelper.firstErrorMessage(e.getConstraintViolations()));
    }

    @ExceptionHandler(SQLException.class)
    public R<Void> handleSqlException(SQLException e) {
        logError("SQLException", e);
        return R.fail(ResultCodeEnum.SQL_ERROR.getCode(), ResultCodeEnum.SQL_ERROR.getMessage());
    }

    static class ExceptionHelper {

        static String firstErrorMessage(Set<ConstraintViolation<?>> constraintViolations) {
            return Optional.ofNullable(constraintViolations).orElseGet(HashSet::new).stream().findFirst()
                    .map(ConstraintViolation::getMessage).orElse("");
        }

        static String firstErrorMessage(BindingResult bindingResult) {
            return bindingResult.getAllErrors().stream().findFirst().map(ObjectError::getDefaultMessage).orElse("");
        }
    }
}

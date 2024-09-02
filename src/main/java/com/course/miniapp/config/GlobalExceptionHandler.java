package com.course.miniapp.config;

import com.course.miniapp.response.ResultData;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 统一捕获处理ConstraintViolationException异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<String> resolveConstraintViolationException(ConstraintViolationException ex) {
        return ResultData.fail(0, ex.getMessage());
    }

    /**
     * 统一捕获处理MethodArgumentNotValidException异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        StringBuilder msgBuilder = new StringBuilder();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            for (ObjectError objectError : objectErrors) {
                String msg = objectError.getDefaultMessage();
                msgBuilder.append(msg).append(" ");
            }
        }
        return msgBuilder.toString();
    }
}

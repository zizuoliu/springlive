package net.nvsoftware.springmono.error;

import net.nvsoftware.springmono.model.ErrorMsg;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMsg productNotFoundHandler(ProductNotFoundException e) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.NOT_FOUND, e.getMessage());
        return errorMsg;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMsg genericException(Exception e) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return errorMsg;
    }
}

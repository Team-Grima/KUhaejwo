package grima.kuhaejwo.except;

import grima.kuhaejwo.config.model.response.CommonResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import grima.kuhaejwo.except.auth.LoginFailedException;
import grima.kuhaejwo.except.auth.SignUpFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return this.responseService.getFailResult("Default Exception");
    }

    @ExceptionHandler(SignUpFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult memberNotFoundException(HttpServletRequest request, SignUpFailedException e) {
        return this.responseService.getFailResult("이미 존재하는 이메일입니다.");
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailLoginFailedException(HttpServletRequest request, LoginFailedException e) {
        return this.responseService.getFailResult(e.getMessage());
    }
}

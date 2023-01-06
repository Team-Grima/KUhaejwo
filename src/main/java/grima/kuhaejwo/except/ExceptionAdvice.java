package grima.kuhaejwo.except;

import grima.kuhaejwo.config.model.response.CommonResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import grima.kuhaejwo.except.auth.LoginFailedException;
import grima.kuhaejwo.except.auth.SignUpFailedException;
import grima.kuhaejwo.except.auth.TokenFailedException;
import grima.kuhaejwo.except.mateoffer.MateOfferNotFoundException;
import grima.kuhaejwo.except.user.UserNotificationNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return this.responseService.getFailResult("해당 리소스에 접근하기 권한이 없습니다.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return this.responseService.getFailResult("Permission not accessible to this resource");
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

    @ExceptionHandler(MateOfferNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult mateOfferNotFoundException(HttpServletRequest request, MateOfferNotFoundException e) {
        return this.responseService.getFailResult("존재 하지 않는 mateOffer 입니다.");
    }

    @ExceptionHandler(TokenFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult tokenFailedException(HttpServletRequest request, TokenFailedException e) {
        return this.responseService.getFailResult("만료된 토큰이거나 유효성이 존재하지 않는 토큰입니다.");
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult jwtException(HttpServletRequest request, JwtException e) {
        return this.responseService.getFailResult("유효성이 존재하지 않는 토큰입니다.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult expiredJwtException(HttpServletRequest request, ExpiredJwtException e) {
        return this.responseService.getFailResult("만료된 토큰입니다.");
    }

    @ExceptionHandler(UserNotificationNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult expiredJwtException(HttpServletRequest request, UserNotificationNotFoundException e) {
        return this.responseService.getFailResult("존재 하지 않는 알림 입니다.");
    }
}


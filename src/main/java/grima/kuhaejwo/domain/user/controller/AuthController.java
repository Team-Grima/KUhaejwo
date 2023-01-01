package grima.kuhaejwo.domain.user.controller;

import grima.kuhaejwo.config.model.response.SingleResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import grima.kuhaejwo.domain.user.dto.UserLoginRequest;
import grima.kuhaejwo.domain.user.dto.UserSignupRequest;
import grima.kuhaejwo.domain.user.service.AuthService;
import grima.kuhaejwo.token.TokenDto;
import grima.kuhaejwo.token.TokenRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Controller", description = "유저 로그인 회원가입 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final ResponseService responseService;
    private final AuthService authService;

    @Operation(description = "로그인 합니다.")
    @PostMapping("/login")
    public SingleResult<TokenDto> login(@Parameter @RequestBody UserLoginRequest userLoginRequest) {
        return responseService.getSingleResult(authService.login(userLoginRequest));
    }

    @Operation(description = "회원가입 합니다.")
    @PostMapping("/signup")
    public SingleResult<Long> signup(@Parameter @RequestBody UserSignupRequest userSignupRequest) {
        return responseService.getSingleResult(authService.signup(userSignupRequest));
    }

    @Operation(description = "토큰을 재발급 합니다.")
    @PostMapping("/reissue")
    public SingleResult<TokenDto> reissue(@Parameter @RequestBody TokenRequestDto tokenRequestDto) {
        return responseService.getSingleResult(authService.reissue(tokenRequestDto));
    }

    @Operation(description = "인증 정보를 확인 합니다.")
    @GetMapping("/auth")
    public SingleResult<Boolean> auth(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(authService.isAuth());
    }
}

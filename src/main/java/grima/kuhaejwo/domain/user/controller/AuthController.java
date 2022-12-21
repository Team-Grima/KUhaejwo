package grima.kuhaejwo.domain.user.controller;

import grima.kuhaejwo.config.model.response.SingleResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import grima.kuhaejwo.domain.user.dto.UsersRequest;
import grima.kuhaejwo.domain.user.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Controller", description = "유저 로그인 회원가입 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final UsersService usersService;
    private final ResponseService responseService;

    @Operation(description = "로그인 합니다.")
    @PostMapping("/login")
    public SingleResult<Long> login(@Parameter @RequestBody UsersRequest usersRequest) {
        return responseService.getSingleResult(1L);
    }
}

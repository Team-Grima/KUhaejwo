package grima.kuhaejwo.domain.user.controller;

import grima.kuhaejwo.config.model.response.SingleResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import grima.kuhaejwo.domain.user.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users Controller",description = "유저 관련 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;
    private final ResponseService responseService;

    @Operation(description = "유저 기본 정보를 생성합니다.")
    @PostMapping("/info")
    public SingleResult<Long> createInfo(
            @Header(name = "X-AUTH-TOKEN",
                    description = "로그인 성공 후 AccessToken",
                    required = true
    ))
    {

    }

}

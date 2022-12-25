package grima.kuhaejwo.domain.user.controller;

import grima.kuhaejwo.config.model.response.SingleResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import grima.kuhaejwo.config.security.JwtProvider;
import grima.kuhaejwo.domain.user.dto.UserBasicInfoRequest;
import grima.kuhaejwo.domain.user.dto.UserBasicInfoResponse;
import grima.kuhaejwo.domain.user.dto.UserInfoDetailRequest;
import grima.kuhaejwo.domain.user.dto.UserInfoDetailResponse;
import grima.kuhaejwo.domain.user.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;
    private final ResponseService responseService;
    private final JwtProvider jwtProvider;


    /**
     * 유저 기본 정보
     * @param token
     */
    @PostMapping("/info")
    @Operation(summary = "유저 기본 정보 생성", description = "User 기본 정보를 생성합니다.")
    public SingleResult<UserBasicInfoResponse> createInfo(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            HttpServletRequest request,
            UserBasicInfoRequest userBasicInfoRequest
    ) {
        return responseService.getSingleResult(usersService.createInfo(jwtProvider.resolveToken(request), userBasicInfoRequest));
    }

    @GetMapping("/info")
    @Operation(summary = "유저 기본 정보 조회", description = "User 기본 정보를 조회합니다.")
    public SingleResult<UserBasicInfoResponse> getInfo(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            HttpServletRequest request
    ) {
        return responseService.getSingleResult(usersService.getInfo(jwtProvider.resolveToken(request)));
    }

    @PutMapping("/info")
    @Operation(summary = "유저 기번 정보 수정", description = "User 기본 정보를 수정합니다.")
    public SingleResult<UserBasicInfoResponse> updateInfo(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            HttpServletRequest request,
            UserBasicInfoRequest userBasicInfoRequest
    ) {
        return responseService.getSingleResult(usersService.updateInfo(jwtProvider.resolveToken(request), userBasicInfoRequest));
    }

    /**
     * 유저 세부 정보
     * @param request
     */

    @PostMapping("/infoDetail")
    @Operation(summary = "유저 세부 정보 생성", description = "User 세부 정보를 생성합니다.")
    public SingleResult<UserInfoDetailResponse> createInfoDetail(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            HttpServletRequest request,
            @RequestBody UserInfoDetailRequest userInfoDetailRequest
    ) {
        return responseService.getSingleResult(usersService.createInfoDetail(jwtProvider.resolveToken(request), userInfoDetailRequest));
    }

    @GetMapping("/infoDetail")
    @Operation(summary = "유저 세부 정보 조회", description = "User 세부 정보를 조회합니다.")
    public SingleResult<UserInfoDetailResponse> getInfoDetail(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            HttpServletRequest request
    ) {
        return responseService.getSingleResult(usersService.getInfoDetail(jwtProvider.resolveToken(request)));
    }

    @PutMapping("/infoDetail")
    @Operation(summary = "유저 세부 정보 수정", description = "User 세부 정보를 수정합니다.")
    public SingleResult<UserInfoDetailResponse> updateInfoDetail(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            HttpServletRequest request,
            @RequestBody UserInfoDetailRequest userInfoDetailRequest
    ) {
        return responseService.getSingleResult(usersService.updateInfoDetail(jwtProvider.resolveToken(request), userInfoDetailRequest));
    }

}

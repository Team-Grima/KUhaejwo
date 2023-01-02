package grima.kuhaejwo.domain.user.controller;

import grima.kuhaejwo.config.model.response.SingleResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import grima.kuhaejwo.config.security.JwtProvider;
import grima.kuhaejwo.domain.user.domain.Users;
import grima.kuhaejwo.domain.user.dto.*;
import grima.kuhaejwo.domain.user.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

@Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;
    private final ResponseService responseService;


    /**
     * 유저 기본 정보
     *
     * @param token
     */
    @PostMapping("/info")
    @Operation(summary = "유저 기본 정보 생성", description = "User 기본 정보를 생성합니다.")
    public SingleResult<UserBasicInfoResponse> createInfo(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserBasicInfoRequest userBasicInfoRequest
    ) {
        return responseService.getSingleResult(usersService.createInfo(userBasicInfoRequest));
    }

    @GetMapping("/info")
    @Operation(summary = "유저 기본 정보 조회", description = "User 기본 정보를 조회합니다.")
    public SingleResult<UserBasicInfoResponse> getInfo(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(usersService.getInfo());
    }

    @PutMapping("/info")
    @Operation(summary = "유저 기본 정보 수정", description = "User 기본 정보를 수정합니다.")
    public SingleResult<UserBasicInfoResponse> updateInfo(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserBasicInfoRequest userBasicInfoRequest
    ) {
        return responseService.getSingleResult(usersService.updateInfo(userBasicInfoRequest));
    }

    /**
     * 유저 세부 정보
     *
     * @param
     */

    @PostMapping("/infoDetail")
    @Operation(summary = "유저 세부 정보 생성", description = "User 세부 정보를 생성합니다.")
    public SingleResult<UserInfoDetailResponse> createInfoDetail(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserInfoDetailRequest userInfoDetailRequest
    ) {
        return responseService.getSingleResult(usersService.createInfoDetail(userInfoDetailRequest));
    }

    @PostMapping("/infoDetailFormdata")
    @Operation(summary = "유저 세부 정보 생성", description = "User 세부 정보를 생성합니다.")
    public SingleResult<UserInfoDetailResponse> createInfoDetailFormdata(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @ModelAttribute UserInfoDetailRequest userInfoDetailRequest
    ) {
        return responseService.getSingleResult(usersService.createInfoDetail(userInfoDetailRequest));
    }

    @GetMapping("/infoDetail")
    @Operation(summary = "유저 세부 정보 조회", description = "User 세부 정보를 조회합니다.")
    public SingleResult<UserInfoDetailResponse> getInfoDetail(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(usersService.getInfoDetail());
    }

    @PutMapping("/infoDetail")
    @Operation(summary = "유저 세부 정보 수정", description = "User 세부 정보를 수정합니다.")
    public SingleResult<UserInfoDetailResponse> updateInfoDetail(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserInfoDetailRequest userInfoDetailRequest
    ) {
        return responseService.getSingleResult(usersService.updateInfoDetail(userInfoDetailRequest));
    }

    @GetMapping("")
    @Operation(summary = "해당 유저 모든 정보 조회", description = "해당 유저의 모든 정보를 조회합니다.")
    public SingleResult<UserResponse> getInfoAll(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(usersService.getInfoAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "해당 유저 모든 정보 조회", description = "해당 유저의 모든 정보를 id로 조회합니다.")
    public SingleResult<UserResponse> getInfoAllById(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @Parameter(name = "id", description = "User Id", in = ParameterIn.PATH) @PathVariable Long id
    ) {
        return responseService.getSingleResult(usersService.getInfoAllById(id));
    }

    @PostMapping("/prefer")
    @Operation(summary = "해당 유저 선호 생성", description = "해당 유저에게 선호를 생성합니다.")
    public SingleResult<UserPreferResponse> createPrefer(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserPreferRequest userPreferRequest
    ) {
        return responseService.getSingleResult(usersService.createPrefer(userPreferRequest));
    }

    @GetMapping("/prefer")
    @Operation(summary = "해당 유저 선호 조회", description = "해당 유저의 선호를 조회합니다.")
    public SingleResult<UserPreferResponse> getPrefer(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(usersService.getPrefer());
    }

    @PutMapping("/prefer")
    @Operation(summary = "해당 유저 선호 수정", description = "해당 유저의 선호를 수정합니다.")
    public SingleResult<UserPreferResponse> updatePrefer(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserPreferRequest userPreferRequest
    ) {
        return responseService.getSingleResult(usersService.updatePrefer(userPreferRequest));
    }

    @PostMapping(value = "/profileImage" ,consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "해당 유저 프로필 사진 생성", description = "해당 유저의 프로필 사진을 생성합니다.")
    public SingleResult<String> createProfileImage(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestParam MultipartFile file
    ) throws IOException {
        return responseService.getSingleResult(usersService.createProfileImage(file));
    }

    @GetMapping("/profileImage")
    @Operation(summary = "해당 유저 프로필 사진 조회", description = "해당 유저의 프로필 사진을 조회합니다.")
    public ResponseEntity<Resource> getProfileImage (
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) throws IOException{
        return usersService.getProfileImage();
    }

    //img파일 썸네일
    @GetMapping("/profileImage2")
    @Operation(summary = "해당 유저 프로필 사진 조회 byte", description = "해당 유저의 프로필 사진을 조회합니다.")
    public ResponseEntity<byte[]> getProfileImage2(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ){
        return usersService.getProfileImage2();
    }

    @GetMapping("/profileIamge3")
    @Operation(summary = "해당 유저 프로필 사진 조회", description = "해당 유저의 프로필 사진을 조회합니다.")
    public SingleResult<String> getProfileImage3(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ){
        return responseService.getSingleResult(usersService.getProfileImage3());
    }

    @GetMapping("/profileIamge4")
    @Operation(summary = "해당 유저 프로필 사진 조회 outputstream", description = "해당 유저의 프로필 사진을 조회합니다.")
    public ResponseEntity<OutputStream> getProfileImage4(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ){
        return usersService.getProfileImage4();
    }

    @PostMapping(value = "/passImage" ,consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "해당 유저 합격증 사진 업로드", description = "해당 유저의 합격증 사진 업로드합니다.")
    public SingleResult<String> createPassImage(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestParam MultipartFile file
    ) throws IOException {
        return responseService.getSingleResult(usersService.createPassImage(file));
    }

    @GetMapping("/passImage")
    @Operation(summary = "해당 유저 합격증 사진 조회", description = "해당 유저의 합격증 사진을 조회합니다.")
    public ResponseEntity<byte[]> getPassImage (
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ){
        return usersService.getPassImage();
    }
}

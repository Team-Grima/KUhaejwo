package grima.kuhaejwo.domain.user.controller;

import grima.kuhaejwo.config.model.response.ListResult;
import grima.kuhaejwo.config.model.response.SingleResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import grima.kuhaejwo.domain.user.dto.*;
import grima.kuhaejwo.domain.user.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
    @Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
    @PostMapping("/info")
    @Operation(summary = "유저 기본 정보 생성", description = "User 기본 정보를 생성합니다.")
    public SingleResult<UserBasicInfoResponse> createInfo(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserBasicInfoRequest userBasicInfoRequest
    ) {
        return responseService.getSingleResult(usersService.createInfo(userBasicInfoRequest));
    }
    @Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
    @GetMapping("/info")
    @Operation(summary = "유저 기본 정보 조회", description = "User 기본 정보를 조회합니다.")
    public SingleResult<UserBasicInfoResponse> getInfo(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(usersService.getInfo());
    }

    @Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
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

    @Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
    @PostMapping("/infoDetail")
    @Operation(summary = "유저 세부 정보 생성", description = "User 세부 정보를 생성합니다.")
    public SingleResult<UserInfoDetailResponse> createInfoDetail(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserInfoDetailRequest userInfoDetailRequest
    ) {
        return responseService.getSingleResult(usersService.createInfoDetail(userInfoDetailRequest));
    }

    @Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
    @PostMapping("/infoDetailFormdata")
    @Operation(summary = "유저 세부 정보 생성", description = "User 세부 정보를 생성합니다.")
    public SingleResult<UserInfoDetailResponse> createInfoDetailFormdata(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @ModelAttribute UserInfoDetailRequest userInfoDetailRequest
    ) {
        return responseService.getSingleResult(usersService.createInfoDetail(userInfoDetailRequest));
    }
    @Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
    @GetMapping("/infoDetail")
    @Operation(summary = "유저 세부 정보 조회", description = "User 세부 정보를 조회합니다.")
    public SingleResult<UserInfoDetailResponse> getInfoDetail(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(usersService.getInfoDetail());
    }
    @Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
    @PutMapping("/infoDetail")
    @Operation(summary = "유저 세부 정보 수정", description = "User 세부 정보를 수정합니다.")
    public SingleResult<UserInfoDetailResponse> updateInfoDetail(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserInfoDetailRequest userInfoDetailRequest
    ) {
        return responseService.getSingleResult(usersService.updateInfoDetail(userInfoDetailRequest));
    }
    @Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
    @GetMapping("")
    @Operation(summary = "해당 유저 모든 정보 조회", description = "해당 유저의 모든 정보를 조회합니다.")
    public SingleResult<UserResponse> getInfoAll(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(usersService.getInfoAll());
    }
    @Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
    @GetMapping("/{id}")
    @Operation(summary = "해당 유저 모든 정보 조회", description = "해당 유저의 모든 정보를 id로 조회합니다.")
    public SingleResult<UserResponse> getInfoAllById(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @Parameter(name = "id", description = "User Id", in = ParameterIn.PATH) @PathVariable Long id
    ) {
        return responseService.getSingleResult(usersService.getInfoAllById(id));
    }
    @Tag(name = "Users Controller", description = "유저 관련 컨트롤러")
    @PostMapping("/prefer")
    @Operation(summary = "해당 유저 선호 생성", description = "해당 유저에게 선호를 생성합니다.")
    public SingleResult<UserPreferResponse> createPrefer(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserPreferRequest userPreferRequest
    ) {
        return responseService.getSingleResult(usersService.createPrefer(userPreferRequest));
    }
    @Tag(name = "Prefer Controller", description = "유저 선호 관련 컨트롤러")
    @GetMapping("/prefer")
    @Operation(summary = "해당 유저 선호 조회", description = "해당 유저의 선호를 조회합니다.")
    public SingleResult<UserPreferResponse> getPrefer(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(usersService.getPrefer());
    }
    @Tag(name = "Prefer Controller", description = "유저 선호 관련 컨트롤러")
    @PutMapping("/prefer")
    @Operation(summary = "해당 유저 선호 수정", description = "해당 유저의 선호를 수정합니다.")
    public SingleResult<UserPreferResponse> updatePrefer(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody UserPreferRequest userPreferRequest
    ) {
        return responseService.getSingleResult(usersService.updatePrefer(userPreferRequest));
    }
    @Tag(name = "Image Controller", description = "유저 관련 이미지 컨트롤러")
    @PostMapping(value = "/profileImage", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "해당 유저 프로필 사진 생성", description = "해당 유저의 프로필 사진을 생성합니다.")
    public SingleResult<String> createProfileImage(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestParam MultipartFile file
    ) throws IOException {
        return responseService.getSingleResult(usersService.createProfileImage(file));
    }
    @Tag(name = "Image Controller", description = "유저 관련 이미지 컨트롤러")
    @GetMapping("/profileImage")
    @Operation(summary = "해당 유저 프로필 사진 조회", description = "해당 유저의 프로필 사진을 조회합니다.")
    public ResponseEntity<Resource> getProfileImage(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) throws IOException {
        return usersService.getProfileImage();
    }

    //img파일 썸네일
    @Tag(name = "Image Controller", description = "유저 관련 이미지 컨트롤러")
    @GetMapping("/profileImage2")
    @Operation(summary = "해당 유저 프로필 사진 조회 byte", description = "해당 유저의 프로필 사진을 조회합니다.")
    public ResponseEntity<byte[]> getProfileImage2(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return usersService.getProfileImage2();
    }
    @Tag(name = "Image Controller", description = "유저 관련 이미지 컨트롤러")
    @GetMapping("/profileIamge3")
    @Operation(summary = "해당 유저 프로필 사진 조회", description = "해당 유저의 프로필 사진을 조회합니다.")
    public SingleResult<String> getProfileImage3(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(usersService.getProfileImage3());
    }
    @Tag(name = "Image Controller", description = "유저 관련 이미지 컨트롤러")
    @GetMapping("/profileIamge4")
    @Operation(summary = "해당 유저 프로필 사진 조회 outputstream", description = "해당 유저의 프로필 사진을 조회합니다.")
    public ResponseEntity<OutputStream> getProfileImage4(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return usersService.getProfileImage4();
    }
    @Tag(name = "Image Controller", description = "유저 관련 이미지 컨트롤러")
    @PostMapping(value = "/passImage", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "해당 유저 합격증 사진 업로드", description = "해당 유저의 합격증 사진 업로드합니다.")
    public SingleResult<String> createPassImage(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestParam MultipartFile file
    ) throws IOException {
        return responseService.getSingleResult(usersService.createPassImage(file));
    }
    @Tag(name = "Image Controller", description = "유저 관련 이미지 컨트롤러")
    @GetMapping("/passImage")
    @Operation(summary = "해당 유저 합격증 사진 조회", description = "해당 유저의 합격증 사진을 조회합니다.")
    public ResponseEntity<byte[]> getPassImage(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return usersService.getPassImage();
    }

    /**
     * Notification 관련 Controller
     */

    @Tag(name = "Notification Controller", description = "알림 관련 컨트롤러")
    @GetMapping("/notification")
    @Operation(summary = "해당 유저의 알림리스트", description = "해당 유저의 알림리스트를 보여줍니다.")
    public ListResult<UserNotificationResponse> getNotification(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getListResult(usersService.getNotification());
    }
    @Tag(name = "Notification Controller", description = "알림 관련 컨트롤러")
    @GetMapping("/notification/notRead")
    @Operation(summary = "해당 유저의 읽지않은 알림리스트", description = "해당 유저의 읽지않은 알림리스트를 보여줍니다.")
    public ListResult<UserNotificationResponse> getNotificationNotRead(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getListResult(usersService.getNotificationNotRead());
    }
    @Tag(name = "Notification Controller", description = "알림 관련 컨트롤러")
    @PostMapping("/notification")
    @Operation(summary = "해당 유저의 알림 생성(이거는 그냥 잠시 만들어 놓 는 거 임)", description = "해당 유저의 알림을 생성합니다.")
    public SingleResult<UserNotificationResponse> createNotification(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(usersService.createNotification());
    }
    @Tag(name = "Notification Controller", description = "알림 관련 컨트롤러")
    @GetMapping("/notification/{id}")
    @Operation(summary = "알람 정보 조회", description = "알람 정보를 id로 조회합니다. 조회시 Read 된다.")
    public SingleResult<UserNotificationResponse> getNotificationById(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @Parameter(name = "id", description = "Notification Id", in = ParameterIn.PATH) @PathVariable Long id
    ) {
        return responseService.getSingleResult(usersService.getNotificationById(id));
    }
}

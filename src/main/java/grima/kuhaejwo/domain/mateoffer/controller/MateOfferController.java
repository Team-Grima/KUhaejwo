package grima.kuhaejwo.domain.mateoffer.controller;

import grima.kuhaejwo.config.model.response.ListResult;
import grima.kuhaejwo.config.model.response.SingleResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import grima.kuhaejwo.config.security.JwtProvider;
import grima.kuhaejwo.domain.mateoffer.dto.MateOfferRequest;
import grima.kuhaejwo.domain.mateoffer.dto.MateOfferResponse;
import grima.kuhaejwo.domain.mateoffer.service.MateOfferService;
import grima.kuhaejwo.domain.user.dto.UserBasicInfoRequest;
import grima.kuhaejwo.domain.user.dto.UserBasicInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "MateOffer Controller", description = "룸메 구해요 관련 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mateoffer")
public class MateOfferController {

    private final ResponseService responseService;
    private final MateOfferService mateOfferService;
    private final JwtProvider jwtProvider;

    @PostMapping()
    @Operation(summary = "룸메 글 생성", description = "룸메 올리는 글을 생성합니다.")
    public SingleResult<MateOfferResponse> createMateOffer(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody MateOfferRequest mateOfferRequest
    ) {
        return responseService.getSingleResult(mateOfferService.createMateOffer(mateOfferRequest));
    }

    @GetMapping()
    @Operation(summary = "룸메 글 조회", description = "룸메 올리는 글을 조회합니다.")
    public SingleResult<MateOfferResponse> getMateOffer(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getSingleResult(mateOfferService.getMateOffer());
    }

    @PutMapping()
    @Operation(summary = "룸메 글 수정", description = "룸메 올리는 글을 수정합니다.")
    public SingleResult<MateOfferResponse> updateMateOffer(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody MateOfferRequest mateOfferRequest
    ) {
        return responseService.getSingleResult(mateOfferService.updateMateOffer(mateOfferRequest));
    }

    @GetMapping("/list")
    @Operation(summary = "룸메 글 리스트 조회", description = "룸메 올리는 글 리스트들을 조회합니다.")
    public ListResult<MateOfferResponse> getMateOfferList(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token
    ) {
        return responseService.getListResult(mateOfferService.getMateOfferList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "룸메 글 조회 By Id", description = "룸메 올리는 글을 Id를 통해 조회합니다.")
    public SingleResult<MateOfferResponse> getMateOfferById(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @Parameter(name="id",description = "User Id",in = ParameterIn.PATH) @PathVariable Long id
    ) {
        return responseService.getSingleResult(mateOfferService.getMateOfferByUserId(id));
    }
}


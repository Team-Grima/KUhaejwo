package grima.kuhaejwo.email;

import grima.kuhaejwo.config.model.response.SingleResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import grima.kuhaejwo.domain.mateoffer.dto.MateOfferRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping
@Tag(name = "Mail Controller", description = "유저 이메일 인증 컨트롤러")
public class EmailController {

    private final EmailService emailService;
    private final ResponseService responseService;
    private final EmailTokenService emailTokenService;

    @GetMapping("/confirm-email")
    @Operation(summary = "이메일 인증 확인", description = "이메일 인증을 확인합니다.")
    public SingleResult<Boolean> viewConfirmEmail(@Valid @RequestParam String token) {
        return responseService.getSingleResult(emailService.verifyEmail(token));
    }

    @PostMapping("/confirm-email")
    @Operation(summary = "이메일 인증 요청", description = "이메일 인증을 요청합니다.")
    public SingleResult<String> confirmEmail(
            @Parameter(name = "X-AUTH-TOKEN", description = "로그인 성공 후 AccessToken", in = ParameterIn.HEADER) String token,
            @RequestBody EmailRequest emailRequest
    ) {
        return responseService.getSingleResult(emailTokenService.createEmailToken(emailRequest));
    }
}

package grima.kuhaejwo.firebase;

import grima.kuhaejwo.config.model.response.SingleResult;
import grima.kuhaejwo.config.model.service.ResponseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "FCM Controller", description = "FCM 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fcm")
public class FCMController {

    private final ResponseService responseService;
    private final FCMService fcmService;

    @PostMapping("")
    public SingleResult<String> emitMessages(@RequestParam List<String> tokenList) {
        return responseService.getSingleResult(fcmService.sendByTokenList(tokenList));
    }
}

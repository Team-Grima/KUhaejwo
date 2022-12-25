package grima.kuhaejwo.domain.mateoffer.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MateOffer Controller", description = "룸메 구해요 관련 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mateoffer")
public class MateOfferController {
}

package grima.kuhaejwo.config.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {
    @Schema(defaultValue = "응답 성공 여부: T/F")
    private boolean success;

    @Schema(defaultValue = "응답 코드")
    private int code;

    @Schema(defaultValue = "응답 메세지")
    private String msg;
}

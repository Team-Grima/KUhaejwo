package grima.kuhaejwo;


import lombok.AllArgsConstructor;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@RestController
@AllArgsConstructor
public class WebRestController {

    private Environment environment;

    @GetMapping("/profile")
    public String getProfile(){
        return Arrays.stream(environment.getActiveProfiles())
                .findFirst()
                .orElse("");
    }

    @GetMapping("/health")
    public String getHealth(){
        LocalDateTime now=LocalDateTime.now();
        String str=new Date()+"시간"+now+"시간";
        return str;
    }
}

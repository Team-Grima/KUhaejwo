package grima.kuhaejwo.domain.user.dto;

import grima.kuhaejwo.domain.user.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupRequest {

    private String email;
    private String password;
    private String name;

    public Users toEntity(PasswordEncoder passwordEncoder) {
        return new Users(email,passwordEncoder.encode(password),name);
    }
}

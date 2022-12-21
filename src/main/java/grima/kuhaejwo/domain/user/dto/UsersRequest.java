package grima.kuhaejwo.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersRequest {
    private String email;
    private String password;
}

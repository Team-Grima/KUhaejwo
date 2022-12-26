package grima.kuhaejwo.domain.mateoffer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private Long UserId;
    private String department;
    private Long age;
}

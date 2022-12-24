package grima.kuhaejwo.domain.user.domain;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicInfo {
    private String college;
    private String department;
    private String studentId;
    private Long age;
    private String mbti;

    @Enumerated(value=EnumType.STRING)
    private Gender gender;
}

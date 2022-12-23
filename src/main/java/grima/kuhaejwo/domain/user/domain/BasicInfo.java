package grima.kuhaejwo.domain.user.domain;

import lombok.*;

import javax.persistence.Embeddable;

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
    private Gender gender;
}

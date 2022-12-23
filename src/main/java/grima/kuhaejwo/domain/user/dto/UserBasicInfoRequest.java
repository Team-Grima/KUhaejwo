package grima.kuhaejwo.domain.user.dto;

import grima.kuhaejwo.domain.user.domain.BasicInfo;
import grima.kuhaejwo.domain.user.domain.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserBasicInfoRequest {
    private String college;
    private String department;
    private String studentId;
    private Long age;
    private String mbti;
    private Gender gender;

    public BasicInfo toEntity() {
        return new BasicInfo(college, department, studentId, age, mbti, gender);
    }
}

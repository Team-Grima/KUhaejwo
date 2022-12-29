package grima.kuhaejwo.domain.user.dto;

import grima.kuhaejwo.domain.user.domain.BasicInfo;
import grima.kuhaejwo.domain.user.domain.Gender;
import grima.kuhaejwo.domain.user.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserBasicInfoResponse {
    private String college;
    private String department;
    private String studentId;
    private Long age;
    private String mbti;
    private Gender gender;

    public UserBasicInfoResponse(BasicInfo basicInfo) {
        this.college= basicInfo.getCollege();
        this.department= basicInfo.getDepartment();
        this.studentId= basicInfo.getStudentId();
        this.age = basicInfo.getAge();
        this.mbti = basicInfo.getMbti();
        this.gender=basicInfo.getGender();
    }
}

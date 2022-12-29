package grima.kuhaejwo.domain.user.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasicInfo {
    private String college;
    private String department;
    private String studentId;
    private Long age;
    private String mbti;

    @Enumerated(value=EnumType.STRING)
    private Gender gender;


}

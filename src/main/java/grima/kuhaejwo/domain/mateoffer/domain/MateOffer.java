package grima.kuhaejwo.domain.mateoffer.domain;

import grima.kuhaejwo.domain.user.domain.Users;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MateOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "mateOffer")
    private Users user;

    private String title;

    private String dormitoryName;

    private String body;

    private Boolean matching;

    private Long goodnessOfFit;

    @CreatedDate
    private LocalDateTime createdAt;

}

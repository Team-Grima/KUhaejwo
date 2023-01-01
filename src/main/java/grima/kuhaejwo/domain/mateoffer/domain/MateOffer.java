package grima.kuhaejwo.domain.mateoffer.domain;

import grima.kuhaejwo.domain.user.domain.BasicInfo;
import grima.kuhaejwo.domain.user.domain.Users;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MateOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "mateOffer",fetch = FetchType.LAZY)
    private Users user;

    @Embedded
    private UserProfile userProfile;

    private String title;

    private String dormitoryName;

    private String body;

    private Boolean matching;

    private Long goodnessOfFit;

    @CreatedDate
    private LocalDateTime createdAt;

    public MateOffer(String title, String dormitoryName, String body, Boolean matching, Long goodnessOfFit, UserProfile userProfile) {
        this.userProfile = userProfile;
        this.title = title;
        this.dormitoryName = dormitoryName;
        this.body = body;
        this.matching = matching;
        this.goodnessOfFit = goodnessOfFit;
    }
    public UserProfile getUserProfile() {
        return this.userProfile == null ? new UserProfile() : this.userProfile;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}

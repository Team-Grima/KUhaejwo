package grima.kuhaejwo.domain.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;
    // Embedded 써야할지 OneToOne 써야할지 모르겠음.. 생각해보자 나중에
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="userInfoDetail_id")
    private UserInfoDetail userInfoDetail;

    private String mobileNumber;

    private String email;

    private String password;

    private Boolean emailAuth;

    private Boolean dormitory;

}

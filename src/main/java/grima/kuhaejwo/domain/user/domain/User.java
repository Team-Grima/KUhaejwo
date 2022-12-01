package grima.kuhaejwo.domain.user.domain;

import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
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

    // mateOffer 엔티티에도 fetch 를 지연로딩으로 바꿔야 하는가? cascade 또한 어떻게 설정 해야하는가
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mateOffer_id")
    private MateOffer mateOffer;

    private String mobileNumber;

    private String email;

    private String password;

    private Boolean emailAuth;

    private Boolean dormitory;

}

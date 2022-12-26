package grima.kuhaejwo.domain.user.domain;

import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
import grima.kuhaejwo.domain.user.domain.detail.Sleeper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;
    // Embedded 써야할지 OneToOne 써야할지 모르겠음.. 생각해보자 나중에
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="userInfoDetail_id")
    private UserInfoDetail userInfoDetail;

    @Embedded
    private BasicInfo basicInfo;

    // mateOffer 엔티티에도 fetch 를 지연로딩으로 바꿔야 하는가? cascade 또한 어떻게 설정 해야하는가
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mateOffer_id")
    private MateOffer mateOffer;

    private String mobileNumber;

    private String name;
    private String email;

    private String password;

    private Boolean emailAuth;

    private Boolean dormitory;

    public Users(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRole=UserRole.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return Long.toString(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public void setInfoDetail(UserInfoDetail userInfoDetail) {
        this.userInfoDetail = userInfoDetail;
    }

    public void setMateOffer(MateOffer mateOffer) {
        this.mateOffer = mateOffer;
    }
}

package grima.kuhaejwo.domain.user.domain;

import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
import grima.kuhaejwo.domain.user.domain.detail.Sleeper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Embedded
    private ProfileImage profileImage;

    @Embedded
    private PassImage passImage;
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

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Prefer> prefers = new ArrayList<>();

    public Users(String email, String password, String name) {
        ProfileImage image = ProfileImage.builder()
                .fileOriName("defualt")
                .fileUrl("images/basicImage.jpeg")
                .build();
        this.setProfileImage(image);
        this.emailAuth=Boolean.FALSE;
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRole = UserRole.USER;
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

    public BasicInfo getBasicInfo() {
        return this.basicInfo == null ? new BasicInfo() : this.basicInfo;
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

    public void setPrefers(List<Prefer> prefers) {
        this.prefers = prefers;
    }

    public void setEmailAuth(Boolean emailAuth) {
        this.emailAuth = emailAuth;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public void setPassImage(PassImage passImage) {
        this.passImage = passImage;
    }
}

package grima.kuhaejwo.domain.user.dto;

import grima.kuhaejwo.domain.mateoffer.dto.MateOfferResponse;
import grima.kuhaejwo.domain.user.domain.UserRole;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private UserInfoDetailResponse userInfoDetailResponse;
    private UserBasicInfoResponse userBasicInfoResponse;
    private MateOfferResponse mateOfferResponse;
    private UserPreferResponse userPreferResponse;
    private String mobileNumber;
    private String name;
    private String email;
    private Boolean emailAuth;
    private Boolean dormitory;

}

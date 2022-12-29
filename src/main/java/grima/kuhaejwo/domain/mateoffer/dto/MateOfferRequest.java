package grima.kuhaejwo.domain.mateoffer.dto;

import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
import grima.kuhaejwo.domain.mateoffer.domain.UserProfile;
import grima.kuhaejwo.domain.user.domain.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateOfferRequest {
    private String title;

    private String dormitoryName;

    private String body;

    private Boolean matching;

    private Long goodnessOfFit;

    public MateOffer toEntity(Users users){
        UserProfile userProfile = new UserProfile(users.getId(), users.getBasicInfo().getDepartment(), users.getBasicInfo().getAge());
        return new MateOffer(title, dormitoryName, body, matching, goodnessOfFit, userProfile);
    }
}

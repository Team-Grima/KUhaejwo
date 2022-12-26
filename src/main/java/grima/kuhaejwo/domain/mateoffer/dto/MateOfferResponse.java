package grima.kuhaejwo.domain.mateoffer.dto;

import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
import grima.kuhaejwo.domain.mateoffer.domain.UserProfile;
import grima.kuhaejwo.domain.user.domain.BasicInfo;
import grima.kuhaejwo.domain.user.domain.Users;
import grima.kuhaejwo.domain.user.dto.UserBasicInfoResponse;
import grima.kuhaejwo.domain.user.dto.UserInfoDetailResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateOfferResponse {
    private Long id;

    private String title;

    private String dormitoryName;

    private String body;

    private Boolean matching;

    private Long goodnessOfFit;


    private UserProfile userProfile;

    public MateOfferResponse(MateOffer mateOffer) {
        this.id= mateOffer.getId();
        this.userProfile = mateOffer.getUserProfile();
        this.title= mateOffer.getTitle();
        this.dormitoryName= mateOffer.getDormitoryName();
        this.body= mateOffer.getBody();
        this.matching = mateOffer.getMatching();
        this.goodnessOfFit= mateOffer.getGoodnessOfFit();
    }
}

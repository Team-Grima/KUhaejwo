package grima.kuhaejwo.domain.mateoffer.dto;

import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
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
    private UserBasicInfoResponse userBasicInfoResponse;

    private String title;

    private String dormitoryName;

    private String body;

    private Boolean matching;

    private Long goodnessOfFit;

    public MateOfferResponse(MateOffer mateOffer, BasicInfo basicInfo) {
        this.id= mateOffer.getId();
        this.userBasicInfoResponse = new UserBasicInfoResponse(basicInfo);
        this.title= mateOffer.getTitle();
        this.dormitoryName= mateOffer.getDormitoryName();
        this.body= mateOffer.getBody();
        this.matching = mateOffer.getMatching();
        this.goodnessOfFit= mateOffer.getGoodnessOfFit();
    }
}

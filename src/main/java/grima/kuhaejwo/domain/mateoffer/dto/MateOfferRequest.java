package grima.kuhaejwo.domain.mateoffer.dto;

import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
import grima.kuhaejwo.domain.user.domain.Users;
import lombok.Getter;

@Getter
public class MateOfferRequest {
    private String title;

    private String dormitoryName;

    private String body;

    private Boolean matching;

    private Long goodnessOfFit;

    public MateOffer toEntity(Users user){
        return new MateOffer(user, title, dormitoryName, body, matching, goodnessOfFit);
    }
}

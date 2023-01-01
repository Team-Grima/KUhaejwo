package grima.kuhaejwo.domain.user.service;

import grima.kuhaejwo.config.security.JwtProvider;
import grima.kuhaejwo.domain.mateoffer.dto.MateOfferResponse;
import grima.kuhaejwo.domain.user.dao.UserPreferRepository;
import grima.kuhaejwo.domain.user.dao.UsersRepository;
import grima.kuhaejwo.domain.user.domain.BasicInfo;
import grima.kuhaejwo.domain.user.domain.Prefer;
import grima.kuhaejwo.domain.user.domain.UserInfoDetail;
import grima.kuhaejwo.domain.user.domain.Users;
import grima.kuhaejwo.domain.user.domain.detail.*;
import grima.kuhaejwo.domain.user.dto.*;
import grima.kuhaejwo.except.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository userRepository;
    private final UserPreferRepository userPreferRepository;

    //반환 값 무엇으로 할까요
    @Transactional
    public UserBasicInfoResponse createInfo(UserBasicInfoRequest userBasicInfoRequest) {
        Users user = getUser();
        BasicInfo basicInfo = userBasicInfoRequest.toEntity();
        user.setBasicInfo(basicInfo);
        return new UserBasicInfoResponse(basicInfo);
    }

    @Transactional
    public UserBasicInfoResponse getInfo() {
        Users user = getUser();
        return new UserBasicInfoResponse(user.getBasicInfo());
    }
    //업데이트 부분 어떻게 할까요
    @Transactional
    public UserBasicInfoResponse updateInfo(UserBasicInfoRequest userBasicInfoRequest) {
        Users user = getUser();
        BasicInfo basicInfo = userBasicInfoRequest.toEntity();
        user.setBasicInfo(basicInfo);
        return new UserBasicInfoResponse(basicInfo);
    }

    @Transactional
    public UserInfoDetailResponse createInfoDetail(UserInfoDetailRequest userInfoDetailRequest) {
        Users user = getUser();
        UserInfoDetail userInfoDetail = userInfoDetailRequest.toEntity(user);
        user.setInfoDetail(userInfoDetail);
        return new UserInfoDetailResponse(userInfoDetail);
    }

    @Transactional
    public UserInfoDetailResponse getInfoDetail() {
        Users user = getUser();
        return new UserInfoDetailResponse(user.getUserInfoDetail());
    }

    @Transactional
    public UserInfoDetailResponse updateInfoDetail(UserInfoDetailRequest userInfoDetailRequest) {
        Users user = getUser();
        UserInfoDetail userInfoDetail = userInfoDetailRequest.toEntity(user);
        user.setInfoDetail(userInfoDetail);
        return new UserInfoDetailResponse(userInfoDetail);
    }

    @Transactional
    public UserResponse getInfoAll() {
        Users user = getUser();
        return UserResponse.builder()
                .dormitory(user.getDormitory())
                .email(user.getEmail())
                .emailAuth(user.getEmailAuth())
                .id(user.getId())
                .mateOfferResponse(new MateOfferResponse(user.getMateOffer()))
                .mobileNumber(user.getMobileNumber())
                .name(user.getName())
                .userBasicInfoResponse(new UserBasicInfoResponse(user.getBasicInfo()))
                .userInfoDetailResponse(new UserInfoDetailResponse(user.getUserInfoDetail()))
                .userPreferResponse(new UserPreferResponse(user.getPrefers()))
                .build();
    }

    @Transactional
    public UserResponse getInfoAllById(Long userId){
        Users user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserResponse.builder()
                .dormitory(user.getDormitory())
                .email(user.getEmail())
                .emailAuth(user.getEmailAuth())
                .id(user.getId())
                .mateOfferResponse(new MateOfferResponse(user.getMateOffer()))
                .mobileNumber(user.getMobileNumber())
                .name(user.getName())
                .userBasicInfoResponse(new UserBasicInfoResponse(user.getBasicInfo()))
                .userInfoDetailResponse(new UserInfoDetailResponse(user.getUserInfoDetail()))
                .userPreferResponse(new UserPreferResponse(user.getPrefers()))
                .build();
    }

    @Transactional
    public UserPreferResponse createPrefer(UserPreferRequest userPreferRequest) {
        Users user = getUser();
        List<String> contents = userPreferRequest.getPreferList();
        List<Prefer> prefers = new ArrayList<>();
        if (user.getPrefers() != null) {
            userPreferRepository.deleteAllInBatch(user.getPrefers());
        }
        for (String content : contents) {
            Prefer prefer = new Prefer(user, content);
            prefers.add(prefer);
        }
        userPreferRepository.saveAll(prefers);
        return new UserPreferResponse(prefers);
    }

    @Transactional
    public UserPreferResponse getPrefer() {
        Users user = getUser();
        List<Prefer> prefers = user.getPrefers();
        return new UserPreferResponse(prefers);
    }

    @Transactional
    public UserPreferResponse updatePrefer(UserPreferRequest userPreferRequest) {
        Users user = getUser();
        List<String> contents = userPreferRequest.getPreferList();
        List<Prefer> prefers = new ArrayList<>();
        if (user.getPrefers() != null) {
            userPreferRepository.deleteAllInBatch(user.getPrefers());
        }
        for (String content : contents) {
            Prefer prefer = new Prefer(user, content);
            prefers.add(prefer);
        }
        userPreferRepository.saveAll(prefers);
        return new UserPreferResponse(prefers);
    }

    public static int preference(UserInfoDetail user , UserInfoDetail writer){
//        enum Which{
//            WELL_CLEAN,
//            ONE_CLEAN,
//            NO_DRINK,
//            NO_SMOKE,
//            SAME_SLEEP,
//            SAME_WAKE,
//            NO_SLEEP_HABIT,
//            NO_YEMIN,
//            NO_COLD,
//            NO_HOT,
//            GOOD_FRIEND;
//        }
        double preference=100;
        System.out.println(preference);
        for(int i=0;i<3;i++){
            //user.which[i].ordinal()+1
            switch(1){
                case 1:
                    if(writer.getCleanHabit()== CleanHabit.valueOf("LAZY"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 2:
                    if(writer.getCleanHabit()==CleanHabit.valueOf("EAGER"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 3:
                    if(writer.getAlcohol()== Alcohol.valueOf("OFTEN"))
                        preference*=0.5;
                    else if(writer.getAlcohol()==Alcohol.valueOf("YES"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 4:
                    if(writer.getSmoking()== Smoking.valueOf("YES"))
                        preference*=0.6;
                    else
                        preference*=1.3;
                    break;
                case 5:
                    preference*=1-Math.abs(writer.getSleepingTime().ordinal()-user.getSleepingTime().ordinal())/3.0;
                    break;
                case 6:
                    preference*=1-Math.abs(writer.getWakeUpTime().ordinal()-user.getWakeUpTime().ordinal())/3.0;
                    break;
                case 7:
                    if(writer.getSleepingHabit()== SleepingHabit.valueOf("NO"))
                        preference*=1.3;
                    else
                        preference*=0.7;
                    break;
                case 8:
                    if(writer.getSleeper()== Sleeper.valueOf("LIGHT"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 9:
                    if(writer.getTemperature()==Temperature.valueOf("COLD"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 10:
                    if(writer.getTemperature()==Temperature.valueOf("HEAT"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 11:
                    if(writer.getFriend()==Friend.valueOf("FRIEND_WITH_BENEFIT"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;

                default:
                    break;

            }
            System.out.println(preference);
        }


        System.out.println(preference);
        return (int)Math.round(preference);
    }


    //프록시 객체 때문에 써야 하는 것
    public Users getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userdetails = (UserDetails) principal;
        Long userLongPk = Long.parseLong(userdetails.getUsername());
        Users user = userRepository.findById(userLongPk).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        return user;
    }

    //그냥 필터에 걸쳐서 조회 된 거 가져오기.
    public Users getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userdetails = (UserDetails) principal;
        Users user = (Users) principal;
        return user;
    }

}

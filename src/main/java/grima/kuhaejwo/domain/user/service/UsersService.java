package grima.kuhaejwo.domain.user.service;

import grima.kuhaejwo.config.security.JwtProvider;
import grima.kuhaejwo.domain.user.dao.UsersRepository;
import grima.kuhaejwo.domain.user.domain.BasicInfo;
import grima.kuhaejwo.domain.user.domain.UserInfoDetail;
import grima.kuhaejwo.domain.user.domain.Users;
import grima.kuhaejwo.domain.user.dto.UserBasicInfoRequest;
import grima.kuhaejwo.domain.user.dto.UserBasicInfoResponse;
import grima.kuhaejwo.domain.user.dto.UserInfoDetailRequest;
import grima.kuhaejwo.domain.user.dto.UserInfoDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    //반환 값 무엇으로 할까요
    @Transactional
    public UserBasicInfoResponse createInfo(String token, UserBasicInfoRequest userBasicInfoRequest) {
        Users user = getUserByToken(token);
        BasicInfo basicInfo = userBasicInfoRequest.toEntity();
        user.setBasicInfo(basicInfo);
        return new UserBasicInfoResponse(basicInfo);
    }

    @Transactional
    public UserBasicInfoResponse getInfo(String token) {
        Users user = getUserByToken(token);
        return new UserBasicInfoResponse(user.getBasicInfo());
    }
    //업데이트 부분 어떻게 할까요
    @Transactional
    public UserBasicInfoResponse updateInfo(String token, UserBasicInfoRequest userBasicInfoRequest) {
        Users user = getUserByToken(token);
        BasicInfo basicInfo = userBasicInfoRequest.toEntity();
        user.setBasicInfo(basicInfo);
        return new UserBasicInfoResponse(basicInfo);
    }

    @Transactional
    public UserInfoDetailResponse createInfoDetail(String token, UserInfoDetailRequest userInfoDetailRequest) {
        Users user = getUserByToken(token);
        UserInfoDetail userInfoDetail = userInfoDetailRequest.toEntity(user);
        user.setInfoDetail(userInfoDetail);
        return new UserInfoDetailResponse(userInfoDetail);
    }

    @Transactional
    public UserInfoDetailResponse getInfoDetail(String token) {
        Users user = getUserByToken(token);
        return new UserInfoDetailResponse(user.getUserInfoDetail());
    }

    @Transactional
    public UserInfoDetailResponse updateInfoDetail(String token, UserInfoDetailRequest userInfoDetailRequest) {
        Users user = getUserByToken(token);
        UserInfoDetail userInfoDetail = userInfoDetailRequest.toEntity(user);
        user.setInfoDetail(userInfoDetail);
        return new UserInfoDetailResponse(userInfoDetail);
    }


    public Users getUserByToken(String token) {
        Long userLongPk = Long.parseLong(jwtProvider.getUserPk(token));
        Users user = userRepository.findById(userLongPk).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        return user;
    }

}

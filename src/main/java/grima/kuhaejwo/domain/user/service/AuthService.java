package grima.kuhaejwo.domain.user.service;

import grima.kuhaejwo.config.security.JwtProvider;
import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
import grima.kuhaejwo.domain.user.dao.UsersRepository;
import grima.kuhaejwo.domain.user.domain.BasicInfo;
import grima.kuhaejwo.domain.user.domain.UserInfoDetail;
import grima.kuhaejwo.domain.user.domain.Users;
import grima.kuhaejwo.domain.user.dto.UserLoginRequest;
import grima.kuhaejwo.domain.user.dto.UserSignupRequest;
import grima.kuhaejwo.except.auth.LoginFailedException;
import grima.kuhaejwo.except.auth.SignUpFailedException;
import grima.kuhaejwo.token.RefreshToken;
import grima.kuhaejwo.token.RefreshTokenRepository;
import grima.kuhaejwo.token.TokenDto;
import grima.kuhaejwo.token.TokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;


    @Transactional
    public Long signup(UserSignupRequest userSignupRequest) {
//        if (usersRepository.existsByEmail(userSignupRequest.getEmail())) throw new SignUpFailedException();
//        return usersRepository.save(userSignupRequest.toEntity(passwordEncoder)).getId();
        if(usersRepository.findByEmail(userSignupRequest.getEmail()).isPresent())
            throw new SignUpFailedException();
        Users user = usersRepository.save(userSignupRequest.toEntity(passwordEncoder));
        user.setBasicInfo(new BasicInfo());
        user.setInfoDetail(new UserInfoDetail());
        user.setPrefers(new ArrayList<>());
        user.setMateOffer(MateOffer.builder()
                .matching(false)
                .build());
        usersRepository.save(user);
        return user.getId();
    }

    @Transactional
    public TokenDto login(UserLoginRequest userLoginRequest) {
        //?????? ?????? ?????? ??????
        Users loginUser = usersRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(()->new LoginFailedException("???????????? ???????????? ????????????."));

        //?????? ???????????? ?????? ?????? ??????
        if(!passwordEncoder.matches(userLoginRequest.getPassword(), loginUser.getPassword()))
            throw new LoginFailedException("??????????????? ???????????????.");

        //AccessToken, RefreshToken ??????
        TokenDto tokenDto = jwtProvider.createTokenDto(loginUser.getId(), loginUser.getUserRole());

        //RefreshToken ??????
        RefreshToken refreshToken = RefreshToken.builder()
                .key(loginUser.getId())
                .token(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        //????????? refresh token ??????
        if(!jwtProvider.validateToken(tokenRequestDto.getAccessToken())){
            throw new IllegalArgumentException(); //CrefreshTokenException()
        }

        //AccessToken ?????? Membername (pk) ????????????
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication= jwtProvider.getAuthentication(accessToken);

        // member pk??? ?????? ?????? / repo ??? ????????? Refresh token ??? ??????
        Users user = usersRepository.findById(Long.parseLong(authentication.getName())).orElseThrow(IllegalAccessError::new);
        RefreshToken refreshToken=refreshTokenRepository.findByKey(user.getId())
                .orElseThrow(IllegalArgumentException::new); //CUserNotFoundException

        //???????????? ?????? ????????? ??????
        if(!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new IllegalArgumentException(); //CRefreshTokenExceiption

        // AccessToken, RefreshToken ?????? ?????????, ???????????? ?????? ??????
        TokenDto newCreatedToken=jwtProvider.createTokenDto(user.getId(),user.getUserRole());
        RefreshToken updateRefreshToken=refreshToken.updateToken(newCreatedToken.getRefreshToken());
        refreshTokenRepository.save(updateRefreshToken);

        return newCreatedToken;
    }

    @Transactional
    public Boolean isAuth() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userdetails = (UserDetails) principal;
        Users user = (Users) principal;
        return user.getEmailAuth();
    }
}

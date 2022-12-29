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
        user.setMateOffer(new MateOffer());
        usersRepository.save(user);
        return user.getId();
    }

    @Transactional
    public TokenDto login(UserLoginRequest userLoginRequest) {
        //회원 정보 존재 확인
        Users loginUser = usersRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(()->new LoginFailedException("존재하는 이메일이 없습니다."));

        //회원 패스워드 일치 여부 확인
        if(!passwordEncoder.matches(userLoginRequest.getPassword(), loginUser.getPassword()))
            throw new LoginFailedException("비밀번호가 틀렸습니다.");

        //AccessToken, RefreshToken 발급
        TokenDto tokenDto = jwtProvider.createTokenDto(loginUser.getId(), loginUser.getUserRole());

        //RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(loginUser.getId())
                .token(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        //만료된 refresh token 에러
        if(!jwtProvider.validateToken(tokenRequestDto.getAccessToken())){
            throw new IllegalArgumentException(); //CrefreshTokenException()
        }

        //AccessToken 에서 Membername (pk) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication= jwtProvider.getAuthentication(accessToken);

        // member pk로 멤버 검색 / repo 에 저장된 Refresh token 이 없음
        Users user = usersRepository.findById(Long.parseLong(authentication.getName())).orElseThrow(IllegalAccessError::new);
        RefreshToken refreshToken=refreshTokenRepository.findByKey(user.getId())
                .orElseThrow(IllegalArgumentException::new); //CUserNotFoundException

        //리프레시 토큰 불일치 에러
        if(!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new IllegalArgumentException(); //CRefreshTokenExceiption

        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newCreatedToken=jwtProvider.createTokenDto(user.getId(),user.getUserRole());
        RefreshToken updateRefreshToken=refreshToken.updateToken(newCreatedToken.getRefreshToken());
        refreshTokenRepository.save(updateRefreshToken);

        return newCreatedToken;
    }
}

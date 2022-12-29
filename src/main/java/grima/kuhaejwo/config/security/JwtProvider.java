package grima.kuhaejwo.config.security;



import grima.kuhaejwo.domain.user.domain.UserRole;
import grima.kuhaejwo.domain.user.service.CustomMemberDetailService;
import grima.kuhaejwo.except.CAuthenticationEntryPointException;
import grima.kuhaejwo.except.auth.TokenFailedException;
import grima.kuhaejwo.token.TokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;



@RequiredArgsConstructor
@Component
public class JwtProvider {
    @Value("spring.jwt.secret")
    private String secretKey;
    private String ROLES="USER";

    //토큰 유효시간
//    private Long tokenValidMillisecond=60*60*100L;
    private final Long accessTokenValidMillisecond=60*60*1000L; // 1 hour
    private final Long refreshTokenValidMillisecond=14*24*60*60*1000L; // 14 day

    private final CustomMemberDetailService customMemberDetailService;

    //객체 초기화 하는 것 , secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init(){
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // 생성
    public TokenDto createTokenDto(Long userPk, UserRole userRole){

        // user 구분을 위해 Claims에 User Pk값 넣어줌
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
        claims.put(ROLES,userRole);
        // 생성날짜, 만료날짜를 위한 Date
        Date now=new Date();

        String accessToken=Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+accessTokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        String refreshToken=Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime()+refreshTokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        return TokenDto.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireDate(accessTokenValidMillisecond)
                .build();
    }

    //Jwt 로 인증정보를 조회
    public Authentication getAuthentication (String token){

        // Jwt 에서 claims 추출
        Claims claims=parseClaims(token);

        // 권한 정보가 없음
        if(claims.get(ROLES)==null){
            throw new CAuthenticationEntryPointException();
        }
        //UserDetails userDetails= customMemberDetailService.loadUserByUsername(claims.getSubject());
        UserDetails userDetails=customMemberDetailService.loadUserByUsername(this.getUserPk(token));

        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    // Jwt 토큰 복호화해서 가져오기
    private Claims parseClaims(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
//        try {
//            Jws<Claims> claimsJws= Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
//            return !claimsJws.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return true;
        }catch(JwtException | IllegalArgumentException e){
            // log.error(e.toString());
            return false;
        }
    }


}

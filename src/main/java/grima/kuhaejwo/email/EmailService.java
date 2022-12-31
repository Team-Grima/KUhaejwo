package grima.kuhaejwo.email;

import grima.kuhaejwo.domain.user.dao.UsersRepository;
import grima.kuhaejwo.domain.user.domain.Users;
import grima.kuhaejwo.except.auth.TokenFailedException;
import grima.kuhaejwo.except.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {

    private final EmailTokenService emailTokenService;
    private final UsersRepository usersRepository;

    @Transactional
    public boolean verifyEmail(String token) throws TokenFailedException {
        // 이메일 토큰을 찾아옴
        EmailToken findEmailToken = emailTokenService.findByIdAndExpirationDateAfterAndExpired(token);

        // 토큰의 유저 ID를 이용하여 유저 인증 정보를 가져온다.
        Users findUser = usersRepository.findById(findEmailToken.getMemberId()).orElseThrow(UserNotFoundException::new);
        findUser.setEmailAuth(true);
        findEmailToken.setTokenToUsed();    // 사용 완료

        return true;
    }
}
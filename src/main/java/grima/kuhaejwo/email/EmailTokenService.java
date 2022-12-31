package grima.kuhaejwo.email;

import grima.kuhaejwo.domain.user.domain.Users;
import grima.kuhaejwo.except.auth.TokenFailedException;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailTokenService {

    private final EmailSenderService emailSenderService;
    private final EmailTokenRepository emailTokenRepository;

    // 이메일 인증 토큰 생성
    public String createEmailToken(EmailRequest emailRequest) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userdetails = (UserDetails) principal;
        Users user = (Users) principal;
        Long userId = user.getId();

        String receiverEmail = emailRequest.getEmail() + "@konkuk.ac.kr";
        System.out.println(receiverEmail);

        // 이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToken(userId);
        emailTokenRepository.save(emailToken);

        // 이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("http://13.209.48.23/confirm-email?token="+emailToken.getId());
        emailSenderService.sendEmail(mailMessage);

        return emailToken.getId();    // 인증메일 전송 시 토큰 반환

    }

    // 유효한 토큰 가져오기
    public EmailToken findByIdAndExpirationDateAfterAndExpired(String emailTokenId) throws TokenFailedException {
        Optional<EmailToken> emailToken = emailTokenRepository
                .findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);

        // 토큰이 없다면 예외 발생
        return emailToken.orElseThrow(() -> new TokenFailedException());
    }
}
package grima.kuhaejwo.firebase;

import com.google.firebase.messaging.Message;
import grima.kuhaejwo.domain.user.dao.UserNotificationRepository;
import grima.kuhaejwo.domain.user.domain.Notification;
import grima.kuhaejwo.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//EventListener
@Component
@Async("matching")
@Transactional
@RequiredArgsConstructor
public class MatchingEventListener {
    private final FCMService fcmService;
    private final UserNotificationRepository userNotificationRepository;

    @EventListener
    public void handleMatchingCompleteEvent(MatchingCompleteEvent matchingCompleteEvent) {
        //알림 보낼 멤버 목록
        List<Users> usersList = matchingCompleteEvent.getUsersList();

        // 로그인 한 회원의 fcmToken 뽑기
        // 로그아웃 한 회원들의 fcmToken은 "" 공백
        List<String> fcmTokenList = usersList
                .stream()
                .filter(m -> !m.getFcmToken().isBlank())
                .map(m -> m.getFcmToken()).collect(Collectors.toList());

        if (fcmTokenList.size() != 0) {
            fcmService.sendByTokenList(fcmTokenList);
        }

        //알림 엔티티 만드는 로직 생략//
        for (Users user : usersList) {
            Notification notification = Notification.builder()
                    .body("Test Body")
                    .title("Test Title")
                    .isRead(false)
                    .user(user)
                    .build();
            userNotificationRepository.save(notification);
            user.getNotificationList().add(notification);
        }
        //알림 벌크 저장


    }
}

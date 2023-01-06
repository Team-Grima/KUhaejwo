package grima.kuhaejwo.domain.user.dto;

import grima.kuhaejwo.domain.user.domain.Notification;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserNotificationResponse {
    private String title;

    private String body;

    private Boolean isRead;

    private LocalDateTime createdAt;

    public UserNotificationResponse(Notification notification) {
        this.title = notification.getTitle();
        this.body = notification.getBody();
        this.isRead = notification.getIsRead();
        this.createdAt = notification.getCreatedAt();
    }
}

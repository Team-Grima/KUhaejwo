package grima.kuhaejwo.domain.user.dao;

import grima.kuhaejwo.domain.user.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<Notification,Long> {
}

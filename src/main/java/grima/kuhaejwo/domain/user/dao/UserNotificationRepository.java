package grima.kuhaejwo.domain.user.dao;

import grima.kuhaejwo.domain.user.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findAllByUser_Id(Long id);
}

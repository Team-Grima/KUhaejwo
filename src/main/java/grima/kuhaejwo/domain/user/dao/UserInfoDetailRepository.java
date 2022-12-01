package grima.kuhaejwo.domain.user.dao;

import grima.kuhaejwo.domain.user.domain.UserInfoDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDetailRepository extends JpaRepository<UserInfoDetail,Long> {
}

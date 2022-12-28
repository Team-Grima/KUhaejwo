package grima.kuhaejwo.domain.user.dao;

import grima.kuhaejwo.domain.user.domain.Prefer;
import grima.kuhaejwo.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPreferRepository extends JpaRepository<Prefer, Long> {
    List<Prefer> findAllByUsersId(Long users_id);

}

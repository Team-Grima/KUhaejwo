package grima.kuhaejwo.domain.user.dao;

import grima.kuhaejwo.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
}

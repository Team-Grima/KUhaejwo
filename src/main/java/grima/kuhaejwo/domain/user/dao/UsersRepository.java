package grima.kuhaejwo.domain.user.dao;

import grima.kuhaejwo.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);
}

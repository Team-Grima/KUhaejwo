package grima.kuhaejwo.domain.user.service;

import grima.kuhaejwo.domain.user.dao.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository userRepository;
}

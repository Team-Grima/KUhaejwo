package grima.kuhaejwo.domain.user.service;

import grima.kuhaejwo.domain.user.dao.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userPK) throws UsernameNotFoundException {
        return this.userRepository.findById(Long.parseLong(userPK)).orElseThrow(() -> new UsernameNotFoundException("asd"));
    }
}


package grima.kuhaejwo.domain.mateoffer.service;

import grima.kuhaejwo.config.security.JwtProvider;
import grima.kuhaejwo.domain.mateoffer.dao.MateOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MateOfferService {

    private final MateOfferRepository mateOfferRepository;
    private JwtProvider jwtProvider;

    @Transactional
    public
}

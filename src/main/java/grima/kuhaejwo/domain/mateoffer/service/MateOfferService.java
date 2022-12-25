package grima.kuhaejwo.domain.mateoffer.service;

import grima.kuhaejwo.config.security.JwtProvider;
import grima.kuhaejwo.domain.mateoffer.dao.MateOfferRepository;
import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
import grima.kuhaejwo.domain.mateoffer.dto.MateOfferRequest;
import grima.kuhaejwo.domain.mateoffer.dto.MateOfferResponse;
import grima.kuhaejwo.domain.user.dao.UsersRepository;
import grima.kuhaejwo.domain.user.domain.Users;
import grima.kuhaejwo.domain.user.dto.UserBasicInfoResponse;
import grima.kuhaejwo.except.mateoffer.MateOfferNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MateOfferService {

    private final MateOfferRepository mateOfferRepository;
    private final UsersRepository userRepository;
    private JwtProvider jwtProvider;

    private final ModelMapper modelMapper;

    @Transactional
    public MateOfferResponse createMateOffer(String token, MateOfferRequest mateOfferRequest) {
        Users user = getUserByToken(token);
        MateOffer mateOffer = mateOfferRequest.toEntity(user);
        user.setMateOffer(mateOffer);
        return new MateOfferResponse(mateOffer, user.getBasicInfo());
    }

    @Transactional
    public MateOfferResponse getMateOffer(String token) {
        Users user = getUserByToken(token);
        return new MateOfferResponse(user.getMateOffer(), user.getBasicInfo());
    }

    @Transactional
    public MateOfferResponse updateMateOffer(String token, MateOfferRequest mateOfferRequest) {
        Users user = getUserByToken(token);
        MateOffer mateOffer = mateOfferRequest.toEntity(user);
        user.setMateOffer(mateOffer);
        return new MateOfferResponse(user.getMateOffer(), user.getBasicInfo());
    }

    @Transactional
    public MateOfferResponse getMateOfferById(Long mateOfferId) {
        MateOffer mateOffer = mateOfferRepository.findById(mateOfferId).orElseThrow(MateOfferNotFoundException::new);
        return new MateOfferResponse(mateOffer, mateOffer.getUser().getBasicInfo());
    }

    @Transactional
    public List<MateOfferResponse> getMateOfferList() {
        return mateOfferRepository.findAll().stream()
                .map(o -> new MateOfferResponse(o,o.getUser().getBasicInfo()))
                .collect(Collectors.toList());
    }



    public Users getUserByToken(String token) {
        Long userLongPk = Long.parseLong(jwtProvider.getUserPk(token));
        Users user = userRepository.findById(userLongPk).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        return user;
    }
}

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
import grima.kuhaejwo.except.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final JwtProvider jwtProvider;


    @Transactional
    public MateOfferResponse createMateOffer(MateOfferRequest mateOfferRequest) {

        Users user = getUser();
        //Users user = userRepository.findById(getUserDetails()).orElseThrow(UserNotFoundException::new);
        //Users user = getUserDetails();
        MateOffer mateOffer = mateOfferRequest.toEntity(user);
        mateOfferRepository.save(mateOffer);
        user.setMateOffer(mateOffer);
        return new MateOfferResponse(mateOffer);
    }

    @Transactional
    public MateOfferResponse getMateOffer() {
        Users user = getUser();
        return new MateOfferResponse(user.getMateOffer());
    }

    @Transactional
    public MateOfferResponse updateMateOffer(MateOfferRequest mateOfferRequest) {
        Users user = getUser();
        MateOffer mateOffer = mateOfferRequest.toEntity(user);
        mateOfferRepository.save(mateOffer);
        user.setMateOffer(mateOffer);
        return new MateOfferResponse(user.getMateOffer());
    }

    @Transactional
    public MateOfferResponse getMateOfferByUserId(Long mateOfferId) {
        MateOffer mateOffer = mateOfferRepository.findById(mateOfferId).orElseThrow(MateOfferNotFoundException::new);
        return new MateOfferResponse(mateOffer);
    }

    @Transactional
    public List<MateOfferResponse> getMateOfferList() {
        return mateOfferRepository.findAll().stream()
                .map(o->new MateOfferResponse(o))
                .collect(Collectors.toList());
    }


    //프록시 객체 때문에 써야 하는 것
    public Users getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userdetails = (UserDetails) principal;
        Long userLongPk = Long.parseLong(userdetails.getUsername());
        Users user = userRepository.findById(userLongPk).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        return user;
    }

    //그냥 필터에 걸쳐서 조회 된 거 가져오기.
    public Users getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userdetails = (UserDetails) principal;
        Users user = (Users) principal;
        return user;
    }
}

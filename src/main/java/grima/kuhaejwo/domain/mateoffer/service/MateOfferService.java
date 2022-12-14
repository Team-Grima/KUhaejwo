package grima.kuhaejwo.domain.mateoffer.service;

import grima.kuhaejwo.config.security.JwtProvider;
import grima.kuhaejwo.domain.mateoffer.dao.MateOfferRepository;
import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
import grima.kuhaejwo.domain.mateoffer.dto.MatchingResponse;
import grima.kuhaejwo.domain.mateoffer.dto.MateOfferRequest;
import grima.kuhaejwo.domain.mateoffer.dto.MateOfferResponse;
import grima.kuhaejwo.domain.user.dao.UsersRepository;
import grima.kuhaejwo.domain.user.domain.Users;
import grima.kuhaejwo.domain.user.dto.UserBasicInfoResponse;
import grima.kuhaejwo.except.mateoffer.MateOfferNotFoundException;
import grima.kuhaejwo.except.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MateOfferService {

    private final MateOfferRepository mateOfferRepository;
    private final UsersRepository userRepository;

    private final ApplicationEventPublisher eventPublisher;



    @Transactional
    public MateOfferResponse createMateOffer(MateOfferRequest mateOfferRequest) {
        Users user = getUser();
        //Users user = userRepository.findById(getUserDetails()).orElseThrow(UserNotFoundException::new);
        //Users user = getUserDetails();
        if (user.getMateOffer() != null) {
            mateOfferRepository.delete(user.getMateOffer());
        }
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
        if (user.getMateOffer() != null) {
            mateOfferRepository.delete(user.getMateOffer());
        }
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

    @Transactional
    public List<MatchingResponse> matching() {
        Users user = getUser();
        List<Long> userIdList = mateOfferRepository.findAll().stream()
                .filter(m -> !m.getMatching())
                .map(m -> m.getUserProfile().getUserId())
                .collect(Collectors.toList());
        List<Users> userList = userRepository.findAllById(userIdList);
        List<MatchingResponse> matchingResponses = userList.stream()
                .map(o -> new MatchingResponse(user, o))
                .collect(Collectors.toList());
        return matchingResponses;
    }


    //????????? ?????? ????????? ?????? ?????? ???
    public Users getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userdetails = (UserDetails) principal;
        Long userLongPk = Long.parseLong(userdetails.getUsername());
        Users user = userRepository.findById(userLongPk).orElseThrow(() -> new UsernameNotFoundException("???????????? ?????? ???????????????."));
        return user;
    }

    //?????? ????????? ????????? ?????? ??? ??? ????????????.
    public Users getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userdetails = (UserDetails) principal;
        Users user = (Users) principal;
        return user;
    }
}

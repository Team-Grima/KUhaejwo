package grima.kuhaejwo.domain.user.service;


import grima.kuhaejwo.domain.mateoffer.dto.MateOfferResponse;
import grima.kuhaejwo.domain.user.dao.UserPreferRepository;
import grima.kuhaejwo.domain.user.dao.UsersRepository;
import grima.kuhaejwo.domain.user.domain.*;
import grima.kuhaejwo.domain.user.domain.detail.*;
import grima.kuhaejwo.domain.user.dto.*;
import grima.kuhaejwo.except.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository userRepository;
    private final UserPreferRepository userPreferRepository;

    //반환 값 무엇으로 할까요
    @Transactional
    public UserBasicInfoResponse createInfo(UserBasicInfoRequest userBasicInfoRequest) {
        Users user = getUser();
        BasicInfo basicInfo = userBasicInfoRequest.toEntity();
        user.setBasicInfo(basicInfo);
        return new UserBasicInfoResponse(basicInfo);
    }

    @Transactional
    public UserBasicInfoResponse getInfo() {
        Users user = getUser();
        return new UserBasicInfoResponse(user.getBasicInfo());
    }
    //업데이트 부분 어떻게 할까요
    @Transactional
    public UserBasicInfoResponse updateInfo(UserBasicInfoRequest userBasicInfoRequest) {
        Users user = getUser();
        BasicInfo basicInfo = userBasicInfoRequest.toEntity();
        user.setBasicInfo(basicInfo);
        return new UserBasicInfoResponse(basicInfo);
    }

    @Transactional
    public UserInfoDetailResponse createInfoDetail(UserInfoDetailRequest userInfoDetailRequest) {
        Users user = getUser();
        UserInfoDetail userInfoDetail = userInfoDetailRequest.toEntity(user);
        user.setInfoDetail(userInfoDetail);
        return new UserInfoDetailResponse(userInfoDetail);
    }

    @Transactional
    public UserInfoDetailResponse getInfoDetail() {
        Users user = getUser();
        return new UserInfoDetailResponse(user.getUserInfoDetail());
    }

    @Transactional
    public UserInfoDetailResponse updateInfoDetail(UserInfoDetailRequest userInfoDetailRequest) {
        Users user = getUser();
        UserInfoDetail userInfoDetail = userInfoDetailRequest.toEntity(user);
        user.setInfoDetail(userInfoDetail);
        return new UserInfoDetailResponse(userInfoDetail);
    }

    @Transactional
    public UserResponse getInfoAll() {
        Users user = getUser();
        return UserResponse.builder()
                .dormitory(user.getDormitory())
                .email(user.getEmail())
                .emailAuth(user.getEmailAuth())
                .id(user.getId())
                .mateOfferResponse(new MateOfferResponse(user.getMateOffer()))
                .mobileNumber(user.getMobileNumber())
                .name(user.getName())
                .userBasicInfoResponse(new UserBasicInfoResponse(user.getBasicInfo()))
                .userInfoDetailResponse(new UserInfoDetailResponse(user.getUserInfoDetail()))
                .userPreferResponse(new UserPreferResponse(user.getPrefers()))
                .build();
    }

    @Transactional
    public UserResponse getInfoAllById(Long userId){
        Users user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserResponse.builder()
                .dormitory(user.getDormitory())
                .email(user.getEmail())
                .emailAuth(user.getEmailAuth())
                .id(user.getId())
                .mateOfferResponse(new MateOfferResponse(user.getMateOffer()))
                .mobileNumber(user.getMobileNumber())
                .name(user.getName())
                .userBasicInfoResponse(new UserBasicInfoResponse(user.getBasicInfo()))
                .userInfoDetailResponse(new UserInfoDetailResponse(user.getUserInfoDetail()))
                .userPreferResponse(new UserPreferResponse(user.getPrefers()))
                .build();
    }

    @Transactional
    public UserPreferResponse createPrefer(UserPreferRequest userPreferRequest) {
        Users user = getUser();
        List<String> contents = userPreferRequest.getPreferList();
        List<Prefer> prefers = new ArrayList<>();
        if (user.getPrefers() != null) {
            userPreferRepository.deleteAllInBatch(user.getPrefers());
        }
        for (String content : contents) {
            Prefer prefer = new Prefer(user, content);
            prefers.add(prefer);
        }
        userPreferRepository.saveAll(prefers);
        return new UserPreferResponse(prefers);
    }

    @Transactional
    public UserPreferResponse getPrefer() {
        Users user = getUser();
        List<Prefer> prefers = user.getPrefers();
        return new UserPreferResponse(prefers);
    }

    @Transactional
    public UserPreferResponse updatePrefer(UserPreferRequest userPreferRequest) {
        Users user = getUser();
        List<String> contents = userPreferRequest.getPreferList();
        List<Prefer> prefers = new ArrayList<>();
        if (user.getPrefers() != null) {
            userPreferRepository.deleteAllInBatch(user.getPrefers());
        }
        for (String content : contents) {
            Prefer prefer = new Prefer(user, content);
            prefers.add(prefer);
        }
        userPreferRepository.saveAll(prefers);
        return new UserPreferResponse(prefers);
    }

    public static int preference(UserInfoDetail user , UserInfoDetail writer){
//        enum Which{
//            WELL_CLEAN,
//            ONE_CLEAN,
//            NO_DRINK,
//            NO_SMOKE,
//            SAME_SLEEP,
//            SAME_WAKE,
//            NO_SLEEP_HABIT,
//            NO_YEMIN,
//            NO_COLD,
//            NO_HOT,
//            GOOD_FRIEND;
//        }
        double preference=100;
        System.out.println(preference);
        for(int i=0;i<3;i++){
            //user.which[i].ordinal()+1
            switch(1){
                case 1:
                    if(writer.getCleanHabit()== CleanHabit.valueOf("LAZY"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 2:
                    if(writer.getCleanHabit()==CleanHabit.valueOf("EAGER"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 3:
                    if(writer.getAlcohol()== Alcohol.valueOf("OFTEN"))
                        preference*=0.5;
                    else if(writer.getAlcohol()==Alcohol.valueOf("YES"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 4:
                    if(writer.getSmoking()== Smoking.valueOf("YES"))
                        preference*=0.6;
                    else
                        preference*=1.3;
                    break;
                case 5:
                    preference*=1-Math.abs(writer.getSleepingTime().ordinal()-user.getSleepingTime().ordinal())/3.0;
                    break;
                case 6:
                    preference*=1-Math.abs(writer.getWakeUpTime().ordinal()-user.getWakeUpTime().ordinal())/3.0;
                    break;
                case 7:
                    if(writer.getSleepingHabit()== SleepingHabit.valueOf("NO"))
                        preference*=1.3;
                    else
                        preference*=0.7;
                    break;
                case 8:
                    if(writer.getSleeper()== Sleeper.valueOf("LIGHT"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 9:
                    if(writer.getTemperature()==Temperature.valueOf("COLD"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 10:
                    if(writer.getTemperature()==Temperature.valueOf("HEAT"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;
                case 11:
                    if(writer.getFriend()==Friend.valueOf("FRIEND_WITH_BENEFIT"))
                        preference*=0.7;
                    else
                        preference*=1.3;
                    break;

                default:
                    break;

            }
            System.out.println(preference);
        }


        System.out.println(preference);
        return (int)Math.round(preference);
    }

    @Transactional
    public String createProfileImage(MultipartFile multipartFile) throws IOException {
        Users user = getUser();

        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장할 것이다
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath() + "/";
        // 경로를 지정하고 그곳에다가 저장할 심산이다
        String path = "images/" + current_date;
        File file = new File(path);
        // 저장할 위치의 디렉토리가 존지하지 않을 경우
        if (!file.exists()) {
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }

        if (!multipartFile.isEmpty()) {
            // jpeg, png, gif 파일들만 받아서 처리할 예정
            String contentType = multipartFile.getContentType();
            String originalFileExtension;
            // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
            if (ObjectUtils.isEmpty(contentType)) {
                throw new RuntimeException();
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else if (contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                }
                // 다른 파일 명이면 아무 일 하지 않는다
                else {
                    throw new RuntimeException();
                }
            }
            // 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
            String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;
            ProfileImage image = ProfileImage.builder()
                    .fileOriName(multipartFile.getOriginalFilename())
                    .fileUrl(path+"/"+new_file_name)
                    .build();

            user.setProfileImage(image);
            file = new File(absolutePath + path + "/" + new_file_name);
            multipartFile.transferTo(file);
        }
        return path;
    }

    @Transactional
    public ResponseEntity<Resource> getProfileImage() throws IOException {
        Users user = getUser();
        String imgPath = user.getProfileImage().getFileUrl();
        String absolutePath = new File("").getAbsolutePath() + "/";
        Path path = Paths.get(absolutePath + imgPath);
        String contentType = Files.probeContentType(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                        .filename(user.getProfileImage().getFileOriName(), StandardCharsets.UTF_8)
                        .build());
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }
    @Transactional
    public ResponseEntity<byte[]> getProfileImage2() {
        Users user = getUser();
        String imgPath = user.getProfileImage().getFileUrl();
        String absolutePath = new File("").getAbsolutePath() + "/";
        File file=new File(absolutePath+imgPath);
        ResponseEntity<byte[]> result=null;
        try {
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result=new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),headers,HttpStatus.OK );
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Transactional
    public String getProfileImage3() {
        Users user = getUser();
        String imgPath = user.getProfileImage().getFileUrl();
        String absolutePath = new File("").getAbsolutePath() + "/";
        return "<img src=" + absolutePath + imgPath + ">";
    }

    @Transactional
    public ResponseEntity<OutputStream> getProfileImage4() {
        Users user = getUser();
        String imgPath = user.getProfileImage().getFileUrl();
        String absolutePath = new File("").getAbsolutePath() + "/";
        File file=new File(absolutePath+imgPath);
        ResponseEntity<OutputStream> result=null;
        try {
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result=new ResponseEntity<>(new FileOutputStream(file),headers,HttpStatus.OK );
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Transactional
    public String createPassImage(MultipartFile multipartFile) throws IOException {
        Users user = getUser();

        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장할 것이다
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath() + "/";
        // 경로를 지정하고 그곳에다가 저장할 심산이다
        String path = "images/" + current_date;
        File file = new File(path);
        // 저장할 위치의 디렉토리가 존지하지 않을 경우
        if (!file.exists()) {
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }

        if (!multipartFile.isEmpty()) {
            // jpeg, png, gif 파일들만 받아서 처리할 예정
            String contentType = multipartFile.getContentType();
            String originalFileExtension;
            // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
            if (ObjectUtils.isEmpty(contentType)) {
                throw new RuntimeException();
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else if (contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                }
                // 다른 파일 명이면 아무 일 하지 않는다
                else {
                    throw new RuntimeException();
                }
            }
            // 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
            String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;
            PassImage image = PassImage.builder()
                    .fileOriName(multipartFile.getOriginalFilename())
                    .fileUrl(path+"/"+new_file_name)
                    .build();

            user.setPassImage(image);
            file = new File(absolutePath + path + "/" + new_file_name);
            multipartFile.transferTo(file);
        }
        return path;
    }

    @Transactional
    public ResponseEntity<byte[]> getPassImage() {
        Users user = getUser();
        String imgPath = user.getPassImage().getFileUrl();
        String absolutePath = new File("").getAbsolutePath() + "/";
        File file=new File(absolutePath+imgPath);
        ResponseEntity<byte[]> result=null;
        try {
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result=new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),headers,HttpStatus.OK );
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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

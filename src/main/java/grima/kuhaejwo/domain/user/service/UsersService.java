package grima.kuhaejwo.domain.user.service;


import grima.kuhaejwo.domain.mateoffer.dto.MateOfferResponse;
import grima.kuhaejwo.domain.user.dao.UserNotificationRepository;
import grima.kuhaejwo.domain.user.dao.UserPreferRepository;
import grima.kuhaejwo.domain.user.dao.UsersRepository;
import grima.kuhaejwo.domain.user.domain.*;
import grima.kuhaejwo.domain.user.domain.detail.*;
import grima.kuhaejwo.domain.user.dto.*;
import grima.kuhaejwo.except.user.UserNotFoundException;
import grima.kuhaejwo.except.user.UserNotificationNotFoundException;
import grima.kuhaejwo.except.user.UserNotificationNotSameException;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository userRepository;
    private final UserPreferRepository userPreferRepository;
    private final UserNotificationRepository userNotificationRepository;
    //?????? ??? ???????????? ?????????
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
    //???????????? ?????? ????????? ?????????
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

        // ?????? ????????? ????????? ??? ????????? ???????????? ????????? ?????????
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // ???????????? ????????? ???????????? ?????? ??????????????? ?????? (Window ??? Tomcat ??? Temp ????????? ????????????)
        String absolutePath = new File("").getAbsolutePath() + "/";
        // ????????? ???????????? ??????????????? ????????? ????????????
        String path = "images/" + current_date;
        File file = new File(path);
        // ????????? ????????? ??????????????? ???????????? ?????? ??????
        if (!file.exists()) {
            // mkdir() ????????? ?????? ?????? ?????? ??????????????? ???????????? ?????? ??? ???????????? ??????
            file.mkdirs();
        }

        if (!multipartFile.isEmpty()) {
            // jpeg, png, gif ???????????? ????????? ????????? ??????
            String contentType = multipartFile.getContentType();
            String originalFileExtension;
            // ????????? ?????? ????????? ??? ????????? ??? ??? ??? ?????????
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
                // ?????? ?????? ????????? ?????? ??? ?????? ?????????
                else {
                    throw new RuntimeException();
                }
            }
            // ??? ????????? ????????? ???????????? ?????? ????????? ???????????? ??????
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
        //InputStreamResource??? ???????????? ?????? ?????? ???????????? ??????????????? Resource??? ???????????? ???????????? ????????? ????????? ????????????.
        // ??????????????? ????????? Resource??? ?????? ??? ??????.
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

        // ?????? ????????? ????????? ??? ????????? ???????????? ????????? ?????????
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // ???????????? ????????? ???????????? ?????? ??????????????? ?????? (Window ??? Tomcat ??? Temp ????????? ????????????)
        String absolutePath = new File("").getAbsolutePath() + "/";
        // ????????? ???????????? ??????????????? ????????? ????????????
        String path = "images/" + current_date;
        File file = new File(path);
        // ????????? ????????? ??????????????? ???????????? ?????? ??????
        if (!file.exists()) {
            // mkdir() ????????? ?????? ?????? ?????? ??????????????? ???????????? ?????? ??? ???????????? ??????
            file.mkdirs();
        }

        if (!multipartFile.isEmpty()) {
            // jpeg, png, gif ???????????? ????????? ????????? ??????
            String contentType = multipartFile.getContentType();
            String originalFileExtension;
            // ????????? ?????? ????????? ??? ????????? ??? ??? ??? ?????????
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
                // ?????? ?????? ????????? ?????? ??? ?????? ?????????
                else {
                    throw new RuntimeException();
                }
            }
            // ??? ????????? ????????? ???????????? ?????? ????????? ???????????? ??????
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

    @Transactional
    public List<UserNotificationResponse> getNotification() {
        Users user = getUser();
        List<Notification> notificationList = userNotificationRepository.findAllByUser_Id(user.getId());
        return notificationList.stream()
                .map(o -> new UserNotificationResponse(o))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<UserNotificationResponse> getNotificationNotRead() {
        Users user = getUser();
        List<Notification> notificationList = userNotificationRepository.findAllByUser_Id(user.getId());
        return notificationList.stream()
                .filter(o->!o.getIsRead())
                .map(o -> new UserNotificationResponse(o))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserNotificationResponse createNotification() {
        Users user = getUser();
        Notification notification = Notification.builder()
                .user(user)
                .isRead(Boolean.FALSE)
                .body("test body")
                .title("test title")
                .build();
        userNotificationRepository.save(notification);
//        user.getNotificationList().add(notification);
        return new UserNotificationResponse(notification);
    }

    @Transactional
    public UserNotificationResponse getNotificationById(Long id) {
        Users user = getUser();
        Notification notification = userNotificationRepository.findById(id).orElseThrow(UserNotificationNotFoundException::new);
        if (notification.getUser().getId() != user.getId()) {
            throw new UserNotificationNotSameException();
        }
        notification.readed();
        return new UserNotificationResponse(notification);
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

    public void docsUserdetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
        } else {
            String username = principal.toString();
        }
    }

}

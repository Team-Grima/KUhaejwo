package grima.kuhaejwo.domain.user.domain;

import grima.kuhaejwo.domain.user.domain.detail.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserInfoDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userInfoDetail")
    private Users user;

    //나의 청소 습관은?
    @Enumerated(value = EnumType.STRING)
    private CleanHabit cleanHabit;

    //나의 씻는시간은?
    @Enumerated(value = EnumType.STRING)
    private WashingTime washingTime;

    //나의 음주 여부는?
    @Enumerated(value = EnumType.STRING)
    private Alcohol alcohol;

    //나의 흡연 여부는?
    @Enumerated(value = EnumType.STRING)
    private Smoking smoking;

    //나의 취침시간은?
    @Enumerated(value = EnumType.STRING)
    private SleepingTime sleepingTime;

    //나의 잠버릇은?
    @Enumerated(value = EnumType.STRING)
    private SleepingHabit sleepingHabit;

    //나의 잠귀는?
    @Enumerated(value = EnumType.STRING)
    private Sleeper sleeper;

    //나의 기상시간은?
    @Enumerated(value = EnumType.STRING)
    private WakeUpTime wakeUpTime;

    //나는 알람을?
    @Enumerated(value = EnumType.STRING)
    private Alarm alarm;

    //나는 밖에 있는 시간이?
    @Enumerated(value = EnumType.STRING)
    private Outing outing;

    //나는 벌레를?
    @Enumerated(value = EnumType.STRING)
    private Bug bug;

    //나의 온도는?
    @Enumerated(value = EnumType.STRING)
    private Temperature temperature;

    //나는 룸메와?
    @Enumerated(value = EnumType.STRING)
    private Friend friend;
}

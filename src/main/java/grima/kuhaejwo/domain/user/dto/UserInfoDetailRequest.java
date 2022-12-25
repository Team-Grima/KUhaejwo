package grima.kuhaejwo.domain.user.dto;

import grima.kuhaejwo.domain.user.domain.UserInfoDetail;
import grima.kuhaejwo.domain.user.domain.Users;
import grima.kuhaejwo.domain.user.domain.detail.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDetailRequest {
    //나의 청소 습관은?
    private CleanHabit cleanHabit;

    //나의 씻는시간은?
    private WashingTime washingTime;

    //나의 음주 여부는?
    private Alcohol alcohol;

    //나의 흡연 여부는?
    private Smoking smoking;

    //나의 취침시간은?
    private SleepingTime sleepingTime;

    //나의 잠버릇은?
    private SleepingHabit sleepingHabit;

    //나의 잠귀는?
    private Sleeper sleeper;

    //나의 기상시간은?
    private WakeUpTime wakeUpTime;

    //나는 알람을?
    private Alarm alarm;

    //나는 밖에 있는 시간이?
    private Outing outing;

    //나는 벌레를?
    private Bug bug;

    //나의 온도는?
    private Temperature temperature;

    //나는 룸메와?
    private Friend friend;

    public UserInfoDetail toEntity(Users user) {
        return new UserInfoDetail(user, cleanHabit, washingTime, alcohol, smoking, sleepingTime, sleepingHabit, sleeper, wakeUpTime, alarm, outing, bug, temperature, friend);
    }

}

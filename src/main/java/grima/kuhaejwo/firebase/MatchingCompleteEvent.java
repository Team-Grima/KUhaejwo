package grima.kuhaejwo.firebase;

import grima.kuhaejwo.domain.user.domain.Users;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class MatchingCompleteEvent {
    private final List<Users> usersList;
}

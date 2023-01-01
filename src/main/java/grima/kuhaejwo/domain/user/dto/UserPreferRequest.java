package grima.kuhaejwo.domain.user.dto;

import grima.kuhaejwo.domain.user.domain.Prefer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserPreferRequest {
    private List<String> preferList= new ArrayList<>();

}

package grima.kuhaejwo.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserPreferResponse {
    private List<String> contents = new ArrayList<>();
}

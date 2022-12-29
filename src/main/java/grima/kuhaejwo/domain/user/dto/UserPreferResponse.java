package grima.kuhaejwo.domain.user.dto;

import grima.kuhaejwo.domain.user.domain.Prefer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserPreferResponse {
    private List<String> contents = new ArrayList<>();

    public UserPreferResponse(List<Prefer> prefers) {
        this.contents= prefers.stream().map(o -> new String(o.getContent())).collect(Collectors.toList());
    }
}

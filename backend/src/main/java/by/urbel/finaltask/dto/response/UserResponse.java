package by.urbel.finaltask.dto.response;

import by.urbel.finaltask.domain.user.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private List<String> roles;
}

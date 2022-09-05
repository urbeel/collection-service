package by.urbel.finaltask.mapper;

import by.urbel.finaltask.domain.user.User;
import by.urbel.finaltask.dto.requests.SignUpRequest;
import by.urbel.finaltask.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "username", source = "username")
    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(role->role.getName().toString()).toList())")
    UserResponse userToUserResponse(User user);
    @Mapping(target = "username", source = "username")
    User signUpRequestToUser(SignUpRequest signUpRequest);
}

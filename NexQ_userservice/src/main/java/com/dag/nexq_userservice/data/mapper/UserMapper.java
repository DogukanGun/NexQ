package com.dag.nexq_userservice.data.mapper;
import com.dag.nexq_userservice.data.entity.User;
import com.dag.nexq_userservice.data.entity.UserConfig;
import com.dag.nexq_userservice.data.request.LoginRequest;
import com.dag.nexq_userservice.data.request.RegisterRequest;
import com.dag.nexq_userservice.data.request.UserConfigRequest;
import com.dag.nexq_userservice.data.sec.UserDetailsImpl;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "authorities", ignore = true)
    UserDetailsImpl convertToUserDetail(User user);
    User createUser(RegisterRequest registerRequest);
    User oolog(LoginRequest registerRequest);
    UserConfig createConfig(UserConfigRequest userConfigRequest);

}

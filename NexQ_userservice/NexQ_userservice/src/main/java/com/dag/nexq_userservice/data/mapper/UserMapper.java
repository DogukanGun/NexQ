package com.dag.nexq_userservice.data.mapper;
import com.dag.nexq_userservice.data.entity.User;
import com.dag.nexq_userservice.data.entity.UserConfig;
import com.dag.nexq_userservice.data.request.auth.RegisterRequest;
import com.dag.nexq_userservice.data.request.auth.UserConfigRequest;
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
    UserConfig createConfig(UserConfigRequest userConfigRequest);
    User a(UserConfigRequest userConfigRequest);
}

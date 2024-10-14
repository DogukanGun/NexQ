package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.data.entity.UserConfig;
import com.dag.nexq_userservice.data.enums.Language;
import com.dag.nexq_userservice.data.request.UserConfigRequest;
import com.dag.nexq_userservice.repository.UserConfigRepository;
import com.dag.nexq_userservice.services.interfaces.IConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.dag.nexq_userservice.data.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class ConfigService implements IConfigService {

    private final UserConfigRepository userConfigRepository;

    @Override
    public UserConfig saveConfig(UserConfigRequest userConfigRequest) {
        UserConfig userConfig = USER_MAPPER.createConfig(userConfigRequest);
        return userConfigRepository.save(userConfig);
    }

    @Override
    public Optional<UserConfig> getConfig(String id) {
        return userConfigRepository.findById(id);
    }

    @Override
    public void updateLanguage(String id,String language) {
        userConfigRepository.updateLanguageById(id,language);
    }

    @Override
    public List<String> getLanguages() {
        return Language.getAllShortForms();
    }
}

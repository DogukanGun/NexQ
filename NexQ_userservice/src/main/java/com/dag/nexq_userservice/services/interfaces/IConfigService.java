package com.dag.nexq_userservice.services.interfaces;

import com.dag.nexq_userservice.data.entity.UserConfig;
import com.dag.nexq_userservice.data.request.UserConfigRequest;

import java.util.List;
import java.util.Optional;

public interface IConfigService {
    UserConfig saveConfig(UserConfigRequest userConfigRequest);
    Optional<UserConfig> getConfig(String id);
    void updateLanguage(String id,String language);
    List<String> getLanguages();
}

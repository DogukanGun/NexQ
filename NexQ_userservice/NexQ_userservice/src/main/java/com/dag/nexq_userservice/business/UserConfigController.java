package com.dag.nexq_userservice.business;

import com.dag.nexq_userservice.data.entity.UserConfig;
import com.dag.nexq_userservice.data.request.auth.UserConfigRequest;
import com.dag.nexq_userservice.services.interfaces.IConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserConfigController {

    private final IConfigService configService;

    @GetMapping("/config/{id}")
    public ResponseEntity<Optional<UserConfig>> getConfig(@PathVariable("id") String id){
        return ResponseEntity.ok(configService.getConfig(id));
    }

    @PostMapping("/config")
    public ResponseEntity<UserConfig> saveConfig(@RequestBody UserConfigRequest userConfigRequest){
        return ResponseEntity.ok(configService.saveConfig(userConfigRequest));
    }

    @PatchMapping("/config/{id}/language/{lang}")
    public void updateConfig(@PathVariable("id") String id, @PathVariable("lang") String lang){
        configService.updateLanguage(id, lang);
    }

}

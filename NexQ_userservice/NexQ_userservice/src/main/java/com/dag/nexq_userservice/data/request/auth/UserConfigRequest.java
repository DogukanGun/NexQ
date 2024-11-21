package com.dag.nexq_userservice.data.request.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserConfigRequest {
    private String imei;
    private String language;
}

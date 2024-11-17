package com.dag.nexq_userservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("google")
public record GoogleAuthConfig(String clientKey) {}

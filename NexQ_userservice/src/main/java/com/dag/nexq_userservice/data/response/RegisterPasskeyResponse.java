package com.dag.nexq_userservice.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterPasskeyResponse {
    private String challenge;
    private Rp rp;
    private User user;
    private List<PubKeyCredParam> pubKeyCredParams;
    private int timeout;
    private String attestation;
    private List<ExcludeCredential> excludeCredentials;
    private AuthenticatorSelection authenticatorSelection;

    @Data
    @Builder
    public static class Rp {
        private String name;
        private String id;
    }

    @Data
    @Builder
    public static class User {
        private String id;
        private String name;
        private String displayName;
    }

    @Data
    @Builder
    public static class PubKeyCredParam {
        private String type;
        private int alg;
    }

    @Data
    @Builder
    public static class ExcludeCredential {
        private String id;
        private String type;
    }

    @Data
    @Builder
    public static class AuthenticatorSelection {
        private String authenticatorAttachment;
        private boolean requireResidentKey;
        private String residentKey;
        private String userVerification;
    }
}

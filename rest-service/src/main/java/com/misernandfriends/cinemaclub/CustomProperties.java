package com.misernandfriends.cinemaclub;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "custom")
@Validated
@Getter
@Setter
public class CustomProperties {

    private MailOptions mailOptions;
    private String verificationTokenLink;

    @Getter
    @Setter
    public static class MailOptions {
        private Boolean enable;
        private String host;
        private String hostPort;
        private String socketFactoryPort;
        private String socketFactoryClass;
        private String email;
        private String password;
    }
}

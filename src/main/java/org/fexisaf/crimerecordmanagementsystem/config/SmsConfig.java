package org.fexisaf.crimerecordmanagementsystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties("twilio")
public class SmsConfig {

    private String sid;

    private String token;

    private String phoneNumber;
}

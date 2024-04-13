package org.fexisaf.crimerecordmanagementsystem.config;

import com.twilio.Twilio;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    private SmsConfig smsConfig;

    public TwilioConfig(SmsConfig smsConfig){
        this.smsConfig = smsConfig;
        Twilio.init(smsConfig.getSid(), smsConfig.getToken());
    }



}


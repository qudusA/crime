package org.fexisaf.crimerecordmanagementsystem.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    private final SmsConfig smsConfig;

    @Autowired
    public TwilioConfig(SmsConfig smsConfig){
        this.smsConfig = smsConfig;
        Twilio.init(smsConfig.getSid(), smsConfig.getToken());
    }



}


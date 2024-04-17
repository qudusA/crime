package org.fexisaf.crimerecordmanagementsystem.event.emailEvent;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.fexisaf.crimerecordmanagementsystem.event.emailEvent.EventObj;
import org.fexisaf.crimerecordmanagementsystem.service.userService.UserServiceAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventObjListener implements ApplicationListener<EventObj> {


    private UserServiceAuthentication authentication;

    @Autowired
    public EventObjListener(UserServiceAuthentication authentication){
        this.authentication = authentication;
    }

    @Override
    public void onApplicationEvent(EventObj event) {
        String user = event.getTokenModel().getUserEntity().getUsername();
        String url = event.getUrl();
        String msg = getMsg(event, url);
        System.out.println(msg);
        try {
            authentication.sendEmail(user,"Email Verification",msg);
        } catch (
    MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getMsg(EventObj event, String url) {
        String token = event.getTokenModel().getToken();
        String msg = """
                                        VERIFY YOUR EMAIL ADDRESS
                            
                    thanks for signing up on this app.we want to make sure its really you.
                    please click on the link below, this code is valid for 10 minutes. if
                    you do not want to create an account, you can ignore this message.
                
                """ + url + "/verification?token="+token;
        return msg;
    }
}

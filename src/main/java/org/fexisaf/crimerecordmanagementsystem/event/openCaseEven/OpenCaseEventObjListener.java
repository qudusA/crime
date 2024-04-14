package org.fexisaf.crimerecordmanagementsystem.event.openCaseEven;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.fexisaf.crimerecordmanagementsystem.service.userService.UserServiceAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OpenCaseEventObjListener implements ApplicationListener<OpenCaseEventObj> {


    private UserServiceAuthentication authentication;

    @Autowired
    public OpenCaseEventObjListener(UserServiceAuthentication authentication){
        this.authentication = authentication;
    }

    @Override
    public void onApplicationEvent(OpenCaseEventObj event) {
        String user = event.getTokenModel().getUserEntity().getUsername();
        String url = event.getUrl();
        String msg = getMsg(event, url);
        System.out.println(msg);
        try {
            authentication.sendEmail(user,"Email Verification",msg);
            log.info("email sent to {} with: {}", user,url);
        } catch (
    MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getMsg(OpenCaseEventObj event, String url) {
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

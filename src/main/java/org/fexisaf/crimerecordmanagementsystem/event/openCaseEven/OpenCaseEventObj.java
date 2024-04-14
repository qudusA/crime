package org.fexisaf.crimerecordmanagementsystem.event.openCaseEven;

import lombok.Getter;
import lombok.Setter;
import org.fexisaf.crimerecordmanagementsystem.entity.TokenEntity;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OpenCaseEventObj extends ApplicationEvent {
    private TokenEntity tokenModel;
    private String url;

    public OpenCaseEventObj(TokenEntity tokenEntity, String url) {
        super(tokenEntity);
        this.tokenModel = tokenEntity;
        this.url = url;
    }
}

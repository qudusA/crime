package org.fexisaf.crimerecordmanagementsystem.event.chargedCaseEvent;

import lombok.Getter;
import lombok.Setter;
import org.fexisaf.crimerecordmanagementsystem.entity.ChargedCaseEntity;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ChargedCaseEvent extends ApplicationEvent {

    private ChargedCaseEntity chargedCaseEntity;

    public ChargedCaseEvent(ChargedCaseEntity chargedCaseEntity) {
        super(chargedCaseEntity);
        this.chargedCaseEntity = chargedCaseEntity;
    }
}

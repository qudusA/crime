package org.fexisaf.crimerecordmanagementsystem.event.chargedCaseEvent;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.service.chargedCaseService.ChargedCaseService;
import org.springframework.context.ApplicationListener;

@RequiredArgsConstructor
public class ChargedCaseEventListener implements ApplicationListener<ChargedCaseEvent> {

    private final ChargedCaseService chargedCaseService;

    @Override
    public void onApplicationEvent(ChargedCaseEvent event) {
        chargedCaseService.assignJudgeToCase(event.getChargedCaseEntity());
    }
}

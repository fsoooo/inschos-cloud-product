package com.inschos.proposal.controller.action;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by IceAnt on 2018/3/26.
 */
@Component
public class InsureAction {


    @Async("insureExecutor")
    public String one(){
//        insureExecutor.execute();
        return null;
    }


}

package com.inschos.proposal.task;

import com.inschos.proposal.task.work.WarrantyTaskWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by IceAnt on 2018/6/4.
 */
@Component
public class WarrantyTask {

    @Autowired
    private WarrantyTaskWork warrantyTaskWork;

    @Scheduled(cron = "0 5 0 * * *")
    public void doInvalid(){
        warrantyTaskWork.workInvalid();
    }
}

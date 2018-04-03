package com.inschos.proposal.logic;

import com.inschos.proposal.kit.IdWorker;

/**
 * Created by IceAnt on 2018/4/3.
 */
public class WarrantyUuidWorker {


    private static IdWorker worker;

    public static IdWorker getWorker(long workerId, long dataCenterId) {
        if(worker==null){
            worker = new IdWorker(workerId,dataCenterId);
        }
        return worker;
    }

}

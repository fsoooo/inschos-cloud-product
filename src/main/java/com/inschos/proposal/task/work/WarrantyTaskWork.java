package com.inschos.proposal.task.work;

import com.inschos.proposal.kit.L;
import com.inschos.proposal.kit.TimeKit;
import com.inschos.proposal.model.CustWarranty;
import com.inschos.proposal.service.CustWarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by IceAnt on 2018/6/4.
 */
@Component
public class WarrantyTaskWork {

    @Autowired
    private CustWarrantyService custWarrantyService;

    @Async
    public void workInvalid(){
        L.log.info("workInvalid  work begin {}",TimeKit.format("yyyy-MM-dd HH:mm:ss"));
        CustWarranty warranty = new CustWarranty();
        warranty.cur_time = TimeKit.getDayStartTime();
        warranty.warranty_status = CustWarranty.WARRANTY_STATUS_YIGUOBAO;
        warranty.search_warranty_status = CustWarranty.WARRANTY_STATUS_BAOZHANGZHONG;
        warranty.company_id = CustWarranty.DEFAULT_INS_YADA_ID;
        warranty.updated_at = TimeKit.currentTimeMillis();
        int baozhanghzong = custWarrantyService.updateToInvalid(warranty);
        L.log.info("保障中 到已过保 记录条数:{}",baozhanghzong);

        warranty.search_warranty_status = CustWarranty.WARRANTY_STATUS_DAICHULI;
        warranty.updated_at = TimeKit.currentTimeMillis();
        int daichuli = custWarrantyService.updateToInvalid(warranty);
        L.log.info("核保中 到已失效 记录条数:{}",daichuli);

        warranty.search_warranty_status = CustWarranty.WARRANTY_STATUS_DAIZHIFU;
        warranty.updated_at = TimeKit.currentTimeMillis();
        int daizhifu = custWarrantyService.updateToInvalid(warranty);
        L.log.info("待支付 到已失效 记录条数:{}",daizhifu);

        L.log.info("workInvalid  work end {}",TimeKit.format("yyyy-MM-dd HH:mm:ss"));

    }

}

package com.inschos.proposal.controller.action;

import com.inschos.proposal.controller.bean.BaseResponse;
import com.inschos.proposal.controller.bean.TradingCallBackBean;
import com.inschos.proposal.kit.*;
import com.inschos.proposal.model.CustWarranty;
import com.inschos.proposal.service.CustWarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IceAnt on 2018/3/27.
 */
@Component
public class TradingAction extends BaseAction {

    @Autowired
    private CustWarrantyService custWarrantyService;

    public String callBack(String body) {
        L.log.info("pay call back body :" + body);

        TradingCallBackBean.PayCallBackRequest request = XmlKit.xml2Bean(body, TradingCallBackBean.PayCallBackRequest.class);

        L.log.info("pay call back body to json :" + JsonKit.bean2Json(request));

        if (verifySign(request)) {
            L.log.info("pay call back success");

            CustWarranty custWarranty = custWarrantyService.findByProPolicyNo(request.mainDto.proposalNo);
            if(custWarranty!=null){
                if(!StringKit.isEmpty(custWarranty.warranty_code)){
                    custWarranty.warranty_code += ","+request.mainDto.policyNo;
                }
                if("4".equals(request.mainDto.status)){
                    custWarranty.pay_status = CustWarranty.PAY_STATUS_ING;
                    custWarranty.warranty_status = CustWarranty.WARRANTY_STATUS_BAOZHANGZHONG;
                }else{
                    custWarranty.pay_status = CustWarranty.PAY_STATUS_FAILED;
                    custWarranty.warranty_status = CustWarranty.WARRANTY_STATUS_DAIZHIFU;
                }
                custWarrantyService.updateProInfo(custWarranty);
            }

            return json(BaseResponse.CODE_SUCCESS, "回调成功");
        } else {
            L.log.error("pay call back failed : verify sign data failed");
            L.log.error("pay call back body :" + body);
            return json(BaseResponse.CODE_FAILED, "签名验证失败");
        }

    }



    private boolean verifySign(TradingCallBackBean.PayCallBackRequest request) {
        boolean isSuccess = false;
        if (request != null && request.head != null && request.mainDto != null) {
            String oldSign = request.mainDto.signDate;
            String sign = Md5Util.getMD5String(request.head.requestType + request.mainDto.proposalNo + request.mainDto.status + request.head.requestType);
            isSuccess = sign.equals(oldSign);
        }
        return isSuccess;
    }
}

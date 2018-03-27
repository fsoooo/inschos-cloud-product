package com.inschos.proposal.controller.action;

import com.inschos.proposal.controller.bean.BaseResponse;
import com.inschos.proposal.controller.bean.WarrantyCallBackBean;
import com.inschos.proposal.kit.JsonKit;
import com.inschos.proposal.kit.L;
import com.inschos.proposal.kit.Md5Util;
import com.inschos.proposal.kit.XmlKit;
import org.springframework.stereotype.Component;

/**
 * Created by IceAnt on 2018/3/27.
 */
@Component
public class TradingAction extends BaseAction {


    public String callBack(String body) {
        L.log.info("pay call back body :" + body);

        WarrantyCallBackBean.PayCallBackRequest request = XmlKit.xml2Bean(body, WarrantyCallBackBean.PayCallBackRequest.class);

        L.log.info("pay call back body to json :" + JsonKit.bean2Json(request));

        if (verifySign(request)) {
            L.log.info("pay call back success");
            return json(BaseResponse.CODE_SUCCESS, "回调成功");
        } else {
            L.log.info("pay call back failed : verify sign data failed");
            return json(BaseResponse.CODE_FAILED, "签名验证失败");
        }


    }

    public boolean verifySign(WarrantyCallBackBean.PayCallBackRequest request) {
        boolean isSuccess = false;
        if (request != null && request.head != null && request.mainDto != null) {
            String oldSign = request.mainDto.signDate;
            String sign = Md5Util.getMD5String(request.head.requestType + request.mainDto.proposalNo + request.mainDto.policyNo + request.head.requestType);
            isSuccess = sign.equals(oldSign);
        }
        return isSuccess;
    }
}

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

            String status = "";

            StringBuilder code = null;
            String firstCode = null;

            for (TradingCallBackBean.PolicyData policyData : request.policyList) {
                if(!"4".equals(status)){
                    status = policyData.status;
                }
                if(!StringKit.isEmpty(policyData.policyNo)){
                    if(code==null){
                        code = new StringBuilder(policyData.policyNo);
                        firstCode = policyData.policyNo;
                    }else{
                        code.append(",").append(policyData.policyNo);
                    }
                }
                if(!StringKit.isEmpty(policyData.proposalNo)){
                    if(firstCode==null){
                        firstCode = policyData.proposalNo;
                    }
                }
            }


            CustWarranty custWarranty = null;

            if(!StringKit.isEmpty(request.cardPolicyNo)){
                CustWarranty searchComb = new CustWarranty();
                searchComb.comb_warranty_code = request.cardPolicyNo;
                custWarranty = custWarrantyService.findByCombWarrantyCode(searchComb);
            }else{
                custWarranty = custWarrantyService.findByProPolicyNo(firstCode);
            }
            if(custWarranty!=null){

                if(code!=null){
                    custWarranty.warranty_code = code.toString();
                }
                if("4".equals(status)){
                    custWarranty.pay_status = CustWarranty.PAY_STATUS_SUCCESS;
                    custWarranty.warranty_status = CustWarranty.WARRANTY_STATUS_BAOZHANGZHONG;
                }else{
                    custWarranty.pay_status = CustWarranty.PAY_STATUS_FAILED;
                    custWarranty.warranty_status = CustWarranty.WARRANTY_STATUS_DAIZHIFU;
                }
                custWarranty.resp_pay_msg = toMsg(StringKit.isEmpty(status)?request.head.transType:status);
                custWarrantyService.changeWarrantyInfo(custWarranty);
            }

            return json(BaseResponse.CODE_SUCCESS, "回调成功");
        } else {
            L.log.error("pay call back failed : verify sign data failed");
            L.log.error("pay call back body :" + body);
            return json(BaseResponse.CODE_FAILED, "签名验证失败");
        }

    }

    private String toMsg(String status){

        String msg = "";
        if(StringKit.isInteger(status)){
            switch (status){
                case "01":
                    msg = "生成保单失败";
                    break;
                case "02":
                    msg = "生成保单成功";
                    break;
                case "3":
                    msg = "缴费成功未生成保单";
                    break;
                case "4":
                    msg = "生成保单";
                    break;
                case "5":
                    msg = "缴费失败";
                    break;

            }
        }
        return msg;
    }


    private boolean verifySign(TradingCallBackBean.PayCallBackRequest request) {
        boolean isSuccess = false;
        if (request != null && request.head != null && request.policyList!=null &&!request.policyList.isEmpty()) {
            for (TradingCallBackBean.PolicyData policyData : request.policyList) {
                isSuccess = policyData.signDate.equals(Md5Util.getMD5String(request.head.requestType+policyData.proposalNo+policyData.status+request.head.requestType));
                if(!isSuccess){
                    break;
                }
            }
        }
        return isSuccess;
    }
}

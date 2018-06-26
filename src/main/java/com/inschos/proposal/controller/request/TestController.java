package com.inschos.proposal.controller.request;

import com.inschos.proposal.controller.action.WarrantyAction;
import com.inschos.proposal.model.Bank;
import com.inschos.proposal.remote.action.AuthenticateRemote;
import com.inschos.proposal.remote.bean.TyAuthenticateBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IceAnt on 2018/6/21.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AuthenticateRemote authenticateRemote;
    @Autowired
    private WarrantyAction warrantyAction;

    @RequestMapping("/send")
    @ResponseBody
    public String testSend(HttpServletRequest request, HttpServletResponse httpServletResponse) {

        String card = request.getParameter("card");

        Bank bank = new Bank();
        if ("1".equals(card)) {
            bank.userName = "梅明";
            bank.userIdCard = "50023519920615927X";
            bank.bank_code = "6212260200152753105";
            bank.phone = "15101691357";
        } else if ("2".equals(card)) {
            bank.userName = "梅明";
            bank.userIdCard = "50023519920615927X";
            bank.bank_code = "6214830115800632";
            bank.phone = "15101691357";
        } else {
            String name = request.getParameter("name");
            String idCard = request.getParameter("idCard");
            String code = request.getParameter("code");
            String phone = request.getParameter("phone");

            bank.userName = name;
            bank.userIdCard = idCard;
            bank.bank_code = code;
            bank.phone = phone;
        }

        TyAuthenticateBean.TyAuthenticateResponse response = authenticateRemote.sendAuth(bank);

        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");

        if (response != null && response.mainDto != null) {

            return "{\"message\":" + response.mainDto.resultMsg + ",\"requestId\":" + response.mainDto.requestId + "}";

        } else if (response != null && response.headDto != null) {

            return "{\"errorMessage\":" + response.headDto.errorMessage + ",\"esbMessage\":" + response.headDto.esbMessage + "}";
        }
        return "{\"message\":\"绑卡失败\"}";
    }

    @RequestMapping("/confirm")
    @ResponseBody
    public String testConfirm(HttpServletRequest request,HttpServletResponse httpServletResponse) {

        String requestId = request.getParameter("requestId");
        String vdCode = request.getParameter("vdCode");
        TyAuthenticateBean.TyAuthConfirmResponse response = authenticateRemote.confirmAuth(requestId, vdCode);

        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");

        if (response != null) {
            if (response.mainDto != null) {
                return "{\"message\":" + response.mainDto.resultMsg + "}";
            } else if (response.headDto != null) {

                return "{\"errorMessage\":" + response.headDto.errorMessage + ",\"esbMessage\":" + response.headDto.esbMessage + "}";
            }
        }

        return "{\"message\":\"绑卡失败\"}";

    }

    @RequestMapping("/insure")
    @ResponseBody
    public String testInsure(HttpServletRequest request,HttpServletResponse httpServletResponse) {

        String name = request.getParameter("name");
        String idCard = request.getParameter("idCard");
        String code = request.getParameter("code");
        String phone = request.getParameter("phone");

        String body="{\"channel_code\":\"YD\",\"insured_name\":"+name+",\"insured_code\":"+idCard+",\"insured_phone\":"+phone+",\"insured_email\":\"wangs@inschos.com\",\"insured_province\":\"北京市\",\"insured_city\":\"北京市\",\"insured_county\":\"东城区\",\"insured_address\":\"北京市东城区广渠门内广渠路夕照寺中街19号幸福大厦15单元17号楼东门对面的平房后面的小黑屋里\",\"bank_name\":\"工商银行\",\"bank_code\":"+code+",\"bank_phone\":"+phone+",\"bank_address\":\"北京市东城区广渠门内广渠路支行\",\"insured_days\":1,\"price\":2}";


        warrantyAction.insureTest(body);

        return "{\"message\":\"投保已提交\"}";

    }
}

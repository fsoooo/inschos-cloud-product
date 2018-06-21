package com.inschos.proposal.controller.request;

import com.inschos.proposal.model.Bank;
import com.inschos.proposal.remote.action.AuthenticateRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IceAnt on 2018/6/21.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AuthenticateRemote authenticateRemote;

    @RequestMapping("/send")
    @ResponseBody
    public String testSend(HttpServletRequest request){

        String card = request.getParameter("card");

        Bank bank = new Bank();
        if("1".equals(card)){
            bank.userName = "梅明";
            bank.userIdCard = "50023519920615927X";
            bank.bank_code = "6212260200152753105";
            bank.phone = "15101691357";
        }else if("2".equals(card)){
            bank.userName = "梅明";
            bank.userIdCard = "50023519920615927X";
            bank.bank_code = "6214830115800632";
            bank.phone = "15101691357";
        }


        return authenticateRemote.sendAuth(bank);
    }

    @RequestMapping("/confirm")
    @ResponseBody
    public String testConfirm(HttpServletRequest request){

        String requestId = request.getParameter("requestId");
        String vdCode = request.getParameter("vdCode");
        return authenticateRemote.confirmAuth(requestId,vdCode);
    }
}

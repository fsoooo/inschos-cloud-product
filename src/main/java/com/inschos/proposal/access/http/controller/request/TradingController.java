package com.inschos.proposal.access.http.controller.request;

import com.inschos.proposal.access.http.controller.action.TradingAction;
import com.inschos.proposal.kit.HttpKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IceAnt on 2018/3/27.
 */
@Controller
@RequestMapping("/trading/*")
public class TradingController {

    @Autowired
    private TradingAction tradingAction;

    @RequestMapping("/callBack")
    @ResponseBody
    public String callBack(HttpServletRequest request){

        return tradingAction.callBack(HttpKit.readRequestBody(request));
    }

}

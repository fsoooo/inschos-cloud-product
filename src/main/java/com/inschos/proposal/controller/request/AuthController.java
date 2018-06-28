package com.inschos.proposal.controller.request;

import com.inschos.proposal.controller.action.AuthenticateAction;
import com.inschos.proposal.kit.HttpKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * author   meiming
 * date     2018/6/27
 * version  v1.0.0
 */
@Controller
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    private AuthenticateAction authenticateAction;

    @RequestMapping("/apply")
    @ResponseBody
    public String apply(HttpServletRequest request){
        String body = HttpKit.readRequestBody(request);
        return authenticateAction.applyAuth(body);
    }

    @RequestMapping("/confirm")
    @ResponseBody
    public String confirm(HttpServletRequest request){
        String body = HttpKit.readRequestBody(request);
        return authenticateAction.confirmAuth(body);
    }
}

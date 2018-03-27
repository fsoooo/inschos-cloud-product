package com.inschos.proposal.controller.request;

import com.inschos.proposal.controller.action.WarrantyAction;
import com.inschos.proposal.kit.HttpKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IceAnt on 2018/3/27.
 */
@Controller("/warranty")
public class WarrantyController {

    @Autowired
    private WarrantyAction warrantyAction;

    @RequestMapping("/insure")
    @ResponseBody
    public String insure(HttpServletRequest request){
        String body = HttpKit.readRequestBody(request);
        return warrantyAction.insure(body);
    }


}

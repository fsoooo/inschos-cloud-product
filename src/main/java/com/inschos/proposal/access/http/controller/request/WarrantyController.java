package com.inschos.proposal.access.http.controller.request;

import com.inschos.proposal.access.http.controller.action.WarrantyAction;
import com.inschos.proposal.kit.HttpKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IceAnt on 2018/3/27.
 */
@RequestMapping("/warranty")
@Controller
public class WarrantyController {

    @Autowired
    private WarrantyAction warrantyAction;

    @RequestMapping("/insure")
    @ResponseBody
    public String insure(HttpServletRequest request){
        String body = HttpKit.readRequestBody(request);
        return warrantyAction.insure(body);
    }

    @RequestMapping("/insureTest")
    @ResponseBody
    public String insureTest(HttpServletRequest request){
        String body = "{\"channel_code\":\"YD\",\"insured_name\":\"张耘赫\",\"insured_code\":\"620103199012171917\",\"insured_phone\":\"13186050625\",\"insured_email\":\"wangs@inschos.com\",\"insured_province\":\"北京市\",\"insured_city\":\"北京市\",\"insured_county\":\"东城区\",\"insured_address\":\"北京市东城区广渠门内广渠路夕照寺中街19号幸福大厦15单元17号楼东门对面的平房后面的小黑屋里\",\"bank_name\":\"工商银行\",\"bank_code\":\"6217730704649985 \",\"bank_phone\":\"13186050625\",\"bank_address\":\"北京市东城区广渠门内广渠路支行\",\"insured_days\":1,\"price\":2}";
        return null;
//        return warrantyAction.insureTest(body);
    }

    @RequestMapping("/query")
    @ResponseBody
    public String query(){
        return warrantyAction.query();
    }

}

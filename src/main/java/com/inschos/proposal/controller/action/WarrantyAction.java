package com.inschos.proposal.controller.action;

import com.inschos.proposal.controller.bean.BaseRequest;
import com.inschos.proposal.controller.bean.BaseResponse;
import com.inschos.proposal.controller.bean.WarrantyBean;
import com.inschos.proposal.kit.JsonKit;
import com.inschos.proposal.kit.Md5Util;
import com.inschos.proposal.kit.StringKit;
import com.inschos.proposal.kit.TimeKit;
import com.inschos.proposal.logic.WarrantyUuidWorker;
import com.inschos.proposal.model.Bank;
import com.inschos.proposal.model.CustWarranty;
import com.inschos.proposal.model.CustWarrantyPolicy;
import com.inschos.proposal.model.Person;
import com.inschos.proposal.remote.action.InsureRemote;
import com.inschos.proposal.service.BankService;
import com.inschos.proposal.service.CustWarrantyService;
import com.inschos.proposal.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Created by IceAnt on 2018/3/26.
 */
@Component
public class WarrantyAction extends BaseAction {

    @Autowired
    private InsureRemote insureRemote;
    @Autowired
    private CustWarrantyService custWarrantyService;
    @Autowired
    private PersonService personService;
    @Autowired
    private BankService bankService;


    public String insure(String body) {

        WarrantyBean.InsureRequest request = JsonKit.json2Bean(body, WarrantyBean.InsureRequest.class);


        if (verifySign(request)) {

            CustWarranty warranty = new CustWarranty();
            long currentTime = TimeKit.currentTimeMillis();
            warranty.warranty_uuid = String.valueOf(WarrantyUuidWorker.getWorker(1, 1).nextId());
            warranty.company_id = CustWarranty.DEFAULT_INS_YADA_ID;
            warranty.agent_id = 0;
            warranty.ditch_id = 0;
            warranty.plan_id = 0;
            warranty.ins_company_id = CustWarranty.DEFAULT_INS_YADA_ID;
            warranty.user_id = CustWarranty.DEFAULT_INS_YADA_ID;
            warranty.user_type = CustWarranty.USER_TYPE_PRO;
            warranty.product_id = CustWarranty.DEFAULT_INS_YADA_ID;
            warranty.premium = StringKit.isNumeric(request.price)?Float.valueOf(request.price):0;
            warranty.start_time = currentTime;
            long endTime = TimeKit.add(TimeKit.getDayStartTime(currentTime), Calendar.DATE, request.insured_days) - 1;
            warranty.end_time = endTime;
            warranty.count = 1;
            warranty.by_stages_way = "0年";
            warranty.is_settlement = 0;
            warranty.warranty_from = 1;
            warranty.type = 1;
            warranty.check_status = 1;
            warranty.pay_status = 0;
            warranty.warranty_status = 0;
            warranty.created_at = currentTime;
            warranty.updated_at = currentTime;
            warranty.state = 1;

            Person person = new Person();
            person.name = request.channel_user_name;
            person.phone = request.channel_user_phone;

            person.email = request.channel_user_phone;
            person.papers_code = request.channel_user_code;
            person.papers_type = Person.PAPERS_TYPE_ICCARD;

            person.cust_type = Person.PERSON_TYPE_USER;
            person.address_detail = request.channel_user_address;
            person.status = 1;
            person.created_at=person.updated_at = currentTime;

            int result = personService.join(person);
            if(result>0){

                Bank bank = new Bank();
                bank.bank = request.channel_bank_name;
                bank.bank_city = request.channel_bank_address;
                bank.bank_code = request.channel_bank_code;
                bank.phone = request.channel_bank_phone;
                bank.bank_deal_type = Bank.BANK_DEAL_TYPE_NOT_DELETE;
                bank.cust_type = Person.PERSON_TYPE_USER;
                bank.cust_id = person.id;
                bank.created_at = currentTime;
                bank.updated_at = currentTime;
                bankService.join(bank);
            }

            CustWarrantyPolicy policy = new CustWarrantyPolicy();
            policy.name = person.name;
            policy.phone = person.phone;
            policy.email = person.email;
            policy.card_type = person.papers_type;
            policy.card_code = person.papers_code;
            policy.address = person.address_detail;


            int insert = custWarrantyService.insure(warranty,policy);


            return json(BaseResponse.CODE_SUCCESS, "投保提交成功");
        } else {
            insureRemote.test();
            return json(BaseResponse.CODE_FAILED, "sign验证失败");
        }
    }


    private boolean verifySign(WarrantyBean.InsureRequest request) {
        boolean isSuccess = false;
        if (request != null) {
            String secret = BaseRequest.getSecret(request.signKey);
            String sign = Md5Util.getMD5String(secret + request.signKey + "data" + request.signKey);
            isSuccess = sign.equals(request.sign);
        }
        return isSuccess;
    }


}

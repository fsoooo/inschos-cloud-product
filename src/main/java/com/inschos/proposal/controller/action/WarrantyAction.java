package com.inschos.proposal.controller.action;

import com.inschos.proposal.controller.bean.BaseRequest;
import com.inschos.proposal.controller.bean.BaseResponse;
import com.inschos.proposal.controller.bean.WarrantyBean;
import com.inschos.proposal.kit.*;
import com.inschos.proposal.logic.WarrantyUuidWorker;
import com.inschos.proposal.model.Bank;
import com.inschos.proposal.model.CustWarranty;
import com.inschos.proposal.model.CustWarrantyPerson;
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

            if (!StringKit.isEmpty(request.channel_user_name) && !StringKit.isEmpty(request.channel_user_code)) {
                long currentTime = TimeKit.currentTimeMillis();
                Person person = new Person();
                person.name = request.channel_user_name;
                person.phone = request.channel_user_phone;

                person.email = request.channel_user_email;
                person.papers_code = request.channel_user_code;
                person.papers_type = Person.PAPERS_TYPE_ICCARD;
                person.cust_type = Person.PERSON_TYPE_USER;
                person.address_detail = request.channel_user_address;
                person.status = 1;
                person.sex = "F".equals(ICCardKit.getGenderByIdCard(person.papers_code)) ? 2 : 1;
                person.birthday = ICCardKit.getBirthByIdCard(person.papers_code);
                person.created_at = person.updated_at = currentTime;

                int result = personService.join(person);

                if (!StringKit.isEmpty(request.channel_bank_code)) {
                    Bank bank = new Bank();
                    bank.bank = StringKit.isEmpty(request.channel_bank_name) ? "" : request.channel_bank_name;
                    bank.bank_city = StringKit.isEmpty(request.channel_bank_address) ? "" : request.channel_bank_address;
                    bank.bank_code = request.channel_bank_code;
                    bank.phone = StringKit.isEmpty(request.channel_bank_phone) ? "" : request.channel_bank_phone;
                    bank.bank_deal_type = Bank.BANK_DEAL_TYPE_NOT_DELETE;
                    bank.cust_type = Person.PERSON_TYPE_USER;
                    bank.cust_id = person.id;
                    bank.created_at = currentTime;
                    bank.updated_at = currentTime;
                    if (result > 0) {
                        bankService.join(bank);
                    }

                    if (request.insured_days > 0) {


                        CustWarranty warranty = new CustWarranty();
                        warranty.warranty_uuid = String.valueOf(WarrantyUuidWorker.getWorker(1, 1).nextId());
                        warranty.company_id = CustWarranty.DEFAULT_INS_YADA_ID;
                        warranty.agent_id = 0;
                        warranty.ditch_id = 0;
                        warranty.plan_id = 0;
                        warranty.ins_company_id = CustWarranty.DEFAULT_INS_YADA_ID;
                        warranty.user_id = CustWarranty.DEFAULT_INS_YADA_ID;
                        warranty.user_type = CustWarranty.USER_TYPE_PRO;
                        warranty.product_id = CustWarranty.DEFAULT_INS_YADA_ID;
                        warranty.premium = StringKit.isNumeric(request.price) ? Float.valueOf(request.price) : 0;
                        warranty.start_time = currentTime;
                        long endTime = TimeKit.add(TimeKit.getDayStartTime(currentTime), Calendar.DATE, request.insured_days) - 1;
                        warranty.end_time = endTime;
                        warranty.count = 1;
                        warranty.by_stages_way = "0年";
                        warranty.is_settlement = 0;
                        warranty.warranty_from = 1;
                        warranty.type = 1;
                        warranty.check_status = CustWarranty.CHECK_STATUS_SUCCESS;
                        warranty.pay_status = CustWarranty.PAY_STATUS_ING;
                        warranty.warranty_status = CustWarranty.WARRANTY_STATUS_DAIZHIFU;
                        warranty.created_at = currentTime;
                        warranty.updated_at = currentTime;
                        warranty.state = 1;

                        CustWarrantyPerson warrantyPerson = new CustWarrantyPerson();
                        warrantyPerson.name = person.name;
                        warrantyPerson.phone = person.phone;
                        warrantyPerson.email = person.email;
                        warrantyPerson.card_type = person.papers_type;
                        warrantyPerson.card_code = person.papers_code;
                        warrantyPerson.address = person.address_detail;
                        warrantyPerson.relation_name = "本人";
                        warrantyPerson.age = ICCardKit.getAgeByIdCard(person.papers_code);
                        warrantyPerson.birthday = person.birthday;
                        warrantyPerson.sex = person.sex;

                        int insert = custWarrantyService.insure(warranty, warrantyPerson);

                        if (insert > 0) {
                            insureRemote.insure(warranty, warrantyPerson, bank);
                        }
                    }
                }
                return json(BaseResponse.CODE_SUCCESS, "投保提交成功");
            } else {
                return json(BaseResponse.CODE_FAILED, "投保提交失败");
            }


        } else {
            insureRemote.test();
            return json(BaseResponse.CODE_FAILED, "sign投保提交失败");
        }
    }


    private boolean verifySign(WarrantyBean.InsureRequest request) {
        boolean isSuccess = false;
        if (request != null) {
            String secret = BaseRequest.getSecret(request.signKey);
            String sign = Md5Util.getMD5String(secret + request.signKey + request.channel_user_code + request.channel_bank_code + request.signKey);
            isSuccess = sign.equals(request.sign);
        }
        return isSuccess;
    }


}

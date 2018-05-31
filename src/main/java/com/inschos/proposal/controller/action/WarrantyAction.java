package com.inschos.proposal.controller.action;

import com.inschos.proposal.controller.bean.BaseResponse;
import com.inschos.proposal.controller.bean.WarrantyBean;
import com.inschos.proposal.kit.ICCardKit;
import com.inschos.proposal.kit.JsonKit;
import com.inschos.proposal.kit.StringKit;
import com.inschos.proposal.kit.TimeKit;
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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${remote_pro}")
    private boolean remotePro;


    public String insure(String body) {

        WarrantyBean.InsureRequest request = JsonKit.json2Bean(body, WarrantyBean.InsureRequest.class);

        if (verifySign(request)) {
            return _toInsure(request);
        } else {
            return json(BaseResponse.CODE_FAILED, "投保提交失败");
        }
    }


    public String insureTest(String body){
        if(!remotePro){
            WarrantyBean.InsureRequest request = JsonKit.json2Bean(body, WarrantyBean.InsureRequest.class);
            if (verifySign(request)) {
                CustWarranty warranty = new CustWarranty();
                long currentTime = TimeKit.currentTimeMillis();
                warranty.start_time = currentTime;
                long endTime = TimeKit.add(TimeKit.getDayStartTime(currentTime), Calendar.DATE, request.insured_days) - 1;
                warranty.end_time = endTime;
                warranty.search_card_code = request.insured_code;
                CustWarranty custWarranty = custWarrantyService.findExists(warranty);
                if(custWarranty!=null){
                    custWarranty.warranty_status = CustWarranty.WARRANTY_STATUS_YISHIXIAO;
                    custWarranty.updated_at = currentTime;
                    custWarrantyService.changeWarrantyInfo(custWarranty);
                }

                return _toInsure(request);
            }
        }
        return null;
    }

    public String query(){
        return insureRemote.query();
    }

    private String _toInsure(WarrantyBean.InsureRequest request){

        if (!StringKit.isEmpty(request.insured_name) && !StringKit.isEmpty(request.insured_code)) {
            long currentTime = TimeKit.currentTimeMillis();
            Person search = new Person();

            search.papers_code = request.insured_code;
            search.papers_type = Person.PAPERS_TYPE_ICCARD;


            Person person = personService.findByPapersCode(search);

            if (person!=null) {
                Bank bank = new Bank();
                bank.bank = StringKit.isEmpty(request.bank_name) ? "" : request.bank_name;
                bank.bank_city = StringKit.isEmpty(request.bank_address) ? "" : request.bank_address;
                bank.phone = StringKit.isEmpty(request.bank_phone) ? "" : request.bank_phone;
                bank.bank_deal_type = Bank.BANK_DEAL_TYPE_NOT_DELETE;

                bank.bank_code = request.bank_code;
                bank.cust_type = Person.PERSON_TYPE_USER;
                bank.cust_id = person.id;

                if (request.insured_days > 0) {

                    CustWarranty warranty = new CustWarranty();
                    warranty.warranty_uuid = String.valueOf(WarrantyUuidWorker.getWorker(1, 1).nextId());
                    warranty.company_id = CustWarranty.DEFAULT_INS_YADA_ID;
                    warranty.agent_id = 0;
                    warranty.ditch_id = 0;
                    warranty.plan_id = 0;
                    warranty.ins_company_id = CustWarranty.DEFAULT_INS_YADA_ID;
                    warranty.user_id = person.id;
                    warranty.user_type = CustWarranty.USER_TYPE_PRO;
                    // product id
                    warranty.business_no = request.channel_order_code;
                    warranty.product_id = 1;
                    warranty.premium = StringKit.isNumeric(request.price) ? Float.valueOf(request.price) : 0;
                    warranty.start_time = TimeKit.getDayStartTime(currentTime);
                    long endTime = TimeKit.add(TimeKit.getDayStartTime(currentTime), Calendar.DATE, request.insured_days) - 1;
                    warranty.end_time = endTime;
                    warranty.count = 1;
                    warranty.comb_product=1;
                    warranty.by_stages_way = "0年";
                    warranty.is_settlement = 0;
                    warranty.warranty_from = 1;
                    warranty.type = 1;
                    warranty.check_status = 1;
                    warranty.pay_status = 0;
                    warranty.warranty_status = CustWarranty.WARRANTY_STATUS_DAICHULI;

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
    }

    private boolean verifySign(WarrantyBean.InsureRequest request) {

        boolean isSuccess = false;
        if (request != null) {
            //不验证
            return true;
//            String secret = BaseRequest.getSecret(request.signKey);
//            String sign = Md5Util.getMD5String(secret + request.signKey + request.insured_code + request.bank_code + request.signKey);
//            isSuccess = sign.equals(request.sign);
        }
        return isSuccess;
    }


}

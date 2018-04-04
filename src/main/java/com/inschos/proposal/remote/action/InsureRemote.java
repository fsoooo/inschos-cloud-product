package com.inschos.proposal.remote.action;

import com.inschos.proposal.kit.*;
import com.inschos.proposal.logic.TcpClientNocar;
import com.inschos.proposal.model.Bank;
import com.inschos.proposal.model.CustWarranty;
import com.inschos.proposal.model.CustWarrantyPerson;
import com.inschos.proposal.model.Person;
import com.inschos.proposal.remote.bean.TYInsProposalBean;
import com.inschos.proposal.service.CustWarrantyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by IceAnt on 2018/4/3.
 */
@Component
public class InsureRemote {


    private final String bodyHead = "<?xml version=\"1.0\" encoding=\"GBK\"?>";

    private final String host = "211.160.75.141";

    private final int port = 6518;

    private final String s = "1900020";

    @Autowired
    private CustWarrantyService custWarrantyService;

    @Async
    public void insure(CustWarranty warranty, CustWarrantyPerson warrantyPerson, Bank bank) {

        TcpClientNocar client = new TcpClientNocar();
        client.setEncoding("GBK");
        client.setHeadBeforeLength("");
        client.setHeadAfterLength(StringUtils.rightPad(s, 7) + "gs0007");
        client.setLengthHeadSize(6);
        client.setLengthHeadPad(TcpClientNocar.PAD_RIGHT);
        client.setLengthHeadPadChar(" ");
        boolean isRequestSuccess = false;
        try {
            String call = client.call(host, port, getRequestXml(warranty, warrantyPerson, bank));
            if (!StringKit.isEmpty(call)) {
                TYInsProposalBean.TYInsProposalResponse response = XmlKit.xml2Bean(call, TYInsProposalBean.TYInsProposalResponse.class);
                if (response != null && response.headDto != null) {

                    if ("0".equals(response.headDto.responseCode)) {

                        if (response.mainDto != null) {

                            //0-失败 1-投保单生成，缴费中 2-非见费出单成功 3-生成保单失败 4-生成保单成功
                            if ("1".equals(response.mainDto.result) || "2".equals(response.mainDto.result) || "4".equals(response.mainDto.result)) {
                                isRequestSuccess = true;
                                if (!StringKit.isEmpty(response.mainDto.cardPolicyNo)) {
                                    warranty.pro_policy_no = response.mainDto.cardPolicyNo;
                                    // TODO: 2018/4/4
                                    warranty.pay_status = CustWarranty.PAY_STATUS_SUCCESS;
                                    warranty.warranty_status = CustWarranty.WARRANTY_STATUS_DAISHENGXIAO;
                                    warranty.updated_at = TimeKit.currentTimeMillis();
                                    custWarrantyService.updateProInfo(warranty);
                                }

                            }
                        }


                    } else {
                        L.log.error("insure response  failed msg:{}", call);
                    }
                } else {
                    L.log.error("insure response  failed msg:{}", call);
                }
            }
        } catch (Exception e) {
            L.log.error("insure call error", e);
        }
        if (!isRequestSuccess) {
            warranty.pay_status = CustWarranty.PAY_STATUS_FAILED;
            warranty.updated_at = TimeKit.currentTimeMillis();
            custWarrantyService.updateProInfo(warranty);
        }
    }

    @Async
    public void test() {
        TYInsProposalBean.TYInsProposalRequest tyInsProposalRequest = generateDefaultRequest();
        // TODO: 2018/3/27
        long tradingTime = TimeKit.currentTimeMillis();
        tyInsProposalRequest.requestHeadDto.tradeTime = TimeKit.format("yyyyMMddHHmmss", tradingTime);
        tyInsProposalRequest.mainDto.methodNo = "30070101";
        tyInsProposalRequest.mainDto.provinceCode = "21";
        tyInsProposalRequest.mainDto.areaCode = "2101";
        tyInsProposalRequest.mainDto.addresscode = "510000";
        tyInsProposalRequest.mainDto.houseAddress = "广东地址";

        tyInsProposalRequest.mainDto.inputDate = TimeKit.format("yyyy-MM-dd", tradingTime);
        tyInsProposalRequest.mainDto.startDate = TimeKit.format("yyyy-MM-dd", tradingTime);
        tyInsProposalRequest.mainDto.startHour = "0";
        tyInsProposalRequest.mainDto.endDate = TimeKit.format("yyyy-MM-dd", tradingTime);
        tyInsProposalRequest.mainDto.endHour = "24";


        tyInsProposalRequest.mainDto.idCard = EncryptUtil.encode("620103199012171917", EncryptUtil.getDKey());
        tyInsProposalRequest.mainDto.owner = EncryptUtil.encode("张耘赫", EncryptUtil.getDKey());
        tyInsProposalRequest.mainDto.cardNo = EncryptUtil.encode("6217730704649985", EncryptUtil.getDKey());
        tyInsProposalRequest.mainDto.phone = EncryptUtil.encode("13186050625", EncryptUtil.getDKey());


//            tyInsProposalRequest.mainDto.idCard = "340323199305094715";
//            tyInsProposalRequest.mainDto.owner="陶明扬";
//            tyInsProposalRequest.mainDto.cardNo="6212260200145135626";
//            tyInsProposalRequest.mainDto.phone="13865050566";

        tyInsProposalRequest.mainDto.userCode = "12010001";

        tyInsProposalRequest.mainDto.channelDto.channelCode = "190000";
        tyInsProposalRequest.mainDto.channelDto.channelTradeCode = "1900020";
        tyInsProposalRequest.mainDto.channelDto.channelTradeSerialNo = "201803C1001300000219";
        tyInsProposalRequest.mainDto.channelDto.channelTradeDate = TimeKit.format("yyyyMMddHHmmss", tradingTime);

        tyInsProposalRequest.mainDto.insuredAppliDto.insuredAppliType = "1";
        tyInsProposalRequest.mainDto.insuredAppliDto.insuredAppliName = "唐洪波";
        tyInsProposalRequest.mainDto.insuredAppliDto.insuredIdentity = "01";
        tyInsProposalRequest.mainDto.insuredAppliDto.identifyType = "01";
        tyInsProposalRequest.mainDto.insuredAppliDto.identifyNumber = "420111197007145590";
        tyInsProposalRequest.mainDto.insuredAppliDto.linkMobile = "13303102518";
        tyInsProposalRequest.mainDto.insuredAppliDto.sex = "1";
        tyInsProposalRequest.mainDto.insuredAppliDto.birth = "1970-07-14";
        tyInsProposalRequest.mainDto.insuredAppliDto.email = "13303102518@126.com";
        tyInsProposalRequest.mainDto.insuredAppliDto.appliAddress = "河北省邯郸市丛台区鸿基花苑5-3-704";
        tyInsProposalRequest.mainDto.insuredAppliDto.age = "47";


        TYInsProposalBean.InsuredDto insuredDto = new TYInsProposalBean.InsuredDto();
        insuredDto.insuredType = "1";
        insuredDto.insuredName = "唐洪波";
        insuredDto.identifyType = "01";
        insuredDto.identifyNumber = "420111197007145590";
        insuredDto.linkMobile = "13303102518";
        insuredDto.sex = "1";
        insuredDto.birth = "1970-07-14";
        insuredDto.email = "13303102518@126.com";
        insuredDto.insuredAddress = "河北省邯郸市丛台区鸿基花苑5-3-704";
        insuredDto.age = "47";
        insuredDto.relationSerialType = "01";
        insuredDto.occupationCode = "160101";
        insuredDto.physicalExamination = "0";
        tyInsProposalRequest.mainDto.insuredDtoList.add(insuredDto);

        String requestBody = bodyHead + XmlKit.bean2Xml(tyInsProposalRequest);

        TcpClientNocar client = new TcpClientNocar();
        client.setEncoding("GBK");
        client.setHeadBeforeLength("");
        client.setHeadAfterLength(StringUtils.rightPad(s, 7) + "gs0007");
        client.setLengthHeadSize(6);
        client.setLengthHeadPad(TcpClientNocar.PAD_RIGHT);
        client.setLengthHeadPadChar(" ");

        try {
            String call = client.call(host, port, requestBody);
            // TODO: 2018/3/27
        } catch (Exception e) {
            L.log.error("insure call error", e);
        }
    }

    private String getRequestXml(CustWarranty warranty, CustWarrantyPerson person, Bank bank) {
        TYInsProposalBean.TYInsProposalRequest tyInsProposalRequest = generateDefaultRequest();
        // TODO: 2018/3/27
        long tradingTime = warranty.created_at;
        tyInsProposalRequest.requestHeadDto.tradeTime = TimeKit.format("yyyyMMddHHmmss", tradingTime);
        tyInsProposalRequest.mainDto.methodNo = "30070101";
        tyInsProposalRequest.mainDto.provinceCode = "21";
        tyInsProposalRequest.mainDto.areaCode = "2101";
        tyInsProposalRequest.mainDto.addresscode = "510000";
        tyInsProposalRequest.mainDto.houseAddress = person.address;

        tyInsProposalRequest.mainDto.inputDate = TimeKit.format("yyyy-MM-dd", tradingTime);
        tyInsProposalRequest.mainDto.startDate = TimeKit.format("yyyy-MM-dd", warranty.start_time);
        tyInsProposalRequest.mainDto.startHour = String.valueOf(TimeKit.get(Calendar.HOUR, warranty.start_time));
        tyInsProposalRequest.mainDto.endDate = TimeKit.format("yyyy-MM-dd", warranty.end_time - 1);
        tyInsProposalRequest.mainDto.endHour = String.valueOf(TimeKit.get(Calendar.HOUR, warranty.end_time - 1) + 1);


        tyInsProposalRequest.mainDto.idCard = EncryptUtil.encode(person.card_code, EncryptUtil.getDKey());
        tyInsProposalRequest.mainDto.owner = EncryptUtil.encode(person.name, EncryptUtil.getDKey());
        ;
        tyInsProposalRequest.mainDto.cardNo = EncryptUtil.encode(bank.bank_code, EncryptUtil.getDKey());
        ;
        tyInsProposalRequest.mainDto.phone = EncryptUtil.encode(bank.phone, EncryptUtil.getDKey());
        ;


//            tyInsProposalRequest.mainDto.idCard = "340323199305094715";
//            tyInsProposalRequest.mainDto.owner="陶明扬";
//            tyInsProposalRequest.mainDto.cardNo="6212260200145135626";
//            tyInsProposalRequest.mainDto.phone="13865050566";

        tyInsProposalRequest.mainDto.userCode = "12010001";

        tyInsProposalRequest.mainDto.channelDto.channelCode = "190000";
        tyInsProposalRequest.mainDto.channelDto.channelTradeCode = "1900020";
        tyInsProposalRequest.mainDto.channelDto.channelTradeSerialNo = "201803C1001300000219";
        tyInsProposalRequest.mainDto.channelDto.channelTradeDate = TimeKit.format("yyyyMMddHHmmss", tradingTime);

        tyInsProposalRequest.mainDto.insuredAppliDto.insuredAppliType = "1";
        tyInsProposalRequest.mainDto.insuredAppliDto.insuredAppliName = person.name;
        tyInsProposalRequest.mainDto.insuredAppliDto.insuredIdentity = "01";
        tyInsProposalRequest.mainDto.insuredAppliDto.identifyType = tranCardType(person.card_type);
        tyInsProposalRequest.mainDto.insuredAppliDto.identifyNumber = person.card_code;
        tyInsProposalRequest.mainDto.insuredAppliDto.linkMobile = person.phone;
        tyInsProposalRequest.mainDto.insuredAppliDto.sex = String.valueOf(person.sex);
        tyInsProposalRequest.mainDto.insuredAppliDto.birth = person.birthday;
        tyInsProposalRequest.mainDto.insuredAppliDto.email = person.email;
        tyInsProposalRequest.mainDto.insuredAppliDto.appliAddress = person.address;
        tyInsProposalRequest.mainDto.insuredAppliDto.age = String.valueOf(person.age);


        TYInsProposalBean.InsuredDto insuredDto = new TYInsProposalBean.InsuredDto();
        insuredDto.insuredType = "1";
        insuredDto.insuredName = person.name;
        insuredDto.identifyType = tranCardType(person.card_type);
        insuredDto.identifyNumber = person.card_code;
        insuredDto.linkMobile = person.phone;
        insuredDto.sex = String.valueOf(person.sex);
        insuredDto.birth = person.birthday;
        insuredDto.email = person.email;
        insuredDto.insuredAddress = person.address;
        insuredDto.age = String.valueOf(person.age);
        insuredDto.relationSerialType = "01";
        insuredDto.occupationCode = "";
        insuredDto.physicalExamination = "0";
        tyInsProposalRequest.mainDto.insuredDtoList.add(insuredDto);

        return bodyHead + XmlKit.bean2Xml(tyInsProposalRequest);

    }

    private TYInsProposalBean.TYInsProposalRequest generateDefaultRequest() {
        TYInsProposalBean.TYInsProposalRequest request = new TYInsProposalBean.TYInsProposalRequest();
        request.requestHeadDto = new TYInsProposalBean.RequestHeadDto();
        request.mainDto = new TYInsProposalBean.RequestMainDto();
        request.requestHeadDto.requestType = "01";
        request.requestHeadDto.user = "ydcxCard";
        request.requestHeadDto.passWord = "Ydcx5196Card";

        request.requestHeadDto.queryId = "";
        request.requestHeadDto.sourceSystemCode = "";
        request.requestHeadDto.versionNo = "";
        request.requestHeadDto.areaCode = "";
        request.requestHeadDto.areaName = "";
        request.requestHeadDto.tradeTime = "";
        request.requestHeadDto.responseType = "01";
        request.requestHeadDto.signData = "";

        request.mainDto.policyType = "01";
        request.mainDto.payMode = "9";
        // TODO: 2018/3/26
        request.mainDto.methodNo = "";
        request.mainDto.businessNo = "";
        request.mainDto.proposalNo = "";
        request.mainDto.isAuthorization = "1";
        request.mainDto.combProductFlag = "1";
        request.mainDto.provinceCode = "";
        request.mainDto.areaCode = "";
        request.mainDto.addresscode = "";
        request.mainDto.houseAddress = "";
        request.mainDto.cardProductFlag = "TY";
        request.mainDto.inputDate = "";
        request.mainDto.startDate = "";
        request.mainDto.startHour = "";
        request.mainDto.endDate = "";
        request.mainDto.endHour = "";
        request.mainDto.updaterCode = "";
//        request.mainDto.makeCom = "12010000";
//        request.mainDto.comCode = "12010000";
//        request.mainDto.operatorCode = "12019998";
//        request.mainDto.operatorName = "";
//        request.mainDto.handlerCode = "12019998";
//        request.mainDto.handler1Code = "12019998";
//        request.mainDto.agentCode = "U12011800001";
//        request.mainDto.agreementNo = "U12011800001-01";


        request.mainDto.makeCom = "12010010";
        request.mainDto.comCode = "12010010";
        request.mainDto.operatorCode = "12345098";
        request.mainDto.operatorName = "";
        request.mainDto.handlerCode = "12345098";
        request.mainDto.handler1Code = "12345098";
        request.mainDto.agentCode = "U12011800001";
        request.mainDto.agreementNo = "U12011800001-01";


        request.mainDto.shareholderFlag = "0";
        request.mainDto.businessNature = "2";
        request.mainDto.shareholderName = "A27";
        request.mainDto.shareholderKindCode = "B90";
        request.mainDto.idCard = "";
        request.mainDto.owner = "";
        request.mainDto.cardNo = "";
        request.mainDto.phone = "";
        request.mainDto.userCode = "";
        request.mainDto.pName = "英大非机动车驾驶员意外险";
        request.mainDto.userCode = "";

        request.mainDto.channelDto = new TYInsProposalBean.ChannelDto();
        request.mainDto.channelDto.channelCode = "190000";

        request.mainDto.insuredAppliDto = new TYInsProposalBean.InsuredAppliDto();
        request.mainDto.insuredDtoList = new ArrayList<>();
        request.mainDto.itemDeviceDto = new TYInsProposalBean.ItemDeviceDto();

        return request;
    }

    private String tranCardType(int type) {
        String result = "";
        switch (type) {
            case Person.PAPERS_TYPE_ICCARD:
                result = "01";
                break;
            default:
                result = "99";
                break;

        }
        return result;
    }
}

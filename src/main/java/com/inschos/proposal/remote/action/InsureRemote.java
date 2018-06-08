package com.inschos.proposal.remote.action;

import com.inschos.proposal.kit.*;
import com.inschos.proposal.logic.TcpClientNocar;
import com.inschos.proposal.model.Bank;
import com.inschos.proposal.model.CustWarranty;
import com.inschos.proposal.model.CustWarrantyPerson;
import com.inschos.proposal.model.Person;
import com.inschos.proposal.remote.bean.NoCarPrPoEnQueryBean;
import com.inschos.proposal.remote.bean.TYInsProposalBean;
import com.inschos.proposal.service.CustWarrantyService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IceAnt on 2018/4/3.
 */
@Component
public class InsureRemote {

    private Logger logger = LoggerFactory.getLogger(InsureRemote.class);

    private final String bodyHead = "<?xml version=\"1.0\" encoding=\"%s\"?>";

    // 10.128.20.64

    private final String host = "211.160.75.141";

    private final String host_pro = "211.160.75.143";

    private final int port = 6518;

    private final int port_pro = 6518;

    private final String s = "1900020";

    @Value("${remote_pro}")
    private boolean remotePro;


    @Autowired
    private CustWarrantyService custWarrantyService;

    @Async
    public void insure(CustWarranty warranty, CustWarrantyPerson warrantyPerson, Bank bank) {

        TcpClientNocar client = new TcpClientNocar();
        String charsetName = Charset.defaultCharset().displayName();
        logger.debug("system charset displayName {}", charsetName);
        client.setEncoding(charsetName);
        client.setHeadBeforeLength("");

        client.setHeadAfterLength(StringUtils.rightPad(this.s, 7) + "gs0007");
        client.setLengthHeadSize(6);
        client.setLengthHeadPad(TcpClientNocar.PAD_RIGHT);
        client.setLengthHeadPadChar(" ");
        boolean isRequestSuccess = false;
        String errMsg = null;
        try {


            String call = client.call(getHost(), getPort(), getRequestXml(warranty, warrantyPerson, bank));
            if (!StringKit.isEmpty(call)) {
                TYInsProposalBean.TYInsProposalResponse response = XmlKit.xml2Bean(call, TYInsProposalBean.TYInsProposalResponse.class);
                if (response != null && response.headDto != null) {

                    if ("0".equals(response.headDto.responseCode)) {

                        if (response.mainDto != null) {


                            if ("1".equals(response.mainDto.result) || "2".equals(response.mainDto.result) || "4".equals(response.mainDto.result)) {
                                isRequestSuccess = true;
                                List<TYInsProposalBean.ProToPoDto> poDtoList = response.mainDto.proToPoDtoList;
                                List<String> proPolicyNos = new ArrayList<>();

                                if (poDtoList != null) {
                                    for (TYInsProposalBean.ProToPoDto proToPoDto : poDtoList) {
                                        proPolicyNos.add(proToPoDto.proposalNo);
                                    }
                                }
                                if (!proPolicyNos.isEmpty()) {

                                    warranty.pro_policy_no = StringKit.join(proPolicyNos, ",");
                                    warranty.comb_warranty_code = response.mainDto.cardPolicyNo;
                                    warranty.check_status = CustWarranty.CHECK_STATUS_SUCCESS;
                                    warranty.pay_status = CustWarranty.PAY_STATUS_WAITING;
                                    warranty.warranty_status = CustWarranty.WARRANTY_STATUS_DAIZHIFU;
                                    warranty.updated_at = TimeKit.currentTimeMillis();
                                    warranty.resp_insure_msg = response.mainDto.message+(StringKit.isEmpty(response.mainDto.errMsg)?"":("["+response.mainDto.errMsg+"]"));
                                    custWarrantyService.updateProInfo(warranty);
                                }

                            }else{
                                List<TYInsProposalBean.ProToPoDto> poDtoList = response.mainDto.proToPoDtoList;
                                List<String> proPolicyNos = new ArrayList<>();
                                if (poDtoList != null) {
                                    for (TYInsProposalBean.ProToPoDto proToPoDto : poDtoList) {
                                        proPolicyNos.add(proToPoDto.proposalNo);
                                    }
                                }
                                warranty.pro_policy_no = StringKit.join(proPolicyNos, ",");
                                errMsg = StringKit.isEmpty(response.mainDto.errMsg)?response.mainDto.message:response.mainDto.errMsg;
                            }
                        }else {
                            errMsg = !StringKit.isEmpty(response.headDto.errorCode)?(response.headDto.errorCode+"|"+response.headDto.errorMessage):(!StringKit.isEmpty(response.headDto.esbCode)?(response.headDto.esbCode+"|"+response.headDto.esbMessage):null);
                        }


                    } else {
                        errMsg = !StringKit.isEmpty(response.headDto.errorCode)?(response.headDto.errorCode+"|"+response.headDto.errorMessage):(!StringKit.isEmpty(response.headDto.esbCode)?(response.headDto.esbCode+"|"+response.headDto.esbMessage):null);
                        logger.error("insure response  failed msg:{}", call);
                    }
                } else {
                    logger.error("insure response  failed msg:{}", call);
                }
            }
        } catch (Exception e) {
            logger.error("insure call error", e);
        }
        if (!isRequestSuccess) {
            warranty.resp_insure_msg = errMsg;
            warranty.check_status = CustWarranty.CHECK_STATUS_FAILED;
            warranty.warranty_status = CustWarranty.WARRANTY_STATUS_YISHIXIAO;
            warranty.updated_at = TimeKit.currentTimeMillis();
            custWarrantyService.updateProInfo(warranty);
        }
    }

    public String query(){
        TcpClientNocar client = new TcpClientNocar();
        String charsetName = Charset.defaultCharset().displayName();
        logger.debug("system charset displayName {}", charsetName);
        client.setEncoding(charsetName);
        client.setHeadBeforeLength("");

        client.setHeadAfterLength(StringUtils.rightPad(this.s, 7) + "gs0007");
        client.setLengthHeadSize(6);
        client.setLengthHeadPad(TcpClientNocar.PAD_RIGHT);
        client.setLengthHeadPadChar(" ");
        String call = null;
        try {
            call = client.call(getHost(), getPort(), getQueryRequestXml());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringKit.isEmpty(call)) {

        }
        logger.debug(call);
        return call;
    }

    private String getQueryRequestXml(){
        NoCarPrPoEnQueryBean.NoCarPrPoEnQueryRequest request = new NoCarPrPoEnQueryBean.NoCarPrPoEnQueryRequest();
        request.headDto = new NoCarPrPoEnQueryBean.RequestHeadDto();


        request.headDto.requestType = "01";
        request.headDto.user = "ydcxCard";
        request.headDto.passWord = "Ydcx5196Card";

        request.headDto.queryId = "";
        request.headDto.sourceSystemCode = "";
        request.headDto.versionNo = "";
        request.headDto.areaCode = "";
        request.headDto.areaName = "";
        request.headDto.tradeTime = TimeKit.format("yyyyMMddHHmmss",TimeKit.currentTimeMillis());;
        request.headDto.responseType = "01";
        request.headDto.signData = "";

        request.mainDto = new NoCarPrPoEnQueryBean.NoCarPrPoEnQueryRequestDto();
        request.mainDto.businessNo = "1120130072018000631YD";
        request.mainDto.businessType = "T";
        //T120115282018000533,T120127702018000514
        request.mainDto.cardProductFlag = "TY";
        request.mainDto.channelDto = new NoCarPrPoEnQueryBean.ChannelDto();
        request.mainDto.channelDto.channelCode="190000";
        request.mainDto.channelDto.channelTradeCode = "1900020";

        request.mainDto.channelDto.channelTradeSerialNo = TimeKit.format("yyyyMMddHHmmss",TimeKit.currentTimeMillis());
        request.mainDto.channelDto.channelTradeDate =TimeKit.format("yyyyMMdd",TimeKit.currentTimeMillis());


        String displayName = Charset.defaultCharset().displayName();
        String head = String.format(bodyHead, "GBK");
        L.log.debug("head {}", head);
        return head + XmlKit.bean2Xml(request);
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
        tyInsProposalRequest.mainDto.houseAddress = _toUrlEncode(person.address);

        tyInsProposalRequest.mainDto.inputDate = TimeKit.format("yyyy-MM-dd", tradingTime);
        tyInsProposalRequest.mainDto.startDate = TimeKit.format("yyyy-MM-dd", warranty.start_time);
//        tyInsProposalRequest.mainDto.startHour = String.valueOf(TimeKit.get(Calendar.HOUR, warranty.start_time));
        tyInsProposalRequest.mainDto.startHour = "0";
        tyInsProposalRequest.mainDto.endDate = TimeKit.format("yyyy-MM-dd", warranty.end_time - 1);
//        tyInsProposalRequest.mainDto.endHour = String.valueOf(TimeKit.get(Calendar.HOUR, warranty.end_time - 1) + 1);
        tyInsProposalRequest.mainDto.endHour = "24";


        tyInsProposalRequest.mainDto.idCard = toEncrypt(person.card_code);
        //todo  url de
        tyInsProposalRequest.mainDto.owner = toEncrypt(_toUrlEncode(person.name));
        tyInsProposalRequest.mainDto.cardNo = toEncrypt(bank.bank_code);
        tyInsProposalRequest.mainDto.phone = toEncrypt(bank.phone);


        tyInsProposalRequest.mainDto.channelDto.channelCode = "190000";
        tyInsProposalRequest.mainDto.channelDto.channelTradeCode = "1900020";
        tyInsProposalRequest.mainDto.channelDto.channelTradeSerialNo = "201803C1001300000219";
        tyInsProposalRequest.mainDto.channelDto.channelTradeDate = TimeKit.format("yyyyMMddHHmmss", tradingTime);

        tyInsProposalRequest.mainDto.insuredAppliDto.insuredAppliType = "1";
        tyInsProposalRequest.mainDto.insuredAppliDto.insuredAppliName = _toUrlEncode(person.name);
        tyInsProposalRequest.mainDto.insuredAppliDto.insuredIdentity = "01";
        tyInsProposalRequest.mainDto.insuredAppliDto.identifyType = tranCardType(person.card_type);
        tyInsProposalRequest.mainDto.insuredAppliDto.identifyNumber = person.card_code;
        tyInsProposalRequest.mainDto.insuredAppliDto.linkMobile = person.phone;
        tyInsProposalRequest.mainDto.insuredAppliDto.sex = String.valueOf(person.sex);
        tyInsProposalRequest.mainDto.insuredAppliDto.birth = person.birthday;
        tyInsProposalRequest.mainDto.insuredAppliDto.email = person.email;
        tyInsProposalRequest.mainDto.insuredAppliDto.appliAddress = _toUrlEncode(person.address);

        tyInsProposalRequest.mainDto.insuredAppliDto.age = String.valueOf(person.age);


        TYInsProposalBean.InsuredDto insuredDto = new TYInsProposalBean.InsuredDto();
        insuredDto.insuredType = "1";
        insuredDto.insuredName = _toUrlEncode(person.name);
        insuredDto.identifyType = tranCardType(person.card_type);
        insuredDto.identifyNumber = person.card_code;
        insuredDto.linkMobile = person.phone;
        insuredDto.sex = String.valueOf(person.sex);
        insuredDto.birth = person.birthday;
        insuredDto.email = person.email;
        insuredDto.insuredAddress = _toUrlEncode(person.address);
        insuredDto.age = String.valueOf(person.age);
        insuredDto.relationSerialType = "01";
        insuredDto.occupationCode = "";
        insuredDto.physicalExamination = "0";
        tyInsProposalRequest.mainDto.insuredDtoList.add(insuredDto);

        String displayName = Charset.defaultCharset().displayName();
        String head = String.format(bodyHead, displayName);
        L.log.debug("head {}", head);
        return head + XmlKit.bean2Xml(tyInsProposalRequest);

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


        //线上
        if (remotePro) {
//            request.mainDto.makeCom = "12260402";
//            request.mainDto.comCode = "12260402";
//            request.mainDto.operatorCode = "12030018";
//            request.mainDto.operatorName = "";
//            request.mainDto.handlerCode = "12063706";
//            request.mainDto.handler1Code = "12063706";
//            request.mainDto.agentCode = "U12262000019";
//            request.mainDto.agreementNo = "U12262000019-01";
//            request.mainDto.userCode = "12030018";
//            request.mainDto.shareholderFlag = "0";
//            request.mainDto.businessNature = "2";
//            request.mainDto.shareholderName = "A27";
//            request.mainDto.shareholderKindCode = "B90";

            request.mainDto.makeCom = "12260402";
            request.mainDto.comCode = "12260402";
            request.mainDto.operatorCode = "12030018";
            request.mainDto.operatorName = "";
            request.mainDto.handlerCode = "12063706";
            request.mainDto.handler1Code = "12063706";
            request.mainDto.agentCode = "O12261800005";
            request.mainDto.agreementNo = "O12261800005-01";
            request.mainDto.userCode = "12030018";

            request.mainDto.shareholderFlag = "0";
            request.mainDto.businessNature = "4";
            request.mainDto.shareholderName = "A27";
//            request.mainDto.shareholderKindCode = "B75";
            request.mainDto.shareholderKindCode = "B90";


        } else {

            //模拟环境
            //        request.mainDto.makeCom = "12010000";
//        request.mainDto.comCode = "12010000";
//        request.mainDto.operatorCode = "12069004";
//        request.mainDto.operatorName = "";
//        request.mainDto.handlerCode = "12069004";
//        request.mainDto.handler1Code = "12069004";
//        request.mainDto.agentCode = "U12002000001";
//        request.mainDto.agreementNo = "U12002000001-01";
//        request.mainDto.userCode = "12010001";
            //用户环境
//        request.mainDto.makeCom = "12010000";
//        request.mainDto.comCode = "12010000";
//        request.mainDto.operatorCode = "12019998";
//        request.mainDto.operatorName = "";
//        request.mainDto.handlerCode = "12019998";
//        request.mainDto.handler1Code = "12019998";
//        request.mainDto.agentCode = "U12242000001";
//        request.mainDto.agreementNo = "U12242000001-02";
//        request.mainDto.userCode = "12010001";

            //集成环境
            request.mainDto.makeCom = "12010010";
            request.mainDto.comCode = "12010010";
            request.mainDto.operatorCode = "12345098";
            request.mainDto.operatorName = "";
            request.mainDto.handlerCode = "12345098";
            request.mainDto.handler1Code = "12345098";
            request.mainDto.agentCode = "U12011800001";
            request.mainDto.agreementNo = "U12011800001-01";
            request.mainDto.userCode = "12010001";


            request.mainDto.shareholderFlag = "0";
            request.mainDto.businessNature = "4";
            request.mainDto.shareholderName = "A27";
            request.mainDto.shareholderKindCode = "B90";
        }



        request.mainDto.idCard = "";
        request.mainDto.owner = "";
        request.mainDto.cardNo = "";
        request.mainDto.phone = "";
        request.mainDto.pName = _toUrlEncode("英大非机动车驾驶员意外险");

        request.mainDto.channelDto = new TYInsProposalBean.ChannelDto();
        request.mainDto.channelDto.channelCode = "190000";

        request.mainDto.insuredAppliDto = new TYInsProposalBean.InsuredAppliDto();
        request.mainDto.insuredDtoList = new ArrayList<>();
        request.mainDto.itemDeviceDto = new TYInsProposalBean.ItemDeviceDto();

        return request;
    }

    private String _toUrlEncode(String str) {
        return str != null ? URLEncoder.encode(str) : "";
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

    private String getHost() {
        if (remotePro) {
            return host_pro;
        } else {
            return host;
        }
    }

    private int getPort() {
        if (remotePro) {
            return port_pro;
        } else {
            return port;
        }
    }

    private String toEncrypt(String input){

        if(input==null){
            return "";
        }else{
            return EncryptUtil.encode(input, EncryptUtil.getDKey());
        }
    }
}

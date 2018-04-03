package com.inschos.proposal.remote.action;

import com.inschos.proposal.kit.EncryptUtil;
import com.inschos.proposal.kit.L;
import com.inschos.proposal.kit.TimeKit;
import com.inschos.proposal.kit.XmlKit;
import com.inschos.proposal.logic.TcpClientNocar;
import com.inschos.proposal.remote.bean.TYInsProposalBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by IceAnt on 2018/4/3.
 */
@Component
public class InsureRemote {

    private final String bodyHead = "<?xml version=\"1.0\" encoding=\"GBK\"?>";

    private final String host = "211.160.75.141";

    private final int port = 6518;

    private final String s = "1900020";

    @Async
    public void insure(String requestBody){

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
            L.log.error("insure call error",e);
        }

    }

    @Async
    public void test(){
        TYInsProposalBean.TYInsProposalRequest tyInsProposalRequest = generateDefaultRequest();
        // TODO: 2018/3/27
        long tradingTime = TimeKit.currentTimeMillis();
        tyInsProposalRequest.requestHeadDto.tradeTime = TimeKit.format("yyyyMMddHHmmss",tradingTime);
        tyInsProposalRequest.mainDto.methodNo = "30070101";
        tyInsProposalRequest.mainDto.provinceCode = "21";
        tyInsProposalRequest.mainDto.areaCode = "2101";
        tyInsProposalRequest.mainDto.addresscode="510000";
        tyInsProposalRequest.mainDto.houseAddress="广东地址";

        tyInsProposalRequest.mainDto.inputDate = TimeKit.format("yyyy-MM-dd",tradingTime);
        tyInsProposalRequest.mainDto.startDate = TimeKit.format("yyyy-MM-dd",tradingTime);
        tyInsProposalRequest.mainDto.startHour = "0";
        tyInsProposalRequest.mainDto.endDate = TimeKit.format("yyyy-MM-dd",tradingTime);
        tyInsProposalRequest.mainDto.endHour = "24";


        tyInsProposalRequest.mainDto.idCard = EncryptUtil.encode("620103199012171917",EncryptUtil.getDKey());
        tyInsProposalRequest.mainDto.owner=EncryptUtil.encode("张耘赫",EncryptUtil.getDKey());;
        tyInsProposalRequest.mainDto.cardNo=EncryptUtil.encode("6217730704649985",EncryptUtil.getDKey());;
        tyInsProposalRequest.mainDto.phone=EncryptUtil.encode("13186050625",EncryptUtil.getDKey());;


//            tyInsProposalRequest.mainDto.idCard = "340323199305094715";
//            tyInsProposalRequest.mainDto.owner="陶明扬";
//            tyInsProposalRequest.mainDto.cardNo="6212260200145135626";
//            tyInsProposalRequest.mainDto.phone="13865050566";

        tyInsProposalRequest.mainDto.userCode="12010001";

        tyInsProposalRequest.mainDto.channelDto.channelCode="190000";
        tyInsProposalRequest.mainDto.channelDto.channelTradeCode = "1900020";
        tyInsProposalRequest.mainDto.channelDto.channelTradeSerialNo = "201803C1001300000219";
        tyInsProposalRequest.mainDto.channelDto.channelTradeDate = TimeKit.format("yyyyMMddHHmmss",tradingTime);

        tyInsProposalRequest.mainDto.insuredAppliDto.insuredAppliType="1";
        tyInsProposalRequest.mainDto.insuredAppliDto.insuredAppliName="唐洪波";
        tyInsProposalRequest.mainDto.insuredAppliDto.insuredIdentity="01";
        tyInsProposalRequest.mainDto.insuredAppliDto.identifyType="01";
        tyInsProposalRequest.mainDto.insuredAppliDto.identifyNumber="420111197007145590";
        tyInsProposalRequest.mainDto.insuredAppliDto.linkMobile="13303102518";
        tyInsProposalRequest.mainDto.insuredAppliDto.sex="1";
        tyInsProposalRequest.mainDto.insuredAppliDto.birth="1970-07-14";
        tyInsProposalRequest.mainDto.insuredAppliDto.email="13303102518@126.com";
        tyInsProposalRequest.mainDto.insuredAppliDto.appliAddress="河北省邯郸市丛台区鸿基花苑5-3-704";
        tyInsProposalRequest.mainDto.insuredAppliDto.age="47";


        TYInsProposalBean.InsuredDto insuredDto = new TYInsProposalBean.InsuredDto();
        insuredDto.insuredType="1";
        insuredDto.insuredName="唐洪波";
        insuredDto.identifyType="01";
        insuredDto.identifyNumber="420111197007145590";
        insuredDto.linkMobile="13303102518";
        insuredDto.sex="1";
        insuredDto.birth="1970-07-14";
        insuredDto.email="13303102518@126.com";
        insuredDto.insuredAddress="河北省邯郸市丛台区鸿基花苑5-3-704";
        insuredDto.age="47";
        insuredDto.relationSerialType="01";
        insuredDto.occupationCode="160101";
        insuredDto.physicalExamination="0";
        tyInsProposalRequest.mainDto.insuredDtoList.add(insuredDto);

        String requestBody = bodyHead+ XmlKit.bean2Xml(tyInsProposalRequest);

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
            L.log.error("insure call error",e);
        }
    }

    private TYInsProposalBean.TYInsProposalRequest  generateDefaultRequest(){
        TYInsProposalBean.TYInsProposalRequest request = new TYInsProposalBean.TYInsProposalRequest();
        request.requestHeadDto = new TYInsProposalBean.RequestHeadDto();
        request.mainDto = new TYInsProposalBean.RequestMainDto();
        request.requestHeadDto.requestType = "01";
        request.requestHeadDto.user = "ydcxCard";
        request.requestHeadDto.passWord ="Ydcx5196Card";

        request.requestHeadDto.queryId ="";
        request.requestHeadDto.sourceSystemCode ="";
        request.requestHeadDto.versionNo ="";
        request.requestHeadDto.areaCode ="";
        request.requestHeadDto.areaName ="";
        request.requestHeadDto.tradeTime ="";
        request.requestHeadDto.responseType ="01";
        request.requestHeadDto.signData ="";

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
        request.mainDto.makeCom = "12010000";
        request.mainDto.comCode = "12010000";
        request.mainDto.operatorCode = "12019998";
        request.mainDto.operatorName = "";
        request.mainDto.handlerCode = "12019998";
        request.mainDto.handler1Code = "12019998";
        request.mainDto.agentCode = "U12242000001";
        request.mainDto.agreementNo = "U12242000001-02";
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
}

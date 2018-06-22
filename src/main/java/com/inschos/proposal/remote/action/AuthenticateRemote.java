package com.inschos.proposal.remote.action;

import com.inschos.proposal.kit.*;
import com.inschos.proposal.logic.TcpClientNocar;
import com.inschos.proposal.model.Bank;
import com.inschos.proposal.remote.bean.TyAuthenticateBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Created by IceAnt on 2018/6/21.
 */
@Component
public class AuthenticateRemote {

    private final String bodyHead = "<?xml version=\"1.0\" encoding=\"%s\"?>";

    @Value("${remote_pro}")
    private boolean remotePro;

    private String getHost(){
        String host ;
        if(remotePro){
            host = "211.160.75.143";
        }else{
            host = "211.160.75.141";
        }
        return host;
    }

    private int getPort(){
        int port ;
        if(remotePro){
            port = 6518;
        }else{
            port = 6518;
        }
        return port;
    }



    public TyAuthenticateBean.TyAuthenticateResponse sendAuth(Bank bank){

        String tradeCode = "1900026";

        TcpClientNocar client = new TcpClientNocar();

        client.setHeadBeforeLength("");

        client.setHeadAfterLength(StringUtils.rightPad(tradeCode, 7) + "gs0007");
        client.setLengthHeadSize(6);
        client.setLengthHeadPad(TcpClientNocar.PAD_RIGHT);
        client.setLengthHeadPadChar(" ");
        boolean isRequestSuccess = false;
        String errMsg = null;
        try {
            long tradeTime = TimeKit.currentTimeMillis();
            String call = client.call(getHost(), getPort(), getSendRequestXml(bank,tradeCode,tradeTime));
            if(!StringKit.isEmpty(call)){
                TyAuthenticateBean.TyAuthenticateResponse response = XmlKit.xml2Bean(call, TyAuthenticateBean.TyAuthenticateResponse.class);
                L.log.info(call);
                return response;
            }
        }catch (Exception e){

        }
        return null;
    }

    public TyAuthenticateBean.TyAuthConfirmResponse confirmAuth(String requestId,String vdCode){

        String tradeCode = "1900027";

        TcpClientNocar client = new TcpClientNocar();
        String charsetName = Charset.defaultCharset().displayName();

        client.setEncoding(charsetName);
        client.setHeadBeforeLength("");

        client.setHeadAfterLength(StringUtils.rightPad(tradeCode, 7) + "gs0007");
        client.setLengthHeadSize(6);
        client.setLengthHeadPad(TcpClientNocar.PAD_RIGHT);
        client.setLengthHeadPadChar(" ");
        boolean isRequestSuccess = false;
        String errMsg = null;
        try {
            long tradeTime = TimeKit.currentTimeMillis();
            String call = client.call(getHost(), getPort(), getConfirmRequestXml(requestId,vdCode,tradeCode,tradeTime));
            if(!StringKit.isEmpty(call)){
                TyAuthenticateBean.TyAuthConfirmResponse response = XmlKit.xml2Bean(call, TyAuthenticateBean.TyAuthConfirmResponse.class);
                return response;
            }
        }catch (Exception e){

        }
        return null;
    }


    private String getSendRequestXml(Bank bank,String tradeCode,long tradeTime){

        TyAuthenticateBean.TyAuthenticateRequest request = new TyAuthenticateBean.TyAuthenticateRequest();
        request.requestHeadDto = toHead(tradeTime);
        request.mainDto = new TyAuthenticateBean.RequestMainDto();
        request.mainDto.pId = "2018020700001117";
        request.mainDto.owner = toEncrypt(_toUrlEncode(bank.userName));
        request.mainDto.cardNo = toEncrypt(bank.bank_code);
        request.mainDto.idCard =toEncrypt(bank.userIdCard);
        request.mainDto.phone =toEncrypt(bank.phone);
        request.mainDto.channelDto = toChannel(tradeCode,tradeTime);


        String head = String.format(bodyHead, "GBK");
        L.log.debug("head {}", head);
        return head + XmlKit.bean2Xml(request);

    }

    private String getConfirmRequestXml(String requestId,String vdCode,String tradeCode,long tradeTime){

        TyAuthenticateBean.TyAuthConfirmRequest request = new TyAuthenticateBean.TyAuthConfirmRequest();
        request.requestHeadDto = toHead(tradeTime);
        request.mainDto = new TyAuthenticateBean.ConfirmRequestMainDto();
        request.mainDto.requestId = requestId;
        request.mainDto.validateCode = vdCode;
        request.mainDto.channelDto = toChannel(tradeCode,tradeTime);

        String head = String.format(bodyHead, "GBK");
        L.log.debug("head {}", head);
        return head + XmlKit.bean2Xml(request);

    }

    private TyAuthenticateBean.RequestHeadDto toHead(long tradeTime){
        TyAuthenticateBean.RequestHeadDto headDto = new TyAuthenticateBean.RequestHeadDto();
        headDto.requestType = "01";
        headDto.user = "ydcxCard";
        headDto.passWord = "Ydcx5196Card";
        headDto.queryId = "";
        headDto.sourceSystemCode = "TY";
        headDto.versionNo = "";
        headDto.areaCode = "";
        headDto.areaName = "";
        headDto.tradeTime = TimeKit.format("yyyyMMddHHmmss",tradeTime);
        headDto.responseType="ZH046";
        headDto.signData = "";
        return headDto;
    }

    private TyAuthenticateBean.ChannelDto toChannel(String tradeCode,long tradeTime){
        TyAuthenticateBean.ChannelDto channelDto = new TyAuthenticateBean.ChannelDto();
        channelDto.channelCode = "190000";
        channelDto.channelTradeCode = tradeCode;
        channelDto.channelTradeSerialNo = "201704271711043";
        channelDto.channelTradeDate = TimeKit.format("yyyyMMdd",tradeTime);
        return channelDto;
    }


    private String _toUrlEncode(String str) {
        return str != null ? URLEncoder.encode(str) : "";
    }

    private String toEncrypt(String input){

        if(input==null){
            return "";
        }else{
            return EncryptUtil.encode(input, EncryptUtil.getDKey());
        }
    }

}

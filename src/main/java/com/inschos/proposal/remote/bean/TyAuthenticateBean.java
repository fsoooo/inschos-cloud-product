package com.inschos.proposal.remote.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.inschos.proposal.annotation.EncryptAnnotation;

/**
 * Created by IceAnt on 2018/6/20.
 */
public class TyAuthenticateBean {

    //鉴权绑卡发短验接口

    @JacksonXmlRootElement(localName = "TyAuthenticateRequest")
    public static class TyAuthenticateRequest{

        @JacksonXmlProperty(localName = "RequstHeadDto")
        public RequestHeadDto requestHeadDto;
        @JacksonXmlProperty(localName = "TyAuthenticateRequestMainDto")
        public RequestMainDto mainDto;
    }

    @JacksonXmlRootElement(localName = "TyAuthenticateResponse")
    public static class TyAuthenticateResponse{

        @JacksonXmlProperty(localName = "ResponseHeadDto")
        public ResponseHeadDto headDto;

        @JacksonXmlProperty(localName = "TyAuthenticateResponseMainDto")
        public ResponseMainDto mainDto;
    }

    //确认接口
    @JacksonXmlRootElement(localName = "TyAuthConfirmRequest")
    public static class TyAuthConfirmRequest{
        @JacksonXmlProperty(localName = "RequstHeadDto")
        public RequestHeadDto requestHeadDto;
        @JacksonXmlProperty(localName = "TyAuthConfirmRequestMainDto")
        public ConfirmRequestMainDto mainDto;
    }

    @JacksonXmlRootElement(localName = "TyAuthConfirmResponse")
    public static class TyAuthConfirmResponse{

        @JacksonXmlProperty(localName = "ResponseHeadDto")
        public ResponseHeadDto headDto;

        @JacksonXmlProperty(localName = "TyAuthConfirmResponseMainDto")
        public ConfirmResponseMainDto mainDto;
    }



    //报文头信息
    public static class RequestHeadDto {

        /**	请求类型	Y	默认 01*/
        public String requestType;
        /**	用户名	Y	默认ydcxCard*/
        public String user;
        /**	密码	Y	"默认*/
        public String passWord;

        /**	唯一标志码	N	*/
        public String queryId;
        /**	来源系统代码	N  默认 NewDocSys	*/
        public String sourceSystemCode;
        /**	版本号	N	*/
        public String versionNo;
        /**	区域/机构代码	N	默认传空*/
        public String areaCode;
        /**	区域/机构名称	N	*/
        public String areaName;
        /**	交易时间	Y	"格式为：年月日时分秒，YYYYMMDDHHMMSS，中间不用连接符号*/
        public String tradeTime;

        /**	响应类型	Y	01-实时响应（默认值） */
        public String responseType;
        /**	数据加密	Y	*/
        public String signData;


    }

    //  请求信息

    public static class  RequestMainDto{
        //请求主信息

        /**	业务系统请求号	Y	默认传NewDocSys*/
        public String pId;

        /**	持卡人身份证	Y	将字段加密*/
        @EncryptAnnotation(doEncode = true)
        public String idCard;
        /**	持卡人姓名	Y	将字段加密*/
        @EncryptAnnotation(doEncode = true)
        public String owner;
        /**	持卡人卡号	Y	将字段加密*/
        @EncryptAnnotation(doEncode = true)
        public String cardNo;
        /**	持卡人手机号	Y	将字段加密*/
        @EncryptAnnotation(doEncode = true)
        public String phone;
        /**	登录用户名	Y	英大提供*/

        @JacksonXmlProperty(localName = "ChannelDto")
        public ChannelDto channelDto;

    }
    public static class ChannelDto{
        /**	渠道代码	Y	默认190000*/
        public String channelCode;
        /**	渠道名称	N	*/
        public String channelName;
        /**	渠道机构代码	N	*/
        public String channelComCode;
        /**	渠道机构名称	N	*/
        public String channelComName;
        /**	渠道产品代码	N	*/
        public String channelProductCode;
        /**	渠道操作员代码	N	*/
        public String channelOperateCode;
        /**	渠道交易代码	Y	默认1900020*/
        public String channelTradeCode;
        /**	渠道交易流水号	Y	当前日期，格式为YYYYMMDDHHmmss*/
        public String channelTradeSerialNo;
        /**	渠道关联单号	N	*/
        public String channelRelationNo;
        /**	渠道交易日期	Y	当前日期，格式为YYYYMMDD*/
        public String channelTradeDate;

    }

    public static class ResponseHeadDto{

        /**	请求类型	N	参见：数据字典中的 编号0001 的代码定义*/
        public String requestType;
        /**	响应类型	N	0-成功  1-应用失败 2-ESB失败*/
        public String responseCode;
        /**	错误代码	N	返回3位错误代码*/
        public String errorCode;
        /**	错误描述	N	返回错误信息*/
        public String errorMessage;
        /**	ESB错误代码	Y	返回3位错误代码*/
        public String esbCode;
        /**	ESB错误描述	Y	返回错误信息*/
        public String esbMessage;
        /**	数据加密	Y	*/
        public String signData;

    }

    public static class ResponseMainDto {

        /**
         * 返回结果状态	Y	"
         * 0-失败
         * 1-成功
         */
        public String result;


        public String resultMsg;

        public String requestId;

    }

    public static class ConfirmRequestMainDto{

//        绑卡请求号	Y	String	同鉴权绑卡发短验接口返回参数requestId
        public String requestId;
//        validateCode	短信验证码	Y	String	客户收到发送的短信验证码
        public String validateCode;

        @JacksonXmlProperty(localName = "ChannelDto")
        public ChannelDto channelDto;
    }


    public static class ConfirmResponseMainDto{
        /**
         * 返回结果状态	Y	"
         * 0-失败
         * 1-成功
         */
        public String result;

        public String resultMsg;
    }

}

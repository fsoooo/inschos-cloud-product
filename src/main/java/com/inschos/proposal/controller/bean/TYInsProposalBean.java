package com.inschos.proposal.controller.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.inschos.proposal.annotation.EncryptAnnotation;

import java.util.List;

/**
 * Created by IceAnt on 2018/3/24.
 */
public class TYInsProposalBean {

    @JacksonXmlRootElement(localName = "TYInsProposalRequest")
    public static class TYInsProposalRequest{

        @JacksonXmlProperty(localName = "RequestHeadDto")
        public RequestHeadDto requestHeadDto;
        @JacksonXmlProperty(localName = "TYInsProposalRequestMainDto")
        public RequestMainDto mainDto;
    }

    @JacksonXmlRootElement(localName = "TYInsProposalResponse")
    public static class TYInsProposalResponse{

        @JacksonXmlProperty(localName = "ResponseHeadDto")
        public ResponseHeadDto headDto;

        @JacksonXmlProperty(localName = "TYInsProposalResponseMainDto")
        public ResponseMainDto mainDto;
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
        /**	来源系统代码	N	*/
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

    //  请求信息

    public static class  RequestMainDto{
        //请求主信息

        /**	保单类型	Y	默认01-个人*/
        public String policyType;
        /**	缴费方式	N	1.现金2.银行转账9.其他*/
        public String payMode;
        /**   	方案号 	Y	英大提供（根据具体的方案传值）*/
        public String methodNo;
        /**	业务号	S	已有大保单号时，传值*/
        public String businessNo;
        /**	投保单号	S	已有大保单号时传值。多个时，中间用英文逗号隔开*/
        public String proposalNo;
        /**	是否授权	S	1-授权 0授权，天眼默认传1*/
        public String isAuthorization;
        /**	组合产品标识	Y	0 非组合 1 组合险*/
        public String combProductFlag;
        /**	被保险财产地址省级代码	S	家财企财险种时必传(依据归属机构而定)*/
        public String provinceCode;
        /**	被保险财产地址市级代码	S	家财企财险种时必传(依据归属机构而定)*/
        public String areaCode;

        public String addresscode;
        /**	被保险财产地址	N	*/
        public String houseAddress;
        /**	渠道标识	Y	默认TY*/
        public String cardProductFlag;
        /**	投保日期	Y	*/
        public String inputDate;
        /**	起保日期	Y	*/
        public String startDate;
        /**	起保小时	Y	*/
        public String startHour;
        /**	终保日期	Y	*/
        public String endDate;
        /**	终保小时	Y	*/
        public String endHour;
        /**	最后一次修改人员代码	N	英大提供*/
        public String updaterCode;
        /**	出单机构代码	Y	英大提供*/
        public String makeCom;
        /**	归属机构代码	Y	英大提供*/
        public String comCode;
        /**	操作员代码/第一次录入人员代码	Y	英大提供*/
        public String operatorCode;
        /**	操作员名称	N	英大提供*/
        public String operatorName;
        /**	经办人代码	Y	英大提供*/
        public String handlerCode;
        /**	归属业务员代码	Y	英大提供*/
        public String handler1Code;
        /**	代理人代码	S	英大提供*/
        public String agentCode;
        /**	代理人协议	S	英大提供*/
        public String agreementNo;
        /**	一级业务标识	Y	英大提供*/
        public String shareholderFlag;
        /**	二级业务标识	Y	英大提供*/
        public String businessNature;
        /**	三级业务标识	Y	英大提供*/
        public String shareholderName;
        /**	四级业务标识	Y	英大提供*/
        public String shareholderKindCode;
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
        public String userCode;
        /**	产品名称	Y	英大提供*/
        public String pName;
        /**	渠道信息	Y	Object	*/

        @JacksonXmlProperty(localName = "ChannelDto")
        public ChannelDto channelDto;

        @JacksonXmlProperty(localName = "InsuredAppliDto")
        public InsuredAppliDto insuredAppliDto;

        /**	被保人列表	Y	*/
        @JacksonXmlElementWrapper(localName = "InsuredDtoList")
        @JacksonXmlProperty(localName = "InsuredDto")
        public List<InsuredDto> insuredDtoList;

//        @JacksonXmlProperty(localName = "ItemDeviceDto")
        public ItemDeviceDto itemDeviceDto;

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
    /**	投保人信息	Y	*/
    public static class InsuredAppliDto{

        /**	投保人类型 	Y	默认值（个人为1，单位为2）*/
        public String insuredAppliType;
        /**	名称	Y	*/
        public String insuredAppliName;
        /**	证件类型	Y	*/
        public String identifyType;
        /**	证件号码	Y	*/
        public String identifyNumber;
        /**	与被保人关系	Y	英大提供（请看下面说明）*/
        public String insuredIdentity;
        /**	手机	Y	*/
        public String linkMobile;
        /**	电话	N	*/
        public String linkTel;
        /**	性别	Y	*/
        public String sex;
        /**	出生日期	Y	*/
        public String birth;
        /**	年龄	Y	*/
        public String age;
        /**	投保人邮箱地址	N	*/
        public String email;
        /**	投保人地址	Y	*/
        public String appliAddress;

    }

    /**	被保险人信息	Y	*/
    public static class InsuredDto{

        /**	被保险人类型	Y	默认值（个人为1，单位为2）*/
        public String insuredType;
        /**	名称	Y	*/
        public String insuredName;
        /**	证件类型	Y	*/
        public String identifyType;
        /**	证件号码	Y	*/
        public String identifyNumber;
        /**	手机	N	*/
        public String linkMobile;
        /**	性别	Y	*/
        public String sex;
        /**	出生日期	Y	*/
        public String birth;
        /**	年龄	Y	*/
        public String age;
        /**	与投保人关系	Y	英大提供（见关系人码表）*/
        public String relationSerialType;
        /**	职业代码 	Y	英大提供（见职业代码码表）*/

        public String email;
        public String occupationCode;
        /**	职业名称 	N	*/
        public String occupationName;
        /**	职业类别 	N	*/
        public String occupationLevel;
        /**	被保险人地址	Ｙ	*/
        public String insuredAddress;
        /**	被保人地址代码	Ｙ	英大提供（见被保险人地址代码码表）*/
        public String countyCode;
        /**	被保人是否体检	S	"0否，1是（2729，2771，2750险种必传）"*/
        public String physicalExamination;
    }
    public static class ItemDeviceDto{

        public String itemCarTrue;

        public String itemCarFalse;
    }

    public static class ResponseMainDto{

        /**	返回结果状态	Y	"
         * 0-失败
         * 1-投保单生成，缴费中
         * 2-非见费出单成功
         * 3-生成保单失败
         * 4-生成保单成功"
         * */
        public String result;

        /**	错误信息	S	失败时返回提示信息*/
        public String errMsg;
        /**	大保单号	S	当为组合产品时，会传值*/
        public String cardPolicyNo;
        /**	投保单/保单信息	Y	Object	*/

        // TODO: 2018/3/24   这里格式不明确
        @JacksonXmlElementWrapper(localName = "ProToPoDtoList")
        @JacksonXmlProperty(localName = "ProToPoDto")
        public List<ProToPoDto> proToPoDtoList;

        /**	返回信息	Y	*/
        public String message;
        /**	核保信息	S	*/
        public String undwrtMessage;
        /**	核保状态	S	0初始值/1通过/2不通过/3 无需核保 9待核保*/
        public String undwrtFlag;
    }

    public static class ProToPoDto{
        /**	投保单号	Y	*/
        public String proposalNo;
        /**	保单号	S	生成保单数据时，会传值*/
        public String policyNo;
    }




}

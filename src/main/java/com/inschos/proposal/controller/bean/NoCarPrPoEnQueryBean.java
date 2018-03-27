package com.inschos.proposal.controller.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * Created by IceAnt on 2018/3/24.
 */
public class NoCarPrPoEnQueryBean {

    @JacksonXmlRootElement(localName = "NoCarPrPoEnQueryRequest")
    public static class NoCarPrPoEnQueryRequest{

        @JacksonXmlProperty(localName = "RequestHeadDto")
        public RequestHeadDto headDto;

        @JacksonXmlProperty(localName = "NoCarPrPoEnQueryRequestDto")
        public NoCarPrPoEnQueryRequestDto mainDto;

    }

    @JacksonXmlRootElement(localName = "NoCarPrPoEnQueryResponse")
    public static class NoCarPrPoEnQueryResponse{

        @JacksonXmlProperty(localName = "RequestHeadDto")
        public RequestHeadDto headDto;

        @JacksonXmlProperty(localName = "NoCarPrPoEnQueryResponseMainDto")
        public NoCarPrPoEnQueryResponseMainDto mainDto;
    }

    public static class RequestHeadDto{

        /**	请求类型	Y	默认 01*/
        public String requestType;
        /**	用户名	Y	默认ydcxCard*/
        public String user;
        /**	密码	Y	"默认Ydcx5196Card*/
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

    public static class NoCarPrPoEnQueryRequestDto{
        /**	业务号	Y	*/
        public String businessNo;
        /**	业务类型	Y	T--投保单，C--保单，P--批单*/
        public String businessType;
        /**	渠道标识	Y	*/
        public String cardProductFlag;
        @JacksonXmlProperty(localName = "ChannelDto")
        public ChannelDto channelDto;

    }
    /**	渠道信息	Y	Object	*/
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
        /**	渠道交易代码	Y	默认值，英大提供（待定）*/
        public String channelTradeCode;
        /**	渠道交易流水号	Y	*/
        public String channelTradeSerialNo;
        /**	渠道关联单号	Y	*/
        public String channelRelationNo;
        /**	渠道交易日期	Y	*/
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

    public static class NoCarPrPoEnQueryResponseMainDto{

        /**	返回结果状态	Y	1-成功 0-失败*/
        public String result;
        /**	业务号	Y	失败时返回提示信息*/
        public String businessNo;
        /**	业务类型	Y	T--投保单，C--保单，P--批单*/
        public String businessType;
        /**	渠道标识	Y	*/
        public String cardProductFlag;
        /**	起保日期	Y	*/
        public String startDate;
        /**	止保日期	Y	*/
        public String endDate;
        /**	返回信息	Y	*/
        public String message;

        /**	被保险人信息集合	Y	Object	*/
        @JacksonXmlElementWrapper(localName = "InsuredDtoList")
        @JacksonXmlProperty(localName = "InsuredDto")
        public List<InsuredDto> insuredDtoList;
        @JacksonXmlProperty(localName = "SpecialInfoDto")
        public SpecialInfoDto specialInfoDto;
    }

    /**	被保险人信息	Y	Object	*/
    public static class InsuredDto{
        /**	被保险人类型	Y	*/
        public String insuredType;
        /**	名称	Y	*/
        public String insuredName;
        /**	证件类型	Y	*/
        public String identifyType;
        /**	证件号码	Y	*/
        public String identifyNumber;
        /**	手机号	Y	*/
        public String linkMobile;
        /**	性别	N	*/
        public String sex;
        /**	出生日期	N	*/
        public String birth;
        /**	年龄	N	*/
        public String age;
        /**	被保险人地址	Y	*/
        public String insuredAddress;
        /**	被保人地址代码	N	*/
        public String countyCode;

    }

    /**	个性化信息	Y	Object	*/
    public static class SpecialInfoDto{
        /**	剩余次数	Y	*/
        public String count;
    }




}

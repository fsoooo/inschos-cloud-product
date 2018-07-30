package com.inschos.proposal.access.http.controller.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * Created by IceAnt on 2018/3/27.
 */
public class TradingCallBackBean {

    @JacksonXmlRootElement(localName = "ArrivalConfirmEchoRequestNew")
    public static class  PayCallBackRequest {

        @JacksonXmlProperty(localName = "head")
        public CallBackHead head;
        @JacksonXmlElementWrapper(localName = "policyList")
        @JacksonXmlProperty(localName = "ArrivalConfirmEchoRequestMainDto")
        public List<PolicyData> policyList;
        @JacksonXmlProperty(localName = "cardPolicyNo")
        public String cardPolicyNo;
    }

    public static class CallBackHead{
        public String requestType;
        public String transType;
    }

    public static class PolicyData{
        public String proposalNo;
        public String policyNo;
        public String status;
        public String signDate;
    }
}

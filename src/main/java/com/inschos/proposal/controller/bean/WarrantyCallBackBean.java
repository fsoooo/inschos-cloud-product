package com.inschos.proposal.controller.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by IceAnt on 2018/3/27.
 */
public class WarrantyCallBackBean {

    @JacksonXmlRootElement(localName = "ArrivalConfirmEchoRequest")
    public static class  PayCallBackRequest {

        @JacksonXmlProperty(localName = "head")
        public CallBackHead head;
        @JacksonXmlProperty(localName = "ArrivalConfirmEchoRequestMainDto")
        public CallBackMainDto mainDto;
    }

    public static class CallBackHead{
        public String requestType;
        public String transType;
    }

    public static class CallBackMainDto{
        public String proposalNo;
        public String policyNo;
        public String status;
        public String signDate;
    }
}

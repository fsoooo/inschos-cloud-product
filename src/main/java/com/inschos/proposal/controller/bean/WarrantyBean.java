package com.inschos.proposal.controller.bean;

/**
 * Created by IceAnt on 2018/3/27.
 */
public class WarrantyBean {

    public static class InsureRequest extends BaseRequest{


        public String channel_code;
        /** 真实姓名*/
        public String insured_name;
        /**  	??????*/
        public String insured_phone;
        /**  	????????*/
        public String insured_email;
        /**  	?????*/
        public String insured_code;
        /** 	??????*/
        public String insured_address;
        /**	????????(???????)*/
        public String bank_name;
        /**	?????е??*/
        public String bank_address;
        /**	???п???*/
        public String bank_code;
        /**	????????绰*/
        public String bank_phone;
        /**	???*/
        public String insured_province;
        /**	????*/
        public String insured_city;
        /**	??(???)*/
        public String insured_county;

        public String price;

        public int  insured_days;


    }



}

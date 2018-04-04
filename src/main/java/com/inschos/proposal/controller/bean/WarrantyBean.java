package com.inschos.proposal.controller.bean;

/**
 * Created by IceAnt on 2018/3/27.
 */
public class WarrantyBean {

    public static class InsureRequest extends BaseRequest{

        /**	??????Ψ????*/
        public String channel_code;
        /**	???????*/
        public String channel_user_name;
        /**  	??????*/
        public String channel_user_phone;
        /**  	????????*/
        public String channel_user_email;
        /**  	?????*/
        public String channel_user_code;
        /** 	??????*/
        public String channel_user_address;
        /**	????????(???????)*/
        public String channel_bank_name;
        /**	?????е??*/
        public String channel_bank_address;
        /**	???п???*/
        public String channel_bank_code;
        /**	????????绰*/
        public String channel_bank_phone;
        /**	???*/
        public String channel_provinces;
        /**	????*/
        public String channel_city;
        /**	??(???)*/
        public String channel_county;

        public String price;

        public int  insured_days;


    }



}

package com.inschos.proposal.controller.bean;

/**
 * Created by IceAnt on 2018/3/27.
 */
public class WarrantyBean {

    public static class InsureRequest extends BaseRequest{

        /**	渠道的唯一标识*/
        public String channel_code;
        /**	真实姓名*/
        public String channel_user_name;
        /**  	联系方式*/
        public String channel_user_phone;
        /**  	电子邮箱*/
        public String channel_user_email;
        /**  	身份标示*/
        public String channel_user_code;
        /** 	详细地址*/
        public String channel_user_address;
        /**	银行名称(如广发银行)*/
        public String channel_bank_name;
        /**	开户行地址*/
        public String channel_bank_address;
        /**	银行卡号*/
        public String channel_bank_code;
        /**	银行预留电话*/
        public String channel_bank_phone;
        /**	省份*/
        public String channel_provinces;
        /**	城市*/
        public String channel_city;
        /**	区(县级)*/
        public String channel_county;

        public String price;

        public int  insured_days;


    }



}

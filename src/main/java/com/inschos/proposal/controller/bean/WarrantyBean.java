package com.inschos.proposal.controller.bean;

/**
 * Created by IceAnt on 2018/3/27.
 */
public class WarrantyBean {

    public static class InsureRequest extends BaseRequest{


        public String channel_code;
        
        public String insured_name;
        
        public String insured_phone;
        
        public String insured_email;
        
        public String insured_code;
        
        public String insured_address;
        
        public String bank_name;
        
        public String bank_address;
        
        public String bank_code;
        
        public String bank_phone;
        
        public String insured_province;
        
        public String insured_city;
        
        public String insured_county;

        public String price;

        public int  insured_days;

        public String warranty_uuid;

        public String channel_order_code;

    }



}

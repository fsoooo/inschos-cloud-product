package com.inschos.proposal.controller.bean;

/**
 * Created by IceAnt on 2018/3/27.
 */
public class WarrantyBean {

    public static class InsureRequest extends BaseRequest{

        /**	������Ψһ��ʶ*/
        public String channel_code;
        /**	��ʵ����*/
        public String channel_user_name;
        /**  	��ϵ��ʽ*/
        public String channel_user_phone;
        /**  	��������*/
        public String channel_user_email;
        /**  	��ݱ�ʾ*/
        public String channel_user_code;
        /** 	��ϸ��ַ*/
        public String channel_user_address;
        /**	��������(��㷢����)*/
        public String channel_bank_name;
        /**	�����е�ַ*/
        public String channel_bank_address;
        /**	���п���*/
        public String channel_bank_code;
        /**	����Ԥ���绰*/
        public String channel_bank_phone;
        /**	ʡ��*/
        public String channel_provinces;
        /**	����*/
        public String channel_city;
        /**	��(�ؼ�)*/
        public String channel_county;

        public String price;

        public int  insured_days;


    }



}

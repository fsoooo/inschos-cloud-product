package com.inschos.proposal.model;

/**
 * Created by IceAnt on 2018/4/3.
 */
public class Bank {

    public static int BANK_DEAL_TYPE_NOT_DELETE = 1;

    /** */
    public long id;

    /** 客户类型 1、个人 2、企业*/
    public int cust_type;

    /** */
    public long cust_id;

    /** 开户银行*/
    public String bank;

    /** */
    public String bank_code;

    /** 开户地址*/
    public String bank_city;

    /** */
    public int bank_deal_type;

    /** 预留手机*/
    public String phone;

    /** */
    public long created_at;

    /** */
    public long updated_at;


}

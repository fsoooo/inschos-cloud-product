package com.inschos.proposal.model;

/**
 * Created by IceAnt on 2018/5/25.
 */
public class CustWarrantyPayRecord {
    /** 主键*/
    public long id;

    /** 内部保单唯一标识*/
    public String warranty_uuid;

    /** 支付时间*/
    public long pay_time;

    /** 支付状态*/
    public int pay_status;

    /** 支付次数 第几次*/
    public int pay_count;

    /** 支付方式 1 银联 2 支付宝 3 微信 4现金*/
    public int pay_way;

    /** 支付账号*/
    public String pay_account;

    /** 手机号*/
    public String person_phone;

    /** 姓名*/
    public String person_name;

    /** 身份证号*/
    public String person_iccard;

    /** 创建时间*/
    public long created_at;

    /** 修改时间*/
    public long updated_at;

}

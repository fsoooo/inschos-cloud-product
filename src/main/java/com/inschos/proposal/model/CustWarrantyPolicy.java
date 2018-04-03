package com.inschos.proposal.model;

/**
 * Created by IceAnt on 2018/4/3.
 */
public class CustWarrantyPolicy {



    /** 主键*/
    public long id;

    /** 内部保单唯一标识*/
    public String warranty_uuid;

    /** 被保人单号*/
    public String out_order_no;

    /** 人员类型: 1投保人 2被保人 3受益人*/
    public int type;

    /** 被保人 投保人的（关系）*/
    public String relation_name;

    /** 姓名*/
    public String name;

    /** 证件类型（1为身份证，2为护照，3为军官证）*/
    public int card_type;

    /** 证件号*/
    public String card_code;

    /** 手机号*/
    public String phone;

    /** 职业*/
    public String occupation;

    /** 生日*/
    public String birthday;

    /** 性别 1 男 2 女 */
    public int sex;

    /** 邮箱*/
    public String email;

    /** 国籍*/
    public String nationality;

    /** 年收入*/
    public String annual_income;

    /** 身高*/
    public String height;

    /** 体重*/
    public String weight;

    /** 地区*/
    public String area;

    /** 详细地址*/
    public String address;

    /** 开始时间*/
    public long start_time;

    /** 结束时间*/
    public long end_time;


    public long created_at;

    public long updated_at;
}

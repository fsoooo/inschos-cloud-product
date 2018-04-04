package com.inschos.proposal.model;

/**
 * Created by IceAnt on 2018/4/3.
 */
public class Person {

    public final static int PAPERS_TYPE_ICCARD = 1;

    public final static int PERSON_TYPE_USER = 1;
    public final static int PERSON_TYPE_AGENT = 2;

    /** */
    public long id;

    /** 姓名*/
    public String name;

    /** 证件类型，1：身份证，2：护照，3：军官证，4：其他*/
    public int papers_type;

    /** 证件号*/
    public String papers_code;

    /** 身份证开始时间*/
    public String papers_start;

    /** 身份证结束时间*/
    public String papers_end;

    /** 1、男 2、女*/
    public int sex;

    /** 生日*/
    public String birthday;

    /** 家庭住址*/
    public String address;

    /** */
    public String address_detail;

    /** 联系方式*/
    public String phone;

    /** 邮件*/
    public String email;

    /** 邮编*/
    public String postcode;

    /** 客户类型，1：普通用户，2：代理人*/
    public int cust_type;

    /** 认证状态，1：未认证，2：已认证*/
    public int authentication;

    /** 身份证上面*/
    public String up_url;

    /** 身份证背面*/
    public String down_url;

    /** 身份证手持*/
    public String person_url;

    /** 头像*/
    public String head;

    /** 删除标识 1为已经删除*/
    public int del;

    /** 当前用户状态，1：正常，2：异常*/
    public int status;

    /** */
    public long created_at;

    /** */
    public long updated_at;


    public Bank bank;

    //  自定义

}

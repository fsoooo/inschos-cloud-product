package com.inschos.proposal.model;

/**
 * Created by IceAnt on 2018/4/2.
 */
public class CustWarranty {

    public final static int DEFAULT_INS_YADA_ID = -5001;

    public final static int USER_TYPE_PRO = 1;
    public final static int USER_TYPE_COM = 2;

    /** 主键*/
    public long id;

    /** 预投保编号，由保险公司返回*/
    public String union_order_code;

    /** 保单号*/
    public String warranty_code;

    /** 内部保单唯一标识*/
    public String warranty_uuid;

    /** 公司ID*/
    public long company_id;

    /** 客户ID*/
    public long user_id;

    /** 客户类型 1个人 2企业*/
    public int user_type;

    /** 代理人ID,为0则为用户自主购买*/
    public long agent_id;

    /** 渠道ID,为0则为用户自主购买*/
    public long ditch_id;

    /** 计划书ID,为0则为用户自主购买*/
    public long plan_id;

    /** 产品ID*/
    public long product_id;

    /** 保单价格*/
    public float premium;

    /** 起保时间*/
    public long start_time;

    /** 结束时间*/
    public long end_time;

    /** 保险公司ID*/
    public long ins_company_id;

    /** 购买份数*/
    public int count;

    /** 支付时间*/
    public long pay_time;

    /** 支付方式  1 银联 2 支付宝 3 微信*/
    public String pay_way;

    /** 分期方式 */
    public String by_stages_way;

    /** 佣金 0表示未结算，1表示已结算*/
    public int is_settlement;

    /** 电子保单下载地址*/
    public String warranty_url;

    /** 保单来源 1 自购 2线上成交 3线下成交 4导入*/
    public int warranty_from;

    /** 保单类型,1表示个人保单，2表示团险保单，3表示车险保单*/
    public int type;

    /** 核保状态（0, 1待核保，2核保失败，*/
    public int check_status;

    /** 支付状态 0未支付-核保成功，1支付中,2支付失败,3支付成功，*/
    public int pay_status;

    /** 保单状态 0,1保障中,2待生效,3待续保，4已失效，5已退保）*/
    public int warranty_status;

    /** 创建时间*/
    public long created_at;

    /** 结束时间*/
    public long updated_at;

    /** 删除标识 0删除 1可用*/
    public int state;


}

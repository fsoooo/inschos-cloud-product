package com.inschos.proposal.model;

/**
 * Created by IceAnt on 2018/4/2.
 */
public class CustWarranty {

    public final static int DEFAULT_INS_YADA_ID = -5001;

    public final static int USER_TYPE_PRO = 1;
    public final static int USER_TYPE_COM = 2;

    public final static int CHECK_STATUS_SUCCESS = 3;

    public final static int CHECK_STATUS_FAILED = 2;

    public final static int PAY_STATUS_WAITING = 0;
    public final static int PAY_STATUS_ING = 1;

    public final static int PAY_STATUS_SUCCESS = 2;

    public final static int PAY_STATUS_FAILED = 3;

    public final static int WARRANTY_STATUS_DAICHULI = 1;

    public final static int WARRANTY_STATUS_DAIZHIFU = 2;

    public final static int WARRANTY_STATUS_DAISHENGXIAO = 3;

    public final static int WARRANTY_STATUS_BAOZHANGZHONG = 4;

    public final static int WARRANTY_STATUS_YISHIXIAO = 6;

    public final static int WARRANTY_STATUS_YIGUOBAO = 8;


    /** 主键*/
    public long id;

    /** 内部保单唯一标识*/
    public String warranty_uuid;

    /** 投保单号*/
    public String pro_policy_no;

    /** 保单号*/
    public String warranty_code;

    /** 业务识别号*/
    public String business_no;

    /** 组合产品  0 不是  1是*/
    public int comb_product;

    /** 组合保单号*/
    public String comb_warranty_code;

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
    public String pay_time;

    /** 支付次数*/
    public int pay_count;

    /** 支付方式 1 银联 2 支付宝 3 微信 4现金*/
    public long pay_way;

    /** 分期方式*/
    public String by_stages_way;

    /** 佣金 0表示未结算，1表示已结算*/
    public int is_settlement;

    /** 电子保单下载地址*/
    public String warranty_url;

    /** 保单来源 1 自购 2线上成交 3线下成交 4导入*/
    public int warranty_from;

    /** 保单类型,1表示个人保单，2表示团险保单，3表示车险保单*/
    public int type;

    /** 核保状态（0,1核保中, 2核保失败，3核保成功*/
    public int check_status;

    /** 支付状态 0，1支付中,2支付失败,3支付成功，*/
    public int pay_status;

    /** 保单状态 1待处理, 2待支付,3待生效, 4保障中,5可续保，6已失效，7已退保  8已过保*/
    public int warranty_status;

    /** 投保回执信息*/
    public String resp_insure_msg;

    /** 支付回执信息*/
    public String resp_pay_msg;

    /** 创建时间*/
    public long created_at;

    /** 结束时间*/
    public long updated_at;

    /** 删除标识 0删除 1可用*/
    public int state;



    public String search_card_code;

    public long cur_time;

    public int search_warranty_status;


}

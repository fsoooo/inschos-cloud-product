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

    
    public long id;

    
    public String warranty_uuid;

    
    public String pro_policy_no;

    
    public String warranty_code;

    /** 业务识别号*/
    public String business_no;

    /** 组合产品  0 不是  1是*/
    public int comb_product;

    /** 组合保单号*/
    public String comb_warranty_code;



    public long company_id;

    
    public long user_id;

    
    public int user_type;

    
    public long agent_id;

    
    public long ditch_id;

    
    public long plan_id;

    
    public long product_id;

    
    public float premium;

    
    public long start_time;

    
    public long end_time;

    
    public long ins_company_id;

    
    public int count;

    public int pay_count;
    
    public long pay_time;

    
    public String pay_way;

    
    public String by_stages_way;

    
    public int is_settlement;

    
    public String warranty_url;

    
    public int warranty_from;

    
    public int type;

    
    public int check_status;

    
    public int pay_status;

    
    public int warranty_status;

    
    public long created_at;

    
    public long updated_at;

    
    public int state;


    public String search_card_code;


}

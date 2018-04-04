package com.inschos.proposal.model;

/**
 * Created by IceAnt on 2018/4/2.
 */
public class CustWarranty {

    public final static int DEFAULT_INS_YADA_ID = -5001;

    public final static int USER_TYPE_PRO = 1;
    public final static int USER_TYPE_COM = 2;

    public final static int CHECK_STATUS_SUCCESS = 3;

    public final static int PAY_STATUS_ING = 1;

    public final static int PAY_STATUS_SUCCESS = 2;

    public final static int PAY_STATUS_FAILED = 3;

    public final static int WARRANTY_STATUS_DAICHULI = 1;

    public final static int WARRANTY_STATUS_DAIZHIFU = 2;

    public final static int WARRANTY_STATUS_DAISHENGXIAO = 3;

    public final static int WARRANTY_STATUS_YISHIXIAO = 6;

    /** ����*/
    public long id;

    /** �ڲ�����Ψһ��ʶ*/
    public String warranty_uuid;

    /**  Ͷ�����ţ�ԤͶ�����ţ�*/
    public String pro_policy_no;

    /** ������*/
    public String warranty_code;

    /** ��˾ID*/
    public long company_id;

    /** �ͻ�ID*/
    public long user_id;

    /** �ͻ����� 1���� 2��ҵ*/
    public int user_type;

    /** ������ID,Ϊ0��Ϊ�û���������*/
    public long agent_id;

    /** ����ID,Ϊ0��Ϊ�û���������*/
    public long ditch_id;

    /** �ƻ���ID,Ϊ0��Ϊ�û���������*/
    public long plan_id;

    /** ��ƷID*/
    public long product_id;

    /** �����۸�*/
    public float premium;

    /** ��ʱ��*/
    public long start_time;

    /** ����ʱ��*/
    public long end_time;

    /** ���չ�˾ID*/
    public long ins_company_id;

    /** �������*/
    public int count;

    /** ֧��ʱ��*/
    public long pay_time;

    /** ֧����ʽ  1 ���� 2 ֧���� 3 ΢��*/
    public String pay_way;

    /** ���ڷ�ʽ */
    public String by_stages_way;

    /** Ӷ�� 0��ʾδ���㣬1��ʾ�ѽ���*/
    public int is_settlement;

    /** ���ӱ������ص�ַ*/
    public String warranty_url;

    /** ������Դ 1 �Թ� 2���ϳɽ� 3���³ɽ� 4����*/
    public int warranty_from;

    /** ��������,1��ʾ���˱�����2��ʾ���ձ�����3��ʾ���ձ���*/
    public int type;

    /** �˱�״̬��0, 1���˱���2�˱�ʧ�ܣ�*/
    public int check_status;

    /** ֧��״̬ 0δ֧��-�˱��ɹ���1֧����,2֧��ʧ��,3֧���ɹ���*/
    public int pay_status;

    /** ����״̬ 1������, 2��֧��,3����Ч, ,4������,5��������6��ʧЧ��7���˱� */
    public int warranty_status;

    /** ����ʱ��*/
    public long created_at;

    /** ����ʱ��*/
    public long updated_at;

    /** ɾ����ʶ 0ɾ�� 1����*/
    public int state;


    public String search_card_code;


}

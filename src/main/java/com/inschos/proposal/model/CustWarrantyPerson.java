package com.inschos.proposal.model;

/**
 * Created by IceAnt on 2018/4/3.
 */
public class CustWarrantyPerson {


    public static int POLICY_TYPE_APPLY = 1;

    public static int POLICY_TYPE_RECOGNIZEE = 2;

    public static int POLICY_TYPE_BENEFICIARY = 3;

    /** ����*/
    public long id;

    /** �ڲ�����Ψһ��ʶ*/
    public String warranty_uuid;

    /** �����˵���*/
    public String out_order_no;

    /** ��Ա����: 1Ͷ���� 2������ 3������*/
    public int type;

    /** ������ Ͷ���˵ģ���ϵ��*/
    public String relation_name;

    /** ����*/
    public String name;

    /** */
    public int card_type;

    /** ֤����*/
    public String card_code;

    /** �ֻ���*/
    public String phone;

    /** ְҵ*/
    public String occupation;

    /** ����*/
    public String birthday;

    /** �Ա� 1 �� 2 Ů */
    public int sex;

    public int age;

    /** ����*/
    public String email;

    /** ����*/
    public String nationality;

    /** ������*/
    public String annual_income;

    /** ���*/
    public String height;

    /** ����*/
    public String weight;

    /** ����*/
    public String area;

    /** ��ϸ��ַ*/
    public String address;

    /** ��ʼʱ��*/
    public long start_time;

    /** ����ʱ��*/
    public long end_time;


    public long created_at;

    public long updated_at;


}

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

    /** ����*/
    public String name;

    /** ֤�����ͣ�1�����֤��2�����գ�3������֤��4������*/
    public int papers_type;

    /** ֤����*/
    public String papers_code;

    /** ���֤��ʼʱ��*/
    public String papers_start;

    /** ���֤����ʱ��*/
    public String papers_end;

    /** 1���� 2��Ů*/
    public int sex;

    /** ����*/
    public String birthday;

    /** ��ͥסַ*/
    public String address;

    /** */
    public String address_detail;

    /** ��ϵ��ʽ*/
    public String phone;

    /** �ʼ�*/
    public String email;

    /** �ʱ�*/
    public String postcode;

    /** �ͻ����ͣ�1����ͨ�û���2��������*/
    public int cust_type;

    /** ��֤״̬��1��δ��֤��2������֤*/
    public int authentication;

    /** ���֤����*/
    public String up_url;

    /** ���֤����*/
    public String down_url;

    /** ���֤�ֳ�*/
    public String person_url;

    /** ͷ��*/
    public String head;

    /** ɾ����ʶ 1Ϊ�Ѿ�ɾ��*/
    public int del;

    /** ��ǰ�û�״̬��1��������2���쳣*/
    public int status;

    /** */
    public long created_at;

    /** */
    public long updated_at;


    public Bank bank;

    //  �Զ���

}

package com.inschos.proposal.service.impl;

import com.inschos.proposal.kit.StringKit;
import com.inschos.proposal.kit.TimeKit;
import com.inschos.proposal.mapper.CustWarrantyMapper;
import com.inschos.proposal.mapper.CustWarrantyPersonMapper;
import com.inschos.proposal.model.CustWarranty;
import com.inschos.proposal.model.CustWarrantyPerson;
import com.inschos.proposal.service.CustWarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IceAnt on 2018/4/3.
 */
@Service
public class CustWarrantyServiceImpl extends BaseServiceImpl implements CustWarrantyService {

    @Autowired
    private CustWarrantyMapper custWarrantyMapper;

    @Autowired
    private CustWarrantyPersonMapper custWarrantyPersonMapper;



    @Override
    public int insure(CustWarranty record,CustWarrantyPerson warrantyPerson) {
        long currentTime = TimeKit.currentTimeMillis();
        int result = 0;
        if(record!=null && warrantyPerson !=null){
            record.search_card_code = warrantyPerson.card_code;
            if(custWarrantyMapper.findExists(record)==null){
                result = custWarrantyMapper.insert(record);
                warrantyPerson.warranty_uuid = record.warranty_uuid;
                if(result>0){

                    result = addPolicy(warrantyPerson, CustWarrantyPerson.POLICY_TYPE_APPLY);

                    if(result>0){
                        result = addPolicy(warrantyPerson, CustWarrantyPerson.POLICY_TYPE_RECOGNIZEE);

                        if(result>0){
                            result = addPolicy(warrantyPerson, CustWarrantyPerson.POLICY_TYPE_BENEFICIARY);
                        }
                    }
                    if(result==0){
                        rollBack();
                    }
                }
            }

        }
        return result;
    }

    @Override
    public CustWarranty findOne(int id) {
        return id<=0?null:custWarrantyMapper.findOne(id);
    }

    @Override
    public CustWarranty findExists(CustWarranty warranty) {
        return warranty!=null?custWarrantyMapper.findExists(warranty):null;
    }


    @Override
    public CustWarranty findUuid(String uuid) {
        return StringKit.isEmpty(uuid)?null:custWarrantyMapper.findUuid(uuid);
    }

    @Override
    public int changeWarrantyStatus(CustWarranty record) {
        return 0;
    }

    @Override
    public int updateProInfo(CustWarranty record) {
        return record!=null?custWarrantyMapper.updateProInfo(record):0;
    }


    private int addPolicy(CustWarrantyPerson warrantyPerson, int type){

        CustWarrantyPerson addRecord = new CustWarrantyPerson();
        addRecord.relation_name = warrantyPerson.relation_name;
        addRecord.name = warrantyPerson.name;
        addRecord.phone = warrantyPerson.phone;
        addRecord.email = warrantyPerson.email;
        addRecord.card_type = warrantyPerson.card_type;
        addRecord.card_code = warrantyPerson.card_code;
        addRecord.address = warrantyPerson.address;
        addRecord.warranty_uuid = warrantyPerson.warranty_uuid;
        addRecord.created_at = addRecord.updated_at = TimeKit.currentTimeMillis();
        addRecord.type = type;
        addRecord.start_time = warrantyPerson.start_time;
        addRecord.end_time = warrantyPerson.end_time;
        addRecord.sex = warrantyPerson.sex;
        addRecord.birthday = warrantyPerson.birthday;
        addRecord.age = warrantyPerson.age;
        return custWarrantyPersonMapper.insert(addRecord);
    }
}

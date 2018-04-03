package com.inschos.proposal.service.impl;

import com.inschos.proposal.kit.StringKit;
import com.inschos.proposal.kit.TimeKit;
import com.inschos.proposal.mapper.BankMapper;
import com.inschos.proposal.mapper.CustWarrantyMapper;
import com.inschos.proposal.mapper.PersonMapper;
import com.inschos.proposal.model.CustWarranty;
import com.inschos.proposal.model.CustWarrantyPolicy;
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
    private BankMapper bankMapper;
    @Autowired
    private PersonMapper personMapper;



    @Override
    public int insure(CustWarranty record,CustWarrantyPolicy policy) {
        long currentTime = TimeKit.currentTimeMillis();

        int result = custWarrantyMapper.insert(record);

        if(result>0){
            CustWarrantyPolicy applyer = new CustWarrantyPolicy();

            applyer.name = policy.name;
            applyer.phone = policy.phone;
            applyer.email = policy.email;
            applyer.card_type = policy.card_type;
            applyer.card_code = policy.card_code;
            applyer.address = policy.address;
            applyer.warranty_uuid = record.warranty_uuid;
            applyer.created_at = applyer.updated_at = currentTime;
//            applyer.type = CustWarrantyPolicy.class


        }


        return record!=null?custWarrantyMapper.insert(record):0;
    }

    @Override
    public CustWarranty findOne(int id) {
        return id<=0?null:custWarrantyMapper.findOne(id);
    }

    @Override
    public CustWarranty findUuid(String uuid) {
        return StringKit.isEmpty(uuid)?null:custWarrantyMapper.findUuid(uuid);
    }
}

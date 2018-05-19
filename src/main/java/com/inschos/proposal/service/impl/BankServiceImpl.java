package com.inschos.proposal.service.impl;

import com.inschos.proposal.mapper.BankMapper;
import com.inschos.proposal.model.Bank;
import com.inschos.proposal.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IceAnt on 2018/4/4.
 */
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankMapper bankMapper;

    @Override
    public int join(Bank record) {

        int result = 0;
        if(record!=null){
            Bank exists = bankMapper.findByUserAndCode(record);
            if(exists==null){
                result = bankMapper.insert(record);
            }
        }
        return result;
    }

    @Override
    public Bank findByUserAndCode(Bank search) {
        return search!=null?bankMapper.findByUserAndCode(search):null;
    }


}

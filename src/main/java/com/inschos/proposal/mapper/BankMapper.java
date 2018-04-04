package com.inschos.proposal.mapper;

import com.inschos.proposal.model.Bank;

/**
 * Created by IceAnt on 2018/4/3.
 */
public interface BankMapper {

    int insert(Bank record);

    int update(Bank update);

    Bank findOne(int id);

    Bank findByUserAndCode(Bank search);
}

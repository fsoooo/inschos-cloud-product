package com.inschos.proposal.service;

import com.inschos.proposal.model.Bank;

/**
 * Created by IceAnt on 2018/4/3.
 */
public interface BankService {

    int join(Bank record);

    Bank findByUserAndCode(Bank search);
}

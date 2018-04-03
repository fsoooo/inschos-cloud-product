package com.inschos.proposal.service;

import com.inschos.proposal.model.CustWarranty;
import com.inschos.proposal.model.CustWarrantyPolicy;

/**
 * Created by IceAnt on 2018/4/3.
 */
public interface CustWarrantyService {

    int insure(CustWarranty record,CustWarrantyPolicy policy);

    CustWarranty findOne(int id);

    CustWarranty findUuid(String uuid);
}

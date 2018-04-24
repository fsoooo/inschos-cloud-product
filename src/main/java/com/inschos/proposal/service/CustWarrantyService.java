package com.inschos.proposal.service;

import com.inschos.proposal.model.CustWarranty;
import com.inschos.proposal.model.CustWarrantyPerson;

/**
 * Created by IceAnt on 2018/4/3.
 */
public interface CustWarrantyService {

    int insure(CustWarranty record,CustWarrantyPerson warrantyPerson);

    CustWarranty findOne(int id);

    CustWarranty findExists(CustWarranty warranty);

    CustWarranty findUuid(String uuid);

    CustWarranty findByProPolicyNo(String proPolicyNo);

    int changeWarrantyStatus(CustWarranty record);

    int updateProInfo(CustWarranty record);
}

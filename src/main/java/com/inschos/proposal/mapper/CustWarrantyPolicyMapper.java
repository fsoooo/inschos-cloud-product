package com.inschos.proposal.mapper;

import com.inschos.proposal.model.CustWarrantyPolicy;

/**
 * Created by IceAnt on 2018/4/3.
 */
public interface CustWarrantyPolicyMapper {

    int insert(CustWarrantyPolicy record);

    int update(CustWarrantyPolicy update);

    CustWarrantyPolicy findOne(int id);

}

package com.inschos.proposal.mapper;

import com.inschos.proposal.model.CustWarranty;

/**
 * Created by IceAnt on 2018/4/3.
 */
public interface CustWarrantyMapper {

    int insert(CustWarranty record);

    int update(CustWarranty update);

    CustWarranty findOne(int id);

    CustWarranty findUuid(String uuid);

}

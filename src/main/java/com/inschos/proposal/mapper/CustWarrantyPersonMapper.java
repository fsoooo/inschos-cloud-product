package com.inschos.proposal.mapper;

import com.inschos.proposal.model.CustWarrantyPerson;

/**
 * Created by IceAnt on 2018/4/3.
 */
public interface CustWarrantyPersonMapper {

    int insert(CustWarrantyPerson record);

    int update(CustWarrantyPerson update);

    CustWarrantyPerson findOne(int id);

}

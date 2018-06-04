package com.inschos.proposal.mapper;

import com.inschos.proposal.model.CustWarranty;

import java.util.List;

/**
 * Created by IceAnt on 2018/4/3.
 */
public interface CustWarrantyMapper {

    int insert(CustWarranty record);

    int update(CustWarranty update);

    CustWarranty findOne(int id);

    CustWarranty findUuid(String uuid);

    int updateWarrantyInfo(CustWarranty record);

    int updateProInfo(CustWarranty record);

    CustWarranty findExists(CustWarranty search);

    CustWarranty findByProPolicyNo(String proPolicyNo);

    int updateToInvalid(CustWarranty update);

    List<CustWarranty> findPageToInvalid(CustWarranty search);

}

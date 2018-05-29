package com.inschos.proposal.mapper;

import com.inschos.proposal.model.CustWarrantyPayRecord;

/**
 * Created by IceAnt on 2018/5/25.
 */
public interface CustWarrantyPayRecordMapper {

    int insert(CustWarrantyPayRecord record);

    int update(CustWarrantyPayRecord record);

    CustWarrantyPayRecord selectOne(long id);
}

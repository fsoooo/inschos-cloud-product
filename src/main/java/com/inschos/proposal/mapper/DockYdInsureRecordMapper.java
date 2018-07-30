package com.inschos.proposal.mapper;

import com.inschos.proposal.model.DockYdInsureRecord;

/**
 * author   meiming_mm@163.com
 * date     2018/7/30
 * version  v1.0.0
 */
public interface DockYdInsureRecordMapper {

    int insert(DockYdInsureRecord record);

    int update(DockYdInsureRecord update);

    DockYdInsureRecord selectOneByWUuid(String uuid);

}

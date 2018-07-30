package com.inschos.proposal.service.impl;

import com.inschos.proposal.mapper.DockYdInsureRecordMapper;
import com.inschos.proposal.model.DockYdInsureRecord;
import com.inschos.proposal.service.DockYdInsureRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * author   meiming_mm@163.com
 * date     2018/7/30
 * version  v1.0.0
 */
@Repository
public class DockYdInsureRecordDaoImpl implements DockYdInsureRecordDao {

    @Autowired
    private DockYdInsureRecordMapper dockYdInsureRecordMapper;


    @Override
    public int insert(DockYdInsureRecord record) {
        return dockYdInsureRecordMapper.insert(record);
    }

    @Override
    public int update(DockYdInsureRecord update) {
        return dockYdInsureRecordMapper.update(update);
    }

    @Override
    public DockYdInsureRecord findOneByWUuid(String uuid) {
        return dockYdInsureRecordMapper.selectOneByWUuid(uuid);
    }
}

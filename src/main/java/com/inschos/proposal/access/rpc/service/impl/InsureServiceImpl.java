package com.inschos.proposal.access.rpc.service.impl;


import com.inschos.dock.api.InsureService;
import com.inschos.dock.bean.*;
import com.inschos.proposal.kit.JsonKit;
import com.inschos.proposal.model.DockYdInsureRecord;
import com.inschos.proposal.remote.action.InsureRemote;
import com.inschos.proposal.service.DockYdInsureRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author   meiming_mm@163.com
 * date     2018/7/27
 * version  v1.0.0
 */
@Service
public class InsureServiceImpl implements InsureService {

    @Autowired
    private InsureRemote insureRemote;
    @Autowired
    private DockYdInsureRecordDao dockYdInsureRecordDao;

    /**
     * 预投保
     *
     * @param bean
     * @return
     */
    @Override
    public RpcResponse<RspPreInsureBean> preInsure(InsureBean bean) {

        return null;
    }

    /**
     * 投保 -获取支付信息
     *
     * @param bean
     * @return
     */
    @Override
    public RpcResponse<RspInsureBean> insure(InsureBean bean) {
        RpcResponse<RspInsureBean> response = new RpcResponse<>();
        if (bean != null) {

            DockYdInsureRecord record = new DockYdInsureRecord();
            record.warranty_uuid = bean.warrantyUuid;
            record.requst_json = JsonKit.bean2Json(bean);

            int result = dockYdInsureRecordDao.insert(record);

            if (result > 0) {
                response.code = RpcResponse.CODE_SUCCESS;
                response.message = "投保提交成功";
                return response;
            }

        }
        response.code = RpcResponse.CODE_FAILED;
        response.message = "投保提交失败";
        return response;
    }

    /**
     * 支付
     *
     * @param bean
     * @return
     */
    @Override
    public RpcResponse<RspPayBean> pay(PayBean bean) {

        if (bean != null) {

            DockYdInsureRecord record = dockYdInsureRecordDao.findOneByWUuid(bean.payNo);
            if(record!=null){

                InsureBean insureBean = JsonKit.json2Bean(record.requst_json, InsureBean.class);
                insureRemote.insure(insureBean, bean);
            }
        }

        return null;
    }

    /**
     * 出单
     *
     * @param bean
     * @return
     */
    @Override
    public RpcResponse<RspIssueBean> issue(IssueBean bean) {
        return null;
    }
}

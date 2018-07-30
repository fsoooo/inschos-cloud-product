package com.inschos.proposal.access.rpc.service.impl;


import com.inschos.common.assist.kit.StringKit;
import com.inschos.dock.api.InsureService;
import com.inschos.dock.bean.*;
import com.inschos.proposal.kit.JsonKit;
import com.inschos.proposal.kit.L;
import com.inschos.proposal.kit.XmlKit;
import com.inschos.proposal.model.DockYdInsureRecord;
import com.inschos.proposal.remote.action.InsureRemote;
import com.inschos.proposal.remote.bean.TYInsProposalBean;
import com.inschos.proposal.service.DockYdInsureRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            RpcResponse<RspPayBean> rpcResponse = new RpcResponse<>();
            DockYdInsureRecord record = dockYdInsureRecordDao.findOneByWUuid(bean.payNo);
            if(record!=null){

                InsureBean insureBean = JsonKit.json2Bean(record.requst_json, InsureBean.class);
                String call = insureRemote.insure(insureBean, bean);

                boolean isRequestSuccess = false;
                if (!StringKit.isEmpty(call)) {
                    TYInsProposalBean.TYInsProposalResponse response = XmlKit.xml2Bean(call, TYInsProposalBean.TYInsProposalResponse.class);
                    if (response != null && response.headDto != null) {

                        if ("0".equals(response.headDto.responseCode)) {

                            if (response.mainDto != null) {


                                if ("1".equals(response.mainDto.result) || "2".equals(response.mainDto.result) || "4".equals(response.mainDto.result)) {
                                    isRequestSuccess = true;
                                    List<TYInsProposalBean.ProToPoDto> poDtoList = response.mainDto.proToPoDtoList;
                                    List<String> proPolicyNos = new ArrayList<>();

                                    if (poDtoList != null) {
                                        for (TYInsProposalBean.ProToPoDto proToPoDto : poDtoList) {
                                            proPolicyNos.add(proToPoDto.proposalNo);
                                        }
                                    }
                                    rpcResponse.data = new RspPayBean();
                                    if (!proPolicyNos.isEmpty()) {

                                        rpcResponse.data.isCombProduct = true;
                                        rpcResponse.data.combCardCode = response.mainDto.cardPolicyNo;
                                        rpcResponse.data.proposalNo = StringKit.join(proPolicyNos, ",");

                                        rpcResponse.message = response.mainDto.message + (StringKit.isEmpty(response.mainDto.errMsg) ? "" : ("[" + response.mainDto.errMsg + "]"));
                                        rpcResponse.code = RpcResponse.CODE_SUCCESS;

                                    }

                                } else {
                                    rpcResponse.data = new RspPayBean();
                                    List<TYInsProposalBean.ProToPoDto> poDtoList = response.mainDto.proToPoDtoList;
                                    List<String> proPolicyNos = new ArrayList<>();
                                    if (poDtoList != null) {
                                        for (TYInsProposalBean.ProToPoDto proToPoDto : poDtoList) {
                                            proPolicyNos.add(proToPoDto.proposalNo);
                                        }
                                    }
                                    rpcResponse.data.proposalNo = StringKit.join(proPolicyNos, ",");
                                    rpcResponse.data.isCombProduct = true;
                                    rpcResponse.data.combCardCode = response.mainDto.cardPolicyNo;

                                    rpcResponse.message = StringKit.isEmpty(response.mainDto.errMsg) ? response.mainDto.message : response.mainDto.errMsg;
                                    rpcResponse.code = RpcResponse.CODE_FAILED;
                                }
                            } else {
                                rpcResponse.message = !StringKit.isEmpty(response.headDto.errorCode) ? (response.headDto.errorCode + "|" + response.headDto.errorMessage) : (!StringKit.isEmpty(response.headDto.esbCode) ? (response.headDto.esbCode + "|" + response.headDto.esbMessage) : null);
                                rpcResponse.code = RpcResponse.CODE_FAILED;
                            }


                        } else {
                            rpcResponse.message = !StringKit.isEmpty(response.headDto.errorCode) ? (response.headDto.errorCode + "|" + response.headDto.errorMessage) : (!StringKit.isEmpty(response.headDto.esbCode) ? (response.headDto.esbCode + "|" + response.headDto.esbMessage) : null);
                            rpcResponse.code = RpcResponse.CODE_FAILED;
                            L.log.error("insure response  failed msg:{}", call);
                        }
                    } else {
                        L.log.error("insure response  failed msg:{}", call);
                    }
                }


                if(rpcResponse.code==0){
                    rpcResponse.code = RpcResponse.CODE_FAILED;
                    rpcResponse.message = "支付失败";
                }
            }
            return rpcResponse;
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

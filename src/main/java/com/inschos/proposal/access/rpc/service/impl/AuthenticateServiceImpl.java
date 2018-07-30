package com.inschos.proposal.access.rpc.service.impl;


import com.inschos.common.assist.kit.StringKit;
import com.inschos.dock.api.PayAuthService;
import com.inschos.dock.bean.*;
import com.inschos.proposal.model.Bank;
import com.inschos.proposal.remote.action.AuthenticateRemote;
import com.inschos.proposal.remote.bean.TyAuthenticateBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IceAnt on 2018/6/21.
 */
@Service
public class AuthenticateServiceImpl implements PayAuthService {

    @Autowired
    private AuthenticateRemote authenticateRemote;

    /**
     * 银行鉴权申请
     *
     * @param bean
     * @return
     */
    @Override
    public RpcResponse<RspBankApplyBean> bankApplyAuth(BankBean bean) {
        if (bean == null) {
            return null;
        }
        RpcResponse<RspBankApplyBean> rpcResponse = new RpcResponse<>();


        Bank bank = new Bank();
        bank.bank_code = bean.bankCode;
        bank.phone = bean.phone;
        bank.userIdCard = bean.idCard;
        bank.userName = bean.userName;

        TyAuthenticateBean.TyAuthenticateResponse remoteResponse = authenticateRemote.sendAuth(bank);


        boolean isErr = false;
        String errMsg = null;
        if (remoteResponse != null) {
            if (remoteResponse.mainDto != null) {
                if ("1".equals(remoteResponse.mainDto.result)) {
                    rpcResponse.data = new RspBankApplyBean();
                    rpcResponse.data.requestId = remoteResponse.mainDto.requestId;
                    //发送成功
                    rpcResponse.code = RpcResponse.CODE_SUCCESS;
                    rpcResponse.message = remoteResponse.mainDto.resultMsg;
                    return rpcResponse;
                } else {
                    if (!StringKit.isEmpty(remoteResponse.mainDto.resultMsg)) {
                        String[] split = StringKit.split(remoteResponse.mainDto.resultMsg, "\\|");
                        if (split != null) {
                            errMsg = split.length > 1 ? split[1] : split[0];
                            isErr = true;
                        }
                    }
                }
            }
            if (!isErr) {
                if (remoteResponse.headDto != null) {
                    errMsg = !StringKit.isEmpty(remoteResponse.headDto.errorMessage) ? remoteResponse.headDto.errorMessage
                            : (!StringKit.isEmpty(remoteResponse.headDto.esbMessage) ? remoteResponse.headDto.esbMessage : "短信发送失败");
                    isErr = true;
                }
            }
        }
        //发送失败
        if (!isErr) {
            errMsg = "短信发送失败";
        }
        rpcResponse.code = RpcResponse.CODE_FAILED;
        rpcResponse.message = errMsg;

        return rpcResponse;
    }

    /**
     * 银行鉴权确认
     *
     * @param bean
     * @return
     */
    @Override
    public RpcResponse<RspBankConfirmBean> bankConfirmAuth(BankConfirmBean bean) {
        if (bean == null) {
            return null;
        }
        RpcResponse<RspBankConfirmBean> rpcResponse = new RpcResponse<>();
        TyAuthenticateBean.TyAuthConfirmResponse remoteResponse = authenticateRemote.confirmAuth(bean.requestId, bean.vdCode);
        boolean isErr = false;
        String errMsg = null;
        if (remoteResponse != null) {
            if (remoteResponse.mainDto != null) {
                if ("1".equals(remoteResponse.mainDto.result)) {
                    rpcResponse.code = RpcResponse.CODE_SUCCESS;
                    rpcResponse.message = StringKit.isEmpty(remoteResponse.mainDto.resultMsg) ? "绑卡成功" : remoteResponse.mainDto.resultMsg;
                    //鉴权成功返回
                    return rpcResponse;

                } else {
                    if (!StringKit.isEmpty(remoteResponse.mainDto.resultMsg)) {
                        String[] split = StringKit.split(remoteResponse.mainDto.resultMsg, "\\|");
                        if (split != null) {
                            errMsg = split.length > 1 ? split[1] : split[0];
                            isErr = true;
                        }
                    }
                }
            }
            if (!isErr) {
                if (remoteResponse.headDto != null) {
                    errMsg = !StringKit.isEmpty(remoteResponse.headDto.errorMessage) ? remoteResponse.headDto.errorMessage
                            : (!StringKit.isEmpty(remoteResponse.headDto.esbMessage) ? remoteResponse.headDto.esbMessage : "绑卡失败");
                    isErr = true;
                }
            }
        }

        rpcResponse.code = RpcResponse.CODE_FAILED;
        rpcResponse.message = errMsg != null ? errMsg : "绑卡失败";
        return rpcResponse;
    }
}

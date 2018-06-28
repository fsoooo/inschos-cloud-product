package com.inschos.proposal.controller.action;

import com.inschos.proposal.controller.bean.AuthenticateBean;
import com.inschos.proposal.controller.bean.BaseResponse;
import com.inschos.proposal.kit.JsonKit;
import com.inschos.proposal.kit.StringKit;
import com.inschos.proposal.model.Bank;
import com.inschos.proposal.remote.action.AuthenticateRemote;
import com.inschos.proposal.remote.bean.TyAuthenticateBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * author   meiming
 * version  v1.0.0
 * date     2018/6/27
 */
@Component
public class AuthenticateAction extends BaseAction {

    @Autowired
    private AuthenticateRemote authenticateRemote;

    public String applyAuth(String body) {
        AuthenticateBean.ApplyAuthRequest request = JsonKit.json2Bean(body, AuthenticateBean.ApplyAuthRequest.class);
        AuthenticateBean.ApplyAuthResponse response = new AuthenticateBean.ApplyAuthResponse();
        boolean isValid = false;
        String errMsg = null;
        if (request != null) {
            isValid = true;
            if (StringKit.isEmpty(request.phone)) {
                isValid = false;
                errMsg = "手机号不符";
            }
            if (isValid && StringKit.isEmpty(request.name)) {
                isValid = false;
                errMsg = "姓名不能为空";
            }
            if (isValid && StringKit.isEmpty(request.idCard)) {
                isValid = false;
                errMsg = "身份证不能为空";
            }
            if (isValid && StringKit.isEmpty(request.bankCode)) {
                isValid = false;
                errMsg = "银行卡号不能为空";
            }

        } else {
            errMsg = "请填写信息";
        }

        if (isValid) {
            Bank bank = new Bank();
            bank.phone = request.phone;
            bank.userName = request.name;
            bank.userIdCard = request.idCard;
            bank.bank_code = request.bankCode;
            TyAuthenticateBean.TyAuthenticateResponse remoteResponse = authenticateRemote.sendAuth(bank);
            boolean isErr = false;
            if (remoteResponse != null) {
                if (remoteResponse.mainDto != null) {
                    if ("1".equals(remoteResponse.mainDto.result)) {
                        response.data = new AuthenticateBean.ApplyData();
                        response.data.requestId = remoteResponse.mainDto.requestId;
                        return json(BaseResponse.CODE_SUCCESS, "短信已发送", response);
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
            if (!isErr) {
                errMsg = "短信发送失败";
            }
            return json(BaseResponse.CODE_FAILED, errMsg);
        } else {
            return json(BaseResponse.CODE_FAILED, errMsg);
        }

    }

    public String confirmAuth(String body) {
        AuthenticateBean.ConfirmAuthRequest request = JsonKit.json2Bean(body, AuthenticateBean.ConfirmAuthRequest.class);
        boolean isValid = false;
        String errMsg = null;
        if (request != null) {

            if (StringKit.isEmpty(request.requestId)) {
                errMsg = "验证失效，请重新获取";
            } else {
                isValid = true;
            }
            if (isValid && StringKit.isEmpty(request.vdCode)) {
                isValid = false;
                errMsg = "请填写验证码";
            }
        } else {
            errMsg = "请填写验证码";
        }
        if (isValid) {
            TyAuthenticateBean.TyAuthConfirmResponse remoteResponse = authenticateRemote.confirmAuth(request.requestId, request.vdCode);
            boolean isErr = false;
            if (remoteResponse != null) {
                if (remoteResponse.mainDto != null) {
                    if ("1".equals(remoteResponse.mainDto.result)) {

                        return json(BaseResponse.CODE_SUCCESS, StringKit.isEmpty(remoteResponse.mainDto.resultMsg)?"绑卡成功":remoteResponse.mainDto.resultMsg);

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
            if (!isErr) {
                errMsg = "绑卡失败";
            }

        }
        return json(BaseResponse.CODE_FAILED,errMsg!=null?errMsg:"绑卡失败");
    }

}

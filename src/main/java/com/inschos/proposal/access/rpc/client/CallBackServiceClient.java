package com.inschos.proposal.access.rpc.client;

import com.inschos.common.assist.kit.L;
import com.inschos.dock.api.CallBackService;
import com.inschos.dock.bean.RpcResponse;
import com.inschos.dock.bean.RspPayBean;
import hprose.client.HproseHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * author   meiming_mm@163.com
 * date     2018/7/27
 * version  v1.0.0
 */
@Component
public class CallBackServiceClient {

    @Value("${rpc.remote.trading.host}")
    private String host;

    private final String uri = "/rpc/call_back";


    private CallBackService getService() {
        return new HproseHttpClient(host + uri).useService(CallBackService.class);
    }


    public void outPolicy(RpcResponse<RspPayBean> payBean) {
        try {
            getService().outPolicy(payBean);
        } catch (Exception e) {
            L.log.error("rpc remote failed {}", e);
        }
    }

}

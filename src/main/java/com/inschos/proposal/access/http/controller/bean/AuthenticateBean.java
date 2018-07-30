package com.inschos.proposal.access.http.controller.bean;

/**
 * author   meiming_mm@163.com
 * date     2018/6/27
 * version  v1.0.0
 */
public class AuthenticateBean {

    public static class ApplyAuthRequest extends BaseRequest{

        public String name;

        public String phone;

        public String bankCode;

        public String idCard;
    }

    public static class ApplyAuthResponse extends BaseResponse{
        public ApplyData data;
    }

    public static class ConfirmAuthRequest extends BaseRequest{

        public String requestId;

        public String vdCode;
    }

    public static class ApplyData{
        public String requestId;
    }

}

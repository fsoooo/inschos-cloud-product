package com.inschos.proposal.controller.bean;

/**
 * Created by IceAnt on 2018/3/27.
 */
public class BaseResponse {

    public final static int CODE_SUCCESS = 200;
    public final static int CODE_FAILED = 500;

    public int code;
    public String message;
    public Object data;
}

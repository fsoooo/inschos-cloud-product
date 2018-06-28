package com.inschos.proposal.controller.action;

import com.inschos.proposal.controller.bean.BaseResponse;
import com.inschos.proposal.kit.JsonKit;

/**
 * Created by IceAnt on 2018/3/27.
 */
public class BaseAction {

    protected String json(int code, String message){
        BaseResponse response = new BaseResponse();
        response.code = code;
        response.message = message;
        return JsonKit.bean2Json(response);
    }


    protected String json(int code, String message,BaseResponse response){
        if(response==null){
            response = new BaseResponse();
        }
        response.code = code;
        response.message = message;
        return JsonKit.bean2Json(response);
    }


}

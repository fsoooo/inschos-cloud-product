package com.inschos.proposal.controller.bean;

/**
 * Created by IceAnt on 2018/3/27.
 */
public class BaseRequest {

    private final static String REQUEST_SIGN_KEY_INSCHOS = "inshcos";

    private final static String REQUEST_SIGN_KEY_SECRET = "2af494988242dc34d41fa5531f7fb19d";


    public String signKey;

    public String sign;

    public static String  getSecret(String signKey){
        String signSecret = null;
        switch (signKey){
            case REQUEST_SIGN_KEY_INSCHOS:
                signSecret = REQUEST_SIGN_KEY_SECRET;
                break;
        }
        return signSecret;
    }
}

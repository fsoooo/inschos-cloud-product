package com.inschos.proposal;

import com.inschos.proposal.kit.Des3Util;

/**
 * Created by IceAnt on 2018/3/24.
 */
public class Logic {

    public String packageInfo(String xml,String type){

        try {
            String encode = Des3Util.encode(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TCP报文头标志
        switch (type){
            case "CarType":
//                $head = $this->channelTradeCodeCarType.$this->indicate;
                break;
            case "Fee":
//                $head = $this->channelTradeCodeFee.$this->indicate;
                break;
            case "Insure":
//                $head = $this->channelTradeCodeInsure.$this->indicate;
                break;
        }

        return null;
    }
}

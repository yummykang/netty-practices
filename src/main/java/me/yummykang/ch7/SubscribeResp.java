package me.yummykang.ch7;

import java.io.Serializable;

/**
 * write some dec. here.
 * Created by Demon on 2016/11/14 0014.
 */
public class SubscribeResp implements Serializable {
    private static final long serialVersionUID = 1L;

    private int subReqId;

    private int respCode;

    private String desc;

    public int getSubReqId() {
        return subReqId;
    }

    public void setSubReqId(int subReqId) {
        this.subReqId = subReqId;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        String result = "SubscribeResp [subReqId=" + subReqId + ", respCode=" + respCode + ", desc" + desc + "]";
        return result;
    }
}

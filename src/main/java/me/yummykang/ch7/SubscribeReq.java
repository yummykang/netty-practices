package me.yummykang.ch7;

import java.io.Serializable;

/**
 * write some dec. here.
 * Created by Demon on 2016/11/14 0014.
 */
public class SubscribeReq implements Serializable {
    private static final long serialVersionUID = 1L;

    private int subReqId;

    private String userName;

    private String productName;

    private String phoneNumber;

    private String address;

    public int getSubReqId() {
        return subReqId;
    }

    public void setSubReqId(int subReqId) {
        this.subReqId = subReqId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        String result = "SubscribeReq [subReqId=" + subReqId + ", userName=" + userName
                + ", productName=" + productName + ", phoneNumber=" + phoneNumber
                + ", address=" + address + "]";
        return result;
    }
}

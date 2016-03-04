package com.xiongyingqi.vo;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author xiongyingqi
 * @version 2015-12-10 10:51
 */
@JsonRootName("payTypeAmountInfo")
public class PayTypeAmountInfo {
    private int    amount;
    private String bizNo;
    private String payType;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "PayTypeAmountInfo{" +
                "amount=" + amount +
                ", bizNo='" + bizNo + '\'' +
                ", payType='" + payType + '\'' +
                '}';
    }
    
    public static void main(String[] args){
    }
}

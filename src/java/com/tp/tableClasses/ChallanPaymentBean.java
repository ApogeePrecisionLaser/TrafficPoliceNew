/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.tableClasses;

/**
 *
 * @author Shobha
 */
public class ChallanPaymentBean {
    
    int challan_payment_id;
    String receipt_no,mode_of_payment,amount,payment_date,remark,payment_h_m;

    public String getPayment_h_m() {
        return payment_h_m;
    }

    public void setPayment_h_m(String payment_h_m) {
        this.payment_h_m = payment_h_m;
    }
    
    

    public int getChallan_payment_id() {
        return challan_payment_id;
    }

    public void setChallan_payment_id(int challan_payment_id) {
        this.challan_payment_id = challan_payment_id;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public void setMode_of_payment(String mode_of_payment) {
        this.mode_of_payment = mode_of_payment;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
}

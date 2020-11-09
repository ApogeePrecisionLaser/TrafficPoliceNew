/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.general.tableClasses;

import java.util.List;

/**
 *
 * @author jpss
 */
public class ReceiptBookBean {
    private int receipt_book_id;
    private int receipt_book_no;
    private int page_no;
    private double amount;
    private int book_revision_no;
    private int traffic_police_id;
    private int challan_book_no;
    private int challan_no;
    private int receipt_book_revision;
    private String[] challan_book;
    private List<ReceiptBookBean> receiptBookRecords;

    public List<ReceiptBookBean> getReceiptBookRecords() {
        return receiptBookRecords;
    }

    public void setReceiptBookRecords(List<ReceiptBookBean> receiptBookRecords) {
        this.receiptBookRecords = receiptBookRecords;
    }
          
    public String[] getChallan_book() {
        return challan_book;
    }

    public void setChallan_book(String[] challan_book) {
        this.challan_book = challan_book;
    }

    public int getReceipt_book_revision() {
        return receipt_book_revision;
    }

    public void setReceipt_book_revision(int receipt_book_revision) {
        this.receipt_book_revision = receipt_book_revision;
    }

    public int getChallan_book_no() {
        return challan_book_no;
    }

    public int getChallan_no() {
        return challan_no;
    }

    public void setChallan_book_no(int challan_book_no) {
        this.challan_book_no = challan_book_no;
    }

    public void setChallan_no(int challan_no) {
        this.challan_no = challan_no;
    }

    public double getAmount() {
        return amount;
    }

    public int getBook_revision_no() {
        return book_revision_no;
    }

    public int getPage_no() {
        return page_no;
    }

    public int getReceipt_book_id() {
        return receipt_book_id;
    }

    public int getReceipt_book_no() {
        return receipt_book_no;
    }

    public int getTraffic_police_id() {
        return traffic_police_id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBook_revision_no(int book_revision_no) {
        this.book_revision_no = book_revision_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }

    public void setReceipt_book_id(int receipt_book_id) {
        this.receipt_book_id = receipt_book_id;
    }

    public void setReceipt_book_no(int receipt_book_no) {
        this.receipt_book_no = receipt_book_no;
    }

    public void setTraffic_police_id(int traffic_police_id) {
        this.traffic_police_id = traffic_police_id;
    }
    
}

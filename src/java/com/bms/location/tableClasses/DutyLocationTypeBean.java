/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bms.location.tableClasses;

/**
 *
 * @author Shobha
 */
public class DutyLocationTypeBean {
    private int status_type_id;
  private String status_type;
  private String remark;

    public int getStatus_type_id() {
        return status_type_id;
    }

    public void setStatus_type_id(int status_type_id) {
        this.status_type_id = status_type_id;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
  
  
    
}

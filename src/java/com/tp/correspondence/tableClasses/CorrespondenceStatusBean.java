/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.correspondence.tableClasses;

import java.util.Date;

/**
 *
 * @author jpss
 */
public class CorrespondenceStatusBean {
    private int correspondence_status_id;
    private String status;
    private int created_by;
    private Date created_date;
    private String remark;
    private int correspondence_type_id;
    private String correspondence_type;

    public String getCorrespondence_type() {
        return correspondence_type;
    }

    public void setCorrespondence_type(String correspondence_type) {
        this.correspondence_type = correspondence_type;
    }
    
    public int getCorrespondence_status_id() {
        return correspondence_status_id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public String getRemark() {
        return remark;
    }

    public String getStatus() {
        return status;
    }

    public int getCorrespondence_type_id() {
        return correspondence_type_id;
    }

    public void setCorrespondence_status_id(int correspondence_status_id) {
        this.correspondence_status_id = correspondence_status_id;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCorrespondence_type_id(int correspondence_type_id) {
        this.correspondence_type_id = correspondence_type_id;
    }
    
}

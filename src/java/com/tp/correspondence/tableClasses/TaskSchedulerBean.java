/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.correspondence.tableClasses;

/**
 *
 * @author jpss
 */
public class TaskSchedulerBean {
    private int correspondence_task_id;
    private int revision;
    private String task;
    private String start_date;
    private int correspondence_id;
    private String remark;
    private int no_of_days;
    private int status_id;
    private String status;
    private String correspondence_no;
    private String subject;
    private String corr_date;

    public String getCorr_date() {
        return corr_date;
    }

    public String getCorrespondence_no() {
        return correspondence_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setCorr_date(String corr_date) {
        this.corr_date = corr_date;
    }

    public void setCorrespondence_no(String correspondence_no) {
        this.correspondence_no = correspondence_no;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCorrespondence_id() {
        return correspondence_id;
    }

    public int getCorrespondence_task_id() {
        return correspondence_task_id;
    }

    public int getNo_of_days() {
        return no_of_days;
    }

    public String getRemark() {
        return remark;
    }

    public int getRevision() {
        return revision;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getStatus() {
        return status;
    }

    public int getStatus_id() {
        return status_id;
    }

    public String getTask() {
        return task;
    }

    public void setCorrespondence_id(int correspondence_id) {
        this.correspondence_id = correspondence_id;
    }

    public void setCorrespondence_task_id(int correspondence_task_id) {
        this.correspondence_task_id = correspondence_task_id;
    }

    public void setNo_of_days(int no_of_days) {
        this.no_of_days = no_of_days;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public void setTask(String task) {
        this.task = task;
    }

}

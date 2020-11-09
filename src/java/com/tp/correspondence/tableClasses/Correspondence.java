/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.correspondence.tableClasses;

import java.util.List;

/**
 *
 * @author Shruti
 */
public class Correspondence {

    private int correcpondence_id;
    private String correspondece_no;
    private String subject;
    private String type_of_cop;
    private String type_of_document;
    private String destination_office;
    private String source_office;
    private String source_organisation;
    private String destination_organisation;
    private String refrence_no;
    private String description;
    private int created_by;
    private String dealing_person;
    private int dealing_person_id;
    private int type_of_document_id;
    private int source_office_id;
    private int destination_office_id;
    private String image_name;
    private String corr_date;
    private String[] bill_monthM;
    private String bill_month;
    private String[] meter_service_numberM;
    private String meter_service_number;
    private String[] meter_ids;
    private int meter_id;
    private String[] subjectM;
    private String[] correspondenceNo;
    private int pmt_corr_map_id;
    private String[]  pmt_corr_map_ids;
    private String[] descriptionM;
    private String correspondence_type;
    private int correspondence_type_id;
    private int other_person_id;
    private String other_person;
    private String register_date;
    private String is_cc;
    private int correspondence_priority_id;
    private String priority;
    private int correspondence_status_id;
    private String status;
    private int revision;
    private String active;
    private int key_person_id;
    private String key_person;
    private String owner_ref_no;
    private String ref_corres_id;
    private String ref_corres_date;
    private String reason;
    private String reply;
    private String reply_forward;
    private String is_reply;
    private String is_forward;
    private List<TaskSchedulerBean> taskList;

    public List<TaskSchedulerBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskSchedulerBean> taskList) {
        this.taskList = taskList;
    }

    public String getIs_forward() {
        return is_forward;
    }

    public String getIs_reply() {
        return is_reply;
    }

    public void setIs_forward(String is_forward) {
        this.is_forward = is_forward;
    }

    public void setIs_reply(String is_reply) {
        this.is_reply = is_reply;
    }

    public String getReply_forward() {
        return reply_forward;
    }

    public String getReply() {
        return reply;
    }

    public void setReply_forward(String reply_forward) {
        this.reply_forward = reply_forward;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getActive() {
        return active;
    }

    public String getPriority() {
        return priority;
    }

    public int getCorrespondence_priority_id() {
        return correspondence_priority_id;
    }

    public String getStatus() {
        return status;
    }

    public int getCorrespondence_status_id() {
        return correspondence_status_id;
    }

    public String getCorrespondence_type() {
        return correspondence_type;
    }

    public int getCorrespondence_type_id() {
        return correspondence_type_id;
    }

    public String getIs_cc() {
        return is_cc;
    }

    public String getKey_person() {
        return key_person;
    }

    public int getKey_person_id() {
        return key_person_id;
    }

    public String getOther_person() {
        return other_person;
    }

    public int getOther_person_id() {
        return other_person_id;
    }

    public String getOwner_ref_no() {
        return owner_ref_no;
    }

    public String getReason() {
        return reason;
    }

    public String getRef_corres_date() {
        return ref_corres_date;
    }

    public String getRef_corres_id() {
        return ref_corres_id;
    }

    public String getRegister_date() {
        return register_date;
    }

    public int getRevision() {
        return revision;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setCorrespondence_priority_id(int correspondence_priority_id) {
        this.correspondence_priority_id = correspondence_priority_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCorrespondence_status_id(int correspondence_status_id) {
        this.correspondence_status_id = correspondence_status_id;
    }

    public void setCorrespondence_type(String correspondence_type) {
        this.correspondence_type = correspondence_type;
    }

    public void setCorrespondence_type_id(int correspondence_type_id) {
        this.correspondence_type_id = correspondence_type_id;
    }

    public void setIs_cc(String is_cc) {
        this.is_cc = is_cc;
    }

    public void setKey_person(String key_person) {
        this.key_person = key_person;
    }

    public void setKey_person_id(int key_person_id) {
        this.key_person_id = key_person_id;
    }

    public void setOther_person(String other_person) {
        this.other_person = other_person;
    }

    public void setOther_person_id(int other_person_id) {
        this.other_person_id = other_person_id;
    }

    public void setOwner_ref_no(String owner_ref_no) {
        this.owner_ref_no = owner_ref_no;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setRef_corres_date(String ref_corres_date) {
        this.ref_corres_date = ref_corres_date;
    }

    public void setRef_corres_id(String ref_corres_id) {
        this.ref_corres_id = ref_corres_id;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }
    
    public int getCorrecpondence_id() {
        return correcpondence_id;
    }

    public void setCorrecpondence_id(int correcpondence_id) {
        this.correcpondence_id = correcpondence_id;
    }

    public String getCorrespondece_no() {
        return correspondece_no;
    }

    public void setCorrespondece_no(String correspondece_no) {
        this.correspondece_no = correspondece_no;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public String getDealing_person() {
        return dealing_person;
    }

    public void setDealing_person(String dealing_person) {
        this.dealing_person = dealing_person;
    }

    public int getDealing_person_id() {
        return dealing_person_id;
    }

    public void setDealing_person_id(int dealing_person_id) {
        this.dealing_person_id = dealing_person_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination_office() {
        return destination_office;
    }

    public void setDestination_office(String destination_office) {
        this.destination_office = destination_office;
    }

    public int getDestination_office_id() {
        return destination_office_id;
    }

    public void setDestination_office_id(int destination_office_id) {
        this.destination_office_id = destination_office_id;
    }

    public String getDestination_organisation() {
        return destination_organisation;
    }

    public void setDestination_organisation(String destination_organisation) {
        this.destination_organisation = destination_organisation;
    }

    public String getRefrence_no() {
        return refrence_no;
    }

    public void setRefrence_no(String refrence_no) {
        this.refrence_no = refrence_no;
    }

    public String getSource_office() {
        return source_office;
    }

    public void setSource_office(String source_office) {
        this.source_office = source_office;
    }

    public int getSource_office_id() {
        return source_office_id;
    }

    public void setSource_office_id(int source_office_id) {
        this.source_office_id = source_office_id;
    }

    public String getSource_organisation() {
        return source_organisation;
    }

    public void setSource_organisation(String source_organisation) {
        this.source_organisation = source_organisation;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType_of_cop() {
        return type_of_cop;
    }

    public void setType_of_cop(String type_of_cop) {
        this.type_of_cop = type_of_cop;
    }

    public String getType_of_document() {
        return type_of_document;
    }

    public void setType_of_document(String type_of_document) {
        this.type_of_document = type_of_document;
    }

    public int getType_of_document_id() {
        return type_of_document_id;
    }

    public void setType_of_document_id(int type_of_document_id) {
        this.type_of_document_id = type_of_document_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getCorr_date() {
        return corr_date;
    }

    public void setCorr_date(String corr_date) {
        this.corr_date = corr_date;
    }

    public String getBill_month() {
        return bill_month;
    }

    public void setBill_month(String bill_month) {
        this.bill_month = bill_month;
    }

    public String[] getBill_monthM() {
        return bill_monthM;
    }

    public void setBill_monthM(String[] bill_monthM) {
        this.bill_monthM = bill_monthM;
    }

    public String[] getCorrespondenceNo() {
        return correspondenceNo;
    }

    public void setCorrespondenceNo(String[] correspondenceNo) {
        this.correspondenceNo = correspondenceNo;
    }

    public int getMeter_id() {
        return meter_id;
    }

    public void setMeter_id(int meter_id) {
        this.meter_id = meter_id;
    }

    public String[] getMeter_ids() {
        return meter_ids;
    }

    public void setMeter_ids(String[] meter_ids) {
        this.meter_ids = meter_ids;
    }

    public String getMeter_service_number() {
        return meter_service_number;
    }

    public void setMeter_service_number(String meter_service_number) {
        this.meter_service_number = meter_service_number;
    }

    public String[] getMeter_service_numberM() {
        return meter_service_numberM;
    }

    public void setMeter_service_numberM(String[] meter_service_numberM) {
        this.meter_service_numberM = meter_service_numberM;
    }

    public String[] getSubjectM() {
        return subjectM;
    }

    public void setSubjectM(String[] subjectM) {
        this.subjectM = subjectM;
    }

    public int getPmt_corr_map_id() {
        return pmt_corr_map_id;
    }

    public void setPmt_corr_map_id(int pmt_corr_map_id) {
        this.pmt_corr_map_id = pmt_corr_map_id;
    }

    public String[] getPmt_corr_map_ids() {
        return pmt_corr_map_ids;
    }

    public void setPmt_corr_map_ids(String[] pmt_corr_map_ids) {
        this.pmt_corr_map_ids = pmt_corr_map_ids;
    }

    public String[] getDescriptionM() {
        return descriptionM;
    }

    public void setDescriptionM(String[] descriptionM) {
        this.descriptionM = descriptionM;
    }

    
}

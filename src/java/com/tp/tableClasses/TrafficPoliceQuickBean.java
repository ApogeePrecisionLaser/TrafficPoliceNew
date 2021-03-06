/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.tableClasses;

import java.util.List;

/**
 *
 * @author Administrator
 */
public class TrafficPoliceQuickBean
{
    private int traffic_police_id, vehicle_type_id, offence_type_id, receipt_book_id,total_no_of_rows;
    private String officer_name, offence_type, vehicle_type, vehicle_no, offender_name, offender_license_no, offence_place, act, 
            receipt_no, remark, offence_date, book_no, penalty_amount, deposited_amount, book_revision_no,
            key_person_name, act_origin, city, zone,processing_type
            ,relation_type,case_no,case_date,offender_age,jarayam_no,offender_address, offence_code, salutation, relative_name, offender_city;
    private String[] officer_nameM, offence_typeM, vehicle_typeM, vehicle_noM, offender_nameM, offender_license_noM, offence_placeM, actM,
            receipt_noM, remarkM, offence_dateM, book_noM, penalty_amountM, deposited_amountM, book_revision_noM, cityM, zoneM, act_originM,processing_typeM
            ,relation_typeM,case_noM,case_dateM,offender_ageM,jarayam_noM,relative_nameM,salutationM,offender_cityM,offender_address_M, father_nameM, modelM,
            vehicle_no_stateM,vehicle_no_cityM,vehicle_no_seriesM,vehicle_no_digitsM,receipt_page_noM;
    private int[] city_location_idM;
    List<TrafficPoliceQuickBean> offenceList;
    private String lattitude;
    private String longitude;
    private String case_status;
    private int receipt_book_no;
    private int receipt_book_rev_no;
    private int receipt_page_no;
    private String is_commercial;
    private String father_name;
    private String model;
    private int model_id;
    private String vehicle_no_state;
    private String vehicle_no_city;
    private String vehicle_no_series;
    private String vehicle_no_digits;

    public int getReceipt_book_id() {
        return receipt_book_id;
    }

    public void setReceipt_book_id(int receipt_book_id) {
        this.receipt_book_id = receipt_book_id;
    }

    public String getVehicle_no_city() {
        return vehicle_no_city;
    }

    public String[] getVehicle_no_cityM() {
        return vehicle_no_cityM;
    }

    public String getVehicle_no_digits() {
        return vehicle_no_digits;
    }

    public String[] getVehicle_no_digitsM() {
        return vehicle_no_digitsM;
    }

    public String getVehicle_no_series() {
        return vehicle_no_series;
    }

    public String[] getVehicle_no_seriesM() {
        return vehicle_no_seriesM;
    }

    public String getVehicle_no_state() {
        return vehicle_no_state;
    }

    public String[] getVehicle_no_stateM() {
        return vehicle_no_stateM;
    }

    public void setVehicle_no_city(String vehicle_no_city) {
        this.vehicle_no_city = vehicle_no_city;
    }

    public void setVehicle_no_cityM(String[] vehicle_no_cityM) {
        this.vehicle_no_cityM = vehicle_no_cityM;
    }

    public void setVehicle_no_digits(String vehicle_no_digits) {
        this.vehicle_no_digits = vehicle_no_digits;
    }

    public void setVehicle_no_digitsM(String[] vehicle_no_digitsM) {
        this.vehicle_no_digitsM = vehicle_no_digitsM;
    }

    public void setVehicle_no_series(String vehicle_no_series) {
        this.vehicle_no_series = vehicle_no_series;
    }

    public void setVehicle_no_seriesM(String[] vehicle_no_seriesM) {
        this.vehicle_no_seriesM = vehicle_no_seriesM;
    }

    public void setVehicle_no_state(String vehicle_no_state) {
        this.vehicle_no_state = vehicle_no_state;
    }

    public void setVehicle_no_stateM(String[] vehicle_no_stateM) {
        this.vehicle_no_stateM = vehicle_no_stateM;
    }

    public String[] getFather_nameM() {
        return father_nameM;
    }

    public String[] getModelM() {
        return modelM;
    }

    public void setFather_nameM(String[] father_nameM) {
        this.father_nameM = father_nameM;
    }

    public void setModelM(String[] modelM) {
        this.modelM = modelM;
    }
    
    public String getFather_name() {
        return father_name;
    }

    public String getModel() {
        return model;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }
    
    public String getIs_commercial() {
        return is_commercial;
    }

    public void setIs_commercial(String is_commercial) {
        this.is_commercial = is_commercial;
    }

    public void setReceipt_book_rev_no(int receipt_book_rev_no) {
        this.receipt_book_rev_no = receipt_book_rev_no;
    }

    public int getReceipt_book_rev_no() {
        return receipt_book_rev_no;
    }

    public void setReceipt_book_no(int receipt_book_no) {
        this.receipt_book_no = receipt_book_no;
    }

    public void setReceipt_page_no(int receipt_page_no) {
        this.receipt_page_no = receipt_page_no;
    }

    public int getReceipt_book_no() {
        return receipt_book_no;
    }

    public int getReceipt_page_no() {
        return receipt_page_no;
    }

    public String getCase_status() {
        return case_status;
    }

    public void setCase_status(String case_status) {
        this.case_status = case_status;
    }

    
    public String getLattitude() {
        return lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getOffender_city() {
        return offender_city;
    }

    public String getRelative_name() {
        return relative_name;
    }

    public void setOffender_city(String offender_city) {
        this.offender_city = offender_city;
    }

    public void setRelative_name(String relative_name) {
        this.relative_name = relative_name;
    }


    public String getOffence_code() {
        return offence_code;
    }

    public void setOffence_code(String offence_code) {
        this.offence_code = offence_code;
    }


    public String[] getOffender_address_M() {
        return offender_address_M;
    }

    public void setOffender_address_M(String[] offender_address_M) {
        this.offender_address_M = offender_address_M;
    }

    public String[] getOffender_cityM() {
        return offender_cityM;
    }

    public void setOffender_cityM(String[] offender_cityM) {
        this.offender_cityM = offender_cityM;
    }

    public String[] getRelative_nameM() {
        return relative_nameM;
    }

    public void setRelative_nameM(String[] relative_nameM) {
        this.relative_nameM = relative_nameM;
    }

   

    public String[] getSalutationM() {
        return salutationM;
    }

    public void setSalutationM(String[] salutationM) {
        this.salutationM = salutationM;
    }


    public String getOffender_address() {
        return offender_address;
    }

    public void setOffender_address(String offender_address) {
        this.offender_address = offender_address;
    }

    
    public String getCase_date() {
        return case_date;
    }

    public void setCase_date(String case_date) {
        this.case_date = case_date;
    }

    public String getCase_no() {
        return case_no;
    }

    public void setCase_no(String case_no) {
        this.case_no = case_no;
    }

    public String getJarayam_no() {
        return jarayam_no;
    }

    public void setJarayam_no(String jarayam_no) {
        this.jarayam_no = jarayam_no;
    }

    public String getOffender_age() {
        return offender_age;
    }

    public void setOffender_age(String offender_age) {
        this.offender_age = offender_age;
    }

    public String getProcessing_type() {
        return processing_type;
    }

    public void setProcessing_type(String processing_type) {
        this.processing_type = processing_type;
    }

    public String getRelation_type() {
        return relation_type;
    }

    public void setRelation_type(String relation_type) {
        this.relation_type = relation_type;
    }

    public String[] getCase_dateM() {
        return case_dateM;
    }

    public void setCase_dateM(String[] case_dateM) {
        this.case_dateM = case_dateM;
    }

    public String[] getCase_noM() {
        return case_noM;
    }

    public void setCase_noM(String[] case_noM) {
        this.case_noM = case_noM;
    }

    public String[] getJarayam_noM() {
        return jarayam_noM;
    }

    public void setJarayam_noM(String[] jarayam_noM) {
        this.jarayam_noM = jarayam_noM;
    }

    public String[] getOffender_ageM() {
        return offender_ageM;
    }

    public void setOffender_ageM(String[] offender_ageM) {
        this.offender_ageM = offender_ageM;
    }

    public String[] getProcessing_typeM() {
        return processing_typeM;
    }

    public void setProcessing_typeM(String[] processing_typeM) {
        this.processing_typeM = processing_typeM;
    }

    public String[] getRelation_typeM() {
        return relation_typeM;
    }

    public void setRelation_typeM(String[] relation_typeM) {
        this.relation_typeM = relation_typeM;
    }


    public List<TrafficPoliceQuickBean> getOffenceList() {
        return offenceList;
    }

    public void setOffenceList(List<TrafficPoliceQuickBean> offenceList) {
        this.offenceList = offenceList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getAct_origin() {
        return act_origin;
    }

    public void setAct_origin(String act_origin) {
        this.act_origin = act_origin;
    }

    public int[] getCity_location_idM() {
        return city_location_idM;
    }

    public void setCity_location_idM(int[] city_location_idM) {
        this.city_location_idM = city_location_idM;
    }

    public String[] getAct_originM() {
        return act_originM;
    }

    public void setAct_originM(String[] act_originM) {
        this.act_originM = act_originM;
    }

    public String[] getCityM() {
        return cityM;
    }

    public void setCityM(String[] cityM) {
        this.cityM = cityM;
    }

    public String[] getZoneM() {
        return zoneM;
    }

    public void setZoneM(String[] zoneM) {
        this.zoneM = zoneM;
    }

    public String getBook_revision_no() {
        return book_revision_no;
    }

    public void setBook_revision_no(String book_revision_no) {
        this.book_revision_no = book_revision_no;
    }

    public String getKey_person_name() {
        return key_person_name;
    }

    public void setKey_person_name(String key_person_name) {
        this.key_person_name = key_person_name;
    }

    public String[] getBook_revision_noM() {
        return book_revision_noM;
    }

    public void setBook_revision_noM(String[] book_revision_noM) {
        this.book_revision_noM = book_revision_noM;
    }
    private String[] traffic_police_idM, vehicle_type_idM, offence_type_idM;

    public int getOffence_type_id() {
        return offence_type_id;
    }

    public void setOffence_type_id(int offence_type_id) {
        this.offence_type_id = offence_type_id;
    }

    public String[] getOffence_type_idM() {
        return offence_type_idM;
    }

    public void setOffence_type_idM(String[] offence_type_idM) {
        this.offence_type_idM = offence_type_idM;
    }

    public String[] getVehicle_type_idM() {
        return vehicle_type_idM;
    }

    public void setVehicle_type_idM(String[] vehicle_type_idM) {
        this.vehicle_type_idM = vehicle_type_idM;
    }

    public int getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(int vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public String getBook_no() {
        return book_no;
    }

    public void setBook_no(String book_no) {
        this.book_no = book_no;
    }

    public String[] getBook_noM() {
        return book_noM;
    }

    public void setBook_noM(String[] book_noM) {
        this.book_noM = book_noM;
    }

    public String getDeposited_amount() {
        return deposited_amount;
    }

    public void setDeposited_amount(String deposited_amount) {
        this.deposited_amount = deposited_amount;
    }

    public String[] getDeposited_amountM() {
        return deposited_amountM;
    }

    public void setDeposited_amountM(String[] deposited_amountM) {
        this.deposited_amountM = deposited_amountM;
    }

    public String getPenalty_amount() {
        return penalty_amount;
    }

    public void setPenalty_amount(String penalty_amount) {
        this.penalty_amount = penalty_amount;
    }

    public String[] getPenalty_amountM() {
        return penalty_amountM;
    }

    public void setPenalty_amountM(String[] penalty_amountM) {
        this.penalty_amountM = penalty_amountM;
    }

    public String[] getTraffic_police_idM() {
        return traffic_police_idM;
    }

    public void setTraffic_police_idM(String[] traffic_police_idM) {
        this.traffic_police_idM = traffic_police_idM;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String[] getActM() {
        return actM;
    }

    public void setActM(String[] actM) {
        this.actM = actM;
    }

    public String getOffence_date() {
        return offence_date;
    }

    public void setOffence_date(String offence_date) {
        this.offence_date = offence_date;
    }

    public String[] getOffence_dateM() {
        return offence_dateM;
    }

    public void setOffence_dateM(String[] offence_dateM) {
        this.offence_dateM = offence_dateM;
    }

    public String getOffence_place() {
        return offence_place;
    }

    public void setOffence_place(String offence_place) {
        this.offence_place = offence_place;
    }

    public String[] getOffence_placeM() {
        return offence_placeM;
    }

    public void setOffence_placeM(String[] offence_placeM) {
        this.offence_placeM = offence_placeM;
    }

    public String getOffence_type() {
        return offence_type;
    }

    public void setOffence_type(String offence_type) {
        this.offence_type = offence_type;
    }

    public String[] getOffence_typeM() {
        return offence_typeM;
    }

    public void setOffence_typeM(String[] offence_typeM) {
        this.offence_typeM = offence_typeM;
    }

    public String getOffender_license_no() {
        return offender_license_no;
    }

    public void setOffender_license_no(String offender_license_no) {
        this.offender_license_no = offender_license_no;
    }

    public String[] getOffender_license_noM() {
        return offender_license_noM;
    }

    public void setOffender_license_noM(String[] offender_license_noM) {
        this.offender_license_noM = offender_license_noM;
    }

    public String getOffender_name() {
        return offender_name;
    }

    public void setOffender_name(String offender_name) {
        this.offender_name = offender_name;
    }

    public String[] getOffender_nameM() {
        return offender_nameM;
    }

    public void setOffender_nameM(String[] offender_nameM) {
        this.offender_nameM = offender_nameM;
    }

    public String getOfficer_name() {
        return officer_name;
    }

    public void setOfficer_name(String officer_name) {
        this.officer_name = officer_name;
    }

    public String[] getOfficer_nameM() {
        return officer_nameM;
    }

    public void setOfficer_nameM(String[] officer_nameM) {
        this.officer_nameM = officer_nameM;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String[] getReceipt_noM() {
        return receipt_noM;
    }

    public void setReceipt_noM(String[] receipt_noM) {
        this.receipt_noM = receipt_noM;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String[] getRemarkM() {
        return remarkM;
    }

    public void setRemarkM(String[] remarkM) {
        this.remarkM = remarkM;
    }

    public int getTraffic_police_id() {
        return traffic_police_id;
    }

    public void setTraffic_police_id(int traffic_police_id) {
        this.traffic_police_id = traffic_police_id;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String[] getVehicle_noM() {
        return vehicle_noM;
    }

    public void setVehicle_noM(String[] vehicle_noM) {
        this.vehicle_noM = vehicle_noM;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String[] getVehicle_typeM() {
        return vehicle_typeM;
    }

    public void setVehicle_typeM(String[] vehicle_typeM) {
        this.vehicle_typeM = vehicle_typeM;
    }

       public int getTotal_no_of_rows() {
        return total_no_of_rows;
    }

    public void setTotal_no_of_rows(int total_no_of_rows)
    {
        this.total_no_of_rows = total_no_of_rows;
    }
    public String[] getReceipt_page_noM()
    {
        return receipt_page_noM;
    }
    public void setReceipt_page_noM(String[] receipt_page_noM)
    {
        this.receipt_page_noM = receipt_page_noM;
    }

}

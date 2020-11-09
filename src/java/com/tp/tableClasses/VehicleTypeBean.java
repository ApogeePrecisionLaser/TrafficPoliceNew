/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.tableClasses;

/**
 *
 * @author JPSS
 */
public class VehicleTypeBean {
private int vehicle_type_id;
    private String vehicle_type;
private String commercial_type;

    public String getCommercial_type() {
        return commercial_type;
    }

    public void setCommercial_type(String commercial_type) {
        this.commercial_type = commercial_type;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public int getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(int vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    private String remark;
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

   

}

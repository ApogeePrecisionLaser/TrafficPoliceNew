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
public class CameraInfoBean {
    int camera_info_id;
    private String channel_no,camera_no,camera_type,camera_facing;
    private String city_location,lattitude,longitude,remark;

    public int getCamera_info_id() {
        return camera_info_id;
    }

    public void setCamera_info_id(int camera_info_id) {
        this.camera_info_id = camera_info_id;
    }

    public String getChannel_no() {
        return channel_no;
    }

    public void setChannel_no(String channel_no) {
        this.channel_no = channel_no;
    }

    public String getCamera_no() {
        return camera_no;
    }

    public void setCamera_no(String camera_no) {
        this.camera_no = camera_no;
    }

    public String getCamera_type() {
        return camera_type;
    }

    public void setCamera_type(String camera_type) {
        this.camera_type = camera_type;
    }

    public String getCamera_facing() {
        return camera_facing;
    }

    public void setCamera_facing(String camera_facing) {
        this.camera_facing = camera_facing;
    }

    public String getCity_location() {
        return city_location;
    }

    public void setCity_location(String city_location) {
        this.city_location = city_location;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
    
    
}

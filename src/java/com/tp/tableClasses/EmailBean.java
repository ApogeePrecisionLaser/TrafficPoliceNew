/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.tableClasses;

/**
 *
 * @author DELL
 */
public class EmailBean {
    
    int id;
    String user_name;
    String user_password;
    String sendto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getSendto() {
        return sendto;
    }

    public void setSendto(String sendto) {
        this.sendto = sendto;
    }
    
    
    
}

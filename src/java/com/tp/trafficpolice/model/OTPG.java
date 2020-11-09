/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

//import java.sql.PreparedStatement;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//import java.sql.ResultSet;

/**
 *
 * @author DELL
 */
public class OTPG {
    
    static Map<String, String> otpMap = new HashMap<String, String>();
    OTPG otpg = new OTPG();
    
     public String VerifyMoileNo(String mobile_no) throws Exception {
      
//        int affected = slm.getKeyPersonId(mobile_no);
//        if (affected > 0) {
       sendOTP(mobile_no);
        
//        
//        if (affected > 0) {
//            return "success";
//        } else {
//            return "error";
//        }
        return "success";
     }
    
    public void sendOTP(String mobile_no) throws Exception {
        String otp = "";
        System.out.println("UserAppWebServices...");
         OTPView view = new OTPView();
       
        otp = view.random(6);
        otpMap.put(mobile_no, otp);
        view.sendSmsToAssignedFor(mobile_no, "OTP:- " + otp);
        System.out.println("Data Retrived : " + mobile_no);
       // stm.closeConnection();
    }

    public JSONObject verifyOTP(String mobile_no_otp) throws Exception {
        String otp = "";
        JSONObject jsonObj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        System.out.println("PocServiceConnectionServices...");
        String mobile_no = mobile_no_otp.split("_")[0];
        otp = mobile_no_otp.split("_")[1];
//       // ShiftLoginModel slm = new ShiftLoginModel();
//        VehicleDriverWebServiceModel vehicleDriverWebServiceModel = new VehicleDriverWebServiceModel();
//        try {
//            connection = DBConnection.getConnectionForUtf(serveletContext);
//            slm.setConnection(connection);
//            vehicleDriverWebServiceModel.setConnection(connection);
//        } catch (Exception ex) {
//            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
//        }
        if (otp.equals(otpMap.get(mobile_no))) {
            otpMap.remove(mobile_no);
            jsonObj.put("result", "success");
//            int emp_code = slm.getKeyPersonId(mobile_no);
//            jsonObj.put("Data", slm.getAreaDetails("" + emp_code));
//            jsonObj.put("OffenceData", vehicleDriverWebServiceModel.getOffence());
//            
//            //jsonObj.put("vehicleType", vehicleDriverWebServiceModel.vehicleType());
//            
//            slm.closeConnection();
        } else {
            jsonObj.put("result", "fail");
        }
        return jsonObj;
    }
    
    
    
}

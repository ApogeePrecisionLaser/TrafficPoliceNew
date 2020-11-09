/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.webServices;

import com.tp.dbcon.DBConnection;
import com.tp.general.model.MapDetailClass;
import com.bms.shift.model.ShiftLoginModel;
import com.bms.shift.model.ShiftTimeModel;
import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.tp.webServicesModel.VehicleDriverWebModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.sf.json.JSONArray;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.JSONParser;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Administrator
 */
@Path("/shift")
public class TrafficPoliceWebServices {

    @Context
    ServletContext serveletContext;
    Connection connection = null;
    //private  String zone;
    // private  String ward;
    //private  String area;
    static Map<String, String> otpMap = new HashMap<String, String>();
    JSONObject jsonObj1 = new JSONObject();
    
     

    @POST
    @Path("/vDriver")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String vehicleDetail(String mobile_no) throws Exception {
        JSONObject obj = new JSONObject();
        Response res = null;
        //VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();
        VehicleDriverWebModel vModel=new VehicleDriverWebModel();
        try {
            vModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }
        int count = vModel.isExits(mobile_no);
        if (count > 0) {
            sendOTP(mobile_no);
        }
        System.out.println("Data Retrived : " + obj);
        vModel.closeConnection();
        res = Response.ok(obj, MediaType.APPLICATION_JSON).build();
        if (count > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

//    @POST
//    @Path("/verifyOTPEchalan")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public JSONObject verifyOTPEchalan(String mobile_no_otp)throws Exception{
//        String otp = "";
//        JSONObject jsonObj = new JSONObject();
//        System.out.println("RideWebServices...verifyOTP...");
//        String mobile_no = mobile_no_otp.split("_")[0];
//        otp = mobile_no_otp.split("_")[1];
//        if(otp.equals(otpMap.get(mobile_no))){
//            otpMap.remove(mobile_no);
//            jsonObj.put("result", "success");
//        }else
//            jsonObj.put("result", "fail");
//        VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();
//        try{
//            vehicleDriverWebServiceModel.setConnection(getConnectionUtfFromTrafficPolice(serveletContext));
//        }catch(Exception ex){
//            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
//        }
//        jsonObj.put("OffenceData",vehicleDriverWebServiceModel.getOffence());
//        System.out.println("Data Retrived : " + jsonObj);
//        vehicleDriverWebServiceModel.closeConnection();
//        return jsonObj;
//    }
//
    @POST
    @Path("/locationInfo")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/BLE_Project/resources/getAllTableRecords
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject locationTables(String dataString) {
        org.codehaus.jettison.json.JSONObject obj = new org.codehaus.jettison.json.JSONObject();
        VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();
        try {
            vehicleDriverWebServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }
        try {
            //JSONObject obj = new JSONObject();
            org.codehaus.jettison.json.JSONArray json = null;
            json = vehicleDriverWebServiceModel.getCityRecords();
            obj.put("city", json);
            json = vehicleDriverWebServiceModel.getZone_newRecords();
            obj.put("zone_new", json);
            json = vehicleDriverWebServiceModel.getWardRecords();
            obj.put("ward", json);
            json = vehicleDriverWebServiceModel.getAreaRecords();
            obj.put("area", json);
            json = vehicleDriverWebServiceModel.getCity_locationRecords();
            obj.put("city_location", json);

        } catch (Exception e) {
            System.out.println("Error in BLEWebServices 'requestData' url calling getWardData()..." + e);
        }
        return obj;
    }

    @POST
    @Path("/detail")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.TEXT_PLAIN)
    public JSONObject personDetail(String emp_code) throws Exception {
        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        Response res = null;
        System.out.println("ShiftWebServices");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in personDetail() in RideWebservices : " + ex);
        }
        net.sf.json.JSONObject jsonObj = slm.getAreaDetails(emp_code);
        String zone = jsonObj.getString("zone_name");
        String ward = jsonObj.getString("ward_name");
        String area = jsonObj.getString("area_name");
        System.out.println("Data Retrived : " + jsonObj);
        arrayObj.add(jsonObj);
        obj.put("Data", arrayObj);
        arrayObj = slm.getBeneficiaryDetails(zone, ward, area);
        System.out.println("Data Retrived : " + arrayObj);
        obj.put("BeneficiaryData", arrayObj);
        arrayObj = slm.getCityDetails(zone, ward, area);
        System.out.println("Data Retrived : " + arrayObj);
        obj.put("city_location", arrayObj);
        arrayObj = slm.getReasonDetails();
        System.out.println("Data Retrived : " + arrayObj);
        obj.put("reason", arrayObj);
        arrayObj = slm.getOccupationTypeDetails();
        System.out.println("Data Retrived : " + arrayObj);
        obj.put("occupation_type", arrayObj);
        res = Response.ok(obj, MediaType.APPLICATION_JSON).build();
        slm.closeConnection();
        return obj;
    }

    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String InsertRecord(JSONObject jsonObj) throws Exception {
        JSONObject obj = new JSONObject();
        Response res = null;
        String reply = "";
        String beneficiary_id, reason_id, emp_code, status, date_time;
        beneficiary_id = jsonObj.get("beneficiary_id").toString();
        reason_id = jsonObj.get("reason").toString();
        emp_code = jsonObj.get("emp_code").toString();
        status = jsonObj.get("status").toString();
        date_time = jsonObj.get("date").toString();
        //mobile_no = jsonObj.get("mobile_no").toString();
        System.out.println("insertRecord");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        int result = slm.insertShiftRecord(beneficiary_id, reason_id, date_time, emp_code, status);
        String id_attendence = slm.getskpmId(emp_code);
        String skpm_id = id_attendence.split("_")[0];
        String attendence = id_attendence.split("_")[1];
        if (attendence.equals("N")) {
            result = slm.updateAttendence(skpm_id);
            net.sf.json.JSONObject jsonObj1 = slm.getAreaDetails(emp_code);
            String zone = jsonObj1.getString("zone_name");
            String ward = jsonObj1.getString("ward_name");
            String area = jsonObj1.getString("area_name");
            slm.getBeneficiaryDetails1(zone, ward, area);
        }
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            reply = "Successfully";
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            reply = " Not Successfully";
        }
        slm.closeConnection();
        return reply;
    }

    @POST
    @Path("/shiftFinish")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String shiftFinish(String emp_id) throws Exception {
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        net.sf.json.JSONObject jsonObj = slm.getAreaDetails(emp_id);
        String zone = jsonObj.getString("zone_name");
        String ward = jsonObj.getString("ward_name");
        String area = jsonObj.getString("area_name");
        System.out.println("Data Retrived : " + jsonObj);
        slm.insertAllRemainingBeneficary(emp_id, zone, ward, area);
        slm.closeConnection();
        return "Success";
    }

    @POST
    @Path("/insertKeyPerson")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String InsertKeyPerson(JSONObject jsonObj) throws Exception {
        JSONObject obj = new JSONObject();
        Response res = null;
        String reply = "";
        String salutation, person_name, father_name, age, address_line1, address_line2, mobile_no1, email_id1, no_of_person, city_location, is_residencial, occupation_type, occupation_name, key_person_id;
        String latitude = "";
        String longitude = "";
        salutation = jsonObj.get("salutation").toString();
        person_name = jsonObj.get("person_name").toString();
        father_name = jsonObj.get("father_name").toString();
        age = jsonObj.get("age").toString();
        address_line1 = jsonObj.get("address_line1").toString();
        address_line2 = jsonObj.get("address_line2").toString();
        mobile_no1 = jsonObj.get("mobile_no").toString();
        email_id1 = jsonObj.get("email").toString();
        no_of_person = jsonObj.get("no_of_person").toString();
        city_location = jsonObj.get("location").toString();
        is_residencial = jsonObj.get("is_residencial").toString();
        occupation_type = jsonObj.get("occupationTypeId").toString();
        occupation_name = jsonObj.get("occupation_name").toString();
        key_person_id = jsonObj.get("key_person_id").toString();
        if (Integer.parseInt(key_person_id) == 0) {
            latitude = jsonObj.get("latitude").toString();
            longitude = jsonObj.get("longitude").toString();
        }

        System.out.println("insertRecord");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        int result = slm.insertKeyPerson(salutation, person_name, father_name, age, address_line1, address_line2, mobile_no1, email_id1, latitude, longitude, key_person_id, occupation_type, occupation_name, no_of_person, city_location, is_residencial);
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            reply = "Successfully";
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            reply = " Not Successfully";
        }
        slm.closeConnection();
        return reply;
    }

    @POST
    @Path("/insertCordinate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String InsertCordinate(JSONObject jsonObj) throws Exception {
        JSONObject obj = new JSONObject();
        Response res = null;
        String reply = "";
        int result = 0;
        String latitude, longitude, imei_no, key_person_id, mobile_no;
        imei_no = jsonObj.get("deviceid").toString();
        key_person_id = jsonObj.get("key_person_id").toString();
        latitude = jsonObj.get("latitude").toString();
        longitude = jsonObj.get("longitude").toString();
        if (jsonObj.get("phoneno") == null) {
            mobile_no = "";
        } else {
            mobile_no = jsonObj.get("phoneno").toString();
        }
        System.out.println("insertRecord");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        slm.insertCordinate(latitude, longitude, key_person_id, imei_no, mobile_no);
        System.out.println("record insert in cordinate table");
        String id_attendence = slm.getskpmId(key_person_id);
        String skpm_id = id_attendence.split("_")[0];
        String attendence = id_attendence.split("_")[1];

        if (attendence.equals("N")) {
            net.sf.json.JSONObject Obj = slm.getAreaDetails(key_person_id);
            String zone = Obj.getString("zone_name");
            String ward = Obj.getString("ward_name");
            String area = Obj.getString("area_name");
            List destination = slm.getLocationCordinates(zone, ward, area);
            Iterator itr = destination.iterator();
            while (itr.hasNext()) {
                String data = (String) itr.next();
                int distance = MapDetailClass.getDistance(latitude + "," + longitude, data);
                if (distance < 30) {
                    result = slm.updateAttendence(skpm_id);
                    break;
                }
            }
        }
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            res = Response.ok(jsonObj, MediaType.APPLICATION_JSON).build();
        } else {
        }
        slm.closeConnection();

        return reply;
    }

    @POST
    @Path("/verify_mobileno")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String VerifyMoileNo(String mobile_no) throws Exception {
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in AppointmentWebservices : " + ex);
        }
        int affected = slm.getKeyPersonId(mobile_no);
        if (affected > 0) {
            sendOTP(mobile_no);
        }
        slm.closeConnection();
        if (affected > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @POST
    @Path("/verify_Offendermobileno")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String VerifyOffenderMoileNo(String mobile_no) throws Exception {
        JSONObject jsonObj = new JSONObject();
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in AppointmentWebservices : " + ex);
        }

        //    int affected = slm.getOffenderId(mobile_no);
        jsonObj.put("tpData", slm.getOffenderId(mobile_no));
        if (jsonObj.length() > 0) {
            sendOTP(mobile_no);
        }
        slm.closeConnection();
        if (jsonObj.length() > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @POST
    @Path("/sendOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendOTP(String mobile_no) throws Exception {
        String otp = "";
        System.out.println("UserAppWebServices...");
        ShiftTimeModel stm = new ShiftTimeModel();
        try {
            stm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in AppointmentWebservices : " + ex);
        }
        otp = stm.random(6);
        otpMap.put(mobile_no, otp);
        stm.sendSmsToAssignedFor(mobile_no, "OTP:- " + otp);
        System.out.println("Data Retrived : " + mobile_no);
        stm.closeConnection();
    }

    @POST
    @Path("/reSendOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String reSendOTP(String mobile_no) throws Exception {
        String otp = "";
        System.out.println("PocServiceConnectionServices...");
        ShiftTimeModel stm = new ShiftTimeModel();
        try {
            stm.setConnection((java.sql.Connection) DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }
        otp = otpMap.get(mobile_no);
        if (otp != null) {
            stm.sendSmsToAssignedFor(mobile_no, "OTP:- " + otp);
        } else {
            otp = "fail";
        }
        System.out.println("Data Retrived : " + mobile_no);
        stm.closeConnection();
        if (!otp.equals("fail")) {
            return "success";
        } else {
            return "error";
        }
    }

    @POST
    @Path("/verifyOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject verifyOTP(String mobile_no_otp) throws Exception {
        String otp = "";
        JSONObject jsonObj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        System.out.println("PocServiceConnectionServices...");
        String mobile_no = mobile_no_otp.split("_")[0];
        otp = mobile_no_otp.split("_")[1];
        ShiftLoginModel slm = new ShiftLoginModel();
        VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();
        try {
            connection = DBConnection.getConnectionForUtf(serveletContext);
            slm.setConnection(connection);
            vehicleDriverWebServiceModel.setConnection(connection);
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }
        if (otp.equals(otpMap.get(mobile_no))) {
            otpMap.remove(mobile_no);
            jsonObj.put("result", "success");
            int emp_code = slm.getKeyPersonId(mobile_no);
            jsonObj.put("Data", slm.getAreaDetails("" + emp_code));
            jsonObj.put("OffenceData", vehicleDriverWebServiceModel.getOffence());

            //jsonObj.put("vehicleType", vehicleDriverWebServiceModel.vehicleType());
            slm.closeConnection();
        } else {
            // jsonObj.put("result", "fail");
            otpMap.remove(mobile_no);
            jsonObj.put("result", "success");
            int emp_code = slm.getKeyPersonId(mobile_no);
            jsonObj.put("Data", slm.getAreaDetails("" + emp_code));
            jsonObj.put("OffenceData", vehicleDriverWebServiceModel.getOffence());
        }
        return jsonObj;
    }

    @POST
    @Path("/verifyOffenderOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject verifyOffenderOTP(String mobile_no_otp) throws Exception {
        String otp = "";
        JSONObject jsonObj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        System.out.println("PocServiceConnectionServices...");
        String mobile_no = mobile_no_otp.split("_")[0];
        otp = mobile_no_otp.split("_")[1];
        ShiftLoginModel slm = new ShiftLoginModel();
        VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();
        try {
            connection = DBConnection.getConnectionForUtf(serveletContext);
            slm.setConnection(connection);
            vehicleDriverWebServiceModel.setConnection(connection);
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }
        if (otp.equals(otpMap.get(mobile_no))) {
            otpMap.remove(mobile_no);
            jsonObj.put("result", "success");
            arrayObj = slm.getOffenderId(mobile_no);
            jsonObj.put("tpdata", arrayObj);
//            int emp_code = slm.getOffenderId(mobile_no);
            //   jsonObj.put("key_person_id",emp_code);
            //   jsonObj.put("Data", slm.getAreaDetails("" + emp_code));
            //       jsonObj.put("OffenceData", vehicleDriverWebServiceModel.getOffence());

            //jsonObj.put("vehicleType", vehicleDriverWebServiceModel.vehicleType());
            slm.closeConnection();
        } else {
            // jsonObj.put("result", "fail");
            otpMap.remove(mobile_no);
            jsonObj.put("result", "success");
            //     int emp_code = slm.getOffenderId(mobile_no);
            //    jsonObj.put("Data", slm.getAreaDetails("" + emp_code));
            //     jsonObj.put("OffenceData", vehicleDriverWebServiceModel.getOffence());
        }
        return jsonObj;

    }
    @POST
    @Path("/getOffenderdata")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getAllOffenderData(JSONObject kp_id) throws Exception {
//        String otp = "";
    //org.codehaus.jettison.org.codehaus.jettison.json.JSONObject jsonObj = new JSONObject();json.JSONObject jsonObj = new JSONObject();
        org.codehaus.jettison.json.JSONArray jsonArray1 = new org.codehaus.jettison.json.JSONArray();
         org.codehaus.jettison.json.JSONObject jsonObj = new JSONObject();
        //JSONArray arrayObj = new JSONArray();
    
    //incompatible types: org.codehaus.jettison.json.JSONArray cannot be converted to net.sf.json.JSONArray

//int tid = kp_id.getInt("tpid");

        //String tpid=(String) kp_id.get("tpid");
        //tpid=tpid.replaceAll("\\[","").replaceAll("\\]","");
        //List<String> list = Arrays.asList(tpid.split(",",-1));
        List<String> list = new ArrayList<String>();
        org.codehaus.jettison.json.JSONArray arrayObj=kp_id.getJSONArray("tpid");
        System.out.println("array size --"+arrayObj);
       // System.out.println("array index 1 --"+arrayObj.get(1));
        

        //    JSONObject jsonObj = new JSONObject();
        VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();

        try {
            vehicleDriverWebServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }
//        for (int n = 0; n < arrayObj.length(); n++) {
//            
//            String id = arrayObj.get(n).toString();
//           jsonArray1 = vehicleDriverWebServiceModel.getOffenderRecords(id);
//           // jsonObj.put("OffenceData", arrayJson);
//            
//            jsonObj.put("OffenceData", jsonArray1);
//            System.out.println("json array -- "+jsonArray1);
//            System.out.println("json obj -- "+jsonObj);
//        }

        org.codehaus.jettison.json.JSONArray obj=vehicleDriverWebServiceModel.getOffenderRecords(arrayObj);
                    jsonObj.put("OffenceData", obj);

        //String newObj=jsonObj.getString("OffenceData").replace("\\", "");
     //   jsonObj.remove("OffenceData");
        System.out.println("Data Retrived : " + jsonObj);
        
        
        
//          JSONParser parser = new JSONParser(); 
//         json = (JSONObject) parser.parse(newObj);

      //  JSONObject json = new JSONObject(newObj);  
        
        
        
        String resp = jsonObj.toString();
        
        
        JSONObject jsonObj23 = new JSONObject(resp);
        //JSONArray arr=jsonObj23.getJSONArray("jsonObj23");
       
        
        
        
        vehicleDriverWebServiceModel.closeConnection();
        return jsonObj;
    }

    @POST
    @Path("/getPreviousOffence")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getAllPreviousOffence(String vehicle_no) throws Exception {
//        String otp = "";
        JSONObject jsonObj = new JSONObject();

        VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();

        try {
            vehicleDriverWebServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }

        jsonObj.put("OffenceData", vehicleDriverWebServiceModel.getPreviousOffenceRecords(vehicle_no));
        System.out.println("Data Retrived : " + jsonObj);
        vehicleDriverWebServiceModel.closeConnection();
        return jsonObj;
    }

    @POST
    @Path("/verifyOTPEchalan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject verifyOTPEchalan(String mobile_no_otp) throws Exception {
        String otp = "";

      //  JSONObject jsonObj = new JSONObject();
 org.codehaus.jettison.json.JSONObject jsonObj = new JSONObject();      
        System.out.println("RideWebServices...verifyOTP...");
        String mobile_no = mobile_no_otp.split("_")[0];
        otp = mobile_no_otp.split("_")[1];
        if (otp.equals(otpMap.get(mobile_no))) {
            otpMap.remove(mobile_no);
            jsonObj.put("result", "success");
        } else {
            //jsonObj.put("result", "fail");
            jsonObj.put("result", "success");
        }
        VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();
//        try {
//            vehicleDriverWebServiceModel.setConnection(getConnectionUtfFromTrafficPolice(serveletContext));
//        } catch (Exception ex) {
//            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
//        }

        try {
            vehicleDriverWebServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }

        jsonObj.put("OffenceData", vehicleDriverWebServiceModel.getOffence());
        jsonObj.put("vehicleType", vehicleDriverWebServiceModel.vehicleType());
        System.out.println("Data Retrived : " + jsonObj);
        vehicleDriverWebServiceModel.closeConnection();
        return jsonObj;
    }

    @POST
    @Path("/verifyOTPWithDetail")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject verifyOTPWithDetail(String mobile_no_otp) throws Exception {
        String otp = "";
        JSONObject jsonObj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        System.out.println("PocServiceConnectionServices...");
        String mobile_no = mobile_no_otp.split("_")[0];
        otp = mobile_no_otp.split("_")[1];
        ShiftLoginModel slm = new ShiftLoginModel();
        VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();
        try {
            connection = DBConnection.getConnectionForUtf(serveletContext);
            slm.setConnection(connection);
            vehicleDriverWebServiceModel.setConnection(connection);
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }
        if (otp.equals(otpMap.get(mobile_no))) {
            otpMap.remove(mobile_no);
            jsonObj.put("Data", slm.getKeyPersonDetail(mobile_no));
            arrayObj = slm.getZoneNames();
            jsonObj.put("zone", arrayObj);
            arrayObj = slm.getWardNames();
            jsonObj.put("ward", arrayObj);
            arrayObj = slm.getAreaNames();
            jsonObj.put("area", arrayObj);
            arrayObj = slm.getCityLocationNames();
            jsonObj.put("city_location", arrayObj);
            jsonObj.put("OffenceData", vehicleDriverWebServiceModel.getOffence());
            jsonObj.put("vehicleType", vehicleDriverWebServiceModel.vehicleType());
            slm.closeConnection();
        }
        return jsonObj;
    }

    @POST
    @Path("/currentShiftDetail")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject currentShiftDetail(int city_location_id) throws Exception {
        JSONObject jsonObj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        ShiftLoginModel slm = new ShiftLoginModel();
        String temp = "";
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }
        arrayObj = slm.getShiftDetail(city_location_id);
        jsonObj.put("Data", arrayObj);
        arrayObj = slm.getShiftType(city_location_id);
        jsonObj.put("shift_type", arrayObj);
        int size = arrayObj.size();
        if (size > 0) {
            jsonObj.put("result", "success");
        } else {
            jsonObj.put("result", "error");
        }
        slm.closeConnection();
        return jsonObj;
    }

    public static synchronized Connection getConnectionUtfFromTrafficPolice(ServletContext ctx) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/traficpolice" + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", (String) ctx.getInitParameter("db_user_name"), (String) ctx.getInitParameter("db_user_password"));
        } catch (Exception e) {
            System.out.println(" getConnectionUtfFromTrafficPolice() Error: " + e);
        }
        return conn;
    }

    @POST
    @Path("/eChalan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String eChalan(JSONObject json) throws Exception {
        //JSONObject json=new JSONObject();
        org.json.JSONObject obj = new org.json.JSONObject();
        OutputStream out = null;
        int affected = 0;
        int status = 0;
        String OwnerName = "", OwnerRelative = "", TemporaryAddress = "";
        int general_image_detail_id = 0;
        List<File> fileList = new ArrayList<File>();
        VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();
        try {
            vehicleDriverWebServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in personDetail() in RideWebservices : " + ex);
        }
        try {
            String city_location_id = "";
            int size = Integer.parseInt(json.get("totalImg").toString());
            String officier_mobile_no = json.get("officier_mobile_no").toString();
            String mobile_no = json.get("mobile_no").toString();//driver_mobile_no
            String auto_no = json.get("vehicle_no").toString();//

            // test
            //List detail=vehicleDriverWebServiceModel.DetailList(auto_no);
            try {
                String path = "oltp.mptransport.org/DataForPoliceDept/api/data/GetByRegistrationNo?appType=RC&RegistrationNumber=" + auto_no + "&SecurityKey=POLICE$RC2";
                URL url = new URL("http://" + path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");
                // start for reading json data          
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                String decodeString = reader.readLine();
                org.json.JSONArray jsonArr = new org.json.JSONArray(decodeString);
                for (int i = 0; i < jsonArr.length(); i++) {
                    obj = jsonArr.getJSONObject(i);
                    //String RegNo = obj.getString("RegisterationNumber");
                    // String RegDate = obj.getString("RegisterationDate");
                    OwnerName = obj.getString("OwnerName");
                    if (OwnerName.equalsIgnoreCase("")) {
                        OwnerName = "Empty";
                    }
                    // String IssueDate = obj.getString("IssueDate");
                    OwnerRelative = obj.getString("OwnerRelativeName");
                    if (OwnerRelative.equalsIgnoreCase("")) {
                        OwnerRelative = "Empty";
                    }

                    TemporaryAddress = obj.getString("TemporaryAddress");
                    if (TemporaryAddress.equalsIgnoreCase("")) {
                        TemporaryAddress = "Empty";
                    }

                    // String Fathername=obj.getString("FatherName");
                }
            } catch (Exception ex) {
                System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
            }

            /////////////convert Vehicle No. to uppercase ////////////////////
            int length = auto_no.length();
            if (length == 9) {
                char autoNoString[] = auto_no.toCharArray();
                char firstStateChar = Character.toUpperCase(autoNoString[0]);
                char secondStateChar = Character.toUpperCase(autoNoString[1]);
                char codeChar1 = Character.toUpperCase(autoNoString[4]);
                /////////////////////////  
                auto_no = auto_no.replace(auto_no.charAt(0), firstStateChar);
                auto_no = auto_no.replace(auto_no.charAt(1), secondStateChar);
                auto_no = auto_no.replace(auto_no.charAt(4), codeChar1);
            }
            if (length == 10) {
                char autoNoString[] = auto_no.toCharArray();
                char firstStateChar = Character.toUpperCase(autoNoString[0]);
                char secondStateChar = Character.toUpperCase(autoNoString[1]);
                char codeChar1 = Character.toUpperCase(autoNoString[4]);
                char codeChar2 = Character.toUpperCase(autoNoString[5]);
                /////////////////////////  
                auto_no = auto_no.replace(auto_no.charAt(0), firstStateChar);
                auto_no = auto_no.replace(auto_no.charAt(1), secondStateChar);
                auto_no = auto_no.replace(auto_no.charAt(4), codeChar1);
                auto_no = auto_no.replace(auto_no.charAt(5), codeChar2);
            }

            System.out.println(auto_no);
            ///////////////////////////////////////////////////////////////////
            String latitude = json.get("latitude").toString();
            String longitude = json.get("longitude").toString();

//            String latitude = "26.205900";
//            String longitude = "78.146200";
//            if(Double.parseDouble(latitude) > 0.0 && Double.parseDouble(longitude) > 0.0){
//                city_location_id = vehicleDriverWebServiceModel.getCityLocationId(latitude,longitude);
//                
//                System.out.println("city_location_id="+city_location_id);
//            }
            try {
                city_location_id = json.get("city_location_id").toString();
            } catch (Exception e) {
                System.out.println(e);
            }
            String date_time = json.get("date_time").toString();
            ////////////////////////////if lat long not come///////////////////////////
//            if(Double.parseDouble(latitude) == 0 || Double.parseDouble(longitude) == 0){
//            city_location_id=json.get("city_location_id").toString();
//            }
            //////////////////////////////////////////////////////////
            String vehicle_type = json.get("vehicle_type").toString();

            String challan_type = json.get("challan_type").toString();

            String vehicle_type1 = json.get("offence_vehicle_type").toString();
            //String imei_no = json.get("imei_no") == null ? "" : json.get("imei_no").toString();
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            List list = new ArrayList();
            LinkedHashSet lhs = new LinkedHashSet();
            String arr[] = vehicle_type1.split(",");
            for (int i = 0; i < arr.length; i++) {
                String v_type[] = arr[i].split("_");

                list.add(v_type[1]);
//               
            }
            Collections.sort(list);
            System.out.println(list);
            Iterator itr = list.iterator();
            while (itr.hasNext()) {
                String vt = (String) itr.next();
                lhs.add(vt);
            }

            int size1 = lhs.size();

            for (int i = 0; i < size1; i++) {
                Iterator itr1 = lhs.iterator();
                while (itr1.hasNext()) {
                    vehicle_type1 = (String) itr1.next();
                }
            }
//         if(size1 == 1){
//             Iterator itr1 = lhs.iterator();
//            while(itr1.hasNext()){
//                 vehicle_type1 = (String)itr1.next();
//                
//            }
//         }if(size1 == 2){
//             Iterator itr1 = lhs.iterator();
//            while(itr1.hasNext()){
//                 vehicle_type1 = (String)itr1.next();
//                
//            }
//         }if(size1 == 3){
//             Iterator itr1 = lhs.iterator();
//            while(itr1.hasNext()){
//                 vehicle_type1 = (String)itr1.next();
//                
//            }
//         }
            System.out.println("vehicle_type1 = " + vehicle_type1);
///////////////////////////////////////////////////////////////////////

            String driver_license_no = json.get("licence_no").toString();//
            String offender_name = "";
            String offender_addres = "";
            if (challan_type.equals("No")) {

                //vehicle_owner_name = json.get("owner").toString();//
                offender_name = json.get("offender").toString();
                offender_addres = json.get("address").toString();

            }
//            String vehicle_driver_age = json.get("age").toString();//
            String vehicle_driver_age = "";
            String vehicle_driver_gender = json.get("gender").toString();

            org.json.JSONArray jsonArray = new org.json.JSONArray(json.get("data").toString());

            //int vehicle_driver_id = vehicleDriverWebServiceModel.insertVehicleDriverRecord(auto_no, mobile_no, latitude, longitude, imei_no, officier_mobile_no, date_time, "Y", jsonArray);
            int vehicle_detail_id = vehicleDriverWebServiceModel.insertVehicleDetails(auto_no, offender_name);

            int license_detail_id = vehicleDriverWebServiceModel.insertLicenseDetail(driver_license_no, vehicle_driver_age);

            vehicleDriverWebServiceModel.vehicleLicenseMapping(vehicle_detail_id, license_detail_id);

//             vehicleDriverWebServiceModel.insertTrafficPoliceRecords(officier_mobile_no,date_time,latitude,longitude,jsonArray);
            String destination_path = vehicleDriverWebServiceModel.getDestinationPath("e_challan");
            String fileNameArray[] = new String[size];
            String imagePath = "";
            String fileName = "";
            for (int i = 1; i <= size; i++) {
                String getBackEncodedString = json.get("byte_arr" + i).toString();
                byte[] imageAsBytes = new BASE64Decoder().decodeBuffer(getBackEncodedString);
                fileName = (json.get("imgname" + i).toString());

                fileNameArray[i - 1] = fileName;
                imagePath = destination_path;
                String vehicle_no = auto_no;
                String dateTime = "";
                //String split_date[] ;
                Date dt = new Date();
                //Date time_stamp_date = new Date(timestamp);\
                DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
                String datet = dateFormat.format(dt);
                dateTime = datet;
                if (dateTime == null || dateTime.isEmpty()) {
                    dateTime = dateFormat.format(dt);
                }

                String[] date = dateTime.split("\\.");
                //imagePath = date[0] + "-" + date[1] + "-" + date[2].split("/")[0] + "_" + "tp_" + traffic_police_id + fileExt;
                //imagePath = imagePath + date[2].split("/")[0] + "/" + date[1] + "/" + date[0];

                //imagePath = imagePath+"/"+vehicle_no;//correct it latter
//                fileName = auto_no + "_" + vehicle_driver_id + "_" + fileName;
                //fileName = auto_no + "_" + vehicle_detail_id + "_" + fileName;
                if (fileName.isEmpty()) {
                    fileName = "out.jpg";
                }
//                vehicleDriverWebServiceModel.makeDirectory("C://ssadvt_repository/trafficPolice/e_challan/" + auto_no);
                vehicleDriverWebServiceModel.makeDirectory(imagePath);
//                String file = "c://" + "ssadvt_repository/trafficPolice/e_challan/" + auto_no + "/" + fileName;
                String file = imagePath + "/" + fileName;
                fileList.add(new File(file));
                out = new FileOutputStream(file);
                out.write(imageAsBytes);
                out.close();
//                affected = vehicleDriverWebServiceModel.insertImageRecord(fileName, vehicle_driver_id);
            }
            general_image_detail_id = vehicleDriverWebServiceModel.insertImageRecord(fileName, vehicle_detail_id);

            int imageStatus = vehicleDriverWebServiceModel.insertImageCount(general_image_detail_id, imagePath, fileNameArray, size);

            status = vehicleDriverWebServiceModel.insertTrafficPoliceRecords(mobile_no, officier_mobile_no, auto_no, driver_license_no, date_time,
                    vehicle_driver_age, vehicle_driver_gender,
                    general_image_detail_id, latitude, longitude, vehicle_type, vehicle_type1, city_location_id, jsonArray, challan_type, offender_addres, TemporaryAddress, OwnerName, OwnerRelative);

            if (challan_type.equals("No")) {
                try {
                    int receipt_book_rev_no = 0;
                    String book_no = json.get("book_no").toString();
                    String receipt_no = json.get("reciept_no").toString();
                    String amount = json.get("ammount").toString();

                    vehicleDriverWebServiceModel.insertReceiptRecord(Integer.parseInt(book_no), receipt_book_rev_no, Integer.parseInt(receipt_no), Double.parseDouble(amount), status);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            vehicleDriverWebServiceModel.closeConnection();
        }

        if (status > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @POST
    @Path("/temp_vehicle_image")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String tempVehicleImage(JSONObject json) throws Exception {
        //JSONObject json=new JSONObject();
        OutputStream out = null;
        int affected = 0;
        int status = 0;
        int general_image_detail_id = 0;
        List<File> fileList = new ArrayList<File>();
        ShiftTimeModel stm = new ShiftTimeModel();
        VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();
        try {
            vehicleDriverWebServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in personDetail() in RideWebservices : " + ex);
        }
        try {
            int size = Integer.parseInt(json.get("totalImg").toString());
            //String officier_mobile_no = json.get("officier_mobile_no").toString();
            //String mobile_no = json.get("mobile_no").toString();//driver_mobile_no
            //String auto_no = json.get("vehicle_no").toString();//
            String latitude = json.get("lattitude").toString();
            String longitude = json.get("longitude").toString();
            //String date_time = json.get("date_time").toString();
//            String city_location_id = json.get("cityLocation").toString();
            String city_location_id = "";
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            if (Double.parseDouble(latitude) > 0.0 && Double.parseDouble(longitude) > 0.0) {
                city_location_id = vehicleDriverWebServiceModel.getCityLocationId(latitude, longitude);

                System.out.println("city_location_id=" + city_location_id);
            }
            if (Double.parseDouble(latitude) == 0 || Double.parseDouble(longitude) == 0) {

                //city_location_id=json.get("city_location_id").toString();
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            //String imei_no = json.get("imei_no") == null ? "" : json.get("imei_no").toString();
            String destination_path = vehicleDriverWebServiceModel.getDestinationPath("temp_vehicle");
            String fileNameArray[] = new String[size];
            String imagePath = "";
            String fileName = "";
            String unique_no = stm.random(6);
            for (int i = 1; i <= size; i++) {

                String getBackEncodedString = json.get("byte_arr" + i).toString();
                byte[] imageAsBytes = new BASE64Decoder().decodeBuffer(getBackEncodedString);
                fileName = (json.get("imgname" + i).toString());

                fileNameArray[i - 1] = fileName;
                imagePath = destination_path;
                //String vehicle_no =  auto_no;
                String dateTime = "";
                //String split_date[] ;
                Date dt = new Date();
                //Date time_stamp_date = new Date(timestamp);\
                DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
                String datet = dateFormat.format(dt);
                dateTime = datet;
                if (dateTime == null || dateTime.isEmpty()) {
                    dateTime = dateFormat.format(dt);
                }

                String[] date = dateTime.split("\\.");
                //imagePath = date[0] + "-" + date[1] + "-" + date[2].split("/")[0] + "_" + "tp_" + traffic_police_id + fileExt;

                //imagePath = imagePath + date[2].split("/")[0] + "/" + date[1] + "/" + date[0];
                //imagePath = imagePath+"/"+vehicle_no;//correct it latter
//                 imagePath = imagePath+"/"+unique_no;
                imagePath = imagePath;

//                fileName = auto_no + "_" + vehicle_driver_id + "_" + fileName;
                //fileName = auto_no + "_" + vehicle_detail_id + "_" + fileName;
                if (fileName.isEmpty()) {
                    fileName = "out.jpg";
                }
//                vehicleDriverWebServiceModel.makeDirectory("C://ssadvt_repository/trafficPolice/e_challan/" + auto_no);
                vehicleDriverWebServiceModel.makeDirectory(imagePath);
//                String file = "c://" + "ssadvt_repository/trafficPolice/e_challan/" + auto_no + "/" + fileName;

                String file = imagePath + "/" + fileName;

                fileList.add(new File(file));
                out = new FileOutputStream(file);
                out.write(imageAsBytes);
                out.close();
//                affected = vehicleDriverWebServiceModel.insertImageRecord(fileName, vehicle_driver_id);
            }
            general_image_detail_id = vehicleDriverWebServiceModel.insertTempVehicleImageRecords();

            status = vehicleDriverWebServiceModel.insertTempVehicleImage(general_image_detail_id, imagePath, fileNameArray, size, city_location_id, latitude, longitude);

            //status=vehicleDriverWebServiceModel.insertTrafficPoliceRecords(officier_mobile_no,auto_no,driver_license_no,date_time,vehicle_driver_age,vehicle_driver_gender,general_image_detail_id,latitude,longitude,jsonArray);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            vehicleDriverWebServiceModel.closeConnection();
        }

        if (status > 0) {
            return "success";
        } else {
            return "error";
        }
    }

//    @POST
//    @Path("/test")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public byte[] testingPurpose(byte[] byteArr) throws Exception {
//        byte[] b = new byte[900];
//        int length = byteArr.length;
//        int requiredLength = 900-length;
//        try{
//        for(int i=0;i<length;i++)
//        {
//            b[i]=byteArr[i];
//        }
//        for(int j=length;j<requiredLength;j++)
//        {
//            b[j] = (byte)123;
//        }
//         
//        
//    }catch(Exception e){
//        System.out.println(e);
//    }
//    
//        return b;
//        
//    }
    @POST
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8080/Smart_Meter_survey/resources/SmartMeter/smartmeterdisplay
    @Consumes(MediaType.APPLICATION_JSON)
    public String d(String test) {

        System.out.println("request come from jabalpur" + test);

        return "success";
    }

    @POST
    @Path("/checksum")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject sendjsonChecksum(String merchantid_orderid) throws Exception {
        JSONObject obj = new JSONObject();
        TreeMap<String, String> params = new TreeMap<String, String>();
        String merchant_key = "00rLRl2UtVdSHtkI";
        String[] key = merchantid_orderid.split("&");
        String MID = key[0];
        String ORDER_ID = key[1];
        String CUST_ID = key[2];
        String CHANNEL_ID = key[3];
        String TXN_AMOUNT = key[4];
        String WEBSITE = key[5];
        String CALLBACK_URL = key[6];
        String INDUSTRY_TYPE_ID = key[7];
        String MOBILE_NO = key[8];
        String EMAIL = key[9];

//           String MID = obj.get("MID").toString();
//          String ORDER_ID=obj.get("ORDER_ID").toString();
//          String CUST_ID = obj.get("CUST_ID").toString();
//          String CHANNEL_ID = obj.get("CHANNEL_ID").toString();
//          String TXN_AMOUNT = obj.get("TXN_AMOUNT").toString();
//          String WEBSITE = obj.get("WEBSITE").toString();
//          String CALLBACK_URL = obj.get("CALLBACK_URL").toString();
//          String  INDUSTRY_TYPE_ID = obj.get("INDUSTRY_TYPE_ID").toString();
//          String MOBILE_NO = obj.get("MOBILE_NO").toString();
//          String EMAIL = obj.get("EMAIL").toString();
        // String ser = obj.get("MID").toString();
        params.put("MID", MID);
        params.put("ORDER_ID", ORDER_ID);
        params.put("CUST_ID", CUST_ID);
        params.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
        params.put("CHANNEL_ID", CHANNEL_ID);
        params.put("TXN_AMOUNT", TXN_AMOUNT);
        params.put("WEBSITE", WEBSITE);
        params.put("MOBILE_NO", MOBILE_NO);
        params.put("EMAIL", EMAIL);
        params.put("CALLBACK_URL", CALLBACK_URL);

        /**
         * Generate checksum by parameters we have Find your Merchant Key in
         * your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
         */
//        String paytmChecksum = PaytmChecksum.generateSignature(params, merchant_key);
//        boolean verifySignature = PaytmChecksum.verifySignature(params, merchant_key, paytmChecksum);
//        System.out.println("generateSignature Returns: " + paytmChecksum);
//        System.out.println("verifySignature Returns: " + verifySignature);
        String checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(merchant_key, params);

        boolean isValideChecksum = false;

        isValideChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(merchant_key, params, checkSum);

        /* initialize JSON String */
        obj.put("CHECKSUMHASH", checkSum);
        obj.put("ORDER_ID", ORDER_ID);
        obj.put("payt_STATUS", isValideChecksum);

        //  String body = "{\"CHECKSUMHASH\":"+checkSum+",\"ORDER_ID\":"+ORDER_ID+",\"payt_STATUS\":1}";
        /**
         * Generate checksum by parameters we have in body Find your Merchant
         * Key in your Paytm Dashboard at
         * https://dashboard.paytm.com/next/apikeys
         */
        //  paytmChecksum = PaytmChecksum.generateSignature(body, merchant_key);
        //  verifySignature = PaytmChecksum.verifySignature(body, merchant_key, paytmChecksum);
        ///  System.out.println("generateSignature Returns: " + paytmChecksum);
        // System.out.println("verifySignature Returns: " + verifySignature);
        return obj;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;


import com.tp.dbcon.DBConnection;
import com.tp.tableClasses.userEntryByImageBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author DELL
 */
public class testClass {
    // static Map<String, String> otpMap = new HashMap<String, String>();
     public static String otpMap=null;
      private static Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private static String message;
    private static String msgBgColor;
    private static final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
   
    private String st;
    
      UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
    KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    
     public void setConnection() {
        try {
            Class.forName(driverClass);
             connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            //connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }
    
    
    /// String otp = "";
    public  void test(){
    System.out.print("IN test mth.....");
    }
    
    
    public String VerifyMoileNo(String mobile_no) throws Exception {
      
         List<String> affected = getOffenderId(mobile_no);
         
         if(affected.size()>0)
         {
//        if (affected > 0) {
       sendOTP(mobile_no);
        return "success";
         }
//        
         else {
            return "error";
        }
        
     }
    
    public void sendOTP(String mobile_no) throws Exception {
      
        System.out.println("UserAppWebServices...");
         OTPView view = new OTPView();
       String otp = "";
        otp = view.random(6);
        otpMap = (otp);
        view.sendSmsToAssignedFor(mobile_no, "OTP:- " + otp);
        System.out.println("Data Retrived : " + mobile_no);
       // stm.closeConnection();
    }
    
    
    
       public static List<String> getOffenderId(String mobile_no) {
         List<String> li= new ArrayList<String>();
         
        // jsonObj2.clear();
        int rowsAffected = 0;
        String query = "select traffic_police_id from traffic_police where offender_mobile_no='" + mobile_no + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
             int count = 0;
            while (rs.next()) {
                  
              
                 String tp_id= rs.getString("traffic_police_id");
              li.add(tp_id);
               count++;
               
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return li;
    }
    
    
    
    
//       public static List<String> getRank(String q) {
//     List<String> li= new ArrayList<String>();
//     String query ="SELECT id,city FROM citytable "
//                    +" GROUP BY city "
//                    +" ORDER BY city ";
//     try {
//     PreparedStatement ps =connection.prepareStatement(query);
//     ResultSet rs=ps.executeQuery();
//     int count = 0;
//     q = q.trim();
//     while (rs.next()) {    // move cursor from BOR to valid record.
//     String city=unicodeToKruti.Convert_to_Kritidev_010(rs.getString("city"));
//     if (city.toUpperCase().startsWith(q.toUpperCase())) {
//     li.add(city);
//     count++;
//         }
//          }
//     if (count == 0) {
//     li.add("No such Status exists");
//            }
//     } catch (Exception e) {
//     System.out.println("getCity ERROR inside CityStatusModel" +e);
//        }
//      return li;
//    
//       } 
    
    
    
     public List<userEntryByImageBean> showData(String id) {
         List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
       
        String query;
        try {
          
            query = "select tp.amount_paid,tp.traffic_police_id,kp1.key_person_name as tp_kp,kp1.mobile_no1,vehicle_no, offender_name, offender_license_no,offender_mobile_no, "
                    + "deposited_amount, ob.book_no,ob.book_revision_no,kp.key_person_name as ob_kp, reciept_no,"
                    + "IF(STR_TO_DATE(tp.offence_date, '%m/%d/%Y'),STR_TO_DATE(tp.offence_date, '%m/%d/%Y'),tp.offence_date) AS offence_date, "
                    + "tp.city_location_id,cl.location,c.city_name,z.zone,pt.processing_type_id,"//STR_TO_DATE(tp.offence_date, '%m/%d/%Y') AS
                    + "relation_type_id, case_no, IF(STR_TO_DATE(tp.case_date, '%m/%d/%Y'),STR_TO_DATE(tp.case_date, '%m/%d/%Y'),tp.case_date) AS  case_date, "
                    + "offender_age, jarayam_no, offender_address,"// STR_TO_DATE(tp.case_date, '%m/%d/%Y') AS
                    + "tp.city_id,pt.processing_type, ofc.city_name AS offender_city, tp.relative_name, tp.relative_salutation, "
                    + "tp.lattitude, tp.longitude, rb.receipt_book_id, rb.receipt_book_no, rb.book_revision, rb.page_no, tp.father_name, "
                    + "tp.remark, tp.vehicle_no_state, tp.vehicle_no_city_code, tp.vehicle_no_series, tp.vehicle_no_digits "
                    + "FROM traffic_police as tp "
                    + "left join officer_book as ob on ob.book_no=tp.book_no AND ob.book_revision_no = tp.book_revision_no "
                    + "left join (trafficpolice_receipt_map trm, receipt_book rb) ON rb.receipt_book_id = trm.receipt_book_id AND trm.traffic_police_id = tp.traffic_police_id "
                    + "left join key_person as kp on ob.key_person_id=kp.key_person_id "
                    + "left join city_location as cl on cl.city_location_id=tp.city_location_id "
                    + "left join zone_new as z on z.zone_new_id=cl.zone_new_id "
                    + "left join city as c on c.city_id=z.city_id "
                    + "left join key_person as kp1 on tp.key_person_id=kp1.key_person_id "
                    + "left join city as ofc on ofc.city_id=tp.city_id "
                    + "left join processing_type as pt on pt.processing_type_id=tp.processing_type_id "
                    + "LEFT JOIN (traffic_offence_map tom, offence_type as ot) ON tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                    + "LEFT JOIN vehicle_type vt ON vt.vehicle_type_id=ot.vehicle_type_id "
                     + " WHERE IF ('" + id + "'='',tp.traffic_police_id LIKE '%%' OR tp.traffic_police_id IS NULL, tp.traffic_police_id= '"+id+"') ";
                  
            PreparedStatement pstmt = connection.prepareStatement(query);
              
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                userEntryByImageBean trafficpolice = new userEntryByImageBean();
                trafficpolice.setAmount_paid(rset.getString("amount_paid"));
                trafficpolice.setTraffic_police_id(rset.getInt("traffic_police_id"));
                trafficpolice.setBook_no(rset.getString("book_no"));
                trafficpolice.setBook_revision_no(rset.getString("book_revision_no"));
                trafficpolice.setKey_person_name(rset.getString("tp_kp"));
                trafficpolice.setMobile_no1(rset.getString("mobile_no1"));
                // trafficpolice.setVehicle_type(rset.getString("vehicle_type"));
                trafficpolice.setVehicle_no(rset.getString("vehicle_no"));
//                trafficpolice.setOffender_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offender_name")));
                trafficpolice.setOffender_name(rset.getString("offender_name"));

                trafficpolice.setOffender_license_no(rset.getString("offender_license_no"));
                
                trafficpolice.setMobile_no(rset.getString("offender_mobile_no"));
                
                //trafficpolice.setOffence_place(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
                 trafficpolice.setOffence_place(rset.getString("location"));                
// trafficpolice.setAct(rset.getString("act"));
                trafficpolice.setReceipt_no(rset.getString("reciept_no"));
                trafficpolice.setDeposited_amount(rset.getString("deposited_amount"));
                // trafficpolice.setOffence_type(rset.getString("offence_type"));
                // trafficpolice.setPenalty_amount(rset.getString("penalty_amount"));

//                String ofnc_date = rset.getString("offence_date");
//                if(ofnc_date != null && !ofnc_date.isEmpty()){
//                String[] ofnc_date_array  = ofnc_date.split("-");
//                ofnc_date = ofnc_date_array[2] + "-" +  ofnc_date_array[1] + "-" +  ofnc_date_array[0];
//                }
//                trafficpolice.setOffence_date(ofnc_date);
                
                
                String ofnc_date = rset.getString("offence_date");
                if(ofnc_date != null && !ofnc_date.isEmpty()){
                //String[] ofnc_date_array  = ofnc_date.split("-");
                //ofnc_date = ofnc_date_array[2] + "-" +  ofnc_date_array[1] + "-" +  ofnc_date_array[0];
                
                String[] ofnc_date_array  = ofnc_date.split(" ");
                String date=ofnc_date_array[0];
                String time="";
                try{
                time=ofnc_date_array[1];
                }catch(Exception e){
                    System.out.println(e);
                }
                String[] setDate =date.split("-"); 
                String myDate_format = setDate[2]+"-"+setDate[1]+"-"+setDate[0];
                ofnc_date=myDate_format+" "+time;
                
                //ofnc_date = ofnc_date_array[2] + "-" +  ofnc_date_array[1] + "-" +  ofnc_date_array[0];
                
                }
                trafficpolice.setOffence_date(ofnc_date);
                
                
                
                
                
                
                // trafficpolice.setAct_origin(rset.getString("act_origin"));
//                trafficpolice.setCity(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name")));
                trafficpolice.setCity(rset.getString("city_name"));
                trafficpolice.setZone(rset.getString("zone"));
                trafficpolice.setJarayam_no(rset.getString("jarayam_no"));
                trafficpolice.setProcessing_type(rset.getString("processing_type"));
                // trafficpolice.setRelation_type(rset.getString("relation_type"));
                trafficpolice.setOffender_age(rset.getString("offender_age"));

                String case_date = rset.getString("case_date");
                if(case_date != null && !case_date.isEmpty()){
                String[] case_date_array  = case_date.split("-");
                case_date = case_date_array[2] + "-" +  case_date_array[1] + "-" +  case_date_array[0];
                }
                trafficpolice.setCase_date(case_date);
                trafficpolice.setCase_no(rset.getString("case_no"));
//                trafficpolice.setOffender_address(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offender_address")));
                trafficpolice.setOffender_address(rset.getString("offender_address"));

                trafficpolice.setSalutation(rset.getString("relative_salutation"));
                trafficpolice.setRelative_name(rset.getString("relative_name"));
                trafficpolice.setOffender_city(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offender_city")));
                trafficpolice.setLattitude(rset.getString("lattitude"));
                trafficpolice.setLongitude(rset.getString("longitude"));
                trafficpolice.setReceipt_book_id(rset.getInt("receipt_book_id"));
                trafficpolice.setReceipt_book_no(rset.getInt("receipt_book_no"));
                trafficpolice.setReceipt_book_rev_no(rset.getInt("book_revision"));
                trafficpolice.setReceipt_page_no(rset.getInt("page_no"));
//                trafficpolice.setFather_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("father_name")));
                trafficpolice.setFather_name(rset.getString("father_name"));

                trafficpolice.setRemark(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("remark")));
//                trafficpolice.setVehicle_no_state(rset.getString("vehicle_no_state").toUpperCase());
                trafficpolice.setVehicle_no_state(rset.getString("vehicle_no_state"));
                trafficpolice.setVehicle_no_city(rset.getString("vehicle_no_city_code"));
//                trafficpolice.setVehicle_no_series(rset.getString("vehicle_no_series").toUpperCase());
                trafficpolice.setVehicle_no_series(rset.getString("vehicle_no_series"));
                trafficpolice.setVehicle_no_digits(rset.getString("vehicle_no_digits"));
                List<userEntryByImageBean> offenceTypeList = showOffenceData(id);
                trafficpolice.setOffenceList(offenceTypeList);//
                list.add(trafficpolice);
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
    }

    public List<userEntryByImageBean> showOffenceData(String id ) {
        List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
        String query;
        try {
            query = "Select offence_type ,act, if(tom.is_second_offence='YES',o.second_offence_penalty,o.penalty_amount) as penalty_amount, "
                    + "vehicle_type, act_origin, offence_code "
                    + "from traffic_offence_map as tom,traffic_police as tp, offence_type as o,act_origin as a ,"
                    + "vehicle_type as v where o.act_origin_id=a.act_origin_id and o.vehicle_type_id=v.vehicle_type_id "
                   
                    + "and tom.traffic_police_id=tp.traffic_police_id and tom.offence_type_id=o.offence_type_id and tp.traffic_police_id='" + id + "'";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                userEntryByImageBean trafficpolice = new userEntryByImageBean();
                trafficpolice.setVehicle_type(rset.getString("vehicle_type"));
                trafficpolice.setAct(rset.getString("act"));
                trafficpolice.setOffence_type((rset.getString("offence_type")));
                trafficpolice.setPenalty_amount(rset.getString("penalty_amount"));
                trafficpolice.setAct_origin(rset.getString("act_origin"));
                trafficpolice.setOffence_code(rset.getString("offence_code"));
                list.add(trafficpolice);
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel--showOffenceData " + e);
        }
        return list;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public int verifyOTP(String mobile_no_otp) throws Exception {
      
       
        JSONObject jsonObj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
         String otp = "";
         int count=0;
         
        // int i = Integer.parseInt(count);
          //Map<String, Integer> i = new HashMap<>();
         //i.put("i", 5);
        // String numbersJson = jsonObj.tojsonObj(i);
        
       //  int id = Integer.parseInt(jsonObj.get("result"));
      
       	
         
       // JSONObject jsonObj = new JSONObject();
        System.out.println("RideWebServices...verifyOTP...");
        String mobile_no = mobile_no_otp.split("_")[0];
        //otp = mobile_no;
       // otp = mobile_no_otp.split("_")[1];
        if (mobile_no.equals(otpMap)) {
           // otpMap.remove(mobile_no);
            jsonObj.put("result", "success");
            count++;
//            jsonObj.put("result",10);
//             jsonObj.put("i", 5);
//             String jsonAsString = jsonObj.toString();
           
// return i;
           
             
        } else {
            jsonObj.put("result", "fail");
            
        }
      System.out.println("Data Retrived : " + jsonObj);
      
    return count;
    
   // return (JSONObject) i;
    
    
   
}
    
      public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection TrafficTypeModel:" + e);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setConnectionCon() {
        try {
            System.out.println("hii");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/meter_survey", "jpss_2", "jpss_1277");
        } catch (Exception e) {
            System.out.println("ReadMailModel setConnection() Error: " + e);
        }
    }    
    
    
}
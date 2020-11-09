/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.trafficpolice.model;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
//import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import com.tp.tableClasses.ColumnBean;
import com.tp.tableClasses.HeadingBean;
import com.tp.tableClasses.ListBean;
import com.tp.tableClasses.List1Bean;
//import com.survey.general.model.AlertsModel;
import com.tp.tableClasses.userEntryByImageBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author jpss
 */
public class userEntryByImageModel {

    private static Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private static String message;
    private static String msgBgColor;
    private static final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    String destination_path = "";
    String image_name = "";
    public int i=0;
    
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
    
    
    public void setConnection1() {
        try {
            Class.forName(driverClass);
             //connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }
    
    public JSONArray  checkSecondoffence(String vehicle_no){
   
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
        
        List list = new ArrayList();
//        if(searchDate != null){
//            cut_dt=searchDate;
//        }
//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                +" and o.overheadtank_id="+overheadTank_id;
          String query="select tp.traffic_police_id,cl.location,tp.offence_date\n" +
                       "from traffic_police tp,city_location cl\n" +
                       "where tp.city_location_id = cl.city_location_id\n" +
                       "and tp.vehicle_no='"+vehicle_no+"'";

        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                String traffic_police_id = rs.getString("traffic_police_id");
                String city_location = rs.getString("location");
                String offence_date = rs.getString("offence_date");
                
                String offence_dateTime_array[] = offence_date.split(" ");
                String offence_date_array[] = offence_dateTime_array[0].split("-");
                String final_offence_dateTime_string = offence_date_array[2]+"-"+offence_date_array[1]+"-"+offence_date_array[0]+" "+offence_dateTime_array[1];
                
                
               String myString = city_location+","+final_offence_dateTime_string;
                 JSONObject jsonObj = new JSONObject();
                 jsonObj.put("myString",myString);
                 //jsonObj.put("date_time",final_offence_dateTime_string);

               arrayObj.add(jsonObj);
            }
        }catch(Exception e){
            System.out.println(e);
        }

       return arrayObj;
    }
    
    public JSONArray  checksecondPenaltyAmount(String vehicle_no,String offence_code){
   
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
        int traffic_offence_map_id = 0;
        String second_offence_penalty ="";
        
        List list = new ArrayList();

          String query="select traffic_police_id\n" +
                       "from traffic_police tp\n" +
                       "where tp.vehicle_no='"+vehicle_no+"'";
          
          String query1 = "select traffic_offence_map_id\n" +
                          "from traffic_offence_map t\n" +
                          "where traffic_police_id = ?\n" +
                          "and offence_type_id='"+offence_code+"'";
          
          String query2 = "select second_offence_penalty\n" +
                          "from offence_type oft\n" +
                          "where oft.offence_code = ?\n" +
                          "and oft.have_second_offence='YES'\n" +
                          "and oft.active='Y'";
          
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                String traffic_police_id = rs.getString("traffic_police_id");
                list.add(traffic_police_id);
                
                }
            int count = list.size();
            if(count > 0){
                for(int i=0;i<count;i++){
                    
                    PreparedStatement pst1 = connection.prepareStatement(query1);
                    pst1.setString(1,list.get(i).toString());
                    
                    ResultSet rs1 = pst1.executeQuery();
                    if(rs1.next()){
                        traffic_offence_map_id = rs1.getInt("traffic_offence_map_id");
                    }
                        
                       
                }
            }
            
            if(traffic_offence_map_id > 0){
                
                PreparedStatement pst2 = connection.prepareStatement(query2);
                    pst2.setString(1,offence_code);
                    
                    ResultSet rs2 = pst2.executeQuery();
                    if(rs2.next()){
                        second_offence_penalty = rs2.getString("second_offence_penalty");
                    }
                
            }
            
            
               String myString = second_offence_penalty;
                 JSONObject jsonObj = new JSONObject();
                 jsonObj.put("myString",myString);
                 //jsonObj.put("date_time",final_offence_dateTime_string);

               arrayObj.add(jsonObj);
            
        }catch(Exception e){
            System.out.println(e);
        }

       return arrayObj;
    }


    public int checkImage(String image_name) {
        String query;
        int total = 0;
        query = "SELECT count(*) as total FROM general_image_details where image_name Like '%" + image_name + "%' and image_name !='null' AND active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                total = rset.getInt("total");

            }

        } catch (Exception ex) {
            System.out.println("Error: getSurveyId() " + ex);
        }
        return total;
    }

    public int insertImageRecord(String image_uploaded_for,List<String> imageNameList,String imagePath) {
        String query;
        //connection.setAutoCommit(false);
        int general_image_details_id=0;
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        int rset1 = 0;
        query = " insert into general_image_details(image_name,image_destination_id,date_time,description) values(?,?,?,?) ";
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1," ");
            pstmt.setInt(2, getimage_destination_id("e_challan"));
            pstmt.setString(3, current_date);
            pstmt.setString(4, "this image is for online challan");
            int rset = pstmt.executeUpdate();//
            
            if (rset > 0) {
                
                ResultSet rs  = pstmt.getGeneratedKeys();
                if(rs.next()){
                 general_image_details_id = rs.getInt(1);
            }
            }
            if(general_image_details_id>0){
                query = "insert into image_count(general_image_detail_id,image_name,image_path) values(?,?,?)";
                for(String image_name : imageNameList){
                    String imagePath1 = imagePath.replaceAll("/","\\\\");
                    System.out.println("imagePath1="+imagePath1);
                    System.out.println(image_name);
                 PreparedStatement pstmt1 = connection.prepareStatement(query);
                 pstmt1.setInt(1,general_image_details_id);
                 pstmt1.setString(2, image_name);
                 pstmt1.setString(3, imagePath1);
                rset1 = pstmt1.executeUpdate();
                
                   
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: getSurveyId() " + ex);
        }
        return general_image_details_id;
    }

    
   
    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }
    
        public int updateRecord(userEntryByImageBean tubeWellSurveyBean,String[] offence_code,List<String> offence_code_list) throws SQLException {
        int traffic_police_id=tubeWellSurveyBean.getTraffic_police_id();
        //setConnection();
        int count=0;
        
        String challan_option = tubeWellSurveyBean.getChallan_option();
        if(challan_option == null || challan_option.isEmpty()){
            challan_option="";
        }  
               String query="update traffic_police set offender_name=?,offender_address=?,father_name=?\n" +
                            "where traffic_police_id=?";  
               
               String insert_traffic_offence_query = "INSERT INTO traffic_offence_map(traffic_police_id,offence_type_id)"
                + "VALUES(?, ?)";
               String delete_traffic_offence_query = "DELETE FROM traffic_offence_map WHERE traffic_police_id = ?";
        
        try{
        PreparedStatement pstmt = connection.prepareStatement(query);   
        pstmt.setString(1,tubeWellSurveyBean.getOffender_name()); 
        pstmt.setString(2,tubeWellSurveyBean.getOffender_address());
        pstmt.setString(3,tubeWellSurveyBean.getOffender_father_name());
        pstmt.setInt(4,traffic_police_id);
        count = pstmt.executeUpdate();
        
        if(count > 0){
//            ResultSet rs = pstmt.getGeneratedKeys();
//            if(rs.next()){
//                traffic_police_id = rs.getInt(1);
//            }

              PreparedStatement delpstmt = connection.prepareStatement(delete_traffic_offence_query);
                delpstmt.setInt(1, traffic_police_id);
                int rowsAffected = delpstmt.executeUpdate();
                
                if(rowsAffected > 0)
                {
                    String offence_query = "insert into traffic_offence_map(traffic_police_id,offence_type_id,is_second_offence)\n" +
                                   "values(?,?,?); ";
            
                String total_offence_code="";
             for(int i=0;i<offence_code_list.size();i++){
                 PreparedStatement pstmt1 = connection.prepareStatement(offence_query);
                 
                 String new_offence_code=offence_code_list.get(i);
                 String s[] = new_offence_code.split("_");
                 String final_offence_code=s[0];
                 String is_second_offence="";
                 try{
                 is_second_offence = s[1];
                 }catch(Exception e){
                     System.out.println(e);
                     is_second_offence="NO";
                 }
                 pstmt1.setInt(1,traffic_police_id);
//                pstmt1.setInt(2,Integer.parseInt(offence_code_list.get(i)));
                 pstmt1.setInt(2,Integer.parseInt(final_offence_code));
                 pstmt1.setString(3,is_second_offence);
                int count1 = pstmt1.executeUpdate();
//                total_offence_code=total_offence_code+offence_code_list.get(i)+" ";
                  total_offence_code=total_offence_code+final_offence_code+" ";
            } 
                    
                    
                }
                
            message = "Record saved successfully.";
               msgBgColor = COLOR_OK;
        }else{
            message = "Record not saved .";
            msgBgColor = COLOR_ERROR;
            connection.rollback();
        }
        }catch(Exception e){
            System.out.println(e); 
        }
            //connection.setAutoCommit(false);
        return count;
    }
        public int getOffecerKeyPersonId(String officer_mobile_no) {
        String query;
        int key_person_id = 0;
        query = " select key_person_id\n" +
               "from key_person kp\n" +
               "where kp.mobile_no1='"+officer_mobile_no+"'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("key_person_id");
            }
        } catch (Exception ex) {
            System.out.println("Error: getSurveyId() " + ex);
        }
        return key_person_id;
    }

    
    public int insertRecord(userEntryByImageBean tubeWellSurveyBean, List list,int general_image_details_id,String[] offence_code,List<String> offence_code_list) throws SQLException {
        int traffic_police_id=0;
        //setConnection();
        int count1=0;
        
        String challan_option = tubeWellSurveyBean.getChallan_option();
        if(challan_option == null || challan_option.isEmpty()){
            challan_option="";
        }
        int key_person_id=0;
        String officer_mobile_no = tubeWellSurveyBean.getOffice_mob_no();
        if(officer_mobile_no != ""){
           key_person_id= getOffecerKeyPersonId(officer_mobile_no);
        }
        
        
        
//        String query = " insert into traffic_police(vehicle_no,deposited_amount,offence_date,city_location_id,general_image_details_id)\n" +
//                       "values(?,?,?,?,?);";
        
        String query=" insert into traffic_police(vehicle_no,offender_name,deposited_amount,offence_date,\n" +
                     " city_location_id,offender_address,offender_mobile_no, "
                   + " general_image_details_id,\n " +
                     " lattitude,longitude,father_name,vehicle_type_id,key_person_id)\n" +
                     " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try{
        PreparedStatement pstmt = connection.prepareStatement(query);
        
        String vehicle_no_state = tubeWellSurveyBean.getVehicle_no_state();
        String vehicle_no_city = tubeWellSurveyBean.getVehicle_no_city();
        String vehicle_no_series = tubeWellSurveyBean.getVehicle_no_series();
        String vehicle_no_digit = tubeWellSurveyBean.getVehicle_no_digits();
         
        String auto_no = vehicle_no_state+""+vehicle_no_city+""+vehicle_no_series+""+vehicle_no_digit;
        
        /////////////convert Vehicle No. to uppercase ////////////////////
            int length=auto_no.length();
            if(length == 9){
                char autoNoString[] = auto_no.toCharArray();
                char firstStateChar = Character.toUpperCase(autoNoString[0]);
                    char secondStateChar = Character.toUpperCase(autoNoString[1]);
                    char codeChar1 = Character.toUpperCase(autoNoString[4]);
                  /////////////////////////  
                    auto_no = auto_no.replace(auto_no.charAt(0), firstStateChar);
                    auto_no = auto_no.replace(auto_no.charAt(1), secondStateChar);
                    auto_no = auto_no.replace(auto_no.charAt(4), codeChar1);  
            }
            if(length == 10){
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
        
        
        
        System.out.println("vehicle_no length=="+auto_no.length());
        tubeWellSurveyBean.setVehicle_no(auto_no);
        pstmt.setString(1,auto_no);
        pstmt.setString(2,tubeWellSurveyBean.getOffender_name());//
        
        pstmt.setString(3,tubeWellSurveyBean.getDeposit_amount()+"");
        
        String date = convertToSqlDate(tubeWellSurveyBean.getDate())+"";
        String time = tubeWellSurveyBean.getOffenceTime_h_m();
        String offenceDateTime = date+" "+time;
        
        pstmt.setString(4,offenceDateTime);
//        pstmt.setString(4,tubeWellSurveyBean.getDate());
         System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");

        if(challan_option.equals("MobileApp")){
        pstmt.setInt(5, getCityLocationId(tubeWellSurveyBean.getLocation()));
        }
        
        if(challan_option.equals("ControllRoom")){
        pstmt.setInt(5, getCityLocationId_eng(tubeWellSurveyBean.getLocation_eng()));
        }
        
        
        
        pstmt.setString(6,tubeWellSurveyBean.getOffender_address());//
        pstmt.setString(7,tubeWellSurveyBean.getMobile_no());//
        pstmt.setInt(8, general_image_details_id);
        pstmt.setString(9,tubeWellSurveyBean.getLattitude() );
        pstmt.setString(10, tubeWellSurveyBean.getLongitude());   
        pstmt.setString(11,tubeWellSurveyBean.getOffender_father_name());//
        
        pstmt.setInt(12,getVehicleTypeId(tubeWellSurveyBean.getVehicle_type()));
       pstmt.setInt(13,key_person_id);
        int count = pstmt.executeUpdate();
        
        if(count > 0){
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                traffic_police_id = rs.getInt(1);
            }
            int no_of_offence=tubeWellSurveyBean.getOffence_typeM().length;
            
         String offence_query = "insert into traffic_offence_map(traffic_police_id,offence_type_id,is_second_offence)\n" +
                                   "values(?,?,?); ";
            try{
                String total_offence_code="";
             for(int i=0;i<offence_code_list.size();i++){
                 PreparedStatement pstmt1 = connection.prepareStatement(offence_query);
                 
                 String new_offence_code=offence_code_list.get(i);
                 String s[] = new_offence_code.split("_");
                 String final_offence_code=s[0];
                 String is_second_offence = s[1];
                 
                 pstmt1.setInt(1,traffic_police_id);
//                pstmt1.setInt(2,Integer.parseInt(offence_code_list.get(i)));
                 pstmt1.setInt(2,Integer.parseInt(final_offence_code));
                 pstmt1.setString(3,is_second_offence);
                count1 = pstmt1.executeUpdate();
//                total_offence_code=total_offence_code+offence_code_list.get(i)+" ";
                  total_offence_code=total_offence_code+final_offence_code+" ";
            } 
             
             if(count1 > 0){
                 connection.commit();
                 String mobile_no = tubeWellSurveyBean.getMobile_no();
                 total_offence_code = total_offence_code.trim();
                 sendOTP(tubeWellSurveyBean,offenceDateTime,mobile_no,total_offence_code,traffic_police_id);
                 
                 
             }else{
                 connection.rollback();
             }
             
            }catch(Exception e){
                connection.rollback();
                System.out.println(e);
            }
            
            
            
        }
        
        }catch(Exception e){
            //connection.rollback();
            System.out.println(e);
            //connection.rollback();
        }
            //connection.setAutoCommit(false);
        if(count1 > 0){
               message = "Record saved successfully.";
               msgBgColor = COLOR_OK;
        }else{
            message = "Record not saved .";
            msgBgColor = COLOR_ERROR;
            connection.rollback();
        }
                                          //  connection.commit();
                                                    //connection.rollback();
                     //   connection.rollback();
        return count1;
    }
    
    public int getVehicleTypeId(String vehicle_type){
        int vehicle_type_id=0;
        String query = "select vehicle_type_id\n" +
                       "from vehicle_type vt\n" +
                       "where vehicle_type=?";
        
        try{
        PreparedStatement pstmt2 = connection.prepareStatement(query);
        pstmt2.setString(1,vehicle_type);
        ResultSet rset = pstmt2.executeQuery();
        
        if(rset.next()){
            vehicle_type_id = rset.getInt("vehicle_type_id");
            
        
        }
        
        
        }catch(Exception e)
        {
            System.out.println(e);
        }
        return vehicle_type_id;
    }
    
    public void sendOTP(userEntryByImageBean tubeWellSurveyBean,String offenceDateTime,String mobile_no,String total_offence_code,int traffic_police_id) throws Exception {
        String otp = "";
        
        String offence_code_array[] = total_offence_code.split(" ");
        
        String getActQuery = "select act_origin,act\n" +
                             "from offence_type oft,act_origin ao\n" +
                             "where oft.act_origin_id =ao.act_origin_id\n" +
                             "and offence_code = ?";
        
        String act_string = "";
        for(int i=0;i < offence_code_array.length;i++){
            PreparedStatement pstmt2 = connection.prepareStatement(getActQuery);
            
            pstmt2.setString(1, offence_code_array[i]);
            ResultSet rs = pstmt2.executeQuery();
            while(rs.next()){
                String act_origin = rs.getString("act_origin");
                String act = rs.getString("act");
                
                act_string=act_string+act_origin+"  "+act+",";
            }
            
        }
        
        System.out.println("UserAppWebServices...");

               String ofnc_date = offenceDateTime;
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
                  
                }
               
          sendSmsToAssignedFor(mobile_no, "Challan_id="+traffic_police_id+"\nYour vehicle (vehicle no. "+tubeWellSurveyBean.getVehicle_no()+") is caught for  Traffic rule violetion under\n" +
                                "  Act "+act_string+" at "+ofnc_date+". \n"+
                                "  You have to pay penalty \n" +
                                " amount Rs "+tubeWellSurveyBean.getDeposit_amount()+" at Thana Yatayat,"+
                                " City Center, Gwalior, Madhya Pradesh.");
        System.out.println("Data Retrived : " + mobile_no);
        //stm.closeConnection();
    }
    
    public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
       String result = "";
       try {
           String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";//"http://api.mVaayoo.com/mvaayooapi/MessageCompose?";
           String tempMessage = messageStr1;
           String sender_id = java.net.URLEncoder.encode("TEST SMS", "UTF-8");         // e.g. "TEST+SMS"
           System.out.println("messageStr1 is = " + messageStr1);
           messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
           String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number=" + numberStr1 + "&text=" + messageStr1 + "&route=";
           String url = host_url + queryString;
           result = callURL(url);
           System.out.println("SMS URL: " + url);
       } catch (Exception e) {
           result = e.toString();
           System.out.println("SMSModel sendSMS() Error: " + e);
       }
       return result;
   }
    
    private String callURL(String strURL) {
       String status = "";
       try {
           java.net.URL obj = new java.net.URL(strURL);
           HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
           httpReq.setDoOutput(true);
           httpReq.setInstanceFollowRedirects(true);
           httpReq.setRequestMethod("GET");
           status = httpReq.getResponseMessage();
       } catch (MalformedURLException me) {
           status = me.toString();
       } catch (IOException ioe) {
           status = ioe.toString();
       } catch (Exception e) {
           status = e.toString();
       }
       return status;
   }
    
    public int getCityLocationId(String city_location) {//krutiToUnicode
        String query;
        System.out.println("city_location="+city_location);
        int city_location_id = 0;
        query = "select city_location_id from city_location cl\n" +
                "where cl.location='"+krutiToUnicode.convert_to_unicode(city_location)+"'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                city_location_id = rset.getInt("city_location_id");
            }
        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getCityLocationId-" + ex);
        }
        return city_location_id;
    }
    
    public int getCityLocationId_eng(String city_location) {//krutiToUnicode
        String query;
        System.out.println("city_location="+city_location);
        int city_location_id = 0;
        query = "select city_location_id from city_location cl\n" +
                "where cl.location_eng='"+city_location+"'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                city_location_id = rset.getInt("city_location_id");
            }
        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getCityLocationId-" + ex);
        }
        return city_location_id;
    }

    public int writeImage(userEntryByImageBean surveyBean, List<File> file, int survey_id, int survey_rev_no) {
        // getimage_destination_id();
        boolean isCreated = true;
        String imageName = surveyBean.getImage_name();
        int rowsAffected = 0;
        try {
            File srcfile = null;
            survey_rev_no = survey_rev_no + 1;
            //      String dayOfMonthFolder = createAppropriateDirectories(destination_path);
            destination_path = getRepositoryPath("Survey Image") + "//" + "tube_well";//survey_id;
            File desfile = new File(destination_path);
            if (!desfile.exists()) {
                isCreated = desfile.mkdirs();
            }
            File folder = new File(destination_path);
            boolean isuploaded = false;
            Iterator<File> fileItr = file.iterator();
            int number_of_file = folder.list().length;
            while (fileItr.hasNext()) {
//                Object image = fileItr.next();
//                tempSource = image.toString();
                //       number_of_file++;
                srcfile = fileItr.next();
                String img_name = srcfile.getName();//.replace(".", "%#");
                if(image_name.isEmpty())
                    image_name = img_name;
                else
                    image_name = image_name + "," + img_name;
                //ext = ext.split("%#")[1];
//                String image = srcfile.toString();
//                int index = image.indexOf('.');
//                String fileNameWithOutExt = FilenameUtils.removeExtension(imageName);
//                String ext = image.substring(index, image.length());
//                String image_name = fileNameWithOutExt + "_" + number_of_file + ext;
//                System.out.println("" + image_name);

                File temp_desfile = new File(desfile + "/" + img_name);
                // desfile = new File(desfile + "/" + fileNameWithOutExt + "_" + number_of_file + ext);
                isuploaded = srcfile.renameTo(temp_desfile);

                //isuploaded = srcfile.renameTo(desfile);

                if (isuploaded) {
                    rowsAffected = 1;
                    message = "Image Uploaded Successfully.";
                    msgBgColor = COLOR_OK;

                }

            }
            surveyBean.setImage_name(image_name);
            File deleteFile = new File(getRepositoryPath("General"));
            deleteDirectory(deleteFile);
        } catch (Exception ex) {
            System.out.println("File write error" + ex);
            message = "Cannot upload the image, some error.";
            msgBgColor = COLOR_ERROR;
            PreparedStatement pstmt;
            int id;
            String query, query1;
            query = "UPDATE survey"
                    + "SET general_image_details_id = NULL  "
                    + " where image_name = ? ";
            try {
                pstmt = (PreparedStatement) connection.prepareStatement(query);
                pstmt.setString(1, imageName);
                rowsAffected = pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error: GeneralImageDetailsModel-record cannot updated when image is not uploaded successfully-" + e);
            }
            query1 = "DELETE from general_image_details WHERE general_image_details_id= ? ";
            try {
                pstmt = (PreparedStatement) connection.prepareStatement(query1);
                pstmt.setInt(1, getgeneral_image_details_id(imageName));
                rowsAffected = pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error: ReadMailModel-record cannot deleted when image is not uploaded successfully-" + e);
            }
        }
        return rowsAffected;
    }

    public boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
    }

    public int getgeneral_image_details_id(String image_name) {
        String query;
        int key_person_id = 0;
        query = "select general_image_details_id from general_image_details where image_name='" + image_name + "' and active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("general_image_details_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return key_person_id;
    }

    public int getOffenceId(String image_name) {
        String query;
        int key_person_id = 0;
        query = "select general_image_details_id from general_image_details where image_name='" + image_name + "' and active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("general_image_details_id");
            }
        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return key_person_id;
    }

    public int getimage_destination_id(String image_uploaded_for) {
        String query;
        int image_destination_id = 0;
        query = " SELECT image_destination_id, destination_path from image_destination AS id , image_uploaded_for As i "
                + " WHERE id.image_uploaded_for_id=i.image_uploaded_for_id AND i.image_uploaded_for= ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, image_uploaded_for);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                image_destination_id = rset.getInt("image_destination_id");
            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return image_destination_id;
    }

     public List<String> getLocation(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select location "
                        +" from city_location cl "
                        +" group by location ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no_s = rset.getString("location");
                if (pole_no_s.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_no_s);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Switching Point No exists.");
            }
        } catch (Exception e) {
            System.out.println("getSwitchingPointNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }
     
     public String getCityLocationNameById(String city_location_id) {//krutiToUnicode
        String query;
        System.out.println("city_location="+city_location_id);
        String location = "";
//        query = "select city_location_id from city_location cl\n" +
//                "where cl.location='"+krutiToUnicode.convert_to_unicode(city_location)+"'";
             query="select location\n" +
                   "from city_location cl\n" +
                   "where cl.city_location_id="+city_location_id;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                location = rset.getString("location");
            }
        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getCityLocationId-" + ex);
        }
        return location;
    }
     
     public List<String> getOfficerNameUsingMobileNo(String mobile_no) {//krutiToUnicode
        String query;
        List<String> list = new ArrayList<String>();
        String key_person_name="";
        System.out.println("mobile_no="+mobile_no);
         query = "select key_person_name\n" +
                      "from key_person kp\n" +
                      "where kp.mobile_no1='"+mobile_no+"'";

             
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                key_person_name = rset.getString("key_person_name");
                list.add(key_person_name);
            }
        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getCityLocationId-" + ex);
        }
        return list;
    }
     
     public List<String> getLocationUsingLatLong(String user_lat,String user_long) {
         List<String> list = new ArrayList<String>();
        
                String city_location_id = getCityLocationId(user_lat,user_long);
                
                System.out.println("city_location_id="+city_location_id);
            String location = getCityLocationNameById(city_location_id);
            list.add(location);
        return list;
    }
     public String getCityLocationId(String latitude,String longitude)
        {
           String final_city_id=""; 
        net.sf.json.JSONArray rowData = new net.sf.json.JSONArray();
        String query = null;
        HashMap h = new HashMap();
        Double diff=0.0;
        int count=0;
        query = "select city_location_id,latitude,longitude\n" +
                "from city_location cl;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                
                boolean b=false;
                int city_id = rset.getInt("city_location_id");
                String latitude1 = rset.getString("latitude");
                String longitude1 = rset.getString("longitude");
                if(Double.parseDouble(latitude1) > 0.0 && Double.parseDouble(longitude1) > 0.0)
                {
                    b=true;
                    count++;
//                diff=getDifference(latitude1,longitude1,latitude,longitude);
                  diff=distanceTo(latitude1,longitude1,latitude,longitude);
                
                }
                if(b){
                if(count==1){
                    h.put("city_id",city_id);
                    h.put("diff",diff);
                }if(count > 1){
                    Double d = Double.parseDouble(h.get("diff").toString());
                    if(diff < d){
                        h.clear();
                        h.put("city_id",city_id);
                        h.put("diff",diff);     
                    }   
                }  
                }
           }
            final_city_id = h.get("city_id").toString();
            
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return final_city_id;
    }
 public double distanceTo(String latitude1,String longitude1,String latitude,String longitude) {
        Double db_latitude = Double.parseDouble(latitude1);
        Double db_longitude = Double.parseDouble(longitude1);
        
        Double user_latitude = Double.parseDouble(latitude);
        Double user_longitude = Double.parseDouble(longitude);
     
        double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
        double lat1 = Math.toRadians(db_latitude);
        double lon1 = Math.toRadians(db_longitude);
        double lat2 = Math.toRadians(user_latitude);
        double lon2 = Math.toRadians(user_longitude);

        // great circle distance in radians, using law of cosines formula
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        // each degree on a great circle of Earth is 60 nautical miles
        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }
     
     
     
     

    public String getRepositoryPath(String image_uploaded_for) {
        String query;

         query = " SELECT destination_path FROM image_destination id, image_uploaded_for  iuf "
             + " WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id "
             + " AND iuf.image_uploaded_for = '" + image_uploaded_for + "' ";//traffic_police
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, image_uploaded_for);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                this.destination_path = rset.getString("destination_path");
            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return this.destination_path;
    }

    public String getImageName(int survey_id, int survey_rev_no) {
        String image_name = "";
        String query = "select image_name from survey WHERE survey_id = '" + survey_id + "' and survey_rev_no=" + survey_rev_no;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, fuse_type);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                image_name = rset.getString("image_name");
            }
        } catch (Exception e) {
            System.out.println("Exception in getFuseId() in surveyModel" + e);
        }
        return image_name;
    }

    public List<String> getofficerNameList() {
        List<String> officer_name_list = new ArrayList<String>();
        try {
//            String query = " select ob.book_no ,kp.key_person_name from officer_book as ob,key_person as kp "
//                    + "WHERE kp.key_person_id=ob.key_person_id";
            String query = " select ob.book_no ,kp.key_person_name from officer_book as ob,key_person as kp, status_type st "
                    + " WHERE kp.key_person_id=ob.key_person_id AND ob.book_type = 'C' AND ob.status_type_id = st.status_type_id AND ob.active = 'Y' AND st.status_type = 'Issue'";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                officer_name_list.add(rst.getString("key_person_name"));
            }
        } catch (Exception e) {
        }
        return officer_name_list;
    }

    public List<String> getBookNoList() {
        List<String> list = new ArrayList<String>();
        //q = q.trim();
        int count = 0;
        // String query = "SELECT complaint_no FROM complaint_feedback ";
        String query = " select book_no FROM officer_book ob, status_type st "
                + " WHERE ob.status_type_id = st.status_type_id AND ob.book_type = 'C' AND ob.active = 'Y' "
                + " AND st.status_type = 'Issue' GROUP BY book_no  ORDER BY book_no ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                int book_no = rset.getInt("book_no");

                String book_no_list = Integer.toString(book_no);
                //if(book_no_list.startsWith(q))
                list.add(book_no_list);
                count++;
                //    }
            }
            if (count == 0) {
                list.add("No Such Type Of Book Number  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel --getBookNoList-- " + e);
        }
        return list;
    }
public List<String> vehicleTypeList() {
        List<String> list = new ArrayList<String>();
        String vehicleTypeList = "select v.vehicle_type from offence_type as o, vehicle_type as v "
                + "where  o.vehicle_type_id=v.vehicle_type_id";

        try {
            PreparedStatement ps = connection.prepareStatement(vehicleTypeList);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String vehicle_type = rs.getString("vehicle_type");
                list.add(vehicle_type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel vehicleTypeList(" + e);
        }

        return list;
    }

public List<String> getOffenceTypeList() {

        List<String> offence_type_list = new ArrayList<String>();
        try {



            String query = "select offence_type from offence_type "
                    + "WHERE active='Y' group by offence_type";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                offence_type_list.add(rst.getString("offence_type"));
            }
        } catch (Exception e) {
        }
        return offence_type_list;
    }

    public List<String> getActTypeList() {

        List<String> act_type_list = new ArrayList<String>();
        try {
            String query = "select act from offence_type group buy act ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                act_type_list.add(rst.getString("act"));
            }
        } catch (Exception e) {
        }
        return act_type_list;
    }
public List<String> processingTypeList() {
        List<String> list = new ArrayList<String>();
        String query = "select processing_type from processing_type ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String processing_type = rs.getString("processing_type");
                list.add(processing_type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }

        return list;
    }

    public List<String> relationTypeList() {
        List<String> list = new ArrayList<String>();
        String query = "select relation_type from relation_type ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String relation_type = rs.getString("relation_type");
                list.add(relation_type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel relationTypeList(" + e);
        }

        return list;
    }

    public int getNoOfRows( String searchOfficerName,String searchMobile,String searchPlaceOf ,String searchAmount ,String searchcaseDate ,String searchBookNo, String searchOffenceType, String searchActType, String searchVehicleType, String searchVehicleNo, String searchFromDate, String searchToDate, String searchJarayamNo, String searchOffenceCode, String searchOffenderLicenseNo, String receipt_book_no,String searchChallanNo) {
        int noOfRows = 0;
        searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
        searchOffenceType = krutiToUnicode.convert_to_unicode(searchOffenceType);
        try {
            if (!searchFromDate.equals("")) {
                //String da1[] = searchFromDate.split("-");
                //String da2[] = searchToDate.split("-");
                //searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                //searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }


            String query = "SELECT sum(if(count=1,1,1)) FROM (SELECT count(tp.traffic_police_id) AS count from traffic_police as tp "
                    + "left join officer_book as ob on ob.book_no=tp.book_no AND ob.book_revision_no = tp.book_revision_no "
                    + "left join (trafficpolice_receipt_map trm, receipt_book rb) ON rb.receipt_book_id = trm.receipt_book_id AND trm.traffic_police_id = tp.traffic_police_id "
                    + "left join key_person as kp on ob.key_person_id=kp.key_person_id "
                    + "left join city_location as cl on cl.city_location_id=tp.city_location_id "
                    + "left join zone_new as z on z.zone_new_id=cl.zone_new_id "
                    + "left join city as c on c.city_id=z.city_id "
                    + "left join city as ofc on ofc.city_id=tp.city_id "
                    + "left join processing_type as pt on pt.processing_type_id=tp.processing_type_id "
                    + "LEFT JOIN (traffic_offence_map tom, offence_type as ot) ON tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                    + "LEFT JOIN vehicle_type vt ON vt.vehicle_type_id=ot.vehicle_type_id "
                    + " WHERE IF ('" + searchOfficerName + "'='',key_person_name LIKE '%%' OR key_person_name IS NULL, key_person_name= ?) " //tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id " + " AND vt.vehicle_type_id=ot.vehicle_type_id AND
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%' OR tp.book_no IS NULL, tp.book_no = ?) "
                    + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%' OR ot.offence_type IS NULL, ot.offence_type = ?) "
                    + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%' OR ot.act IS NULL, ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%' OR vt.vehicle_type IS NULL, vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%' OR tp.vehicle_no IS NULL, tp.vehicle_no = ?) "
                    + " AND IF ('" + searchMobile + "'='',kp.mobile_no1 LIKE '%%' OR kp.mobile_no1 IS NULL,kp.mobile_no1 = ?) "
                    + " AND IF ('" + searchPlaceOf + "'='',cl.location LIKE '%%' OR cl.location IS NULL,cl.location = ?) "
                    + " AND IF ('" + searchAmount + "'='',tp.deposited_amount LIKE '%%' OR tp.deposited_amount IS NULL,tp.deposited_amount = ?) "
                     + " AND IF ('" + searchcaseDate + "'='',tp.deposited_amount LIKE '%%' OR tp.deposited_amount IS NULL,tp.deposited_amount = ?) "
                    
                    + " AND IF ('" + searchJarayamNo + "'='',tp.jarayam_no LIKE '%%' OR tp.jarayam_no IS NULL, tp.jarayam_no = ?) "
                    + " AND IF ('" + searchOffenceCode + "'='',ot.offence_code LIKE '%%' OR ot.offence_code IS NULL, ot.offence_code = ?) "
                    + " AND IF ('" + searchOffenderLicenseNo + "'='', tp.offender_license_no LIKE '%%' OR tp.offender_license_no IS NULL, tp.offender_license_no = ?) "
                    + " AND IF ('" + receipt_book_no + "'='',rb.receipt_book_no LIKE '%%' OR rb.receipt_book_no IS NULL,rb.receipt_book_no = '" + receipt_book_no + "') "
                    + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,( (STR_TO_DATE(tp.offence_date, '%Y-%m-%d')) Between STR_TO_DATE('" + searchFromDate + "', '%d-%m-%Y') AND STR_TO_DATE('" + searchToDate + "', '%d-%m-%Y'))) "
                    //+ " AND IF( '" + searchFromDate + "' = '' , tp.offence_date LIKE '%%' OR tp.offence_date IS NULL , STR_TO_DATE(tp.offence_date, '%Y-%m-%d') = ? ) "//'"+  +"' ) "
                    //+ " AND IF(  '" + searchToDate + "' = '' , tp.offence_date LIKE '%%' OR tp.offence_date IS NULL , STR_TO_DATE(tp.offence_date, '%Y-%m-%d') <= ? )"//'"+ convertToSqlDate(searchToDate) +"' ) "
                    //+ " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))";
                   + " AND IF ('" + searchChallanNo + "'='',tp.traffic_police_id LIKE '%%' OR tp.traffic_police_id IS NULL, tp.traffic_police_id = ?) "
                    + " GROUP BY tp.traffic_police_id ORDER BY tp.traffic_police_id) AS noOfRows";//group by tp.traffic_police_id
           /* String query = "select count(*) "
                    + "from traffic_police as tp "
                    + "left join officer_book as ob on ob.book_no=tp.book_no "
                    + "left join key_person as kp on ob.key_person_id=kp.key_person_id "
                    + "left join city_location as cl on cl.city_location_id=tp.city_location_id "
                    + "left join zone_new as z on z.zone_new_id=cl.zone_new_id "
                    + "left join city as c on c.city_id=z.city_id "
                    + "left join processing_type as pt on pt.processing_type_id=tp.processing_type_id"
                    + ", vehicle_type vt, offence_type as ot, traffic_offence_map tom"
                    + " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                    + " AND vt.vehicle_type_id=ot.vehicle_type_id AND IF ('" + searchOfficerName + "'='',key_person_name LIKE '%%',key_person_name= ?) "
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%',tp.book_no = ?) "
                    // + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%',ot.offence_type = ?) "
                    // + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%',ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%',vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%',tp.vehicle_no = ?) "
                    // + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"
                    + " group by tp.traffic_police_id ORDER BY tp.traffic_police_id";
            //  + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"
            // + " group by tp.traffic_police_id ORDER BY tp.traffic_police_id";*/
            PreparedStatement pstmt = connection.prepareStatement(query);
            //System.out.println(query);
            pstmt.setString(1, searchOfficerName);
            pstmt.setString(2, searchBookNo);
            pstmt.setString(3, searchOffenceType);
            pstmt.setString(4, searchActType);
            pstmt.setString(5, searchVehicleType);
            pstmt.setString(6, searchVehicleNo);
            pstmt.setString(7, searchMobile);
             pstmt.setString(8, searchPlaceOf);
              pstmt.setString(9, searchAmount);
               pstmt.setString(10, searchcaseDate);
            pstmt.setString(11, searchJarayamNo);
            pstmt.setString(12, searchOffenceCode);
            pstmt.setString(13, searchOffenderLicenseNo);
            
            pstmt.setString(14, searchChallanNo);
//            if(searchFromDate != null && !searchFromDate.isEmpty())
//                pstmt.setDate(10, convertToSqlDate(searchFromDate));
//            else
//                pstmt.setString(10, "");
//            if(searchToDate != null && !searchToDate.isEmpty())
//                pstmt.setDate(11, convertToSqlDate(searchToDate));
//            else
//                pstmt.setString(11, "");

            ResultSet rset = pstmt.executeQuery();
            rset.next();
            if(rset.getString(1) == null)
                noOfRows = 0;
            else
                noOfRows = Integer.parseInt(rset.getString(1));

        } catch (Exception e) {
            System.out.println("Error:keypersonModel-getNoOfRows-- " + e);
        }
        return noOfRows;
    }

    public List<userEntryByImageBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchOfficerName,String searchMobile,String searchPlaceOf ,String searchAmount ,String searchcaseDate ,String searchBookNo, String searchOffenceType, String searchActType, String searchVehicleType, String searchVehicleNo, String searchFromDate, String searchToDate, String searchJarayamNo, String searchOffenceCode, String searchOffenderLicenseNo, String receipt_book_no,String searchChallanNo) {
         List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
        searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
        searchOffenceType = krutiToUnicode.convert_to_unicode(searchOffenceType);
        String query;
        try {
            if (!searchFromDate.equals("")) {
                //String da1[] = searchFromDate.split("-");
                //String da2[] = searchToDate.split("-");
                //searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                //searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }
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
                    + " WHERE IF ('" + searchOfficerName + "'='',kp1.key_person_name LIKE '%%' OR kp1.key_person_name IS NULL, kp1.key_person_name= ?) "// tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id " + " AND vt.vehicle_type_id=ot.vehicle_type_id AND
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%' OR tp.book_no IS NULL,tp.book_no = ?) "
                    + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%' OR ot.offence_type IS NULL,ot.offence_type = ?) "
                    + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%' OR ot.act IS NULL,ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%' OR vt.vehicle_type IS NULL,vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%' OR tp.vehicle_no IS NULL,tp.vehicle_no = ?) "
                    + " AND IF ('" + searchMobile + "'='',kp1.mobile_no1 LIKE '%%' OR kp1.mobile_no1 IS NULL,kp1.mobile_no1 = ?) "
                     + " AND IF ('" + searchPlaceOf + "'='',cl.location LIKE '%%' OR cl.location IS NULL,cl.location = ?) "
                     + " AND IF ('" + searchAmount + "'='',tp.deposited_amount LIKE '%%' OR tp.deposited_amount IS NULL,tp.deposited_amount = ?) "
                         + " AND IF ('" + searchcaseDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,tp.offence_date = ?) "
                   
                    + " AND IF ('" + searchJarayamNo + "'='',tp.jarayam_no LIKE '%%' OR tp.jarayam_no IS NULL,tp.jarayam_no = ?) "
                    + " AND IF ('" + searchOffenceCode + "'='',ot.offence_code LIKE '%%' OR ot.offence_code IS NULL,ot.offence_code = ?) "
                    + " AND IF ('" + searchOffenderLicenseNo + "'='', tp.offender_license_no LIKE '%%' OR tp.offender_license_no IS NULL, tp.offender_license_no = ?) "
                    + " AND IF ('" + receipt_book_no + "'='',rb.receipt_book_no LIKE '%%' OR rb.receipt_book_no IS NULL,rb.receipt_book_no = '" + receipt_book_no + "') "
                    + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,( (STR_TO_DATE(tp.offence_date, '%Y-%m-%d')) Between STR_TO_DATE('" + searchFromDate + "', '%d-%m-%Y') AND STR_TO_DATE('" + searchToDate + "', '%d-%m-%Y'))) "
                    // + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"//%m/%d/%Y
                    + " AND IF ('" + searchChallanNo + "'='',tp.traffic_police_id LIKE '%%' OR tp.traffic_police_id IS NULL,tp.traffic_police_id = ?) "
                    + " GROUP BY tp.traffic_police_id ORDER BY tp.traffic_police_id DESC"
                    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

            PreparedStatement pstmt = connection.prepareStatement(query);
               pstmt.setString(1, searchOfficerName);
            pstmt.setString(2, searchBookNo);
            pstmt.setString(3, searchOffenceType);
            pstmt.setString(4, searchActType);
            pstmt.setString(5, searchVehicleType);
            pstmt.setString(6, searchVehicleNo);
            pstmt.setString(7, searchMobile);
            pstmt.setString(8, searchPlaceOf);
             pstmt.setString(9, searchAmount);
             pstmt.setString(10, searchcaseDate);
            pstmt.setString(11, searchJarayamNo);
            pstmt.setString(12, searchOffenceCode);
            pstmt.setString(13, searchOffenderLicenseNo);
            
            pstmt.setString(14, searchChallanNo);

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
                List<userEntryByImageBean> offenceTypeList = showOffenceData(trafficpolice , rset.getInt("traffic_police_id"),searchVehicleType);
                trafficpolice.setOffenceList(offenceTypeList);//
                list.add(trafficpolice);
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
    }

    public List<userEntryByImageBean> showOffenceData(userEntryByImageBean trafficpolice1, int traffic_police_id,String searchVehicleType ) {
        List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
        String query;
        try {
            query = "Select offence_type ,act, if(tom.is_second_offence='YES',o.second_offence_penalty,o.penalty_amount) as penalty_amount, "
                    + "vehicle_type, act_origin, offence_code "
                    + "from traffic_offence_map as tom,traffic_police as tp, offence_type as o,act_origin as a ,"
                    + "vehicle_type as v where o.act_origin_id=a.act_origin_id and o.vehicle_type_id=v.vehicle_type_id "
                   
                    + "and tom.traffic_police_id=tp.traffic_police_id and tom.offence_type_id=o.offence_type_id and tp.traffic_police_id='" + traffic_police_id + "'";
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

    
    
    
     public List<userEntryByImageBean> showOffenceData1(userEntryByImageBean trafficpolice1, int traffic_police_id,String searchActType,String searchAmount,String searchOffenceCode) {
        List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
        String query;
        
        try {
            query = "Select offence_type ,act, if(tom.is_second_offence='YES',o.second_offence_penalty,o.penalty_amount) as penalty_amount, "
                    + "vehicle_type, act_origin, offence_code "
                    + "from traffic_offence_map as tom,traffic_police as tp, offence_type as o,act_origin as a ,"
                    + "vehicle_type as v where o.act_origin_id=a.act_origin_id and o.vehicle_type_id=v.vehicle_type_id "
                    + "and tom.traffic_police_id=tp.traffic_police_id and tom.offence_type_id=o.offence_type_id and tp.traffic_police_id='" + traffic_police_id + "'";
           
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                userEntryByImageBean trafficpolice = new userEntryByImageBean();
                 if(searchActType.equals(searchActType)){
                         
                 trafficpolice.setOffence1(rset.getString("penalty_amount"));
                 trafficpolice.setOffence2(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offence_type")));
                 trafficpolice.setOffence3(rset.getString("act_origin"));
                  trafficpolice.setOffence4(rset.getString("offence_code"));
                  
                   trafficpolice.setOffence_heading1("Penalty");
                    trafficpolice.setOffence_heading2("Offence Type");
                     trafficpolice.setOffence_heading3("Act Origin");
                  trafficpolice.setOffence_heading4("offence Code");
                 }
                 else if(searchAmount.equals(searchAmount))
                 {
                  trafficpolice.setOffence1(rset.getString("act"));
                    trafficpolice.setOffence2(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offence_type")));
                  trafficpolice.setOffence3(rset.getString("act_origin"));
                  trafficpolice.setOffence4(rset.getString("offence_code"));
                  trafficpolice.setOffence_heading1("Act");
                   trafficpolice.setOffence_heading2("Offence Type");
                    trafficpolice.setOffence_heading3("Act Origin");
                     trafficpolice.setOffence_heading4("offence Code");
                 }
                 
                 else if(searchOffenceCode.equals(searchOffenceCode))
                 {
                        trafficpolice.setOffence1(rset.getString("act"));   
                         trafficpolice.setOffence2(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offence_type")));
                             trafficpolice.setOffence4(rset.getString("act_origin"));
                                 trafficpolice.setOffence3(rset.getString("penalty_amount"));
                                 trafficpolice.setOffence_heading1("Act");
                                 trafficpolice.setOffence_heading2("Offence Type");
                                    trafficpolice.setOffence_heading3("Penalty");
                                     trafficpolice.setOffence_heading4("Act Origin");
                             
                 }
                else
                 {
                trafficpolice.setOffence6(rset.getString("vehicle_type"));
               
                trafficpolice.setOffence1(rset.getString("act"));
                trafficpolice.setOffence2(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offence_type")));
                trafficpolice.setOffence3(rset.getString("penalty_amount"));
                trafficpolice.setOffence4(rset.getString("act_origin"));
                trafficpolice.setOffence5(rset.getString("offence_code"));
                trafficpolice.setOffence_heading1("Act");
                 trafficpolice.setOffence_heading2("Offence Type");
                 trafficpolice.setOffence_heading3("Penalty");
                 trafficpolice.setOffence_heading4("Act Origin");
                  trafficpolice.setOffence_heading5("offence Code");
                   trafficpolice.setOffence_heading6("Vehicle type");
                   
                 } 
                   
                   
                   
                   
                list.add(trafficpolice);
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel--showOffenceData " + e);
        }
        return list;
    }
    
    
    
    
    public byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (Exception e) {
            System.out.println("TafficPoliceModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<userEntryByImageBean> showMISData(String traffic_police_id1){
        List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
        Map<String, String> map = new HashMap<String, String>();
        String query = "select traffic_police_id,vehicle_no,deposited_amount,offence_date, "
                      +" location as city_location,general_image_details_id "
                      +" from traffic_police tp,city_location cl "
                      +" where tp.city_location_id = cl.city_location_id "
                      +" and tp.traffic_police_id="+traffic_police_id1;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                userEntryByImageBean tpBean = new userEntryByImageBean();
                //String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("key_person_name"));

                String traffic_police_id = rs.getString("traffic_police_id");
                String vehicle_no = rs.getString("vehicle_no");
                String deposited_amount = rs.getString("deposited_amount");
                String offence_date = rs.getString("offence_date");
                String city_location = rs.getString("city_location");
                String general_image_details_id = rs.getString("general_image_details_id");
                String challan_no = "12345";
                String vehicle_class = "Dummy Class";

                String office_mob_no = "1234567890";
                String portal_name = "M.P. Online";
                String website_link="http://www.mponline.gov.in/portal/Services/eChallan/Homeaspx";
                String vehicle_owner_name = "Raj Kumar";
                String father_name = "Mohan Singh";
                String offender_address = "Gwalior";
                String offender_mob_no = "0123456789";

                tpBean.setTraffic_police_id(Integer.parseInt(traffic_police_id));
                tpBean.setVehicle_no(vehicle_no);
                tpBean.setDeposited_amount(deposited_amount);
                tpBean.setOffence_date(offence_date);
                tpBean.setCity(city_location);
                tpBean.setChallan_no(challan_no);
                tpBean.setVehicle_class(vehicle_class);


                tpBean.setOffice_mob_no(office_mob_no);
                tpBean.setPortal_name(portal_name);
                tpBean.setWebsite_link(website_link);
                tpBean.setVehicle_owner_name(vehicle_owner_name);
                tpBean.setFather_name(father_name);
                tpBean.setOffender_address(offender_address);
                tpBean.setOffender_mob_no(offender_mob_no);


                list.add(tpBean);
            }
       //////////////////////////////////////////////////////////////////////////////////////////////////////
//                userEntryByImageBean tpBean1 = new userEntryByImageBean();
                // List<userEntryByImageBean> list1 = new ArrayList<userEntryByImageBean>();
                String offencequery = "select offence_type,act_origin,penalty_amount,act "
                                      +" from traffic_offence_map tom,offence_type ot,act_origin aorg "
                                      +" where tom.offence_type_id = ot.offence_type_id "
                                      +" and ot.act_origin_id = aorg.act_origin_id "
                                      +" and tom.traffic_police_id="+traffic_police_id1;

                 ResultSet rs1 = connection.prepareStatement(offencequery).executeQuery();

                 while(rs1.next()){
                     userEntryByImageBean tpBean1 = new userEntryByImageBean();
                     String offence_type = rs1.getString("offence_type");
                     String act_origin = rs1.getString("act_origin");
                     String penalty_amount = rs1.getString("penalty_amount");
                     String act = rs1.getString("act");
                     
                     String final_act_origin = act+" ,"+act_origin;

                     tpBean1.setOffence_type(offence_type);
                     tpBean1.setAct_origin(final_act_origin);
                     tpBean1.setPenalty_amount(penalty_amount);

                     list.add(tpBean1);
                 }
                 //list.add(list1);


            //}
        }catch(Exception ex){
            System.out.println("ERROR : in showMISData() in TrafficPoliceModel : " + query);
        }
        return list;
    }

    public List<userEntryByImageBean> showMISData1(String traffic_police_id1,  userEntryByImageBean userEntryByImageBean1){
        List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
        List<userEntryByImageBean> list1 = new ArrayList<userEntryByImageBean>();
        userEntryByImageBean tpBean = new userEntryByImageBean();
        String imgPath = userEntryByImageBean1.getImgPath();
        String imgPath1 = userEntryByImageBean1.getImgPath1();

        Map<String, String> map = new HashMap<String, String>();
        String query = "select traffic_police_id,vehicle_no,deposited_amount,offence_date, "
                      +" location as city_location,general_image_details_id,offender_name,offender_address, "
                      +" offender_mobile_no,father_name "
                      +" from traffic_police tp,city_location cl "
                      +" where tp.city_location_id = cl.city_location_id "
                      +" and tp.traffic_police_id="+traffic_police_id1;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
               // userEntryByImageBean tpBean = new userEntryByImageBean();
                //String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("key_person_name"));

                String traffic_police_id = rs.getString("traffic_police_id");
                String vehicle_no = rs.getString("vehicle_no");
                String deposited_amount = rs.getString("deposited_amount");
                String offence_date = rs.getString("offence_date");
                
                
                String ofnc_date = offence_date;
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
                  
                }
                
                
                
                String city_location = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("city_location"));
                String general_image_details_id = rs.getString("general_image_details_id");
                String challan_no = "12345";
                String vehicle_class = "Dummy Class";

                String office_mob_no = "1234567890";
                String portal_name = "M.P. Online";
                String website_link="http://www.mponline.gov.in/portal/Services/eChallan/Homeaspx";
                String vehicle_owner_name = rs.getString("offender_name");
                String father_name = rs.getString("father_name");
                String offender_address = rs.getString("offender_address");
                String offender_mob_no = rs.getString("offender_mobile_no");

                tpBean.setTraffic_police_id(Integer.parseInt(traffic_police_id));
                tpBean.setVehicle_no(vehicle_no);
                tpBean.setDeposited_amount(deposited_amount);
                tpBean.setOffence_date(ofnc_date);
                tpBean.setCity(city_location);
                tpBean.setChallan_no(challan_no);
                tpBean.setVehicle_class(vehicle_class);


                tpBean.setOffice_mob_no(office_mob_no);
                tpBean.setPortal_name(portal_name);
                tpBean.setWebsite_link(website_link);
                tpBean.setVehicle_owner_name(vehicle_owner_name);
                tpBean.setFather_name(father_name);
                tpBean.setOffender_address(offender_address);
                tpBean.setOffender_mob_no(offender_mob_no);
                tpBean.setImgPath(imgPath);
                tpBean.setImgPath1(imgPath1);
                //list.add(tpBean);
                
            }                      
                 //list.add(list1);
            showMISData2(tpBean,traffic_police_id1);
            list.add(tpBean);
            //}
        }catch(Exception ex){
            System.out.println("ERROR : in showMISData() in TrafficPoliceModel : " + query);
        }
        return list;
    }
    
    
  public List<userEntryByImageBean> showAllData(String searchOfficerName,String searchMobile,String searchPlaceOf,String searchAmount ,String searchcaseDate,String searchBookNo, String searchOffenceType, String searchActType, String searchVehicleType, String searchVehicleNo, String searchFromDate, String searchToDate, String searchJarayamNo, String searchOffenceCode, String searchOffenderLicenseNo, String receipt_book_no,String searchChallanNo) {
        List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
 
        searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
        searchOffenceType = krutiToUnicode.convert_to_unicode(searchOffenceType);
        String query;
        try {
            if (!searchFromDate.equals("")) {
                //String da1[] = searchFromDate.split("-");
                //String da2[] = searchToDate.split("-");
                //searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                //searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }
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
                    + " WHERE IF ('" + searchOfficerName + "'='',kp1.key_person_name LIKE '%%' OR kp1.key_person_name IS NULL, kp1.key_person_name= ?) "// tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id " + " AND vt.vehicle_type_id=ot.vehicle_type_id AND
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%' OR tp.book_no IS NULL,tp.book_no = ?) "
                    + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%' OR ot.offence_type IS NULL,ot.offence_type = ?) "
                    + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%' OR ot.act IS NULL,ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%' OR vt.vehicle_type IS NULL,vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%' OR tp.vehicle_no IS NULL,tp.vehicle_no = ?) "
                    + " AND IF ('" + searchJarayamNo + "'='',tp.jarayam_no LIKE '%%' OR tp.jarayam_no IS NULL,tp.jarayam_no = ?) "
                    + " AND IF ('" + searchOffenceCode + "'='',ot.offence_code LIKE '%%' OR ot.offence_code IS NULL,ot.offence_code = ?) "
                    + " AND IF ('" + searchOffenderLicenseNo + "'='', tp.offender_license_no LIKE '%%' OR tp.offender_license_no IS NULL, tp.offender_license_no = ?) "
                    + " AND IF ('" + receipt_book_no + "'='',rb.receipt_book_no LIKE '%%' OR rb.receipt_book_no IS NULL,rb.receipt_book_no = '" + receipt_book_no + "') "
                     + " AND IF ('" + searchMobile + "'='',kp1.mobile_no1 LIKE '%%' OR kp1.mobile_no1 IS NULL,kp1.mobile_no1 = ?) "
                     + " AND IF ('" + searchPlaceOf + "'='',cl.location LIKE '%%' OR cl.location IS NULL,cl.location = ?) "
                      + " AND IF ('" + searchAmount + "'='',tp.deposited_amount LIKE '%%' OR tp.deposited_amount IS NULL,tp.deposited_amount = ?) "
                        + " AND IF ('" + searchcaseDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,tp.offence_date = ?) "
               
               + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,( (STR_TO_DATE(tp.offence_date, '%Y-%m-%d')) Between STR_TO_DATE('" + searchFromDate + "', '%d-%m-%Y') AND STR_TO_DATE('" + searchToDate + "', '%d-%m-%Y'))) "
                    // + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"//%m/%d/%Y
                    + " AND IF ('" + searchChallanNo + "'='',tp.traffic_police_id LIKE '%%' OR tp.traffic_police_id IS NULL,tp.traffic_police_id = ?) "
                    + " GROUP BY tp.traffic_police_id ORDER BY tp.traffic_police_id DESC";
                 

            PreparedStatement pstmt = connection.prepareStatement(query);
              pstmt.setString(1, searchOfficerName);
            pstmt.setString(2, searchBookNo);
            pstmt.setString(3, searchOffenceType);
            pstmt.setString(4, searchActType);
            pstmt.setString(5, searchVehicleType);
            pstmt.setString(6, searchVehicleNo);
            pstmt.setString(7, searchMobile);
             pstmt.setString(8, searchPlaceOf);
             pstmt.setString(9, searchAmount);
              pstmt.setString(10, searchcaseDate);
            pstmt.setString(11, searchJarayamNo);
            pstmt.setString(12, searchOffenceCode);
            pstmt.setString(13, searchOffenderLicenseNo);
            
            pstmt.setString(14, searchChallanNo);
        
           
           
            // pstmt.setString(1,searchVehicleType);
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
                
                trafficpolice.setOffence_place(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
//                 trafficpolice.setOffence_place(rset.getString("location"));                
// trafficpolice.setAct(rset.getString("act"));
                trafficpolice.setReceipt_no(rset.getString("reciept_no"));
                trafficpolice.setDeposited_amount(rset.getString("deposited_amount"));
//                 trafficpolice.setOffence_type(rset.getString("offence_type"));
                 
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
                
               
                List<userEntryByImageBean> offenceTypeList = showOffenceData(trafficpolice , rset.getInt("traffic_police_id"),searchVehicleType);
                trafficpolice.setOffenceList(offenceTypeList);//
                list.add(trafficpolice);
              
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
    }
    
    
     public List<userEntryByImageBean> showAllData2(String searchOfficerName,String searchMobile,String searchPlaceOf,String searchAmount ,String searchcaseDate,String searchBookNo, String searchOffenceType, String searchActType, String searchVehicleType, String searchVehicleNo, String searchFromDate, String searchToDate, String searchJarayamNo, String searchOffenceCode, String searchOffenderLicenseNo, String receipt_book_no,String searchChallanNo) {
        List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
  
         searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
        searchOffenceType = krutiToUnicode.convert_to_unicode(searchOffenceType);
        String query;
       
        try {
            if (!searchFromDate.equals("")) {
                //String da1[] = searchFromDate.split("-");
                //String da2[] = searchToDate.split("-");
                //searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                //searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }
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
                    + " WHERE IF ('" + searchOfficerName + "'='',kp1.key_person_name LIKE '%%' OR kp1.key_person_name IS NULL, kp1.key_person_name= ?) "// tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id " + " AND vt.vehicle_type_id=ot.vehicle_type_id AND
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%' OR tp.book_no IS NULL,tp.book_no = ?) "
                    + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%' OR ot.offence_type IS NULL,ot.offence_type = ?) "
                    + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%' OR ot.act IS NULL,ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%' OR vt.vehicle_type IS NULL,vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%' OR tp.vehicle_no IS NULL,tp.vehicle_no = ?) "
                    + " AND IF ('" + searchJarayamNo + "'='',tp.jarayam_no LIKE '%%' OR tp.jarayam_no IS NULL,tp.jarayam_no = ?) "
                    + " AND IF ('" + searchOffenceCode + "'='',ot.offence_code LIKE '%%' OR ot.offence_code IS NULL,ot.offence_code = ?) "
                    + " AND IF ('" + searchOffenderLicenseNo + "'='', tp.offender_license_no LIKE '%%' OR tp.offender_license_no IS NULL, tp.offender_license_no = ?) "
                    + " AND IF ('" + receipt_book_no + "'='',rb.receipt_book_no LIKE '%%' OR rb.receipt_book_no IS NULL,rb.receipt_book_no = '" + receipt_book_no + "') "
                     + " AND IF ('" + searchMobile + "'='',kp1.mobile_no1 LIKE '%%' OR kp1.mobile_no1 IS NULL,kp1.mobile_no1 = ?) "
                     + " AND IF ('" + searchPlaceOf + "'='',cl.location LIKE '%%' OR cl.location IS NULL,cl.location = ?) "
                      + " AND IF ('" + searchAmount + "'='',tp.deposited_amount LIKE '%%' OR tp.deposited_amount IS NULL,tp.deposited_amount = ?) "
                        + " AND IF ('" + searchcaseDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,tp.offence_date = ?) "
               
               + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,( (STR_TO_DATE(tp.offence_date, '%Y-%m-%d')) Between STR_TO_DATE('" + searchFromDate + "', '%d-%m-%Y') AND STR_TO_DATE('" + searchToDate + "', '%d-%m-%Y'))) "
                    // + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"//%m/%d/%Y
                    + " AND IF ('" + searchChallanNo + "'='',tp.traffic_police_id LIKE '%%' OR tp.traffic_police_id IS NULL,tp.traffic_police_id = ?) "
                    + " GROUP BY tp.traffic_police_id ORDER BY tp.traffic_police_id DESC";
                 
       
       

            PreparedStatement pstmt = connection.prepareStatement(query);
              pstmt.setString(1, searchOfficerName);
            pstmt.setString(2, searchBookNo);
            pstmt.setString(3, searchOffenceType);
            pstmt.setString(4, searchActType);
            pstmt.setString(5, searchVehicleType);
            pstmt.setString(6, searchVehicleNo);
            pstmt.setString(7, searchMobile);
             pstmt.setString(8, searchPlaceOf);
             pstmt.setString(9, searchAmount);
              pstmt.setString(10, searchcaseDate);
            pstmt.setString(11, searchJarayamNo);
            pstmt.setString(12, searchOffenceCode);
            pstmt.setString(13, searchOffenderLicenseNo);
            
            pstmt.setString(14, searchChallanNo);
        
           
          
            // pstmt.setString(1,searchVehicleType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                                                
                userEntryByImageBean trafficpolice = new userEntryByImageBean();  
                // testing
                if(searchOfficerName.equalsIgnoreCase("")){
                trafficpolice.setReport_name("CHALLAN REPORT");   
                trafficpolice.setCommon_name("");   
               trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
//                trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
                trafficpolice.setCommon_name2(rset.getString("offence_date"));
                trafficpolice.setCommon_name3(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
                trafficpolice.setCommon_name4(rset.getString("deposited_amount"));
                  trafficpolice.setCommon_name5(rset.getString("vehicle_no"));
                
                trafficpolice.setHeading_name1("Officer Name");
                trafficpolice.setHeading_name2("Offence Date");
                trafficpolice.setHeading_name3("Offence place");
                trafficpolice.setHeading_name4("Deposited Amount");
                  trafficpolice.setHeading_name5("Vehicle No");
               
                }
                else{
                     trafficpolice.setReport_name("");  
                       trafficpolice.setCode1("");
                    trafficpolice.setCommon_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
                    trafficpolice.setCommon_name2(rset.getString("offence_date"));
                     trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
                     trafficpolice.setCommon_name3(rset.getString("deposited_amount"));
                     trafficpolice.setCommon_name4(rset.getString("vehicle_no"));
                     trafficpolice.setCommon_name5("");
                      
                trafficpolice.setHeading_name1("Offence place");
                trafficpolice.setHeading_name2("Offence Date");
                  trafficpolice.setHeading_name3("Deposited Amount");
                  trafficpolice.setHeading_name4("Vehicle No");
                  trafficpolice.setHeading_name5("");
                  
                }                                              
            
              
               
//                  if(searchPlaceOf.equalsIgnoreCase("")){
//                trafficpolice.setReport_name("CHALLAN REPORT");   
//                trafficpolice.setCommon_name("");   
//               trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
////                trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
//                trafficpolice.setCommon_name2(rset.getString("offence_date"));
//                trafficpolice.setCommon_name3(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
//                trafficpolice.setCommon_name4(rset.getString("deposited_amount"));
//                  trafficpolice.setCommon_name5(rset.getString("vehicle_no"));
//                
//                trafficpolice.setHeading_name1("Officer Name");
//                trafficpolice.setHeading_name2("Offence Date");
//                trafficpolice.setHeading_name3("Offence place");
//                trafficpolice.setHeading_name4("Deposited Amount");
//                  trafficpolice.setHeading_name5("Vehicle No");
//               
//               }
//                 else
//                  {
//                   trafficpolice.setReport_name("");
//                   trafficpolice.setCommon_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
//                   trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
//                    trafficpolice.setCommon_name2(rset.getString("offence_date"));
//                  trafficpolice.setCommon_name3(rset.getString("deposited_amount"));
//                     trafficpolice.setCommon_name4(rset.getString("vehicle_no"));
//                     trafficpolice.setCommon_name5("");
//                     
//                    trafficpolice.setHeading_name1("Offence place");
//                trafficpolice.setHeading_name2("Offence Date");
//                  trafficpolice.setHeading_name3("Deposited Amount");
//                  trafficpolice.setHeading_name4("Vehicle No");
//                  trafficpolice.setHeading_name5("");
//                  }
                  
//                  if(searchActType.equalsIgnoreCase("")){
//                   trafficpolice.setAct1("");
//                  trafficpolice.setReport_name("CHALLAN REPORT");   
//                trafficpolice.setCommon_name("");   
//               trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
////                trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
//                trafficpolice.setCommon_name2(rset.getString("offence_date"));
//                trafficpolice.setCommon_name3(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
//                trafficpolice.setCommon_name4(rset.getString("deposited_amount"));
//                  trafficpolice.setCommon_name5(rset.getString("vehicle_no"));
//                
//                trafficpolice.setHeading_name1("Officer Name");
//                trafficpolice.setHeading_name2("Offence Date");
//                trafficpolice.setHeading_name3("Offence place");
//                trafficpolice.setHeading_name4("Deposited Amount");
//                  trafficpolice.setHeading_name5("Vehicle No");
//               
//                  } 
//                else
//                {
//                    
//                     trafficpolice.setReport_name("");
//                    trafficpolice.setAct1(searchActType);
//                      trafficpolice.setCode1("");
//                  trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
//                   trafficpolice.setCommon_name2(rset.getString("offence_date"));
//                trafficpolice.setCommon_name3(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
//                trafficpolice.setCommon_name4(rset.getString("deposited_amount"));
//                  trafficpolice.setCommon_name5(rset.getString("vehicle_no"));
//                  
//                  trafficpolice.setHeading_name1("Officer Name");
//                trafficpolice.setHeading_name2("Offence Date");
//                trafficpolice.setHeading_name3("Offence place");
//                trafficpolice.setHeading_name4("Deposited Amount");
//                  trafficpolice.setHeading_name5("Vehicle No");
//                   }
                  
//                     if(searchAmount.equalsIgnoreCase("")){
//                     
//                     trafficpolice.setAct1("");
//                       trafficpolice.setAmount1("");
//                  trafficpolice.setReport_name("CHALLAN REPORT");   
//                trafficpolice.setCommon_name("");   
//               trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
////                trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
//                trafficpolice.setCommon_name2(rset.getString("offence_date"));
//                trafficpolice.setCommon_name3(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
//                trafficpolice.setCommon_name4(rset.getString("deposited_amount"));
//                  trafficpolice.setCommon_name5(rset.getString("vehicle_no"));
//                
//                trafficpolice.setHeading_name1("Officer Name");
//                trafficpolice.setHeading_name2("Offence Date");
//                trafficpolice.setHeading_name3("Offence place");
//                trafficpolice.setHeading_name4("Deposited Amount");
//                  trafficpolice.setHeading_name5("Vehicle No");
//                     
//                     }
//                  
//                 else
//                     {
//                     
//                      trafficpolice.setReport_name("");
//                        trafficpolice.setAmount1(searchAmount);
//                           trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
//                            trafficpolice.setCommon_name2(rset.getString("offence_date"));
//                trafficpolice.setCommon_name3(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
//                trafficpolice.setCommon_name4(rset.getString("deposited_amount"));
//                  trafficpolice.setCommon_name5(rset.getString("vehicle_no"));
//                  
//                  trafficpolice.setHeading_name1("Officer Name");
//                trafficpolice.setHeading_name2("Offence Date");
//                trafficpolice.setHeading_name3("Offence place");
//                trafficpolice.setHeading_name4("Deposited Amount");
//                  trafficpolice.setHeading_name5("Vehicle No");
//                     }
              
//                       if(searchOffenceCode.equalsIgnoreCase("")){
//                        trafficpolice.setAct1("");
//                       trafficpolice.setAmount1("");
//                  trafficpolice.setReport_name("CHALLAN REPORT");   
//                trafficpolice.setCommon_name("");   
//               trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
////                trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
//                trafficpolice.setCommon_name2(rset.getString("offence_date"));
//                trafficpolice.setCommon_name3(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
//                trafficpolice.setCommon_name4(rset.getString("deposited_amount"));
//                  trafficpolice.setCommon_name5(rset.getString("vehicle_no"));
//                
//                trafficpolice.setHeading_name1("Officer Name");
//                trafficpolice.setHeading_name2("Offence Date");
//                trafficpolice.setHeading_name3("Offence place");
//                trafficpolice.setHeading_name4("Deposited Amount");
//                  trafficpolice.setHeading_name5("Vehicle No");
//                     
//                       }
//                       else{
//                       
//                         trafficpolice.setReport_name("");
//                       trafficpolice.setCode1(searchOffenceCode);
//                        trafficpolice.setCommon_name1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
//                            trafficpolice.setCommon_name2(rset.getString("offence_date"));
//                trafficpolice.setCommon_name3(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
//                trafficpolice.setCommon_name4(rset.getString("deposited_amount"));
//                  trafficpolice.setCommon_name5(rset.getString("vehicle_no"));
//                  
//                  trafficpolice.setHeading_name1("Officer Name");
//                trafficpolice.setHeading_name2("Offence Date");
//                trafficpolice.setHeading_name3("Offence place");
//                trafficpolice.setHeading_name4("Deposited Amount");
//                  trafficpolice.setHeading_name5("Vehicle No");
//                       
//                       
//                       
//                       }


// end testing
             
                
                
                
                
                
                
                
                
                
                
                
                
                List<userEntryByImageBean> offenceTypeList = showOffenceData1(trafficpolice , rset.getInt("traffic_police_id"),searchActType,searchAmount,searchOffenceCode);
                trafficpolice.setOffenceList(offenceTypeList);
               
               list.add(trafficpolice);
           
            
            
            
            }
           
     
  
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }

        return list;
    }
  
  
      
     public List<userEntryByImageBean> showAllData3(String searchOfficerName,String searchMobile,String searchPlaceOf,String searchAmount ,String searchcaseDate,String searchBookNo, String searchOffenceType, String searchActType, String searchVehicleType, String searchVehicleNo, String searchFromDate, String searchToDate, String searchJarayamNo, String searchOffenceCode, String searchOffenderLicenseNo, String receipt_book_no,String searchChallanNo) {
        List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
        searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
        searchOffenceType = krutiToUnicode.convert_to_unicode(searchOffenceType);
        String query;
        try {
            if (!searchFromDate.equals("")) {
                //String da1[] = searchFromDate.split("-");
                //String da2[] = searchToDate.split("-");
                //searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                //searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }
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
                    + " WHERE IF ('" + searchOfficerName + "'='',kp1.key_person_name LIKE '%%' OR kp1.key_person_name IS NULL, kp1.key_person_name= ?) "// tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id " + " AND vt.vehicle_type_id=ot.vehicle_type_id AND
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%' OR tp.book_no IS NULL,tp.book_no = ?) "
                    + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%' OR ot.offence_type IS NULL,ot.offence_type = ?) "
                    + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%' OR ot.act IS NULL,ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%' OR vt.vehicle_type IS NULL,vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%' OR tp.vehicle_no IS NULL,tp.vehicle_no = ?) "
                    + " AND IF ('" + searchMobile + "'='',kp1.mobile_no1 LIKE '%%' OR kp1.mobile_no1 IS NULL,kp1.mobile_no1 = ?) "
                     + " AND IF ('" + searchPlaceOf + "'='',cl.location LIKE '%%' OR cl.location IS NULL,cl.location = ?) "
                     + " AND IF ('" + searchAmount + "'='',tp.deposited_amount LIKE '%%' OR tp.deposited_amount IS NULL,tp.deposited_amount = ?) "
                         + " AND IF ('" + searchcaseDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,tp.offence_date = ?) "
                   
                    + " AND IF ('" + searchJarayamNo + "'='',tp.jarayam_no LIKE '%%' OR tp.jarayam_no IS NULL,tp.jarayam_no = ?) "
                    + " AND IF ('" + searchOffenceCode + "'='',ot.offence_code LIKE '%%' OR ot.offence_code IS NULL,ot.offence_code = ?) "
                    + " AND IF ('" + searchOffenderLicenseNo + "'='', tp.offender_license_no LIKE '%%' OR tp.offender_license_no IS NULL, tp.offender_license_no = ?) "
                    + " AND IF ('" + receipt_book_no + "'='',rb.receipt_book_no LIKE '%%' OR rb.receipt_book_no IS NULL,rb.receipt_book_no = '" + receipt_book_no + "') "
                    + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,( (STR_TO_DATE(tp.offence_date, '%Y-%m-%d')) Between STR_TO_DATE('" + searchFromDate + "', '%d-%m-%Y') AND STR_TO_DATE('" + searchToDate + "', '%d-%m-%Y'))) "
                    // + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"//%m/%d/%Y
                    + " AND IF ('" + searchChallanNo + "'='',tp.traffic_police_id LIKE '%%' OR tp.traffic_police_id IS NULL,tp.traffic_police_id = ?) "
                    + " GROUP BY tp.traffic_police_id ORDER BY tp.traffic_police_id DESC"
                   ;

            PreparedStatement pstmt = connection.prepareStatement(query);
               pstmt.setString(1, searchOfficerName);
            pstmt.setString(2, searchBookNo);
            pstmt.setString(3, searchOffenceType);
            pstmt.setString(4, searchActType);
            pstmt.setString(5, searchVehicleType);
            pstmt.setString(6, searchVehicleNo);
            pstmt.setString(7, searchMobile);
            pstmt.setString(8, searchPlaceOf);
             pstmt.setString(9, searchAmount);
             pstmt.setString(10, searchcaseDate);
            pstmt.setString(11, searchJarayamNo);
            pstmt.setString(12, searchOffenceCode);
            pstmt.setString(13, searchOffenderLicenseNo);
            
            pstmt.setString(14, searchChallanNo);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                   HashSet<HeadingBean> heading=new HashSet<HeadingBean>();
                    HashSet<HeadingBean> heading2=new HashSet<HeadingBean>();
          List<ColumnBean> column=new ArrayList<ColumnBean>();
          List<ColumnBean> column2=new ArrayList<ColumnBean>();
          List<HeadingBean> head=new ArrayList<HeadingBean>();
           List<HeadingBean> head2=new ArrayList<HeadingBean>();
          
          
           userEntryByImageBean trafficpolice = new userEntryByImageBean(); 
           List<userEntryByImageBean> offenceTypeList = showOffenceData1(trafficpolice , rset.getInt("traffic_police_id"),searchActType,searchAmount,searchOffenceCode);
                                      
                
                // testing
                
                if(searchOfficerName.equalsIgnoreCase("")&&searchPlaceOf.equalsIgnoreCase("")&&searchMobile.equalsIgnoreCase("")&&searchVehicleNo.equalsIgnoreCase("")&&searchAmount.equalsIgnoreCase("")&&searchOffenceType.equalsIgnoreCase("")&&searchActType.equalsIgnoreCase("")&&searchcaseDate.equalsIgnoreCase("")&&searchVehicleType.equalsIgnoreCase("")&&searchFromDate.equalsIgnoreCase("")&&searchToDate.equalsIgnoreCase(""))
                {
                 HeadingBean hb=new HeadingBean();
                        hb.setHname("E-challan");
                        hb.setName("Report");
                        heading.add(hb);
                        head=new ArrayList<HeadingBean>(heading);
                        
                        ColumnBean bean=new ColumnBean();
                        bean.setColname("Officer Name");
                        bean.setValue(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
                        column2.add(bean);
                        
                        ColumnBean bean1=new ColumnBean();
                        bean1.setColname("Mobile number");
                        bean1.setValue((rset.getString("mobile_no1")));
                        column.add(bean1);
                        // column2.add(bean1);
                        
                        ColumnBean bean2=new ColumnBean();
                        bean2.setColname("Offence Place");
                       
                        bean2.setValue( unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
                        column2.add(bean2);
               
                         ColumnBean bean3=new ColumnBean();
                        bean3.setColname("Vehicle number");
                        bean3.setValue((rset.getString("vehicle_no")));
                        column.add(bean3);
                      //  column2.add(bean3);
                        
                        
                         ColumnBean bean4=new ColumnBean();
                        bean4.setColname("Deposited Amount");
                        bean4.setValue((rset.getString("deposited_amount")));
                        column.add(bean4);
                   //     column2.add(bean4);
                        
                         ColumnBean bean5=new ColumnBean();
                        bean5.setColname("Offence Date");
                        bean5.setValue(rset.getString("offence_date"));
                        column.add(bean5);
                      //  column.add(bean5);
              
                }
                else{
                    if(searchOfficerName.equalsIgnoreCase(""))
                    {
                        
                        ColumnBean bean=new ColumnBean();
                        bean.setColname("Officer Name");
                        bean.setValue(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tp_kp")));
                        column2.add(bean);
//                    trafficpolice.setColumn(column);
                    
                    }
                    else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("Officer Name");
                        hb.setName(unicodeToKruti.Convert_to_Kritidev_010(searchOfficerName));
                        heading2.add(hb);
                        head2=new ArrayList<HeadingBean>(heading2);
//                    trafficpolice.setHeading(head);
                   
                    
                    }
                    if(searchMobile.equalsIgnoreCase(""))
                    {
                        
                        ColumnBean bean=new ColumnBean();
                        bean.setColname("Mobile number");
                        bean.setValue((rset.getString("mobile_no1")));
                        column.add(bean);
                        //column2.add(bean);
//                    trafficpolice.setColumn(column);
                    
                    }
                    else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("Mobile number");
                        hb.setName((searchMobile));
                        heading.add(hb);
                        head=new ArrayList<HeadingBean>(heading);
//                    trafficpolice.setHeading(head);
                   
                    
                    }
                     if(searchPlaceOf.equalsIgnoreCase(""))
                    {
                        
                        ColumnBean bean=new ColumnBean();
                        bean.setColname("Offence Place");
                       
                        bean.setValue( unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
                        column2.add(bean);
//                    trafficpolice.setColumn(column);
                    
                    }
                    else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("Offence Place");
                        hb.setName(unicodeToKruti.Convert_to_Kritidev_010(searchPlaceOf));
                        heading2.add(hb);
                        head2=new ArrayList<HeadingBean>(heading2);
//                    trafficpolice.setHeading(head);
                   
                    
                    }
                      if(searchVehicleNo.equalsIgnoreCase(""))
                    {
                        
                        ColumnBean bean=new ColumnBean();
                        bean.setColname("Vehicle number");
                        bean.setValue((rset.getString("vehicle_no")));
                        column.add(bean);
                      //  column2.add(bean);
//                    trafficpolice.setColumn(column);
                    
                    }
                    else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("Vehicle number");
                        hb.setName((searchVehicleNo));
                        heading.add(hb);
                        head=new ArrayList<HeadingBean>(heading);
//                    trafficpolice.setHeading(head);
                   
                    
                    }
                      
                        if(searchAmount.equalsIgnoreCase(""))
                    {
                        
                        ColumnBean bean=new ColumnBean();
                        bean.setColname("Deposited Amount");
                        bean.setValue((rset.getString("deposited_amount")));
                        column.add(bean);
                       // column2.add(bean);
//                    trafficpolice.setColumn(column);
                    
                    }
                    else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("Deposited Amount");
                        hb.setName((searchAmount));
                        heading.add(hb);
                        head=new ArrayList<HeadingBean>(heading);
//                    trafficpolice.setHeading(head);
                   
                    
                    }
                      if(searchcaseDate.equalsIgnoreCase(""))
                      {
                   ColumnBean bean=new ColumnBean();
                        bean.setColname("Offence Date");
                        bean.setValue(rset.getString("offence_date"));
                        column.add(bean);
                       // column2.add(bean);
                      }
                      else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("Offence Date");
                        hb.setName((searchcaseDate));
                        heading.add(hb);
                        head=new ArrayList<HeadingBean>(heading);
//                    trafficpolice.setHeading(head);
                   
                    
                    }
                      if(searchFromDate.equalsIgnoreCase(""))
                      {
                   
                      }
                      else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("From Date");
                        hb.setName((searchFromDate));
                        heading.add(hb);
                        head=new ArrayList<HeadingBean>(heading);
//                    trafficpolice.setHeading(head);
                   
                    
                    }
                       if(searchToDate.equalsIgnoreCase(""))
                      {
                  
                      }
                      else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("To Date");
                        hb.setName((searchToDate));
                        heading.add(hb);
                        head=new ArrayList<HeadingBean>(heading);
//                    trafficpolice.setHeading(head);
                   
                    
                    }
                      
//                    trafficpolice.setColumn(column);
                    
                    
                    
                }                                              
            
              System.out.println("Column"+column);
              System.out.println("Column2"+column2);
               List[] col2 = showOffenceData3(trafficpolice , rset.getInt("traffic_police_id"),searchActType,searchAmount,searchOffenceCode,searchVehicleType,searchOffenceType);
//           System.out.println("Column2"+col2);
//               
//                       col2.addAll(column);
//                       
//                    System.out.println("Addition"+col2);    
               trafficpolice.setColumn(column);
                trafficpolice.setColumn1(column2);
               
               List<ListBean> col3=new ArrayList<ListBean>();
               
               for( int a=0;a<i;a=a+2)
               {
               ListBean bean =new ListBean();
               bean.setLiE(col2[a]);
               ListBean bean2 =new ListBean();
               bean.setLiH(col2[a+1]);
               
               
               col3.add(bean);
               }
              
               
//               ListBean bean4 =new ListBean();
//               bean4.setLi(col2[1]);
//               
//               col3.add(bean4);
               
                head.addAll(col2[i]);
                head2.addAll(col2[i+1]);
               trafficpolice.setColumnlist(col3);
            //  trafficpolice.setColumn1(col3);
// end testing
             
                trafficpolice.setHeading(head);
                trafficpolice.setHeading2(head2);

                trafficpolice.setOffenceList(offenceTypeList);
               
               list.add(trafficpolice);
           
            
            
            
            }
           
     
  
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }


        return list;
    }
     
     
  
  
     
     
     public List[] showOffenceData3(userEntryByImageBean trafficpolice1, int traffic_police_id,String searchActType,String searchAmount,String searchOffenceCode,String searchVehicleType,String OffenceType) {
        List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
        List<HeadingBean> head=new ArrayList<HeadingBean>();
         List<HeadingBean> head2=new ArrayList<HeadingBean>();
        List[] collist2 =new ArrayList[15];
        HashSet<HeadingBean> heading=new HashSet<HeadingBean>();
         HashSet<HeadingBean> heading2=new HashSet<HeadingBean>();
        
        String query;
        
        try {
            query = "Select offence_type ,act, if(tom.is_second_offence='YES',o.second_offence_penalty,o.penalty_amount) as penalty_amount, "
                    + "vehicle_type, act_origin, offence_code "
                    + "from traffic_offence_map as tom,traffic_police as tp, offence_type as o,act_origin as a ,"
                    + "vehicle_type as v where o.act_origin_id=a.act_origin_id and o.vehicle_type_id=v.vehicle_type_id "
                    + "and tom.traffic_police_id=tp.traffic_police_id and tom.offence_type_id=o.offence_type_id and tp.traffic_police_id='" + traffic_police_id + "'";
           
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            i=0;
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                
                List<ColumnBean> collist = new ArrayList<ColumnBean>();
                 List<ColumnBean> collist1 = new ArrayList<ColumnBean>();
                 if(searchActType.equalsIgnoreCase(""))
                    {
                        
                        ColumnBean bean=new ColumnBean();
                        bean.setColname("Act");
                        bean.setValue(rset.getString("act"));
                        collist.add(bean);
                    
                    }
                    else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("Act");
                        hb.setName(rset.getString("act"));
                        heading.add(hb);
                        head=new ArrayList<HeadingBean>(heading);
//                    trafficpolice1.setHeading(head);
                   
                    
                    }
                if(OffenceType.equalsIgnoreCase(""))
                    {
                        
                        ColumnBean bean3=new ColumnBean();
                        bean3.setColname("Offence Type");
                        bean3.setValue(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offence_type")));
                        collist1.add(bean3);
                    
                    }
                    else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("Offence Type");
                        hb.setName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offence_type")));
                        heading2.add(hb);
                        head2=new ArrayList<HeadingBean>(heading2);
//                    trafficpolice1.setHeading(head);
                   
                    
                    }
                
                      if(searchOffenceCode.equalsIgnoreCase(""))
                    {
                        
                       ColumnBean bean5=new ColumnBean();
                        bean5.setColname("offence Code");
                        bean5.setValue(rset.getString("offence_code"));
                        collist.add(bean5);
                    
                    }
                    else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("Offence Code");
                        hb.setName(rset.getString("offence_code"));
                        heading.add(hb);
                        head=new ArrayList<HeadingBean>(heading);
//                    trafficpolice1.setHeading(head);
                   
                    
                    }  
                   if(searchVehicleType.equalsIgnoreCase(""))
                    {
                        
                        ColumnBean bean6=new ColumnBean();
                        bean6.setColname("Vehicle type");
                        bean6.setValue(rset.getString("vehicle_type"));
                        collist.add(bean6);
                    
                    }
                    else
                    {
                        HeadingBean hb=new HeadingBean();
                        hb.setHname("Vehicle type");
                        hb.setName(rset.getString("vehicle_type"));
                        heading.add(hb);
                        head=new ArrayList<HeadingBean>(heading);
//                    trafficpolice1.setHeading(head);
                   
                    
                    }  
                        ColumnBean bean2=new ColumnBean();
                        bean2.setColname("Penalty");
                        bean2.setValue(rset.getString("penalty_amount"));
                        collist.add(bean2);
                        
                        
                        
                        
                        ColumnBean bean4=new ColumnBean();
                        bean4.setColname("Act Origin");
                        bean4.setValue(rset.getString("act_origin"));
                        collist.add(bean4);
                       
                        
                       
                       
                        
                        
                
//                        trafficpolice1.setColumn2(collist);
               
                        
                      collist2[i]=collist;
                        collist2[i+1]=collist1;
                   i=i+2;
//                   if(maxi>i)
//                   {
//                   maxi=i;
//                   }
                   
               
            }
            collist2[i]=head;
            collist2[i+1]=head2;
            
            
        } catch (Exception e) 
        {
            System.out.println("Error:TrafficPoliceModel--showOffenceData " + e);
        }

        
        return collist2;
    }
     
     
     
  
    

    public List<userEntryByImageBean> showMISData2(userEntryByImageBean vt,String traffic_police_id1){
    List<userEntryByImageBean> list = new ArrayList<userEntryByImageBean>();
    List<userEntryByImageBean> list1 = new ArrayList<userEntryByImageBean>();
    String offencequery = "select offence_type,act_origin,if(tom.is_second_offence='YES',second_offence_penalty,penalty_amount) as penalty_amount,act "
                                      +" from traffic_offence_map tom,offence_type ot,act_origin aorg "
                                      +" where tom.offence_type_id = ot.offence_type_id "
                                      +" and ot.act_origin_id = aorg.act_origin_id "
                                      +" and tom.traffic_police_id="+traffic_police_id1;

                 try{
                 ResultSet rs1 = connection.prepareStatement(offencequery).executeQuery();

                 while(rs1.next()){
                     userEntryByImageBean tpBean1 = new userEntryByImageBean();
                     String offence_type = unicodeToKruti.Convert_to_Kritidev_010(rs1.getString("offence_type"));
                     //offence_type="For testing";
                     String act_origin = rs1.getString("act_origin");
                     String penalty_amount = rs1.getString("penalty_amount");
                      String act = rs1.getString("act");
                      
                        String final_act_origin = act+", "+act_origin;
                      
                      vt.setOffence_type(offence_type);
                    // tpBean1.setOffence_type("Testing");
                     vt.setAct_origin(final_act_origin);
                     vt.setPenalty_amount(penalty_amount);
                      
                      
                    

                     tpBean1.setOffence_type(offence_type);
                    // tpBean1.setOffence_type("Testing");
                     tpBean1.setAct_origin(final_act_origin);
                     tpBean1.setPenalty_amount(penalty_amount);
                     //tpBean1.setAct(act);
                     list.add(tpBean1);
                     vt.setOffence_list(list);
                 }
                 list1.add(vt);
        }catch (Exception e) {
            System.out.println("error: " + e);
        }

        return list1;

    }


    public List<String> getImagePath(String traffic_police_id) {
        List<String> list = new ArrayList<String>();
        String fullPath="";
        String query = "select im_c.image_path,im_c.image_name "
                       +" from traffic_police tp,general_image_details gid,image_count im_c "
                       +" where tp.general_image_details_id = gid.general_image_details_id "
                       +" and gid.general_image_details_id = im_c.general_image_detail_id "
                       +" and tp.traffic_police_id="+traffic_police_id;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String image_path = rs.getString("image_path");
                String image_name = rs.getString("image_name");
                fullPath = image_path+"\\"+image_name;

                list.add(fullPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel relationTypeList(" + e);
        }

        return list;
    }
    public List<String> getOfficerSearchList(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select key_person_name\n" +
                       "from key_person kp,traffic_police tp\n" +
                       "where tp.key_person_id=kp.key_person_id\n" +
                       "group by key_person_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                //String officer_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
                String officer_name = rset.getString("key_person_name");
                
                if (officer_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(officer_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such officer exists.......");
            }
        } catch (Exception e) {
            System.out.println("getOfficerSearchListERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }
    public List<String> getOffenceSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  ot.offence_type from offence_type as ot "// ,traffic_police as tp, traffic_offence_map tom "
                //+ " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                //+ " AND tp.book_no = tp.book_no"
                + " GROUP BY offence_type"
                + " ORDER BY offence_type";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1,book_no);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
//                String offence_type = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("offence_type"));
                  String offence_type = rs.getString("offence_type");
                if(offence_type.startsWith(q)){
                    list.add(offence_type);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No Offnece Type is exist");
            }
        } catch (Exception e) {
            System.out.println(" getOffenceSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }
    
    public List<String> getOffenceCodeList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  ot.offence_code from offence_type ot "//, traffic_offence_map tom, traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                + " GROUP BY offence_code"
                + " ORDER BY offence_code ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String offence_code = rs.getString("offence_code");
                if(offence_code.startsWith(q))
                    list.add(offence_code);
                count++;
            }

            if (count == 0) {
                list.add("Offence Code is not exist");
            }
        } catch (Exception e) {
            System.out.println(" getOffenceCodeList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }
public List<String> getActSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  ot.offence_type_id,ot.act from offence_type as ot"//,traffic_police as tp, traffic_offence_map tom"
                //+ " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id"
                + " GROUP BY act"
                + " ORDER BY act";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1,offence_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String act = rs.getString("act");
                list.add(act);
                count++;
            }

            if (count == 0) {
                list.add("No Act Type is exist");
            }
        } catch (Exception e) {
            System.out.println(" getActSearchList ERROR inside TrafficPoliceModel - " + e); 
        }
        return list;
    }
public List<String> getVehicleTypeSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  vt.vehicle_type from vehicle_type as vt "//, traffic_police as tp, offence_type as ot, traffic_offence_map tom "
                //+ " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                //+ " AND vt.vehicle_type_id=ot.vehicle_type_id "
                //+ "AND ot.offence_type_id=tp.offence_type_id"
                + " GROUP BY vehicle_type"
                + " ORDER BY vehicle_type";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,act);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String vehicle_type = rs.getString("vehicle_type");
                if(vehicle_type.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(vehicle_type);
                count++;
                }
            }

            if (count == 0) {
                list.add("No vehicle Type is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleTypeSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }


public List<String> getMobileNoSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  kp.mobile_no1 from key_person as kp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE mobile_no1 IS NOT NULL"
                + " GROUP BY mobile_no1"
                + " ORDER BY mobile_no1 ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String mobile_no = rs.getString("mobile_no1");
                if(mobile_no.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(mobile_no);
                count++;
                }
            }

            if (count == 0) {
                list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }


      public List<String> getMobileNoSearchList() {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  kp.mobile_no1 from key_person as kp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE mobile_no1 IS NOT NULL"
                + " GROUP BY mobile_no1"
                + " ORDER BY mobile_no1 ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
           
            while (rs.next()) {
                String mobile_no = rs.getString("mobile_no1");
              
                    list.add(mobile_no);
                count++;
                
            }

            if (count == 0) {
                list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }


public List<String> getOffenderNameSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  tp.offender_name from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE offender_name IS NOT NULL"
                + " GROUP BY offender_name"
                + " ORDER BY offender_name ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String offender_name = rs.getString("offender_name");
                if(offender_name.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(offender_name);
                count++;
                }
            }

            if (count == 0) {
                list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

public List<String> getOffenderNameSearchList() {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  tp.offender_name from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE offender_name IS NOT NULL"
                + " GROUP BY offender_name"
                + " ORDER BY offender_name ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
           
            while (rs.next()) {
                String offender_name = rs.getString("offender_name");
               
                    list.add(offender_name);
                count++;
               
            }

            if (count == 0) {
                list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }
public List<String> getOffenderAgeSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  tp.offender_age from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE offender_age IS NOT NULL"
                + " GROUP BY offender_age"
                + " ORDER BY offender_age ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String offender_age = rs.getString("offender_age");
                if(offender_age.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(offender_age);
                count++;
                }
            }

            if (count == 0) {
                list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }


public List<String> getOffenderAgeSearchList() {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  tp.offender_age from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE offender_age IS NOT NULL"
                + " GROUP BY offender_age"
                + " ORDER BY offender_age ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
       
            while (rs.next()) {
                String offender_age = rs.getString("offender_age");
              
                    list.add(offender_age);
                count++;
                }
          

            if (count == 0) {
                list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }




public List<String> getPlaceOfSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  location from city_location as cl " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE location IS NOT NULL"
                + " GROUP BY location"
                + " ORDER BY location ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String location = rs.getString("location");
                if(location.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(location);
                count++;
                }
            }

            if (count == 0) {
                list.add("No location  is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }


public List<String> getPlaceOfSearchList() {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  location from city_location as cl " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE location IS NOT NULL"
                + " GROUP BY location"
                + " ORDER BY location ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
         
            while (rs.next()) {
                String location = rs.getString("location");
              
                    list.add(location);
                count++;
                
            }

            if (count == 0) {
                list.add("No location  is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }


public List<String> getAmountSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  deposited_amount from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE deposited_amount IS NOT NULL"
                + " GROUP BY deposited_amount"
                + " ORDER BY deposited_amount ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String deposited_amount = rs.getString("deposited_amount");
                if(deposited_amount.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(deposited_amount);
                count++;
                }
            }

            if (count == 0) {
                list.add("No deposited_amount  is exist");
            }
        } catch (Exception e) {
            System.out.println(" deposited_amount ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }


public List<String> getAmountSearchList() {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  deposited_amount from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE deposited_amount IS NOT NULL"
                + " GROUP BY deposited_amount"
                + " ORDER BY deposited_amount ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
      
            while (rs.next()) {
                String deposited_amount = rs.getString("deposited_amount");
                
                    list.add(deposited_amount);
                count++;
          
            }

            if (count == 0) {
                list.add("No deposited_amount  is exist");
            }
        } catch (Exception e) {
            System.out.println(" deposited_amount ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }








public List<String> getCaseDateSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  offence_date from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE offence_date IS NOT NULL"
                + " GROUP BY offence_date"
                + " ORDER BY offence_date ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String offence_date = rs.getString("offence_date");
                if(offence_date.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(offence_date);
                count++;
                }
            }

            if (count == 0) {
                list.add("No offence_date  is exist");
            }
        } catch (Exception e) {
            System.out.println(" offence_date ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }


public List<String> getCaseDateSearchList() {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  offence_date from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE offence_date IS NOT NULL"
                + " GROUP BY offence_date"
                + " ORDER BY offence_date ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
      
            while (rs.next()) {
                String offence_date = rs.getString("offence_date");
                
                    list.add(offence_date);
                count++;
          
            }

            if (count == 0) {
                list.add("No offence_date  is exist");
            }
        } catch (Exception e) {
            System.out.println(" offence_date ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }



public List<String> getVehicleNoSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  tp.vehicle_no from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE vehicle_no IS NOT NULL"
                + " GROUP BY vehicle_no"
                + " ORDER BY vehicle_no ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String vehicle_no = rs.getString("vehicle_no");
                if(vehicle_no.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(vehicle_no);
                count++;
                }
            }

            if (count == 0) {
                list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

    public List<String> getChallanNoSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "select traffic_police_id\n" +
                       "from traffic_police tp ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String traffic_police_id = rs.getString("traffic_police_id");
                if(traffic_police_id.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(traffic_police_id);
                count++;
                }
            }

            if (count == 0) {
                list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }
    
   public int deleteRecord(int traffic_police_id) {
        List<String> list = new ArrayList<String>();
        int  count=0;
        int count1=0;
        int count2=0;
//        String query = " delete tp,tom,tprm\n" +
//                       "from traffic_police tp\n" +
//                       "Left Join traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
//                       "Left Join trafficpolice_receipt_map tprm ON tp.traffic_police_id=tprm.traffic_police_id\n" +
//                       "where tp.traffic_police_id=?";
        
        String query1="DELETE FROM traffic_offence_map WHERE traffic_police_id = ?";
      
//      String query = " DELETE FROM traffic_police WHERE traffic_police_id = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
             pstmt.setInt(1,traffic_police_id);

            count = pstmt.executeUpdate();
            if(count > 0){
//               message = "Record deleted successfully.";
//               msgBgColor = COLOR_OK;
            }
            count1=userEntryByImageModel.deleteRecord1(traffic_police_id);
             if(count1 > 0)
                      {
//                      message = "Record deleted successfully.";
//               msgBgColor = COLOR_OK;
                      }
              count2=userEntryByImageModel.deleteRecord2(traffic_police_id);
            if(count2 > 0)
                      {
//                      message = "Record deleted successfully.";
//               msgBgColor = COLOR_OK;
                      }
        else{
//            message = "Record not deleted .";
//            msgBgColor = COLOR_ERROR;
            
        }
             
        } catch (Exception e) {
            System.out.println(" deleteRecord ERROR inside TrafficPoliceModel - " + e);
        }
        return count;
    }
 
 
 public static  int deleteRecord1(int traffic_police_id){
 List<String> list = new ArrayList<String>();
        int  count1=0;
//        String query = " delete tp,tom,tprm\n" +
//                       "from traffic_police tp\n" +
//                       "Left Join traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
//                       "Left Join trafficpolice_receipt_map tprm ON tp.traffic_police_id=tprm.traffic_police_id\n" +
//                       "where tp.traffic_police_id=?";
        
       
        String query2="DELETE FROM trafficpolice_receipt_map WHERE traffic_police_id = ?";
       
//      String query = " DELETE FROM traffic_police WHERE traffic_police_id = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query2);
             pstmt.setInt(1,traffic_police_id);

            count1 = pstmt.executeUpdate();
            if(count1 > 0){
               message = "Record deleted successfully.";
               
        }else{
            message = "Record not deleted .";
            
            
        }
             
        } catch (Exception e) {
            System.out.println(" deleteRecord ERROR inside TrafficPoliceModel - " + e);
        }
        return count1;
 
 }
 

  public static int deleteRecord2(int traffic_police_id){
 List<String> list = new ArrayList<String>();
        int  count2=0;
//        String query = " delete tp,tom,tprm\n" +
//                       "from traffic_police tp\n" +
//                       "Left Join traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
//                       "Left Join trafficpolice_receipt_map tprm ON tp.traffic_police_id=tprm.traffic_police_id\n" +
//                       "where tp.traffic_police_id=?";
        
       
        String query3="DELETE FROM traffic_police WHERE traffic_police_id = ?";
//      String query = " DELETE FROM traffic_police WHERE traffic_police_id = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query3);
             pstmt.setInt(1,traffic_police_id);

            count2 = pstmt.executeUpdate();
            if(count2 > 0){
               message = "Record deleted successfully.";
              
        }else{
            message = "Record not deleted .";
         
            
        }
             
        } catch (Exception e) {
            System.out.println(" deleteRecord ERROR inside TrafficPoliceModel - " + e);
        }
        return count2;
 
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

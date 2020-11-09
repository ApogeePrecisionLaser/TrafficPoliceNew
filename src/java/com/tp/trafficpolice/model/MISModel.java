/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import com.tp.tableClasses.MISBean;
import com.tp.tableClasses.userEntryByImageBean;
import com.tp.trafficpolice.controller.MISController;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author acer pc
 */
public class MISModel {
    
    private static Connection connection;

    public  static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection TrafficTypeModel:" + e);
        }
    }


   

   
   
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    
    
    public void setConnection() {
        try {
            Class.forName(driverClass);
             connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            //connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }
    UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
    KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    
    public int getNoOfRows(String searchFromDate, String searchToDate, String searchOfficerName) {
        int noOfRows = 8;
        searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
//        searchOffenceType = krutiToUnicode.convert_to_unicode(searchOffenceType);
        try {
            if (!searchFromDate.equals("")) {
                //String da1[] = searchFromDate.split("-");
                //String da2[] = searchToDate.split("-");
                //searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                //searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }


            String query = "";
            //PreparedStatement pstmt = connection.prepareStatement(query);
            //System.out.println(query);
            
//            if(searchFromDate != null && !searchFromDate.isEmpty())
//                pstmt.setDate(10, convertToSqlDate(searchFromDate));
//            else
//                pstmt.setString(10, "");
//            if(searchToDate != null && !searchToDate.isEmpty())
//                pstmt.setDate(11, convertToSqlDate(searchToDate));
//            else
//                pstmt.setString(11, "");

//            ResultSet rset = pstmt.executeQuery();
//            rset.next();
//            if(rset.getString(1) == null)
//                noOfRows = 0;
//            else
//                noOfRows = Integer.parseInt(rset.getString(1));

        } catch (Exception e) {
            System.out.println("Error:keypersonModel-getNoOfRows-- " + e);
        }
        return noOfRows;
    }
    
    public List<MISBean> showData1(int lowerLimit, int noOfRowsToDisplay,String searchFromDate,String searchToDate , String searchOfficerName) {
        
        int bean129_177_count=0,bean128_177_count=0,bean39_192_count=0,bean56_192_count=0,bean66_192_count=0,bean146_196_count=0,bean46_177_count=0,bean77_177_count=0,bean115_177_count=0,bean3_181_count=0;
        int  bean116_177_count=0,bean117_177_count=0,bean122_177_count=0,bean100_177_count=0,bean125_177_count=0,bean21_25_177_count=0,bean119_177_count=0,bean81_177_count=0;
        int bean185_count=0,bean93_8_177_count=0,others_count=0;
        String time=" 23:59:59";
        searchToDate=searchToDate+time;
     List<MISBean> list = new ArrayList<MISBean>();
        List kpId_list = new ArrayList(); 
        int date_status=0;
        
        try {
            
                                       

                 String query = "select tp.key_person_id,kp.key_person_name\n" +
                                 "from traffic_police tp,key_person kp\n" +
                                 "Where IF ('"+searchFromDate+"'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
                                 + " WHERE IF('" + searchOfficerName + "' = '', key_person_name LIKE '%%', key_person_name=?) "
                                +"group by tp.key_person_id\n" +
                                 "Having tp.key_person_id > 0";
                                 
                  String query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
                               "from  traffic_police tp\n" +
                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
                               "where kp.key_person_id=?\n" +
                               "AND IF ('"+searchFromDate+"'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
                               +" group by act";
                  
                  if (searchFromDate.equals(searchToDate) && searchFromDate != null) {
                      date_status++;
                  query = "select tp.key_person_id\n" +
                                 " from traffic_police tp\n" +  
                                " Where tp.offence_date like '"+searchFromDate+"%'"
                                +" group by tp.key_person_id\n" +
                                 " Having tp.key_person_id > 0";
                                 
                   query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
                               "from  traffic_police tp\n" +
                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
                               "where kp.key_person_id=?\n" +
                               " AND tp.offence_date like '"+searchFromDate+"%'\n"
                              +" group by act";
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }
                  
                  
                  PreparedStatement pstmt = connection.prepareStatement(query);
            
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                kpId_list.add(rset.getString("key_person_id"));
            }
            
            PreparedStatement pstmt1 = connection.prepareStatement(query1);
       
            for(int i=0;i<kpId_list.size();i++){
                int offence_count=0;
                pstmt1.setInt(1,Integer.parseInt(kpId_list.get(i).toString()));
            ResultSet rset1 = pstmt1.executeQuery();
            MISBean bean = new MISBean();
            bean.setSearchFromDate(searchFromDate);
            bean.setSearchToDate(searchToDate);
            while (rset1.next()) {
                
                //bean.setKey_person_name(unicodeToKruti.Convert_to_Kritidev_010(rset1.getString("key_person_name")));
                  bean.setKey_person_name(rset1.getString("key_person_name"));
                String act=rset1.getString("act");
                String offence_code = rset1.getString("offence_code");
                //rset.getString("act_count");
                  if(offence_code.equals("34") || offence_code.equals("33")){
                      bean.setBean129_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean129_177_count=bean129_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("32") || offence_code.equals("31")){
                      bean.setBean128_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean128_177_count=bean128_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("81") || offence_code.equals("71") || offence_code.equals("72") || offence_code.equals("77") || offence_code.equals("78") || offence_code.equals("79")){
                      bean.setBean39_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean39_192_count=bean39_192_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("83") || offence_code.equals("84") || offence_code.equals("88")){
                      bean.setBean56_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean56_192_count=bean56_192_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("7") || offence_code.equals("8") || offence_code.equals("9") || offence_code.equals("10")){
                      bean.setBean66_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean66_192_count=bean66_192_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("91") || offence_code.equals("92") || offence_code.equals("94") || offence_code.equals("95") || offence_code.equals("96") || offence_code.equals("97") || offence_code.equals("98")){
                      bean.setBean146_196(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean146_196_count=bean146_196_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("113") || offence_code.equals("114") || offence_code.equals("178") || offence_code.equals("179") || offence_code.equals("180")){
                      bean.setBean77_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean77_177_count=bean77_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("1") || offence_code.equals("2")){
                      bean.setBean3_181(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean3_181_count=bean3_181_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("15") || offence_code.equals("17")){
                      bean.setBean117_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean117_177_count=bean117_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("21") || offence_code.equals("22")){
                      bean.setBean122_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean122_177_count=bean122_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("115") || offence_code.equals("116")){
                      bean.setBean100_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean100_177_count=bean100_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("27") || offence_code.equals("28") || offence_code.equals("131") || offence_code.equals("132")){
                      bean.setBean125_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean125_177_count=bean125_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("107") || offence_code.equals("108")){
                      bean.setBean21_25_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean21_25_177_count=bean21_25_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("16") || offence_code.equals("18")){
                      bean.setBean119_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean119_177_count=bean119_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("155") || offence_code.equals("156")){
                      bean.setBean81_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean81_177_count=bean81_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("57")){
                      bean.setBean185(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean185_count=bean185_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  if(offence_code.equals("133") || offence_code.equals("134")){
                      bean.setBean93_8_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean93_8_177_count=bean93_8_177_count+Integer.parseInt(rset1.getString("act_count"));
                  }
                  
                 else
                  {
                  bean.setOthers(rset1.getString("act_count"));
                  others_count=others_count+Integer.parseInt(rset1.getString("act_count"));
                  others_count=others_count+Integer.parseInt(rset1.getString("act_count"));
                  }
            }
            bean.setPerson_challan_count(offence_count+"");
            list.add(bean);
            }
            MISBean bean1 = new MISBean();
            bean1.setBean129_177_count(bean129_177_count+"");
            bean1.setBean128_177_count(bean128_177_count+"");
            bean1.setBean39_192_count(bean39_192_count+"");
            bean1.setBean56_192_count(bean56_192_count+"");
            bean1.setBean66_192_count(bean66_192_count+"");
            bean1.setBean146_196_count(bean146_196_count+"");
            bean1.setBean77_177_count(bean77_177_count+"");
            bean1.setBean3_181_count(bean3_181_count+"");
            bean1.setBean117_177_count(bean117_177_count+"");
            bean1.setBean122_177_count(bean122_177_count+"");
            bean1.setBean100_177_count(bean100_177_count+"");
            bean1.setBean125_177_count(bean125_177_count+"");
            bean1.setBean21_25_177_count(bean21_25_177_count+"");
            bean1.setBean119_177_count(bean119_177_count+"");
            bean1.setBean81_177_count(bean81_177_count+"");
            bean1.setBean185_count(bean185_count+"");
            bean1.setBean93_8_177_count(bean93_8_177_count+"");
            bean1.setOthers_count(others_count+"");
            //list.add(bean1);
            
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
             
    }
    
//    public List<MISBean> showData(int lowerLimit, int noOfRowsToDisplay,  String searchFromDate, String searchToDate) {
//        List<MISBean> list = new ArrayList<MISBean>();
//        List kpId_list = new ArrayList();     
////        searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
////        searchOffenceType = krutiToUnicode.convert_to_unicode(searchOffenceType);
//        
//        try {
//            if (!searchFromDate.equals("")) {
//                //String da1[] = searchFromDate.split("-");
//                //String da2[] = searchToDate.split("-");
//                //searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
//                //searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
//                System.out.println(searchFromDate);
//                System.out.println(searchToDate);
//            }
////            query = "select tp.amount_paid,tp.traffic_police_id,kp1.key_person_name as tp_kp,kp1.mobile_no1,vehicle_no, offender_name, offender_license_no,offender_mobile_no, "
////                    + "deposited_amount, ob.book_no,ob.book_revision_no,kp.key_person_name as ob_kp, reciept_no,"
////                    + "IF(STR_TO_DATE(tp.offence_date, '%m/%d/%Y'),STR_TO_DATE(tp.offence_date, '%m/%d/%Y'),tp.offence_date) AS offence_date, "
////                    + "tp.city_location_id,cl.location,c.city_name,z.zone,pt.processing_type_id,"//STR_TO_DATE(tp.offence_date, '%m/%d/%Y') AS
////                    + "relation_type_id, case_no, IF(STR_TO_DATE(tp.case_date, '%m/%d/%Y'),STR_TO_DATE(tp.case_date, '%m/%d/%Y'),tp.case_date) AS  case_date, "
////                    + "offender_age, jarayam_no, offender_address,"// STR_TO_DATE(tp.case_date, '%m/%d/%Y') AS
////                    + "tp.city_id,pt.processing_type, ofc.city_name AS offender_city, tp.relative_name, tp.relative_salutation, "
////                    + "tp.lattitude, tp.longitude, rb.receipt_book_id, rb.receipt_book_no, rb.book_revision, rb.page_no, tp.father_name, "
////                    + "tp.remark, tp.vehicle_no_state, tp.vehicle_no_city_code, tp.vehicle_no_series, tp.vehicle_no_digits "
////                    + "FROM traffic_police as tp "
////                    + "left join officer_book as ob on ob.book_no=tp.book_no AND ob.book_revision_no = tp.book_revision_no "
////                    + "left join (trafficpolice_receipt_map trm, receipt_book rb) ON rb.receipt_book_id = trm.receipt_book_id AND trm.traffic_police_id = tp.traffic_police_id "
////                    + "left join key_person as kp on ob.key_person_id=kp.key_person_id "
////                    + "left join city_location as cl on cl.city_location_id=tp.city_location_id "
////                    + "left join zone_new as z on z.zone_new_id=cl.zone_new_id "
////                    + "left join city as c on c.city_id=z.city_id "
////                    + "left join key_person as kp1 on tp.key_person_id=kp1.key_person_id "
////                    + "left join city as ofc on ofc.city_id=tp.city_id "
////                    + "left join processing_type as pt on pt.processing_type_id=tp.processing_type_id "
////                    + "LEFT JOIN (traffic_offence_map tom, offence_type as ot) ON tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
////                    + "LEFT JOIN vehicle_type vt ON vt.vehicle_type_id=ot.vehicle_type_id "
////                    + " WHERE IF ('" + searchOfficerName + "'='',kp1.key_person_name LIKE '%%' OR kp1.key_person_name IS NULL, kp1.key_person_name= ?) "// tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id " + " AND vt.vehicle_type_id=ot.vehicle_type_id AND
////                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%' OR tp.book_no IS NULL,tp.book_no = ?) "
////                    + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%' OR ot.offence_type IS NULL,ot.offence_type = ?) "
////                    + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%' OR ot.act IS NULL,ot.act = ?) "
////                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%' OR vt.vehicle_type IS NULL,vt.vehicle_type = ?) "
////                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%' OR tp.vehicle_no IS NULL,tp.vehicle_no = ?) "
////                    + " AND IF ('" + searchJarayamNo + "'='',tp.jarayam_no LIKE '%%' OR tp.jarayam_no IS NULL,tp.jarayam_no = ?) "
////                    + " AND IF ('" + searchOffenceCode + "'='',ot.offence_code LIKE '%%' OR ot.offence_code IS NULL,ot.offence_code = ?) "
////                    + " AND IF ('" + searchOffenderLicenseNo + "'='', tp.offender_license_no LIKE '%%' OR tp.offender_license_no IS NULL, tp.offender_license_no = ?) "
////                    + " AND IF ('" + receipt_book_no + "'='',rb.receipt_book_no LIKE '%%' OR rb.receipt_book_no IS NULL,rb.receipt_book_no = '" + receipt_book_no + "') "
////                    + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,( (STR_TO_DATE(tp.offence_date, '%Y-%m-%d')) Between STR_TO_DATE('" + searchFromDate + "', '%d-%m-%Y') AND STR_TO_DATE('" + searchToDate + "', '%d-%m-%Y'))) "
////                    // + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"//%m/%d/%Y
////                    + " AND IF ('" + searchChallanNo + "'='',tp.traffic_police_id LIKE '%%' OR tp.traffic_police_id IS NULL,tp.traffic_police_id = ?) "
////                    + " GROUP BY tp.traffic_police_id ORDER BY tp.traffic_police_id DESC"
////                    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
////            
//            
////            String query="select key_person_name,act,count(act) as act_count\n" +
////                          "from  traffic_police tp\n" +
////                          "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
////                          "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
////                          "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
////                          "group by act order by key_person_name";
//
////                                 String query="select key_person_name,act,count(act) as act_count\n" +
////                          "from  traffic_police tp\n" +
////                          "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
////                          "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
////                          "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
////                          "where IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"//%m/%d/%Y               
////                          +"group by act order by key_person_name";
//                                       
//
//                 String query = "select tp.key_person_id\n" +
//                                 "from traffic_police tp\n" +
//                                 "Where IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
//                                +"group by tp.key_person_id\n" +
//                                 "Having tp.key_person_id > 0";
//                                 
//                  String query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
//                               "from  traffic_police tp\n" +
//                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
//                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
//                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
//                               "where kp.key_person_id=?\n" +
//                               "AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
//                               +" group by act";
//                  PreparedStatement pstmt = connection.prepareStatement(query);
//            
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                kpId_list.add(rset.getString("key_person_id"));
//            }
//            
//            
//
//            PreparedStatement pstmt1 = connection.prepareStatement(query1);
//       
//            for(int i=0;i<kpId_list.size();i++){
//                pstmt1.setInt(1,Integer.parseInt(kpId_list.get(i).toString()));
//            ResultSet rset1 = pstmt1.executeQuery();
//            MISBean bean = new MISBean();
//            while (rset1.next()) {
//                
//                bean.setKey_person_name(rset1.getString("key_person_name"));
//                String act=rset1.getString("act");
//                String offence_code = rset1.getString("offence_code");
//                //rset.getString("act_count");
//                  if(offence_code.equals("34") || offence_code.equals("33")){
//                      bean.setBean129_177(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("32") || offence_code.equals("31")){
//                      bean.setBean128_177(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("81") || offence_code.equals("71") || offence_code.equals("72") || offence_code.equals("77") || offence_code.equals("78") || offence_code.equals("79")){
//                      bean.setBean39_192(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("83") || offence_code.equals("84") || offence_code.equals("88")){
//                      bean.setBean56_192(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("7") || offence_code.equals("8") || offence_code.equals("9") || offence_code.equals("10")){
//                      bean.setBean66_192(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("91") || offence_code.equals("92") || offence_code.equals("94") || offence_code.equals("95") || offence_code.equals("96") || offence_code.equals("97") || offence_code.equals("98")){
//                      bean.setBean146_196(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("113") || offence_code.equals("114") || offence_code.equals("178") || offence_code.equals("179") || offence_code.equals("180")){
//                      bean.setBean77_177(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("1") || offence_code.equals("2")){
//                      bean.setBean3_181(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("15") || offence_code.equals("17")){
//                      bean.setBean117_177(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("21") || offence_code.equals("22")){
//                      bean.setBean122_177(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("115") || offence_code.equals("116")){
//                      bean.setBean100_177(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("27") || offence_code.equals("28") || offence_code.equals("131") || offence_code.equals("132")){
//                      bean.setBean125_177(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("107") || offence_code.equals("108")){
//                      bean.setBean21_25_177(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("16") || offence_code.equals("18")){
//                      bean.setBean119_177(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("155") || offence_code.equals("156")){
//                      bean.setBean81_177(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("57")){
//                      bean.setBean185(rset1.getString("act_count"));
//                  }
//                  if(offence_code.equals("133") || offence_code.equals("134")){
//                      bean.setBean93_8_177(rset1.getString("act_count"));
//                  }
//                  
//                
//            }
//            list.add(bean);
//            }
//        } catch (Exception e) {
//            System.out.println("Error:keypersonModel--showData " + e);
//        }
//        return list;
//    }


//    public List<MISBean> showAllData(String searchFromDate,String searchToDate) {
//        List<MISBean> list = new ArrayList<MISBean>();
////        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
////        searchLocationName = krutiToUnicode.convert_to_unicode(searchLocationName);
//
////        String query = " SELECT cl.city_location_id,z.zone_new_id,c.city_id,c.city_name,z.zone,cl.location,cl. remark "
////                + "FROM city_location as cl,city as c,zone_new as z where cl.zone_new_id=z.zone_new_id and z.city_id=c.city_id  "
////                + "And IF('" + searchCityName + "' = '', c.city_name LIKE '%%', c.city_name =?) "
////                + "And IF('" + searchZoneName + "' = '', z.zone LIKE '%%', z.zone =?) "
////                + "And IF('" + searchLocationName + "' = '', cl.location LIKE '%%', cl.location =?) ";
////        //+ " WHERE IF('" + searchStatusType + "' = '', status_type LIKE '%%', status_type =?) "
//        //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//        String query="select key_person_name,act,count(act) as act_count\n" +
//                          "from  traffic_police tp\n" +
//                          "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
//                          "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
//                          "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
//                          "where IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"//%m/%d/%Y               
//                          +"group by act order by key_person_name";
//            
//            
//        
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
////            pstmt.setString(1, searchCityName);
////            pstmt.setString(2, searchZoneName);
////            pstmt.setString(3, searchLocationName);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                MISBean bean = new MISBean();
//                bean.setKey_person_name(rset.getString("key_person_name"));
//                bean.setAct(rset.getString("act"));
//                bean.setAct_count(rset.getString("act_count"));
//                bean.setSearchFromDate(searchFromDate);
//                bean.setSearchToDate(searchToDate);
//                list.add(bean);
//            }
//        } catch (Exception e) {
//            System.out.println("Error in ShowData()....: " + e);
//        }
//        return list;
//    }
    
    public  List<MISBean> showAllData1(String searchOfficerName,String searchFromDate,String searchToDate) {
        
       int bean129_177_count=0,bean128_177_count=0,bean39_192_count=0,bean56_192_count=0,bean66_192_count=0,bean146_196_count=0,bean46_177_count=0,bean77_177_count=0,bean115_177_count=0,bean3_181_count=0;
        int  bean116_177_count=0,bean117_177_count=0,bean122_177_count=0,bean100_177_count=0,bean125_177_count=0,bean21_25_177_count=0,bean119_177_count=0,bean81_177_count=0;
        int bean185_count=0,bean93_8_177_count=0,others_count=0;
        int j = 0;
         int total_other_count = 0,Bean56_192_count=0,Bean66_192_count=0;
          searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
     List<MISBean> list = new ArrayList<MISBean>();
        List kpId_list = new ArrayList(); 
        int date_status=0;
        String query="";
        String query1="";
        String time=" 23:59:59";
        searchToDate=searchToDate+time;
        int username_id =MISModel.getkeypersonid((searchOfficerName));
        try {
                if(username_id==0)
                {
               
                  query = "select tp.key_person_id\n" +
                                 "from traffic_police tp\n" +
                                 "Where IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
                                +"group by tp.key_person_id\n" +
                                 "Having tp.key_person_id > 0";
                                 
                  query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
                               "from  traffic_police tp\n" +
                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
                               "where kp.key_person_id=?\n" +
                               "AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
                               
                              +" group by act";
                  
                  PreparedStatement pstmt = connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
              
                kpId_list.add(rset.getString("key_person_id"));
            }
                  
                }
                
                else{
                    
                   kpId_list.add(String.valueOf(username_id));
                query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
                               "from  traffic_police tp\n" +
                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
                               "where kp.key_person_id=?\n" +
                               "AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
                               
                              +" group by act";
                
                
                }  
                  
                  
                  if (searchFromDate.equals(searchToDate) && searchFromDate != null) {
                      date_status++;
                  query = "select tp.key_person_id\n" +
                                 " from traffic_police tp\n" +  
                                " Where tp.offence_date like '"+searchFromDate+"%'"
                                +" group by tp.key_person_id\n" +
                                 " Having tp.key_person_id > 0";
                                 
                   query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
                               "from  traffic_police tp, key_person kp\n" +
                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
                               "where kp.key_person_id=?\n" +
                               " AND tp.offence_date like '"+searchFromDate+"%'\n"+
                               " AND kp.key_person_name like '"+searchOfficerName+"%'\n"
                              +" group by act";
                   
                   
                  
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
                 
            }
                  
                  
//                  PreparedStatement pstmt = connection.prepareStatement(query);
//           
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//              
//                kpId_list.add(rset.getString("key_person_id"));
//            }
            
            PreparedStatement pstmt1 = connection.prepareStatement(query1);
       
            for(int i=0;i<kpId_list.size();i++){
                int offence_count=0;
                pstmt1.setInt(1,Integer.parseInt(kpId_list.get(i).toString()));
            ResultSet rset1 = pstmt1.executeQuery();
            MISBean bean = new MISBean();
            bean.setSearchFromDate(searchFromDate);
            bean.setSearchToDate(searchToDate.split(" ")[0]);
            bean.setKey_person_name(searchOfficerName);
            while (rset1.next()) {
                
                bean.setKey_person_name(unicodeToKruti.Convert_to_Kritidev_010(rset1.getString("key_person_name")));
//                  bean.setKey_person_name(rset1.getString("key_person_name"));
                String act=rset1.getString("act");
                String offence_code = rset1.getString("offence_code");
                //rset.getString("act_count");
                  if(offence_code.equals("34") || offence_code.equals("33")){
                      bean.setBean129_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean129_177_count=bean129_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                      
                  }
                  
                  if(offence_code.equals("32") || offence_code.equals("31")){
                      bean.setBean128_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean128_177_count=bean128_177_count+Integer.parseInt(rset1.getString("act_count"));
                          j = 1;
                  }
                   
                  if(offence_code.equals("81") || offence_code.equals("71") || offence_code.equals("72") || offence_code.equals("77") || offence_code.equals("78") || offence_code.equals("79")){
                      bean.setBean39_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean39_192_count=bean39_192_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;

                  }
                  
                  if(offence_code.equals("83") || offence_code.equals("84") || offence_code.equals("88")){
                     //bean.setBean56_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean56_192_count=bean56_192_count+Integer.parseInt(rset1.getString("act_count"));
                     bean.setBean56_192(String.valueOf(bean56_192_count));
                     j = 1;

                  }
                   
                  if(offence_code.equals("7") || offence_code.equals("8") || offence_code.equals("9") || offence_code.equals("10")){
                      //bean.setBean66_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean66_192_count=bean66_192_count+Integer.parseInt(rset1.getString("act_count"));
                      bean.setBean66_192(String.valueOf(bean66_192_count));
                      j = 1;
                  }
                  
                  if(offence_code.equals("91") || offence_code.equals("92") || offence_code.equals("94") || offence_code.equals("95") || offence_code.equals("96") || offence_code.equals("97") || offence_code.equals("98")){
                      bean.setBean146_196(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean146_196_count=bean146_196_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  
                  if(offence_code.equals("113") || offence_code.equals("114") || offence_code.equals("178") || offence_code.equals("179") || offence_code.equals("180")){
                      bean.setBean77_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean77_177_count=bean77_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  
                  if(offence_code.equals("1") || offence_code.equals("2")){
                      bean.setBean3_181(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean3_181_count=bean3_181_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                   
                  if(offence_code.equals("15") || offence_code.equals("17")){
                      bean.setBean117_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean117_177_count=bean117_177_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;
                  }
                  if(offence_code.equals("21") || offence_code.equals("22")){
                      bean.setBean122_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean122_177_count=bean122_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("115") || offence_code.equals("116")){
                      bean.setBean100_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean100_177_count=bean100_177_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;
                  }
                  if(offence_code.equals("27") || offence_code.equals("28") || offence_code.equals("131") || offence_code.equals("132")){
                      bean.setBean125_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean125_177_count=bean125_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("107") || offence_code.equals("108")){
                      bean.setBean21_25_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean21_25_177_count=bean21_25_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("16") || offence_code.equals("18")){
                      bean.setBean119_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean119_177_count=bean119_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("155") || offence_code.equals("156")){
                      bean.setBean81_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean81_177_count=bean81_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("57")){
                      bean.setBean185(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean185_count=bean185_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("133") || offence_code.equals("134")){
                      bean.setBean93_8_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean93_8_177_count=bean93_8_177_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;
                  }
                  else{
                      if(j==0){
                 
                  offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                  others_count=others_count+Integer.parseInt(rset1.getString("act_count"));   
                // bean.setOthers_count(String.valueOf(others_count));
                 bean.setOthers(String.valueOf(others_count));
                  }
                      j = 0;
                  }
            }
             Bean56_192_count = Bean56_192_count+bean56_192_count;
           bean56_192_count=0;
           Bean66_192_count = Bean66_192_count+bean66_192_count;
           bean66_192_count=0;
            total_other_count = total_other_count+others_count;
             others_count = 0;
            bean.setPerson_challan_count(offence_count+"");
            if(i == kpId_list.size()-1){
                //MISBean bean1 = new MISBean();
                int total=0;
                List<MISBean> list1 = new ArrayList<MISBean>();
            bean.setBean129_177_count(bean129_177_count+"");
            bean.setBean128_177_count(bean128_177_count+"");
            bean.setBean39_192_count(bean39_192_count+"");
            bean.setBean56_192_count(Bean56_192_count+"");
            bean.setBean66_192_count(Bean66_192_count+"");
            bean.setBean146_196_count(bean146_196_count+"");
            bean.setBean77_177_count(bean77_177_count+"");
            bean.setBean3_181_count(bean3_181_count+"");
            bean.setBean117_177_count(bean117_177_count+"");
            bean.setBean122_177_count(bean122_177_count+"");
            bean.setBean100_177_count(bean100_177_count+"");
            bean.setBean125_177_count(bean125_177_count+"");
            bean.setBean21_25_177_count(bean21_25_177_count+"");
            bean.setBean119_177_count(bean119_177_count+"");
            bean.setBean81_177_count(bean81_177_count+"");
            bean.setBean185_count(bean185_count+"");
            bean.setBean93_8_177_count(bean93_8_177_count+"");
            bean.setOthers_count(total_other_count+"");
            
            //bean.setM(list1);
            total=bean129_177_count+bean128_177_count+bean39_192_count+Bean56_192_count+Bean66_192_count+bean146_196_count+
                    bean77_177_count+bean3_181_count+bean117_177_count+bean122_177_count+bean100_177_count+bean125_177_count+bean21_25_177_count
                    +bean119_177_count+bean81_177_count+bean185_count+bean93_8_177_count+total_other_count;
            
            bean.setTotal(total);
            list1.add(bean);
            }
            list.add(bean);
            }
            
            
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
             
     }
    public List<MISBean> showData2(int lowerLimit, int noOfRowsToDisplay,String searchFromDate,String searchToDate,String searchOfficerName) {
        
        int bean129_177_count=0,bean128_177_count=0,bean39_192_count=0,bean56_192_count=0,bean66_192_count=0,bean146_196_count=0,bean46_177_count=0,bean77_177_count=0,bean115_177_count=0,bean3_181_count=0;
        int  bean116_177_count=0,bean117_177_count=0,bean122_177_count=0,bean100_177_count=0,bean125_177_count=0,bean21_25_177_count=0,bean119_177_count=0,bean81_177_count=0;
        int bean185_count=0,bean93_8_177_count=0,others_count=0;
        int j = 0;
         int total_other_count = 0,Bean56_192_count=0,Bean66_192_count=0;
          searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
     List<MISBean> list = new ArrayList<MISBean>();
        List kpId_list = new ArrayList(); 
        int date_status=0;
        String query="";
        String query1="";
        String time=" 23:59:59";
        searchToDate=searchToDate+time;
        int username_id =MISModel.getkeypersonid((searchOfficerName));
        try {
                if(username_id==0)
                {
               
                  query = "select tp.key_person_id\n" +
                                 "from traffic_police tp\n" +
                                 "Where IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
                                +"group by tp.key_person_id\n" +
                                 "Having tp.key_person_id > 0";
                                 
                  query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
                               "from  traffic_police tp\n" +
                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
                               "where kp.key_person_id=?\n" +
                               "AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
                               
                              +" group by act";
                  
                  PreparedStatement pstmt = connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
              
                kpId_list.add(rset.getString("key_person_id"));
            }
                  
                }
                
                else{
                    
                   kpId_list.add(String.valueOf(username_id));
                query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
                               "from  traffic_police tp\n" +
                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
                               "where kp.key_person_id=?\n" +
                               "AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
                               
                              +" group by act";
                
                
                }  
                  
                  
                  if (searchFromDate.equals(searchToDate) && searchFromDate != null) {
                      date_status++;
                  query = "select tp.key_person_id\n" +
                                 " from traffic_police tp\n" +  
                                " Where tp.offence_date like '"+searchFromDate+"%'"
                                +" group by tp.key_person_id\n" +
                                 " Having tp.key_person_id > 0";
                                 
                   query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
                               "from  traffic_police tp, key_person kp\n" +
                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
                               "where kp.key_person_id=?\n" +
                               " AND tp.offence_date like '"+searchFromDate+"%'\n"+
                               " AND kp.key_person_name like '"+searchOfficerName+"%'\n"
                              +" group by act";
                   
                   
                  
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
                 
            }
                  
                  
//                  PreparedStatement pstmt = connection.prepareStatement(query);
//           
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//              
//                kpId_list.add(rset.getString("key_person_id"));
//            }
            
            PreparedStatement pstmt1 = connection.prepareStatement(query1);
       
            for(int i=0;i<kpId_list.size();i++){
                int offence_count=0;
                pstmt1.setInt(1,Integer.parseInt(kpId_list.get(i).toString()));
            ResultSet rset1 = pstmt1.executeQuery();
            MISBean bean = new MISBean();
            bean.setSearchFromDate(searchFromDate);
            bean.setSearchToDate(searchToDate.split(" ")[0]);
            bean.setKey_person_name(searchOfficerName);
            while (rset1.next()) {
                
                //bean.setKey_person_name(unicodeToKruti.Convert_to_Kritidev_010(rset1.getString("key_person_name")));
                  bean.setKey_person_name(rset1.getString("key_person_name"));
                String act=rset1.getString("act");
                String offence_code = rset1.getString("offence_code");
                //rset.getString("act_count");
                  if(offence_code.equals("34") || offence_code.equals("33")){
                      bean.setBean129_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean129_177_count=bean129_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                      
                  }
                  
                  if(offence_code.equals("32") || offence_code.equals("31")){
                      bean.setBean128_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean128_177_count=bean128_177_count+Integer.parseInt(rset1.getString("act_count"));
                          j = 1;
                  }
                   
                  if(offence_code.equals("81") || offence_code.equals("71") || offence_code.equals("72") || offence_code.equals("77") || offence_code.equals("78") || offence_code.equals("79")){
                      bean.setBean39_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean39_192_count=bean39_192_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;

                  }
                  
                  if(offence_code.equals("83") || offence_code.equals("84") || offence_code.equals("88")){
                     //bean.setBean56_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean56_192_count=bean56_192_count+Integer.parseInt(rset1.getString("act_count"));
                     bean.setBean56_192(String.valueOf(bean56_192_count));
                     j = 1;

                  }
                   
                  if(offence_code.equals("7") || offence_code.equals("8") || offence_code.equals("9") || offence_code.equals("10")){
                      //bean.setBean66_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean66_192_count=bean66_192_count+Integer.parseInt(rset1.getString("act_count"));
                      bean.setBean66_192(String.valueOf(bean66_192_count));
                      j = 1;
                  }
                  
                  if(offence_code.equals("91") || offence_code.equals("92") || offence_code.equals("94") || offence_code.equals("95") || offence_code.equals("96") || offence_code.equals("97") || offence_code.equals("98")){
                      bean.setBean146_196(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean146_196_count=bean146_196_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  
                  if(offence_code.equals("113") || offence_code.equals("114") || offence_code.equals("178") || offence_code.equals("179") || offence_code.equals("180")){
                      bean.setBean77_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean77_177_count=bean77_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  
                  if(offence_code.equals("1") || offence_code.equals("2")){
                      bean.setBean3_181(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean3_181_count=bean3_181_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                   
                  if(offence_code.equals("15") || offence_code.equals("17")){
                      bean.setBean117_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean117_177_count=bean117_177_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;
                  }
                  if(offence_code.equals("21") || offence_code.equals("22")){
                      bean.setBean122_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean122_177_count=bean122_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("115") || offence_code.equals("116")){
                      bean.setBean100_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean100_177_count=bean100_177_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;
                  }
                  if(offence_code.equals("27") || offence_code.equals("28") || offence_code.equals("131") || offence_code.equals("132")){
                      bean.setBean125_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean125_177_count=bean125_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("107") || offence_code.equals("108")){
                      bean.setBean21_25_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean21_25_177_count=bean21_25_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("16") || offence_code.equals("18")){
                      bean.setBean119_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean119_177_count=bean119_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("155") || offence_code.equals("156")){
                      bean.setBean81_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean81_177_count=bean81_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("57")){
                      bean.setBean185(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean185_count=bean185_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("133") || offence_code.equals("134")){
                      bean.setBean93_8_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean93_8_177_count=bean93_8_177_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;
                  }
                  else{
                      if(j==0){
                 
                  offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                  others_count=others_count+Integer.parseInt(rset1.getString("act_count"));   
                 bean.setOthers_count(String.valueOf(others_count));
                  }
                      j = 0;
                  }
            }
           Bean56_192_count = Bean56_192_count+bean56_192_count;
           bean56_192_count=0;
           Bean66_192_count = Bean66_192_count+bean66_192_count;
           bean66_192_count=0;
            total_other_count = total_other_count+others_count;
             others_count = 0;
            bean.setPerson_challan_count(offence_count+"");
            if(i == kpId_list.size()-1){
                MISBean bean1 = new MISBean();
                int total=0;
                List<MISBean> list1 = new ArrayList<MISBean>();
            bean1.setBean129_177_count(bean129_177_count+"");
            bean1.setBean128_177_count(bean128_177_count+"");
            bean1.setBean39_192_count(bean39_192_count+"");
            bean1.setBean56_192_count(Bean56_192_count+"");
            bean1.setBean66_192_count(Bean66_192_count+"");
            bean1.setBean146_196_count(bean146_196_count+"");
            bean1.setBean77_177_count(bean77_177_count+"");
            bean1.setBean3_181_count(bean3_181_count+"");
            bean1.setBean117_177_count(bean117_177_count+"");
            bean1.setBean122_177_count(bean122_177_count+"");
            bean1.setBean100_177_count(bean100_177_count+"");
            bean1.setBean125_177_count(bean125_177_count+"");
            bean1.setBean21_25_177_count(bean21_25_177_count+"");
            bean1.setBean119_177_count(bean119_177_count+"");
            bean1.setBean81_177_count(bean81_177_count+"");
            bean1.setBean185_count(bean185_count+"");

            bean1.setBean93_8_177_count(bean93_8_177_count+"");
            bean1.setOthers_count(total_other_count+"");
            //bean.setM(list1);
            total=bean129_177_count+bean128_177_count+bean39_192_count+Bean56_192_count+Bean66_192_count+bean146_196_count+
                    bean77_177_count+bean3_181_count+bean117_177_count+bean122_177_count+bean100_177_count+bean125_177_count+bean21_25_177_count
                    +bean119_177_count+bean81_177_count+bean185_count+bean93_8_177_count+total_other_count;
            
            bean1.setTotal(total);
            list1.add(bean);
            MISController.count_list.clear();
            MISController.count_list.add(bean1);
            }
             
            list.add(bean);
            }
            
            
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
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
            System.out.println("OfficerBookModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }
    
    
     public static List<String> getOfficerSearchList(String q) {
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
     
     public static List<String> getofficerSearchList() {
       List<String> list = new ArrayList<String>();
         
        String query = "select key_person_name\n" +
                       "from key_person kp,traffic_police tp\n" +
                       
                       "group by key_person_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
          
            while (rset.next()) {    // move cursor from BOR to valid record.
                //String officer_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
                String officer_name = rset.getString("key_person_name");
                
                
                    list.add(officer_name);
                    count++;
                
            }
            if (count == 0) {
                list.add("No such officer exists.......");
            }
        } catch (Exception e) {
            System.out.println("getOfficerSearchListERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

    
    
    public static int getkeypersonid(String searchOfficerName) {
      
         int key_person_id=0;
        String query = "select key_person_id from key_person where key_person_name='" + searchOfficerName + "'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
          
            while (rset.next()) {    // move cursor from BOR to valid record.
                //String officer_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
                 key_person_id = rset.getInt("key_person_id");
                
                
                   
                    count++;
                
            }
            if (count == 0) {
               
            }
        } catch (Exception e) {
            System.out.println("getOfficerSearchListERROR inside TrafficPoliceModel - " + e);
        }
       
        return key_person_id;
    }
    
    
    
    
    
    
    
    
    
    
    

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }
    
    
    
}

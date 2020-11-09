/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.general.model;

import com.tp.general.tableClasses.TrafficPoliceSearchBean;
import com.tp.licenseVehicle.model.VehicleDetailModel;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//import net.sf.json.JSONObject;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
//import org.json.simple.JSONObject;

/**
 *
 * @author jpss
 */
public class TrafficPoliceSearchModel {
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_userName, db_password);
            System.out.println("connected - " + connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
 public int insertRecord(TrafficPoliceSearchBean trafficPoliceSearchBean, int noOfOffence, String  date_time, FileItem image_file, String image_name, String fileExt) {
        int rowsAffected = 0;
        VehicleDetailModel vdm = new VehicleDetailModel();
        /*String query = "INSERT INTO vehicle_type (vehicle_type,commercial_type, remark) VALUES (?,?,?) ";
          String revisequery = "INSERT INTO mail_records (mail_type_value,division_keyperson_id,"
                + "created_by,mail_status_id,mail_type_id,bill_month,mail_direction,mail_date_time,"
                + "type_of_transaction,description,mail_revision_no)"
                + " select ?,division_keyperson_id,created_by,mail_status_id,mail_type_id,bill_month"
                + ", mail_direction,?,type_of_transaction,description,"
                + "(Select max(mail_revision_no)+1 from mail_records where mail_type_value='' )"
                + "as A from mail_records where mail_record_id=? ";*/
        String query = "INSERT INTO traffic_police (vehicle_no, offender_name, offender_license_no, jarayam_no,deposited_amount, "
                + " book_no, reciept_no, city_location_id, offence_date, is_from_mobile, is_updated, lattitude, longitude, processing_type_id,"
                + " vehicle_no_state, vehicle_no_city_code, vehicle_no_series, vehicle_no_digits, book_revision_no) "
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, current_date(), 'Y', 'N', ?, ?, ?, ?, ?, ?, ?, ?)";
        String insert_query = "INSERT INTO traffic_offence_map(traffic_police_id,offence_type_id)"
                + "VALUES(?, ?)";
        String insert_case = "INSERT INTO case_processing (traffic_police_id, case_status_id, case_date) VALUES(?,(SELECT case_status_id FROM case_status WHERE case_status = 'Initial'),current_date())";
        int count = 0;
        try {
            //connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query);
            String vehicle_no = trafficPoliceSearchBean.getVehicle_no().trim();
            pstmt.setString(1, vehicle_no);
            vehicle_no = vdm.checkVehicleNo(vehicle_no);
            pstmt.setString(2, trafficPoliceSearchBean.getOffender_name());
            pstmt.setString(3, trafficPoliceSearchBean.getOffender_license_no());
            pstmt.setString(4, getJarayamNo());//trafficPoliceSearchBean.getJarayam_no()
            //pstmt.setString(1, trafficPoliceSearchBean.getOffence_code());
            pstmt.setInt(5, trafficPoliceSearchBean.getDeposit_amount());
            pstmt.setInt(6, trafficPoliceSearchBean.getBook_no());
            pstmt.setString(7, trafficPoliceSearchBean.getReciept_no());
            int location_id = trafficPoliceSearchBean.getCity_location_id();
            if(location_id == 0)
                pstmt.setNull(8, java.sql.Types.NULL);
            else
                pstmt.setInt(8, location_id);
            pstmt.setString(9, trafficPoliceSearchBean.getLattitude());
            pstmt.setString(10, trafficPoliceSearchBean.getLongitude());
            int processing_type = getProcessTypeId(trafficPoliceSearchBean.getProcessing_type());
            if(processing_type > 0)
                pstmt.setInt(11, processing_type);
            else
                pstmt.setNull(11, java.sql.Types.NULL);
            //pstmt.setString(6, getOfficerBookNo(trafficPoliceSearchBean.getOfficer_name()));
            //pstmt.setInt(4, trafficPoliceSearchBean.getDeposit_amount());
            //trafficPoliceSearchBean.getOffender_name();
            //trafficPoliceSearchBean.getOffender_license_no();
            //trafficPoliceSearchBean.getVehicle_no();
            String[] vehicleParts = vehicle_no.split("_");
            if(vehicleParts.length == 5){
                pstmt.setString(12, vehicleParts[1]);
                pstmt.setString(13, vehicleParts[2]);
                pstmt.setString(14, vehicleParts[3]);
                pstmt.setString(15, vehicleParts[4]);
            }else{
                pstmt.setNull(12, java.sql.Types.NULL);
                pstmt.setNull(13, java.sql.Types.NULL);
                pstmt.setNull(14, java.sql.Types.NULL);
                pstmt.setNull(15, java.sql.Types.NULL);
            }
            pstmt.setInt(16, trafficPoliceSearchBean.getBook_revision_no());
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
//                int traffic_police_id = getTraffic_police_id();
                   int traffic_police_id=0;
                ResultSet rs1 = pstmt.getGeneratedKeys();
                if(rs1.next()){
                    traffic_police_id = rs1.getInt(1);
                }
                
                if(trafficPoliceSearchBean.getProcessing_type().equals("Court")){
                        PreparedStatement pst = connection.prepareStatement(insert_case);
                        pst.setInt(1, traffic_police_id);
                        //pst.setDate(2, convertToSqlDate(trafficPoliceSearchBean.getCase_dateM()[i].toString()));
                        rowsAffected = pst.executeUpdate();
                    }
                for(int i = 0; i < noOfOffence; i++){
                    String offence_type = trafficPoliceSearchBean.getOffence_codeM()[i];//.split("_")
                    //String[] offence_type = trafficPolice.getOffence_type().split("_");
                    //int offence_type_length = offence_type.length;
                    //for (int j = 1; j < offence_type_length; j++) { //offence_type.length - 1
                        int offenceId = 0;
                        try{
                            offenceId = Integer.parseInt(offence_type);//.split("offence")[1]
                        } catch (Exception ex) {
                            System.out.println("Error: in inserting offence_no "+ (i+1) + " : " + ex);
                            offenceId = 0;
                          }
                        if(offenceId != 0){
                            PreparedStatement pstmt1 = connection.prepareStatement(insert_query);
                            pstmt1.setInt(1, traffic_police_id);
                            pstmt1.setInt(2, offenceId);  // getOffence_type_id(trafficPolice.getOffence_typeM()[j], trafficPolice.getAct_originM()[i])
                            rowsAffected = pstmt1.executeUpdate();
                            count++;
                        }
                    //}
                }
                //if(count != 0 && count <= noOfOffence){
                   //  connection.commit();
                   int general_image_detail_id=  uploadImage(image_name, date_time, image_file, traffic_police_id, fileExt);
                     //int general_image_detail_id = getgeneral_image_details_id(date_time);
                     if(general_image_detail_id != 0)
                         connection.prepareStatement("UPDATE traffic_police SET general_image_details_id = "+ general_image_detail_id + " "
                                 + " WHERE traffic_police_id = "+ traffic_police_id).executeUpdate();
                //}
                //else
                  //   connection.rollback();
            }
        } catch (Exception e) {
            System.out.println("Error: vehicle inserting: " + e);
        }
        if (rowsAffected > 0) {
            System.out.println("inserted");
            message = "Record is saved";
            msgBgColor = COLOR_OK;
        } else {
            System.out.println("not inserted");
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if(count != 0 && count <= noOfOffence);
        else{
            System.out.println("not inserted");
            message = "Cannot save the record, no offence found";
            msgBgColor = COLOR_ERROR;
        }
        //insertImageRecord(date_time, date_time);
        return rowsAffected;
    }

 public int insertImageRecord(String image_name, String date_time){
     int rowAffacted = 0;
     String query = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description) VALUES(?,2,?,'this image is for site')";
     try{
         PreparedStatement pst = connection.prepareStatement(query);
         pst.setString(1, image_name);
         pst.setString(2, date_time);
         rowAffacted = pst.executeUpdate();
         if(rowAffacted > 0){
             ResultSet rs = pst.getGeneratedKeys();
             if(rs.next()){
                 rowAffacted=rs.getInt(1);
             }
         }
     }catch(Exception ex){
         System.out.println("ERROR: in insertImageRecord() in TrafficPoliceSearchModel " + ex);
     }
     return rowAffacted;
 }

 public int uploadImage(String image_name, String date_time, FileItem image_file, int traffic_police_id, String fileExt){
     int rowAffacted = 0;
     String img_name = "";
     //String query = "INSERT INTO general_image_details (image_name, image_destination_id, date_time) VALUES(?,2,?)";
      Date dt = new Date();
     //Date time_stamp_date = new Date(timestamp);\
     DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
     String datet = dateFormat.format(dt);
     if(date_time == null || date_time.isEmpty())
         date_time = dateFormat.format(dt);

     try{
         String destination_path = getDestinationPath("traffic_police");
         String[] date = date_time.split("\\.");
         img_name = date[0] + "-" + date[1] + "-" + date[2].split("/")[0] + "_" + "tp_" + traffic_police_id + fileExt;
         destination_path = destination_path + date[2].split("/")[0] + "/" + date[1] + "/" + date[0];
         File ft = new File(destination_path);
         boolean directories = false;
         if(!ft.exists())
            directories = ft.mkdirs();
         else
             directories = true;
         if(directories == true){
             destination_path = destination_path + "/" + img_name;
             ft = new File(destination_path);
             image_file.write(ft);
             rowAffacted = insertImageRecord(img_name, date_time);
         }
     }catch(Exception ex){
         System.out.println("ERROR: in uploadImage() in TrafficPoliceSearchModel " + ex);
     }     
     return rowAffacted;
 }

 public String getImagePath(int traffic_police_id){
     String img_name = "";
     String destination_path = "";
     String query = "SELECT image_name, destination_path FROM general_image_details gid, traffic_police tp, image_destination dp "
             + " WHERE dp.image_destination_id=gid.image_destination_id "
             + " AND gid.general_image_details_id = tp.general_image_details_id "
             + " AND tp.traffic_police_id = " + traffic_police_id;
     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next()){
             img_name = rs.getString("image_name");
             destination_path = rs.getString("destination_path");
         }
         String[] img_path = img_name.split("-");
         destination_path = destination_path + img_path[2].split("_")[0] + "/" + img_path[1] + "/" + img_path[0] + "/" + img_name;
     }catch(Exception ex){
         System.out.println("ERROR: in getImageName in TrafficPoliceSearchModel : " + ex);
     }
     return destination_path;
 }
 public int getGeneralImageDetailId(int traffic_police_id){
     int general_image_detail_id = 0;
     String query = "select general_image_details_id\n" +
                    "from traffic_police tp\n" +
                    "where tp.traffic_police_id=" +traffic_police_id;
     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next()){
             general_image_detail_id = rs.getInt("general_image_details_id");
         }
         
     }catch(Exception ex){
         System.out.println("ERROR: in getImageName in TrafficPoliceSearchModel : " + ex);
     }
     return general_image_detail_id;
 }
 
 public List getExactImageName(int general_image_detail_id){
     List list = new ArrayList();
     String name="";
     String query = "select image_name\n" +
                    "from image_count ic\n" +
                    "where ic.general_image_detail_id="+general_image_detail_id +"\n"+
                    " and ic.active='Y'";
     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         while(rs.next()){
             name = rs.getString("image_name");
             list.add(name);
         }
         
     }catch(Exception ex){
         System.out.println("ERROR: in getImageName in TrafficPoliceSearchModel : " + ex);
     }
     return list;
 }
 
 public List getCompleteImagePath(int general_image_detail_id){
    String image_path = "";
    String image_name="";
    List<String> list = new ArrayList<String>();
     String query = "select image_name,image_path\n" +
                    "from image_count imc\n" +
                    "where imc.general_image_detail_id="+general_image_detail_id;
     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next()){
             image_path = rs.getString("image_path");
             image_name = rs.getString("image_name");
             //String complete_image_path =image_path+"\\"+ image_name;
             String complete_image_path =image_path;
             list.add(complete_image_path);
             
         }
         
     }catch(Exception ex){
         System.out.println("ERROR: in getCompleteImagePath() in TrafficPoliceSearchModel : " + ex);
     }
     return list;
 }
 

 /*public int getGeneralImageDetailId(String image_name, String date_time){
     int rowAffacted = 0;
     String query = "INSERT INTO general_image_details (image_name, image_destination_id, date_time) VALUES(?,2,?)";
     try{
         PreparedStatement pst = connection.prepareStatement(query);
         pst.setString(1, image_name);
         pst.setString(2, date_time);
     }catch(Exception ex){
         System.out.println("ERROR: in insertImageRecord() in TrafficPoliceSearchModel " + ex);
     }
     return rowAffacted;
 }*/

 public String getDestinationPath(String image_uploaded_for){
     String destination_path = "";
     String query = " SELECT destination_path FROM image_destination id, image_uploaded_for  iuf "
             + " WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id "
             + " AND iuf.image_uploaded_for = '" + image_uploaded_for + "' ";//traffic_police
     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next())
             destination_path = rs.getString("destination_path");
     }catch(Exception ex){
         System.out.println("ERROR: in getTrafficPoliceId in TraffiPoliceSearchModel : " + ex);
     }
     return destination_path;
 }

 public int getgeneral_image_details_id(String date_time) {
        String query;
        int general_image_details_id = 0;
        //query = "select general_image_details_id from general_image_details where key_person_id='" + id + "' ";
        query = "select general_image_details_id from general_image_details where date_time='" + date_time + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                general_image_details_id = rset.getInt("general_image_details_id");
            }
        } catch (Exception ex) {
            System.out.println("Error: TrafficPoliceSearchModel-getgeneral_image_details_id- " + ex);
        }
        return general_image_details_id;
    }

 public String getJarayamNo(){
     String jarayam_no = "";
     int no = 0;
     String query = "SELECT jarayam_no FROM traffic_police WHERE jarayam_no LIKE '%mob%' ORDER BY traffic_police_id DESC";//is_from_mobile='Y' AND is_updated='N'
     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next()){
             jarayam_no = rs.getString("jarayam_no");
         }
         if(!jarayam_no.isEmpty()){
             String[] jarayam_array = jarayam_no.split("_");
             jarayam_no = jarayam_array[1];
             if(!jarayam_no.isEmpty())
                 no = Integer.parseInt(jarayam_no);
             jarayam_no = "mob_" + (no+1);
         }
         else
             jarayam_no = "mob_" + 1;
     }catch(Exception ex){
         System.out.println("Error: in getJarayamNo " + ex );
     }
     return jarayam_no;
 }

 public int getTraffic_police_id() {
        int traffic_police_id = 0;
        try {
            String query = "SELECT MAX(traffic_police_id) as tip from traffic_police ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                traffic_police_id = rset.getInt("tip");
            }
        } catch (Exception e) {
            System.out.println("Error: in getTraffic_police_id of TrafficPoliceSearchModel" + e);
        }
        return traffic_police_id;
    }

 public int getProcessTypeId(String processing_type) {
        int processing_type_id = 0;
        try {
            String query = "SELECT processing_type_id from processing_type where processing_type='" + processing_type + "'";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                processing_type_id = rset.getInt("processing_type_id");
            }
        } catch (Exception e) {
        }
        return processing_type_id;
    }

 public String getOfficerBookNo(String officer_name_selected) {//, String book_no
        String officer_book_no = "";
        try {

            String query = "SELECT ob.book_no from officer_book AS ob,key_person as kp "
                    + "Where ob.key_person_id=kp.key_person_id"
                    //+ "AND ob.book_no=?"
                    + "AND IF('" + officer_name_selected + "'='', kp.key_person_name LIKE '%%', kp.key_person_name= ?) ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, officer_name_selected);
            //pstmt.setString(2, book_no);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                officer_book_no = rset.getString("book_no");

            }
        } catch (Exception e) {
        }
        return officer_book_no;
    }

    public int getNoOfRows(String searchOffenderLicenseNo, String searchVehicleNo) {
        int noOfRows = 0;
        try {
            String query = "SELECT count(traffic_police_id) FROM traffic_police"
                    + " WHERE IF('"+searchOffenderLicenseNo+"'='', offender_license_no LIKE '%%' OR offender_license_no IS NULL, offender_license_no=?)"
                    + "AND IF('"+searchVehicleNo+"'='', vehicle_no LIKE '%%' OR vehicle_no IS NULL, vehicle_no=?);";
           PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOffenderLicenseNo);
            pstmt.setString(2, searchVehicleNo);

            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));

        } catch (Exception e) {
            System.out.println("Error:keypersonModel-getNoOfRows-- " + e);
        }
        return noOfRows;
    }

    public List<TrafficPoliceSearchBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchOffenderLicenseNo, String searchVehicleNo) {
        List<TrafficPoliceSearchBean> list = new ArrayList<TrafficPoliceSearchBean>();
        String query;
        try {
            /*query = "SELECT * FROM traffic_police"
                    + " WHERE IF('"+searchOffenderLicenseNo+"'='', offender_license_no LIKE '%%' OR offender_license_no IS NULL, offender_license_no=?)"
                    + "AND IF('"+searchVehicleNo+"'='', vehicle_no LIKE '%%' OR vehicle_no IS NULL, vehicle_no=?);";
*/
//            query = "SELECT tp.traffic_police_id, ot.offence_type, tp.offender_license_no, tp.offence_date, tp.book_no, tp.book_revision_no, tp.lattitude, tp.longitude "
//                    + " FROM traffic_police tp, traffic_offence_map tom, offence_type ot "
//                    + " WHERE tom.traffic_police_id=tp.traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
//                    + " AND IF('"+searchOffenderLicenseNo+"'='', offender_license_no LIKE '%%' OR offender_license_no IS NULL, offender_license_no=?)"
//                    + " AND IF('"+searchVehicleNo+"'='', vehicle_no LIKE '%%' OR vehicle_no IS NULL, vehicle_no=?)";

            query = "SELECT tp.traffic_police_id, ot.offence_type, tp.offender_license_no, tp.offence_date, tp.book_no, tp.book_revision_no, tp.lattitude, tp.longitude "
                    + " FROM traffic_police tp LEFT JOIN (traffic_offence_map tom, offence_type ot) ON tom.traffic_police_id=tp.traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                    + " WHERE IF('"+searchOffenderLicenseNo+"'='', offender_license_no LIKE '%%' OR offender_license_no IS NULL, offender_license_no=?)"
                    + " AND IF('"+searchVehicleNo+"'='', vehicle_no LIKE '%%' OR vehicle_no IS NULL, vehicle_no=?)";
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOffenderLicenseNo);
            pstmt.setString(2, searchVehicleNo);


            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TrafficPoliceSearchBean trafficPoliceSearchBean = new TrafficPoliceSearchBean();
                trafficPoliceSearchBean.setTraffic_police_id(rset.getInt("traffic_police_id"));
                trafficPoliceSearchBean.setBook_no(rset.getInt("book_no"));
                trafficPoliceSearchBean.setBook_revision_no(rset.getInt("book_revision_no"));
                //trafficPoliceSearchBean.setKey_person_name(rset.getString("key_person_name"));
                //trafficPoliceSearchBean.setVehicle_type(rset.getString("vehicle_type"));
//                trafficPoliceSearchBean.setVehicle_no(rset.getString("vehicle_no"));
//                trafficPoliceSearchBean.setOffender_name(rset.getString("offender_name"));
                trafficPoliceSearchBean.setOffender_license_no(rset.getString("offender_license_no"));
                //trafficPoliceSearchBean.setOffence_place(rset.getString("location"));
                //trafficPoliceSearchBean.setAct(rset.getString("act"));
//                trafficPoliceSearchBean.setReciept_no(rset.getString("reciept_no"));
//                trafficPoliceSearchBean.setDeposited_amount(rset.getDouble("deposited_amount"));
                trafficPoliceSearchBean.setOffence_type(rset.getString("offence_type"));
                //trafficPoliceSearchBean.setPenalty_amount(rset.getString("penalty_amount"));
                trafficPoliceSearchBean.setOffence_date(rset.getString("offence_date"));
                //trafficPoliceSearchBean.setAct_origin(rset.getString("act_origin"));
                //trafficPoliceSearchBean.setCity(rset.getString("city_name"));
                //trafficPoliceSearchBean.setZone(rset.getString("zone"));
                trafficPoliceSearchBean.setLattitude(rset.getString("lattitude"));
                trafficPoliceSearchBean.setLongitude(rset.getString("longitude"));

                list.add(trafficPoliceSearchBean);
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
    }

    public List<String> getOffenderLicenseNoSearchList(String q){
        List<String> list = new ArrayList<String>();
        String query = "SELECT  offender_license_no from traffic_police "
                + " WHERE offender_license_no is not null"
                + " GROUP BY offender_license_no"
                + " ORDER BY offender_license_no ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String offender_license_no = rs.getString("offender_license_no");
                if (offender_license_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(offender_license_no);
                    count++;
                }
                //list.add(vehicle_no);
                //count++;
            }

            if (count == 0) {
                //list.add("No Offender License Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getOffenderLicenseNoSearchList ERROR inside TrafficPoliceSearchModel - " + e);
        }

        return list;
    }

    public List<String> getOfficerNameList(String q) {

        List<String> officer_name_list = new ArrayList<String>();
        int count = 0;
        try {
//            String query = " select ob.book_no ,kp.key_person_name from officer_book as ob,key_person as kp "
//                    + "WHERE kp.key_person_id=ob.key_person_id";
            String query = "select ob.book_no ,kp.key_person_name from officer_book as ob,key_person as kp, status_type as st "
                    + " WHERE kp.key_person_id=ob.key_person_id AND ob.status_type_id = st.status_type_id AND ob.book_type = 'C' AND st.status_type = 'Issue';";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            q = q.trim();
            while (rst.next()) {
                String officer_name = rst.getString("key_person_name");
                if(officer_name.toUpperCase().startsWith(q.toUpperCase())){
                    officer_name_list.add(officer_name);
                    count++;
                }
            }
            if(count == 0)
                officer_name_list.add("Officer Name not found");
        } catch (Exception e) {
        }
        return officer_name_list;
    }

     public String getOfficerName(String book_no) {
        String officer_name = "";
        int count = 0;
        try {
            String query = " select ob.book_no, kp.key_person_name, book_revision_no from officer_book as ob,key_person as kp, status_type AS st "
                    + "WHERE kp.key_person_id=ob.key_person_id AND ob.status_type_id = st.status_type_id AND ob.book_type = 'C' "
                    + " AND st.status_type = 'Issue' AND ob.book_no = " + book_no + " AND ob.active='Y'";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            //q = q.trim();
            while (rst.next()) {
                 officer_name = rst.getString("key_person_name") +"_"+ rst.getString("book_revision_no");
            }            
        } catch (Exception e) {
        }
        return officer_name;
    }

    public List<String> getOfficerBooklist(String q, String officer_name) {
        //String book_no_list = "";
        List<String> book_no_list = new ArrayList<String>();
        try {
            String query = "SELECT ob.book_no from officer_book as ob ,key_person as kp, status_type AS st "
                    + "WHERE active='Y' AND ob.key_person_id  =kp.key_person_id "
                    + " AND ob.status_type_id = st.status_type_id AND ob.book_type = 'C' AND st.status_type = 'Issue' "
                    + " AND IF('" + officer_name + "'='', key_person_name LIKE '%%' , key_person_name= ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, officer_name);
            ResultSet rset = pstmt.executeQuery();
            q = q.trim();
            while (rset.next()) {
                String book_no = rset.getString("book_no");
                if(book_no.startsWith(q))
                    book_no_list.add(book_no);
            }
        } catch (Exception e) {
        }
        return book_no_list;
    }

    public int getCity_location_id(String city_name, String zone_name, String location) {
        int city_location_id = 0;
        try {

            String query = "select city_location_id from city_location as cl,city as c,zone_new as z "
                    + "where c.city_id=z.city_id and z.zone_new_id=cl.zone_new_id "
                    + "and  c.city_name=? and z.zone=? and cl.location=? ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            pstmt.setString(2, zone_name);
            pstmt.setString(3, location);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                city_location_id = rset.getInt("city_location_id");

            }
        } catch (Exception e) {
        }
        return city_location_id;
    }

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select c.city_name from city_location as cl,city as c,zone_new as z where c.city_id=z.city_id "
                + "and z.zone_new_id=cl.zone_new_id GROUP BY c.city_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("city_name");
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
                    count++;
                }
            }
            if (count == 0)
                list.add("No such City exists.......");
        } catch (Exception e) {
            System.out.println("getCityName ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

    public List<String> getZoneName(String q, String city_name) {
        List<String> list = new ArrayList<String>();
        String query = "select z.zone from city_location as cl,city as c,zone_new as z where c.city_id=z.city_id and z.zone_new_id=cl.zone_new_id "
                + "and c.city_name='" + city_name + "' group by z.zone";
        try {
            PreparedStatement pstsmt = connection.prepareStatement(query);
            ResultSet rs = pstsmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String zone = rs.getString("zone");
                if (zone.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Zone exists.");
            }

        } catch (Exception e) {
            System.out.println("Error inside getZoneName " + e);
        }
        return list;
    }

    public List<String> getLocationName(String q, String city, String zone_name) {
        List<String> list = new ArrayList<String>();
        String query = "select cl.location from city_location as cl, city as c, zone_new as z where c.city_id = z.city_id AND z.zone_new_id=cl.zone_new_id "
                + " AND c.city_name = '"+ city +"' "
                + "and z.zone='" + zone_name + "' group by cl.location";
        try {
            PreparedStatement pstsmt = connection.prepareStatement(query);
            ResultSet rs = pstsmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String location = rs.getString("location");
                if (location.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(location);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Location exists.");
            }
        } catch (Exception e) {
            System.out.println("Error inside getLocationName " + e);
        }
        return list;
    }

    public List<String> getVehicleNoSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  vehicle_no from traffic_police WHERE vehicle_no IS NOT NULL"
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
                if (vehicle_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(vehicle_no);
                    count++;
                }
                //list.add(vehicle_no);
                //count++;
            }

            if (count == 0) {
                //list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getActSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

    public JSONObject getVehicleNoJsonList() {
        JSONObject json = new JSONObject();
        String query = "SELECT  vehicle_no, traffic_police_id from traffic_police WHERE vehicle_no IS NOT NULL"
                + " GROUP BY vehicle_no"
                + " ORDER BY vehicle_no ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            
            while (rs.next()) {
                int tp_id = rs.getInt("traffic_police_id");
                String vehicle_no = rs.getString("vehicle_no");
                json.put(tp_id, vehicle_no);
            }

            if (count == 0) {
                //list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getActSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return json;
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
                if(offence_code.startsWith(q)){
                    list.add(offence_code);
                    count++;
                }
            }

            if (count == 0) {
                list.add("Offence Code is not exist");
            }
        } catch (Exception e) {
            System.out.println(" getOffenceCodeList ERROR inside TrafficPoliceSearchModel - " + e);
        }
        return list;
    }

    public double getPenaltyAmount(String offence_code){
        double penalty_amount = 0.0;
        String query = "SELECT penalty_amount FROM offence_type WHERE offence_code='" + offence_code + "'";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next())
                penalty_amount = rs.getDouble("penalty_amount");
        }catch(Exception ex){
            System.out.println("ERROR: getPenaltyAmount in TrafficPoliceSearchModel : " + ex);
        }
        return penalty_amount;
    }



    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("keypersonModel closeConnection() Error: " + e);
        }
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

    public String getDb_userName() {
        return db_userName;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public String getDb_userPasswrod() {
        return db_password;
    }

    public void setDb_userPasswrod(String db_password) {
        this.db_password = db_password;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

}

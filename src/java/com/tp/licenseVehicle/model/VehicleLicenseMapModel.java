/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.licenseVehicle.model;

import java.sql.Connection;
import com.tp.licenseVehicle.tableClasses.VehicleLicenseMapBean;
import java.io.ByteArrayOutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author JPSS
 */
public class VehicleLicenseMapModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            System.out.println("connected - " + connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (Exception e) {
            System.out.println("VehicleLicenseMapModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }
    public ByteArrayOutputStream generateLicenceXlsRecordList(String jrxmlFilePath,List list) {
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
              //  HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("LicenceVehicleStatusModel ggenerateLicenceXlsRecordList() JRException: " + e);
                }
                return bytArray;
            }

    public int insertRecord(VehicleLicenseMapBean vehicleLicenseMapBean) {
        int rowsAffected = 0;
        int vehicle_license_map_id = vehicleLicenseMapBean.getVehicle_license_map_id();
        int vehicle_license_map_revision_no = vehicleLicenseMapBean.getVehicle_license_map_revision_no();
        String query = "";
        if(vehicle_license_map_id > 0)
            query = query = "INSERT INTO vehicle_license_map (vehicle_detail_id, license_detail_id, remark, vehicle_license_map_id, vehicle_license_map_revision_no) "
                + " VALUES (?,?,?,"+ vehicle_license_map_id +", "+ (vehicle_license_map_revision_no + 1) +") ";
        else
         query = "INSERT INTO vehicle_license_map (vehicle_detail_id, license_detail_id, remark) "
                + " VALUES (?,?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, getVehicleDetailId(vehicleLicenseMapBean.getVehicle_no()));
            pstmt.setInt(2, getLicenseDetailId(vehicleLicenseMapBean.getLicense_no()));
            pstmt.setString(3, vehicleLicenseMapBean.getRemark());

            rowsAffected = pstmt.executeUpdate();
        } 
        catch (Exception e) {
            System.out.println("Error: insertRecord in VehicleLicenseMapModel: " + e);
        }
        if (rowsAffected > 0) {

            message = "Record saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
            message = "Record not saved Some Error! ";
            msgBgColor = COLOR_ERROR;

            System.out.println("not inserted");
        }
        return rowsAffected;
    }

    public int reviseRecord(VehicleLicenseMapBean vehicleLicenseMapBean) {
        int rowsAffected = 0;        
        try {            
            rowsAffected = insertRecord(vehicleLicenseMapBean);
            if(rowsAffected > 0)
                rowsAffected = cancelRecord("" + vehicleLicenseMapBean.getVehicle_license_map_id(), "" + vehicleLicenseMapBean.getVehicle_license_map_revision_no());
        } catch (Exception e) {
            System.out.println("Error: RevisetRecord in VehicleLicenseMapModel: " + e);
        }
        if (rowsAffected > 0) {

            message = "Record Revised successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("revised");
        } else {
            message = "Record not Revised Some Error!";
            msgBgColor = COLOR_ERROR;

            System.out.println("not revised");
        }
        return rowsAffected;
    }
   
    public List<VehicleLicenseMapBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchLicenseNo, String searchVehicleNo, String searchFromDate, String searchToDate) {
        List<VehicleLicenseMapBean> list = new ArrayList<VehicleLicenseMapBean>();
        if (!searchFromDate.equals("")) {
            String da1[] = searchFromDate.split("-");
            String da2[] = searchToDate.split("-");
            searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
            searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
            System.out.println(searchFromDate);
            System.out.println(searchToDate);
        }
        
        String query = "SELECT vlm.vehicle_license_map_id, vlm.vehicle_license_map_revision_no, vd.vehicle_no, ld.license_no, vlm.remark, vlm.active "
                + " FROM vehicle_license_map vlm, vehicle_detail vd, license_detail ld "
                + " WHERE vd.vehicle_detail_id=vlm.vehicle_detail_id "
                + " AND ld.license_detail_id=vlm.license_detail_id "
                + " AND vd.active='Y' AND vlm.active='Y' "
                + " AND IF('"+ searchVehicleNo +"'='', vd.vehicle_no LIKE '%%', vd.vehicle_no = '" + searchVehicleNo + "') "
                + " AND IF('"+ searchLicenseNo +"'='', ld.license_no LIKE '%%', ld.license_no = '" + searchLicenseNo + "') "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);           

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                VehicleLicenseMapBean vehicleLicenseMapBean = new VehicleLicenseMapBean();
                
                vehicleLicenseMapBean.setVehicle_license_map_id(rset.getInt("vehicle_license_map_id"));
                vehicleLicenseMapBean.setVehicle_license_map_revision_no(rset.getInt("vehicle_license_map_revision_no"));
                vehicleLicenseMapBean.setVehicle_no(rset.getString("vehicle_no"));
                vehicleLicenseMapBean.setLicense_no(rset.getString("license_no"));
                vehicleLicenseMapBean.setRemark(rset.getString("remark"));
                vehicleLicenseMapBean.setActive(rset.getString("active"));
                list.add(vehicleLicenseMapBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public List<VehicleLicenseMapBean> showAllData(String searchLicenseNo) {
        List<VehicleLicenseMapBean> list = new ArrayList<VehicleLicenseMapBean>();        
       String query = "SELECT vlm.vehicle_license_map_id, vlm.vehicle_license_map_revision_no, vd.vehicle_no, ld.license_no, vlm.remark, vlm.active "
                + " FROM vehicle_license_map vlm, vehicle_detail vd, license_detail ld "
                + " WHERE vd.vehicle_detail_id=vlm.vehicle_detail_id "
                + " AND ld.license_detail_id=vlm.license_detail_id "
                + " AND vd.active='Y' AND vlm.active='Y' "
                + " AND IF('"+ searchLicenseNo +"'='', ld.license_no LIKE '%%', ld.license_no = '" + searchLicenseNo + "')";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                VehicleLicenseMapBean vehicleLicenseMapBean = new VehicleLicenseMapBean();

                vehicleLicenseMapBean.setVehicle_license_map_id(rset.getInt("vehicle_license_map_id"));
                vehicleLicenseMapBean.setVehicle_license_map_revision_no(rset.getInt("vehicle_license_map_revision_no"));
                vehicleLicenseMapBean.setVehicle_no(rset.getString("vehicle_no"));
                vehicleLicenseMapBean.setLicense_no(rset.getString("license_no"));
                vehicleLicenseMapBean.setRemark(rset.getString("remark"));
                vehicleLicenseMapBean.setActive(rset.getString("active"));
                list.add(vehicleLicenseMapBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchLicenseNo, String searchVehicleNo,  String searchFromDate, String searchToDate) {
        int noOfRows = 0;
        try {
            if (!searchFromDate.equals("")) {
                String da1[] = searchFromDate.split("-");
                String da2[] = searchToDate.split("-");
                searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }            
            String query = "SELECT count(vlm.vehicle_license_map_id) "
                + " FROM vehicle_license_map vlm, vehicle_detail vd, license_detail ld "
                + " WHERE vd.vehicle_detail_id=vlm.vehicle_detail_id "
                + " AND ld.license_detail_id=vlm.license_detail_id "
                + " AND vd.active='Y' AND vlm.active='Y' "
                + " AND IF('"+ searchVehicleNo +"'='', vd.vehicle_no LIKE '%%', vd.vehicle_no = '" + searchVehicleNo + "') "
                + " AND IF('"+ searchLicenseNo +"'='', ld.license_no LIKE '%%', ld.license_no = '" + searchLicenseNo + "') ";

            PreparedStatement stmt = connection.prepareStatement(query);          

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows in VehicleLicenseMapModel " + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public int cancelRecord(String vehicle_license_map_id, String revision) {
        String query = "Update vehicle_license_map SET active='N' WHERE vehicle_license_map_id=" + vehicle_license_map_id +" AND vehicle_license_map_revision_no=" + revision;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:VehicleLicenseMapModel-cancelRecord-- " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Canceled successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot Canceled the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int getVehicleDetailId(String vehicle_no) {
        int vdehicle_detail_id = 0;
        try {
            String query = "Select vehicle_detail_id from vehicle_detail where active='Y' AND vehicle_no='" + vehicle_no + "'";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                vdehicle_detail_id = rset.getInt("vehicle_detail_id");
            }
        } catch (Exception e) {
            System.out.println("Error:VehicleLicenseMapModel-getVehicleDetailId-- " + e);
        }
        return vdehicle_detail_id;
    }

    public int getLicenseDetailId(String license_no) {
        int license_detail_id = 0;
        try {
            String query = "Select license_detail_id from license_detail where license_no='" + license_no + "'";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                license_detail_id = rset.getInt("license_detail_id");
            }
        } catch (Exception e) {
            System.out.println("Error:VehicleLicenseMapModel-getLicenseDetailId-- " + e);
        }
        return license_detail_id;
    }

    public Map<Integer, String> getVehicleTypeList() {
        Map<Integer, String> vehicleList = new HashMap<Integer, String>();
        try {
            String query = "Select vehicle_type_id, vehicle_type from vehicle_type";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                int vehicle_id = rset.getInt("vehicle_type_id");
                String vehicle_type = rset.getString("vehicle_type");
                vehicleList.put(vehicle_id, vehicle_type);
            }
        } catch (Exception e) {
        }
        return vehicleList;
    }

    public List<String> getVehicleNoList(String q){
        List<String> list = new ArrayList<String>();
        String query = "SELECT vehicle_no FROM vehicle_detail WHERE active='Y' GROUP BY vehicle_no ORDER BY vehicle_no";
        int count = 0;
        try{
            q = q.trim();
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                String vehicle_no = rs.getString("vehicle_no");
                if(vehicle_no.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(vehicle_no);
                    count++;
                }
            }
            if(count == 0)
                list.add("Vehicle No. not found");
        }catch(Exception ex){
        }
        return list;
    }

     public List<String> getLicenseNoList(String q){
        List<String> list = new ArrayList<String>();
        String query = "SELECT license_no FROM license_detail GROUP BY license_no ORDER BY license_no";
        int count = 0;
        try{
            q = q.trim();
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                String license_no = rs.getString("license_no");
                if(license_no.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(license_no);
                    count++;
                }
            }
            if(count == 0)
                list.add("License No. not found");
        }catch(Exception ex){
        }
        return list;
    }

    public List<String> checkVehicleNo(String vehicle_no){
       List<String> results = new ArrayList<String>();
       String searchVehicleNo = vehicle_no;
       Pattern pattern1 = Pattern.compile("/^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$/");
       Pattern pattern2 = Pattern.compile("/^[a-z]{2}[ -][0-9]{1,2}(?: [a-z])?(?: [a-z]*)? [0-9]{4}$/");
       Pattern pattern3 = Pattern.compile("/^[a-z]{2}[0-9]{1,2}(?:[a-z])?(?:[a-z]*)?[0-9]{4}$/");
       Pattern pattern4 = Pattern.compile("/^[A-Z]{2}[0-9]{1,2}(?:[A-Z])?(?:[A-Z]*)?[0-9]{4}$/");
       Matcher match = pattern3.matcher(vehicle_no);
       if(!match.find()){
           match = pattern4.matcher(vehicle_no);
           if(!match.find()){
               match = pattern1.matcher(vehicle_no);
               if(!match.find()){
                   match = pattern2.matcher(vehicle_no);
                   if(!match.find()){

                   }else{
                       while(match.find())
                           results.add(match.group());
                   }
               }else{
                   while(match.find())
                       results.add(match.group());
               }
           }else{
               while(match.find())
                   results.add(match.group());
           }
       }else{
           while(match.find())
               results.add(match.group());
       }
       return results;
   }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("VehicleLicenseMapModel closeConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
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

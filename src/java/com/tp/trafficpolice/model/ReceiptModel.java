/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import com.tp.licenseVehicle.model.*;
import java.sql.Connection;
import com.tp.licenseVehicle.tableClasses.LicenseDetailBean;
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
public class ReceiptModel {

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
            System.out.println("LicenseDetailModel generatReport() JRException: " + e);
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
                    System.out.println("LicenceStatusModel  generateLicenceXlsRecordList() JRException: " + e);
                }
                return bytArray;
            }
   

    public int insertRecord(LicenseDetailBean licenseDetailBean) {
        int rowsAffected = 0;

        String query = "INSERT INTO license_detail (license_no,owner_name,owner_age,address,issue_date,validity,is_permanent,vehicle_type_id,remark) "
                + " VALUES (?,?,?,?,?,?,?,?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, licenseDetailBean.getLicense_no());
            pstmt.setString(2, licenseDetailBean.getOwner_name());
            pstmt.setInt(3, licenseDetailBean.getOwner_age());
            pstmt.setString(4, licenseDetailBean.getAddress());
            pstmt.setDate(5, convertToSqlDate(licenseDetailBean.getIssue_date()));
            pstmt.setDate(6, convertToSqlDate(licenseDetailBean.getValidity()));
            pstmt.setString(7, licenseDetailBean.getIs_permanent());
            pstmt.setInt(8, licenseDetailBean.getVehicle_type_id());
            pstmt.setString(9, licenseDetailBean.getRemark());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: insertRecord in LicenseDetailModel: " + e);
        }
        if (rowsAffected > 0) {

            message = "Record saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
            message = "Record not saved successfully";
            msgBgColor = COLOR_ERROR;

            System.out.println("not inserted");
        }
        return rowsAffected;
    }

    public int updateRecord(LicenseDetailBean licenseDetailBean) {
        int rowsAffected = 0;
        String query = "UPDATE license_detail SET license_no=?,owner_name=?,owner_age=?,address=?,issue_date=?,validity=?,is_permanent=?,vehicle_type_id=?,remark=? "
                + " WHERE license_detail_id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, licenseDetailBean.getLicense_no());
            pstmt.setString(2, licenseDetailBean.getOwner_name());
            pstmt.setInt(3, licenseDetailBean.getOwner_age());
            pstmt.setString(4, licenseDetailBean.getAddress());
            pstmt.setDate(5, convertToSqlDate(licenseDetailBean.getIssue_date()));
            pstmt.setDate(6, convertToSqlDate(licenseDetailBean.getValidity()));
            pstmt.setString(7, licenseDetailBean.getIs_permanent());
            pstmt.setInt(8, licenseDetailBean.getVehicle_type_id());
            pstmt.setString(9, licenseDetailBean.getRemark());
            pstmt.setInt(10, licenseDetailBean.getLicense_detail_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: insertRecord in LicenseDetailModel: " + e);
        }
        if (rowsAffected > 0) {

            message = "Record Update successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
            message = "Record not Update Some Error!";
            msgBgColor = COLOR_ERROR;

            System.out.println("not inserted");
        }
        return rowsAffected;
    }
 
    public List<LicenseDetailBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchOfficeCode, String searchOfficeName, String searchLicenseNo, String searchFromDate, String searchToDate) {
        List<LicenseDetailBean> list = new ArrayList<LicenseDetailBean>();
        if (!searchFromDate.equals("")) {
            String da1[] = searchFromDate.split("-");
            String da2[] = searchToDate.split("-");
            searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
            searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
            System.out.println(searchFromDate);
            System.out.println(searchToDate);
        }        
        String query = "SELECT ld.license_detail_id, ld.license_no, ld.owner_name, ld.owner_age, ld.city, "
                + " ld.address, ld.issue_date, ld.validity, ld.is_permanent, ld.remark, vt.vehicle_type "
                + " FROM license_detail ld, vehicle_type vt "
                + " WHERE vt.vehicle_type_id=ld.vehicle_type_id "
                + " AND IF('"+ searchLicenseNo +"'='', ld.license_no LIKE '%%', ld.license_no = '" + searchLicenseNo + "') "
                
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);            
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                LicenseDetailBean licenseDetailBean = new LicenseDetailBean();
                licenseDetailBean.setLicense_detail_id(rset.getInt("license_detail_id"));
                licenseDetailBean.setLicense_no(rset.getString("license_no"));
                licenseDetailBean.setOwner_name(rset.getString("owner_name"));
                licenseDetailBean.setOwner_age(rset.getInt("owner_age"));
                licenseDetailBean.setAddress(rset.getString("address"));
                licenseDetailBean.setIssue_date(rset.getString("issue_date"));
                licenseDetailBean.setValidity(rset.getString("validity"));
                licenseDetailBean.setIs_permanent(rset.getString("is_permanent"));
                licenseDetailBean.setVehicle_type(rset.getString("vehicle_type"));
                licenseDetailBean.setRemark(rset.getString("remark"));
                list.add(licenseDetailBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public List<LicenseDetailBean> showAllData(String searchLicenseNo) {
        List<LicenseDetailBean> list = new ArrayList<LicenseDetailBean>();        
        String query = "SELECT ld.license_detail_id, ld.license_no, ld.owner_name, ld.owner_age, ld.city, "
                + " ld.address, ld.issue_date, ld.validity, ld.is_permanent, ld.remark, vt.vehicle_type "
                + " FROM license_detail ld, vehicle_type vt "
                + " WHERE vt.vehicle_type_id=ld.vehicle_type_id "
                + " AND IF('"+ searchLicenseNo +"'='', ld.license_no LIKE '%%', ld.license_no = '" + searchLicenseNo + "') ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                LicenseDetailBean licenseDetailBean = new LicenseDetailBean();
                licenseDetailBean.setLicense_detail_id(rset.getInt("license_detail_id"));
                licenseDetailBean.setLicense_no(rset.getString("license_no"));
                licenseDetailBean.setOwner_name(rset.getString("owner_name"));
                licenseDetailBean.setOwner_age(rset.getInt("owner_age"));
                licenseDetailBean.setAddress(rset.getString("address"));
                licenseDetailBean.setIssue_date(rset.getString("issue_date"));
                licenseDetailBean.setValidity(rset.getString("validity"));
                licenseDetailBean.setIs_permanent(rset.getString("is_permanent"));
                licenseDetailBean.setVehicle_type(rset.getString("vehicle_type"));
                licenseDetailBean.setRemark(rset.getString("remark"));
                list.add(licenseDetailBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchOfficeName,String searchOfficeCode, String searchLicenseNo,  String searchFromDate, String searchToDate) {
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
            String query = "SELECT count(ld.license_detail_id) FROM license_detail ld, vehicle_type vt "
                    + "WHERE vt.vehicle_type_id=ld.vehicle_type_id"
                    + " AND IF('"+ searchLicenseNo +"'='', ld.license_no LIKE '%%', ld.license_no = '" + searchLicenseNo + "') ";

            PreparedStatement stmt = connection.prepareStatement(query);          

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows in LicenseDetailModel " + e);
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

    public int deleteRecord(String license_detail_id) {
        String query = "DELETE FROM license_detail WHERE license_detail_id=" + license_detail_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:LicenseDetailModel-deleteRecord-- " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int getVehicle_type_id(String vehicle_type) {
        int model_id = 0;
        try {

            String query = "Select vehicle_type_id from vehicle_type where vehicle_type='" + vehicle_type + "'  ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                model_id = rset.getInt("vehicle_type_id");

            }
        } catch (Exception e) {
        }
        return model_id;
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

    public List<String> getLicenseNoList(String q){
        List<String> list = new ArrayList<String>();
        String query = "SELECT license_no FROM license_detail";
        int count = 0;
        try{
            q = q.trim();
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                String license_no = rs.getString("license_no");
                if(license_no.startsWith(q)){
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("LicenseDetailModel closeConnection() Error: " + e);
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

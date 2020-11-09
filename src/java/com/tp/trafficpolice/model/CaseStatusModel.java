/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import com.tp.trafficpolice.model.*;
import com.tp.tableClasses.CaseStatus;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
//import net.sf.json.JSON;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class CaseStatusModel {

    private Connection connection;
   
   // private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;

    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    
 public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= (Connection) DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8",db_username,db_password);
            System.out.println("connected - "+connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("QueryTypeModel closeConnection() Error: " + e);
        }
    }

  public List<String> getCaseStatus(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT case_status_id, case_status FROM case_status GROUP BY case_status ORDER BY case_status";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String make = rset.getString("case_status");
                if (make.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(make);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getmakeType ERROR inside StausTypeModel - " + e);
        }
        return list;
    }

public JSONArray getCaseStatusJsonList() {
        List<String> list = new ArrayList<String>();
        JSONObject jsnObj = new JSONObject();
        
        JSONArray jarray = new JSONArray();
        Map m = new HashMap();
        String query = " SELECT case_status_id, case_status FROM case_status GROUP BY case_status ORDER BY case_status";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();            
            while (rset.next()) {    // move cursor from BOR to valid record.
                String make = rset.getString("case_status");
                JSONObject jsn = new JSONObject();
                jsn.put("label", make);
                jsn.put("value", make);
                jsnObj.put(jsn, "");
                //m.put(make, make);
                jarray.add(jsn);
                    //list.add(make); 
            }
            //jsn.putAll(m);
        } catch (Exception e) {
            System.out.println("getmakeType ERROR inside StausTypeModel - " + e);
        }
        return jarray;
    }

    public byte[] generateRecordList(String jrxmlFilePath,List list) {
                byte[] reportInbytes = null;
                HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
                } catch (Exception e) {
                    System.out.println("OfficerBookModel generatReport() JRException: " + e);
                }
                return reportInbytes;
            }

    public ByteArrayOutputStream generateExcelList(String jrxmlFilePath,List list) {
                ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
                HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("OfficerBookModel generatReport() JRException: " + e);
                }
                return reportInbytes;
            }

    public List<CaseStatus> showAllData() {
        List<CaseStatus> list = new ArrayList<CaseStatus>();
        PreparedStatement pstmt = null;
        String query;
        query = " SELECT case_status_id, case_status, remark FROM case_status order by case_status ";
             // + " WHERE IF('" + searchCaseStatus + "' = '', case_status LIKE '%%', case_status =?) ";
             //   + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CaseStatus querTypeDetail = new CaseStatus();
                querTypeDetail.setCase_status_id(rset.getInt("case_status_id"));
                querTypeDetail.setCase_status(rset.getString("case_status"));
                querTypeDetail.setDescription(rset.getString("remark"));
                list.add(querTypeDetail);
            }
        } catch (Exception e) {
            System.out.println("CommercialTypeModel showData() Error: " + e);
        }
        return list;
    }


    public int getNoOfRows(String searchCaseStatus) {
        int noOfRows = 0;
        String query = "SELECT count(*) FROM case_status  WHERE IF('" + searchCaseStatus + "' = '', case_status LIKE '%%', case_status ='" + searchCaseStatus + "') ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("CommercialTypeModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<CaseStatus> showData(int lowerLimit, int noOfRowsToDisplay,String searchCaseStatus) {
        List<CaseStatus> list = new ArrayList<CaseStatus>();
        PreparedStatement pstmt = null;
        String query;
        query = " SELECT case_status_id, case_status, remark FROM case_status  "
              + " WHERE IF('" + searchCaseStatus + "' = '', case_status LIKE '%%', case_status ='" + searchCaseStatus + "') "
                + " order by case_status  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CaseStatus querTypeDetail = new CaseStatus();
                querTypeDetail.setQuery_type_id(rset.getInt("case_status_id"));
                querTypeDetail.setQuery_type(rset.getString("case_status"));
                querTypeDetail.setDescription(rset.getString("remark"));
                list.add(querTypeDetail);
            }
        } catch (Exception e) {
            System.out.println("CaseStatusModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(CaseStatus commercialType) {
        String query = "INSERT INTO case_status(case_status ,remark) "
                + " VALUES(?, ?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            String[] query_type_list_List = commercialType.getQuery_typeM();
            for (int i = 0; i < query_type_list_List.length; i++) {
            if (commercialType.getQuery_type_idM()[i].equals("1")) {
            pstmt.setString(1, commercialType.getQuery_typeM()[i]);
            pstmt.setString(2, commercialType.getDescriptionM()[i]);
         //   pstmt.setInt(3, ctreat_by);
            rowsAffected = pstmt.executeUpdate();
                }
                }
          } catch (Exception e) {
            System.out.println("commercialTypeModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(CaseStatus commercialType) {
        String query = " UPDATE case_status  SET case_status =? , remark = ? "
                + " WHERE case_status_id= ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, commercialType.getQuery_typeM()[0]);
            pstmt.setString(2, commercialType.getDescriptionM()[0]);
            pstmt.setInt(3, commercialType.getQuery_type_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("CommercialTypeModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int deleteRecord(int commercial_type_id) {
        String query = " DELETE FROM case_status WHERE case_status_id = " + commercial_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Model deleteRecord() Error: " + e);
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

}

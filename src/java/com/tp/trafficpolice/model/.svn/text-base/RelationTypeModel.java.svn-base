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
 * @author Administrator
 */
public class RelationTypeModel {

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
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    
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

  public List<String> getRelationtype(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT relation_type_id, relation_type FROM relation_type GROUP BY relation_type ORDER BY relation_type";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String make = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("relation_type"));
                if (make.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(make);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getRelationtype ERROR inside RelationTypeModel - " + e);
        }
        return list;
    }

    public byte[] generateRecordList(String jrxmlFilePath,List list) {
                byte[] reportInbytes = null;
                HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
                } catch (Exception e) {
                    System.out.println("RelationTypeModel generatReport() JRException: " + e);
                }
                return reportInbytes;
            }

    public ByteArrayOutputStream generateExcelList(String jrxmlFilePath, List list) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
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
        query = " SELECT relation_type_id, relation_type, remark FROM relation_type order by relation_type ";
             // + " WHERE IF('" + searchCaseStatus + "' = '', relation_type LIKE '%%', relation_type =?) ";
             //   + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CaseStatus querTypeDetail = new CaseStatus();
                querTypeDetail.setCase_status_id(rset.getInt("relation_type_id"));
                querTypeDetail.setCase_status(rset.getString("relation_type"));
                querTypeDetail.setDescription(rset.getString("remark"));
                list.add(querTypeDetail);
            }
        } catch (Exception e) {
            System.out.println("RelationTypeModel showData() Error: " + e);
        }
        return list;
    }


    public int getNoOfRows(String searchRelationType) {
        int noOfRows = 0;
        searchRelationType = krutiToUnicode.convert_to_unicode(searchRelationType);
        String query = "SELECT count(*) FROM relation_type  WHERE IF('" + searchRelationType + "' = '', relation_type LIKE '%%', relation_type ='" + searchRelationType + "') ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("RelationTypeModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<CaseStatus> showData(int lowerLimit, int noOfRowsToDisplay,String searchRelationType) {
        List<CaseStatus> list = new ArrayList<CaseStatus>();
        searchRelationType = krutiToUnicode.convert_to_unicode(searchRelationType);
        PreparedStatement pstmt = null;
        String query;
        query = " SELECT relation_type_id, relation_type, remark FROM relation_type  "
              + " WHERE IF('" + searchRelationType + "' = '', relation_type LIKE '%%', relation_type ='" + searchRelationType + "') "
                + " order by relation_type  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CaseStatus querTypeDetail = new CaseStatus();
                querTypeDetail.setQuery_type_id(rset.getInt("relation_type_id"));
                querTypeDetail.setQuery_type(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("relation_type")));
                querTypeDetail.setDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("remark")));
                list.add(querTypeDetail);
            }
        } catch (Exception e) {
            System.out.println("RelationTypeModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(CaseStatus commercialType) {
        String query = "INSERT INTO relation_type(relation_type ,remark) "
                + " VALUES(?, ?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            String[] query_type_list_List = commercialType.getQuery_typeM();
            for (int i = 0; i < query_type_list_List.length; i++) {
            if (commercialType.getQuery_type_idM()[i].equals("1")) {
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(commercialType.getQuery_typeM()[i]));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(commercialType.getDescriptionM()[i]));
         //   pstmt.setInt(3, ctreat_by);
            rowsAffected = pstmt.executeUpdate();
                }
                }
          } catch (Exception e) {
            System.out.println("RelationTypeModel insertRecord() Error: " + e);
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
        String query = " UPDATE relation_type  SET relation_type =? , remark = ? "
                + " WHERE relation_type_id= ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(commercialType.getQuery_typeM()[0]));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(commercialType.getDescriptionM()[0]));
            pstmt.setInt(3, commercialType.getQuery_type_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("RelationTypeModel updateRecord() Error: " + e);
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

    public int deleteRecord(int relation_type_id) {
        String query = " DELETE FROM relation_type WHERE relation_type_id = " + relation_type_id;
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

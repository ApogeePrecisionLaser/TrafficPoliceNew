/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import com.tp.trafficpolice.model.*;
import com.tp.tableClasses.CommercialType;
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
public class CommercialTypeModel {

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

    public int getNoOfRows() {
        int noOfRows = 0;
        String query = "SELECT count(*) FROM commercial_type  ";
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
 public List<CommercialType> showAllData(String searchCommercial) {
        List<CommercialType> list = new ArrayList<CommercialType>();
        PreparedStatement pstmt = null;
        String query;
        query = " SELECT commercial_type_id, commercial_type, remark FROM commercial_type "
                +"WHERE IF('"+searchCommercial +"'='',commercial_type LIKE '%%',commercial_type=?)";

        try {
               pstmt = connection.prepareStatement(query);
                  pstmt.setString(1, searchCommercial);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CommercialType querTypeDetail = new CommercialType();
                querTypeDetail.setQuery_type_id(rset.getInt("commercial_type_id"));
                querTypeDetail.setQuery_type(rset.getString("commercial_type"));
                querTypeDetail.setDescription(rset.getString("remark"));
                list.add(querTypeDetail);
            }
        } catch (Exception e) {
            System.out.println("CommercialTypeModel showAllData() Error: " + e);
        }
        return list;
    }
    public List<CommercialType> showData(int lowerLimit, int noOfRowsToDisplay,String searchCommercial) {
        List<CommercialType> list = new ArrayList<CommercialType>();
        PreparedStatement pstmt = null;
        String query;
        query = " SELECT commercial_type_id, commercial_type, remark FROM commercial_type "
                +"WHERE IF('"+searchCommercial +"'='',commercial_type LIKE '%%',commercial_type=?) order by commercial_type"
               + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
               pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, searchCommercial);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CommercialType querTypeDetail = new CommercialType();
                querTypeDetail.setQuery_type_id(rset.getInt("commercial_type_id"));
                querTypeDetail.setQuery_type(rset.getString("commercial_type"));
                querTypeDetail.setDescription(rset.getString("remark"));
                list.add(querTypeDetail);
            }
        } catch (Exception e) {
            System.out.println("CommercialTypeModel showData() Error: " + e);
        }
        return list;
    }
    public List<String> getCommercial(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT commercial_type FROM commercial_type order by commercial_type  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String commercial_type = rset.getString("commercial_type");
                if (commercial_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(commercial_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Commercial Type exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCommercial ERROR inside CommercialModel - " + e);
        }
        return list;
    }

    public int insertRecord(CommercialType commercialType) {
        String query = "INSERT INTO commercial_type(commercial_type ,remark) "
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

    public int updateRecord(CommercialType commercialType) {
        String query = " UPDATE commercial_type  SET commercial_type =? , remark = ? "
                + " WHERE commercial_type_id = ? ";
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
        String query = " DELETE FROM commercial_type WHERE commercial_type_id = " + commercial_type_id;
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
public byte[] generateMapReport(String jrxmlFilePath,List listAll,String Ctype) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
             mymap.put("CommType", Ctype);
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in CommercialModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
     public ByteArrayOutputStream generateCityXlsRecordList(String jrxmlFilePath,List list,String Ctype) {
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
              HashMap mymap = new HashMap();
                try {
                    mymap.put("CommType", Ctype);
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("CommercialStatusModel  generateCityXlsRecordList() JRException: " + e);
                }
                return bytArray;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bms.location.model;

import com.bms.location.tableClasses.DutyLocationTypeBean;
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
 * @author Shobha
 */
public class DutyLocationTypeModel {
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
       private String msgBgColor;

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
    private final String COLOR_OK = "yellow";
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
      public byte[] generateRecordList(String jrxmlFilePath,List list) {
                byte[] reportInbytes = null;
                HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);                    
                } catch (Exception e) {
                    System.out.println("StatusTypeModel generatRecordList() JRException: " + e);
                }
                return reportInbytes;
            }

      public ByteArrayOutputStream generateExcelList(String jrxmlFilePath,List list) {                
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
                HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("StatusTypeModel generateExcelList() JRException: " + e);
                }
                return bytArray;
            }

        public int deleteRecord(int status_type_id) {
            String query = " DELETE FROM location_type WHERE location_type_id = " +status_type_id;
            int rowsAffected = 0;
            try {
                rowsAffected = connection.prepareStatement(query).executeUpdate();
            } catch (Exception e) {
                System.out.println("StatusType Model deleteRecord() Error: " + e);
            }
            if (rowsAffected > 0) {

                message = "Record deleted successfully......";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot delete the record, it is used in another GUI.";
                msgBgColor = COLOR_ERROR;
            }
            return rowsAffected;
        }
            public int updateRecord(DutyLocationTypeBean statusTypeBean) {
                String query = " UPDATE location_type SET  location_type_name=?, remark=? WHERE location_type_id = ? ";
                int rowsAffected = 0;
                try {
                    PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

                    pstmt.setString(1, statusTypeBean.getStatus_type());
                    pstmt.setString(2, statusTypeBean.getRemark());
                     pstmt.setInt(3, statusTypeBean.getStatus_type_id());
                    rowsAffected = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("AreaModel updateRecord() Error: " + e);
                }
                if (rowsAffected > 0) {
                    message = "Record updated successfully......";
                    msgBgColor = COLOR_OK;
                } else {
                    message = "Cannot update the record, some error......";
                    msgBgColor = COLOR_ERROR;
                }
                return rowsAffected;
            }

    public int insertRecord(DutyLocationTypeBean statusTypeBean) {
        int rowsAffected = 0;
        String query = "INSERT INTO location_type (location_type_name, remark) VALUES (?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, statusTypeBean.getStatus_type());
            pstmt.setString(2, statusTypeBean.getRemark());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: payment inserting: " + e);
        }
        if (rowsAffected > 0) {
             message = "Record saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
             message = "Record Not saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("not inserted");
        }
        return rowsAffected;
    }

    public List<DutyLocationTypeBean> showData(int lowerLimit, int noOfRowsToDisplay , String searchStatusType) {
        List<DutyLocationTypeBean> list = new ArrayList<DutyLocationTypeBean>();

        String query = " SELECT location_type_id, location_type_name, remark "
                + " FROM location_type "
                + " WHERE IF('" + searchStatusType + "' = '', location_type_name LIKE '%%', location_type_name =?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
         pstmt.setString(1, searchStatusType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DutyLocationTypeBean statusType = new DutyLocationTypeBean();
                statusType.setStatus_type_id(rset.getInt("location_type_id"));
                statusType.setStatus_type(rset.getString("location_type_name"));
                statusType.setRemark(rset.getString("remark"));
                list.add(statusType);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
        public List<DutyLocationTypeBean> showAllData() {
        List<DutyLocationTypeBean> list = new ArrayList<DutyLocationTypeBean>();

        String query = " SELECT location_type_id, location_type_name, remark "
                + " FROM status_type ";
                //+ " WHERE IF('" + searchStatusType + "' = '', status_type LIKE '%%', status_type =?) "
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, searchStatusType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DutyLocationTypeBean statusType = new DutyLocationTypeBean();
                statusType.setStatus_type_id(rset.getInt("location_type_id"));
                statusType.setStatus_type(rset.getString("location_type_name"));
                statusType.setRemark(rset.getString("remark"));
                list.add(statusType);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchStatusType) {
        String query = " SELECT Count(*) "
                + " FROM location_type "
                + " WHERE IF('" + searchStatusType + "' = '', location_type_name LIKE '%%',location_type_name =?) "
                + " ORDER BY location_type_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchStatusType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows State type model" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }
    public List<String> getStatusType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT location_type_id, location_type_name FROM location_type GROUP BY location_type_name ORDER BY location_type_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String status_type = rset.getString("location_type_name");
                if (status_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(status_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getStatustype ERROR inside StausTypeModel - " + e);
        }
        return list;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("statusTypemodel closeConnection() Error: " + e);
        }
    }
}

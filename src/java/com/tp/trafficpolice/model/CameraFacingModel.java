/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import com.tp.tableClasses.CameraFacingBean;
import com.tp.tableClasses.CameraTypeBean;
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
public class CameraFacingModel {
    
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
            String query = " DELETE FROM camera_facing WHERE camera_facing_id = " +status_type_id;
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
            public int updateRecord(CameraFacingBean modeOfPaymentBean) {
                String query = " UPDATE camera_facing SET  camera_facing=?, remark=? WHERE camera_facing_id = ? ";
                int rowsAffected = 0;
                try {
                    PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

                    pstmt.setString(1, modeOfPaymentBean.getCamera_facing());
                    pstmt.setString(2, modeOfPaymentBean.getRemark());
                     pstmt.setInt(3, modeOfPaymentBean.getCamera_facing_id());
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

    public int insertRecord(CameraFacingBean modeOfPaymentBean) {
        int rowsAffected = 0;
        String query = "INSERT INTO camera_facing(camera_facing,remark) VALUES (?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, modeOfPaymentBean.getCamera_facing());
            pstmt.setString(2, modeOfPaymentBean.getRemark());
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

    public List<CameraFacingBean> showData(int lowerLimit, int noOfRowsToDisplay , String searchStatusType) {
        List<CameraFacingBean> list = new ArrayList<CameraFacingBean>();

        String query = " SELECT camera_facing_id, camera_facing, remark "
                + " FROM camera_facing "
                + " WHERE IF('" + searchStatusType + "' = '', camera_facing LIKE '%%', camera_facing =?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
         pstmt.setString(1, searchStatusType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CameraFacingBean modeOfPaymentBean = new CameraFacingBean();
                modeOfPaymentBean.setCamera_facing_id(rset.getInt("camera_facing_id"));
                modeOfPaymentBean.setCamera_facing(rset.getString("camera_facing"));
                modeOfPaymentBean.setRemark(rset.getString("remark"));
                list.add(modeOfPaymentBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
        public List<CameraFacingBean> showAllData() {
        List<CameraFacingBean> list = new ArrayList<CameraFacingBean>();

        String query = " SELECT status_type_id, status_type, remark "
                + " FROM status_type ";
                //+ " WHERE IF('" + searchStatusType + "' = '', status_type LIKE '%%', status_type =?) "
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, searchStatusType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CameraFacingBean modeOfPaymentBean = new CameraFacingBean();
                modeOfPaymentBean.setCamera_facing_id(rset.getInt("status_type_id"));
                modeOfPaymentBean.setCamera_facing(rset.getString("status_type"));
                modeOfPaymentBean.setRemark(rset.getString("remark"));
                list.add(modeOfPaymentBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchStatusType) {
        String query = " SELECT Count(*) "
                + " FROM camera_type "
                + " WHERE IF('" + searchStatusType + "' = '', camera_type LIKE '%%',camera_type =?) "
                + " ORDER BY camera_type ";
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
    public List<String> getCameraType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT camera_facing_id, camera_facing FROM camera_facing GROUP BY camera_facing ORDER BY camera_facing";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String payment_mode = rset.getString("camera_facing");
                if (payment_mode.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(payment_mode);
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

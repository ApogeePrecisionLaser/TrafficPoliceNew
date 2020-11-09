/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.correspondence.model;

import com.tp.correspondence.tableClasses.CorrespondencePriorityBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author jpss
 */
public class CorrespondencePriorityModel {
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();


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
                    System.out.println("CorrespondencePriorityModel generatReport() JRException: " + e);
                }
                return reportInbytes;
            }

        public int deleteRecord(int correspondence_priority_id) {
            String query = " DELETE FROM correspondence_priority WHERE correspondence_priority_id = " + correspondence_priority_id;
            int rowsAffected = 0;
            try {
                rowsAffected = connection.prepareStatement(query).executeUpdate();
            } catch (Exception e) {
                System.out.println("CorrespondencePriorityModel deleteRecord() Error: " + e);
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
            public int updateRecord(CorrespondencePriorityBean correspondencePriorityBean) {
                String query = " UPDATE correspondence_priority SET priority=?, remark = ? WHERE correspondence_priority_id = ? ";
                int rowsAffected = 0;
                try {
                    PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
                    pstmt.setString(1, krutiToUnicode.convert_to_unicode(correspondencePriorityBean.getPriority()));
                    pstmt.setString(2, krutiToUnicode.convert_to_unicode(correspondencePriorityBean.getRemark()));
                    pstmt.setInt(3, correspondencePriorityBean.getCorrespondence_priority_id());
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

    public int insertRecord(CorrespondencePriorityBean correspondencePriorityBean) {
        int rowsAffected = 0;
        String query = "INSERT INTO correspondence_priority (priority, remark) VALUES (?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(correspondencePriorityBean.getPriority()));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(correspondencePriorityBean.getRemark()));
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: inserting: " + e);
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

    public List<CorrespondencePriorityBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchPriority) {
        List<CorrespondencePriorityBean> list = new ArrayList<CorrespondencePriorityBean>();
        searchPriority = krutiToUnicode.convert_to_unicode(searchPriority);
        String query = " SELECT correspondence_priority_id, priority, remark "
                + " FROM correspondence_priority "
                + " WHERE IF('" + searchPriority + "' = '', priority LIKE '%%', priority =?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchPriority);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CorrespondencePriorityBean correspondencePriorityBean = new CorrespondencePriorityBean();
                correspondencePriorityBean.setCorrespondence_priority_id(rset.getInt("correspondence_priority_id"));
                correspondencePriorityBean.setPriority(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("priority")));
                correspondencePriorityBean.setRemark(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("remark")));
                
                list.add(correspondencePriorityBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
    
        public List<CorrespondencePriorityBean> showAllData() {
        List<CorrespondencePriorityBean> list = new ArrayList<CorrespondencePriorityBean>();

        String query = " SELECT correspondence_priority_id, priority, remark "
                + " FROM correspondence_priority ";
                //+ " WHERE IF('" + searchcorrespondencePriorityBean + "' = '', status LIKE '%%', status =?) "
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, searchcorrespondencePriorityBean);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CorrespondencePriorityBean correspondencePriorityBean = new CorrespondencePriorityBean();
                correspondencePriorityBean.setCorrespondence_priority_id(rset.getInt("correspondence_priority_id"));
                correspondencePriorityBean.setPriority(rset.getString("priority"));
                correspondencePriorityBean.setRemark(rset.getString("remark"));
                list.add(correspondencePriorityBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
        
    public int getNoOfRows(String searchPriority) {
        searchPriority = krutiToUnicode.convert_to_unicode(searchPriority);
         String query = " SELECT count(correspondence_priority_id) "
                + " FROM correspondence_priority "
                + " WHERE IF('" + searchPriority + "' = '', priority LIKE '%%', priority =?) ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchPriority);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CorrespondencePriorityModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }
    
    public List<String> getPriorityList(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT correspondence_priority_id, priority FROM correspondence_priority GROUP BY remark ORDER BY remark";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String remark = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("priority"));
                if (remark.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(remark);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Priority exists.......");
            }
        } catch (Exception e) {
            System.out.println("getcorrespondencePriorityBean ERROR inside CorrespondencePriorityModel - " + e);
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
            System.out.println("CorrespondencePriorityModel closeConnection() Error: " + e);
        }
    }
}

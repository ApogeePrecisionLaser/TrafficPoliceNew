/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.correspondence.model;

import com.tp.correspondence.tableClasses.CorrespondenceStatusBean;
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
public class CorrespondenceStatusModel {
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
                    System.out.println("OfficerBookModel generatReport() JRException: " + e);
                }
                return reportInbytes;
            }

        public int deleteRecord(int correspondence_status_id) {
            String query = " DELETE FROM correspondence_status WHERE correspondence_status_id = " + correspondence_status_id;
            int rowsAffected = 0;
            try {
                rowsAffected = connection.prepareStatement(query).executeUpdate();
            } catch (Exception e) {
                System.out.println("correspondenceStatusModel deleteRecord() Error: " + e);
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
            public int updateRecord(CorrespondenceStatusBean CorrespondenceStatusBean) {
                String query = " UPDATE correspondence_status SET  status=?, correspondence_type_id=?, remark=? WHERE correspondence_status_id = ? ";
                int rowsAffected = 0;
                try {
                    PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
                    pstmt.setString(1, CorrespondenceStatusBean.getStatus());
                    int correspondence_type_id = CorrespondenceStatusBean.getCorrespondence_type_id();
                    if(correspondence_type_id > 0)
                        pstmt.setInt(2, correspondence_type_id);
                    else
                        pstmt.setNull(2, java.sql.Types.NULL);
                    pstmt.setString(3, CorrespondenceStatusBean.getRemark());
                    pstmt.setInt(4, CorrespondenceStatusBean.getCorrespondence_status_id());
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

    public int insertRecord(CorrespondenceStatusBean CorrespondenceStatusBean) {
        int rowsAffected = 0;
        String query = "INSERT INTO correspondence_status (status, correspondence_type_id, remark) VALUES (?,?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, CorrespondenceStatusBean.getStatus());
            int correspondence_type_id = CorrespondenceStatusBean.getCorrespondence_type_id();
            if(correspondence_type_id > 0)
                pstmt.setInt(2, correspondence_type_id);
            else
                pstmt.setNull(2, java.sql.Types.NULL);
            pstmt.setString(3, CorrespondenceStatusBean.getRemark());
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

    public List<CorrespondenceStatusBean> showData(int lowerLimit, int noOfRowsToDisplay , String searchStatusType, String searchCorrespondenceType) {
        List<CorrespondenceStatusBean> list = new ArrayList<CorrespondenceStatusBean>();

        String query = " SELECT cs.correspondence_status_id, ct.correspondence_type_id, ct.correspondence_type, cs.status, cs.remark "
                + " FROM correspondence_status cs, correspondence_type ct "
                + " WHERE cs.correspondence_type_id = ct.correspondence_type_id "
                + " AND IF('" + searchStatusType + "' = '', cs.status LIKE '%%', cs.status =?) "
                + " AND IF('" + searchCorrespondenceType + "' = '', ct.correspondence_type LIKE '%%', ct.correspondence_type =?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchStatusType);
            pstmt.setString(2, searchCorrespondenceType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CorrespondenceStatusBean statusType = new CorrespondenceStatusBean();
                statusType.setCorrespondence_status_id(rset.getInt("correspondence_status_id"));
                statusType.setCorrespondence_type_id(rset.getInt("correspondence_type_id"));
                statusType.setCorrespondence_type(rset.getString("correspondence_type"));
                statusType.setStatus(rset.getString("status"));
                statusType.setRemark(rset.getString("remark"));
                list.add(statusType);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
        public List<CorrespondenceStatusBean> showAllData() {
        List<CorrespondenceStatusBean> list = new ArrayList<CorrespondenceStatusBean>();

        String query = " SELECT cs.correspondence_status_id, ct.correspondence_type_id, ct.correspondence_type, cs.status, cs.remark "
                + " FROM correspondence_status cs, correspondence_type ct "
                + " WHERE cs.correspondence_type_id = ct.correspondence_type_id ";
                //+ " WHERE IF('" + searchStatusType + "' = '', status LIKE '%%', status =?) "
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, searchStatusType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CorrespondenceStatusBean statusType = new CorrespondenceStatusBean();
                statusType.setCorrespondence_status_id(rset.getInt("correspondence_status_id"));
                statusType.setCorrespondence_type_id(rset.getInt("correspondence_type_id"));
                statusType.setCorrespondence_type(rset.getString("correspondence_type"));
                statusType.setStatus(rset.getString("status"));
                statusType.setRemark(rset.getString("remark"));
                list.add(statusType);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchStatusType, String searchCorrespondenceType) {
         String query = " SELECT count(cs.correspondence_status_id) "
                + " FROM correspondence_status cs, correspondence_type ct "
                + " WHERE cs.correspondence_type_id = ct.correspondence_type_id "
                + " AND IF('" + searchStatusType + "' = '', cs.status LIKE '%%', cs.status =?) "
                + " AND IF('" + searchCorrespondenceType + "' = '', ct.correspondence_type LIKE '%%', ct.correspondence_type =?) "
                + " ORDER BY status ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchStatusType);
            stmt.setString(2, searchCorrespondenceType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CorrespondenceStatusModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }
    public List<String> getStatusType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT correspondence_status_id, status FROM correspondence_status GROUP BY status ORDER BY status";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String status = rset.getString("status");
                if (status.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(status);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getStatustype ERROR inside CorrespondenceStatusModel - " + e);
        }
        return list;
    }

    public Map<Integer, String> getCorrespondenceTypeList(){
        Map<Integer, String> list = new HashMap<Integer, String>();
        String query = "SELECT correspondence_type_id, correspondence_type FROM correspondence_type";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                list.put(rs.getInt("correspondence_type_id"), rs.getString("correspondence_type"));
            }
        }catch(Exception ex){
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
            System.out.println("CorrespondenceStatusModel closeConnection() Error: " + e);
        }
    }
}

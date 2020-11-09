/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import com.tp.tableClasses.ChallanPaymentBean;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
public class ChallanPaymentModel {
    
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
            String query = " DELETE FROM challan_payment WHERE challan_payment_id = " +status_type_id;
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
            public int updateRecord(ChallanPaymentBean challanPaymentBean) {
                
                int modeOfPaymentId = getModeOfPaymentId(challanPaymentBean.getMode_of_payment());
                
                String query = " update challan_payment set traffic_police_id=?,mode_of_payment_id=?,amount=?,date_time=?,remark=?\n" +
                               " where challan_payment_id=?";
                int rowsAffected = 0;
                try {
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, challanPaymentBean.getReceipt_no());
                    pstmt.setInt(2,modeOfPaymentId);
                    pstmt.setString(3, challanPaymentBean.getAmount());
            
                    String date = convertToSqlDate(challanPaymentBean.getPayment_date())+"";
                    String time = challanPaymentBean.getPayment_h_m();
                    String offenceDateTime = date+" "+time;
        
                    pstmt.setString(4, offenceDateTime);
                    pstmt.setString(5, challanPaymentBean.getRemark());
                    pstmt.setInt(6, challanPaymentBean.getChallan_payment_id());
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
            public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public int insertRecord(ChallanPaymentBean challanPaymentBean) {
        int rowsAffected = 0;
        int modeOfPaymentId = getModeOfPaymentId(challanPaymentBean.getMode_of_payment());
        
        String query = "INSERT INTO challan_payment(traffic_police_id,mode_of_payment_id,amount,date_time,remark) VALUES (?,?,?,?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, challanPaymentBean.getReceipt_no());
            pstmt.setInt(2,modeOfPaymentId);
            pstmt.setString(3, challanPaymentBean.getAmount());
            
            String date = convertToSqlDate(challanPaymentBean.getPayment_date())+"";
            String time = challanPaymentBean.getPayment_h_m();
            String offenceDateTime = date+" "+time;
        
            pstmt.setString(4, offenceDateTime);
            pstmt.setString(5, challanPaymentBean.getRemark());
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

    public List<ChallanPaymentBean> showData(int lowerLimit, int noOfRowsToDisplay , String searchStatusType) {
        List<ChallanPaymentBean> list = new ArrayList<ChallanPaymentBean>();

        String query = " select cp.challan_payment_id,cp.traffic_police_id as receipt_no,mop.payment_mode,cp.amount,cp.date_time,cp.remark\n" +
                       " from challan_payment cp,mode_of_payment mop\n" +
                       " where cp.mode_of_payment_id = mop.mode_of_payment_id "
                  + " AND IF('" + searchStatusType + "' = '', payment_mode LIKE '%%', payment_mode =?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           pstmt.setString(1, searchStatusType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ChallanPaymentBean modeOfPaymentBean = new ChallanPaymentBean();
                modeOfPaymentBean.setChallan_payment_id(rset.getInt("challan_payment_id"));
                modeOfPaymentBean.setReceipt_no(rset.getString("receipt_no"));
                modeOfPaymentBean.setMode_of_payment(rset.getString("payment_mode"));
                modeOfPaymentBean.setAmount(rset.getString("amount"));
                modeOfPaymentBean.setPayment_date(rset.getString("date_time"));
                modeOfPaymentBean.setRemark(rset.getString("remark"));


                list.add(modeOfPaymentBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
        public List<ChallanPaymentBean> showAllData() {
        List<ChallanPaymentBean> list = new ArrayList<ChallanPaymentBean>();

        String query = " SELECT status_type_id, status_type, remark "
                + " FROM status_type ";
                //+ " WHERE IF('" + searchStatusType + "' = '', status_type LIKE '%%', status_type =?) "
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, searchStatusType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ChallanPaymentBean modeOfPaymentBean = new ChallanPaymentBean();
//                modeOfPaymentBean.setPayment_type_id(rset.getInt("status_type_id"));
//                modeOfPaymentBean.setPayment_type(rset.getString("status_type"));
//                modeOfPaymentBean.setRemark(rset.getString("remark"));
                list.add(modeOfPaymentBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchStatusType) {
        String query = " select count(*)\n" +
                       " from challan_payment cp,mode_of_payment mop\n" +
                       " where cp.mode_of_payment_id = mop.mode_of_payment_id "
                    + " AND IF('" + searchStatusType + "' = '', payment_mode LIKE '%%',payment_mode =?) ";
               
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
    public List<String> getPaymentType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select payment_mode\n" +
                       "from mode_of_payment group by payment_mode order by payment_mode";
        
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String payment_mode = rset.getString("payment_mode");
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
    public List<String> getModeOfPayment(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select payment_mode\n" +
                       "from mode_of_payment group by payment_mode order by payment_mode";
        
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String payment_mode = rset.getString("payment_mode");
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
    public int getModeOfPaymentId(String modeOfPayment) {
        int modeOfPaymentId = 0;
        List<String> list = new ArrayList<String>();
        String query = " SELECT mode_of_payment_id  FROM mode_of_payment where payment_mode=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, modeOfPayment);
            ResultSet rset = stmt.executeQuery();
            
            
            while (rset.next()) {   
                modeOfPaymentId = rset.getInt("mode_of_payment_id");
            }
            
        } catch (Exception e) {
            System.out.println("getStatustype ERROR inside StausTypeModel - " + e);
        }
        return modeOfPaymentId;
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

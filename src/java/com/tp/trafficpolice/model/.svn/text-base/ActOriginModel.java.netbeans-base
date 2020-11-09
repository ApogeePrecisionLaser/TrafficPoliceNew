/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import com.tp.tableClasses.ActOriginBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
public class ActOriginModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public Connection getConnection() {
        return connection;
    }

    public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            System.out.println("connected - " + connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int deleteRecord(int act_origin_id) {
        String query = " DELETE FROM act_origin WHERE act_origin_id = " + act_origin_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("ActOrigin Model deleteRecord() Error: " + e);
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

    public int updateRecord(ActOriginBean actOrigin) {
        String query = " UPDATE act_origin SET  act_origin=?, remark=? WHERE act_origin_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            pstmt.setString(1, krutiToUnicode.convert_to_unicode(actOrigin.getAct_origin()));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(actOrigin.getRemark()));
            pstmt.setInt(3, actOrigin.getAct_origin_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ActOrigin updateRecord() Error: " + e);
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

    public int insertRecord(ActOriginBean actOrigin) {
        int rowsAffected = 0;
        String query = "INSERT INTO act_origin (act_origin, remark) VALUES (?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(actOrigin.getAct_origin()));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(actOrigin.getRemark()));
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: act origin inserting: " + e);
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
      public List<ActOriginBean> showAllData(String searchStatusType) {
        List<ActOriginBean> list = new ArrayList<ActOriginBean>();

        String query = " SELECT act_origin_id, act_origin, remark "
                + " FROM act_origin "
                + " WHERE IF('" + searchStatusType + "' = '', act_origin LIKE '%%', act_origin =?) ";
                  try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchStatusType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ActOriginBean actOrigin= new ActOriginBean();
                actOrigin.setAct_origin_id(rset.getInt("act_origin_id"));
                actOrigin.setAct_origin(rset.getString("act_origin"));
                actOrigin.setRemark(rset.getString("remark"));
                list.add(actOrigin);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData()....: " + e);
        }
        return list;
    }

    public List<ActOriginBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchStatusType) {
        List<ActOriginBean> list = new ArrayList<ActOriginBean>();

        String query = " SELECT act_origin_id, act_origin, remark "
                + " FROM act_origin "
                + " WHERE IF('" + searchStatusType + "' = '', act_origin LIKE '%%', act_origin =?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchStatusType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ActOriginBean actOrigin= new ActOriginBean();
                actOrigin.setAct_origin_id(rset.getInt("act_origin_id"));
                actOrigin.setAct_origin(rset.getString("act_origin"));
                actOrigin.setRemark(rset.getString("remark"));
                list.add(actOrigin);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
     public int getNoOfRows(String searchStatusType) {
        String query = " SELECT Count(*) "
                + " FROM act_origin "
                + " WHERE IF('" + searchStatusType + "' = '', act_origin LIKE '%%',act_origin =?) "
                + " ORDER BY act_origin ";
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
       public List<String> getActOrigin(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT act_origin FROM act_origin GROUP BY act_origin ORDER BY act_origin";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String act_origin = rset.getString("act_origin");
                if (act_origin.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(act_origin);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getActOrigin ERROR inside ActOriginModel - " + e);
        }
        return list;
    }
        public byte[] generateOrignReport(String jrxmlFilePath,List listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in OrignModel generateOrignReport() JRException: " + e);
        }
        return reportInbytes;
    }
     public ByteArrayOutputStream generateOrignXlsRecordList(String jrxmlFilePath,List list) {
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
                    System.out.println("OrignStatusModel generateOrignXlsRecordList () JRException: " + e);
                }
                return bytArray;
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
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ActOriginModel closeConnection() Error: " + e);
        }
    }
}

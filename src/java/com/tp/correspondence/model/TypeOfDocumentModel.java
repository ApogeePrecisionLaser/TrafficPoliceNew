/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.correspondence.model;

import com.tp.correspondence.tableClasses.TypeOfDocumentBean;
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
public class TypeOfDocumentModel {
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
                    System.out.println("TypeOfDocumentModel generatReport() JRException: " + e);
                }
                return reportInbytes;
            }

        public int deleteRecord(int remark_id) {
            String query = " DELETE FROM type_of_document WHERE type_of_document_id = " + remark_id;
            int rowsAffected = 0;
            try {
                rowsAffected = connection.prepareStatement(query).executeUpdate();
            } catch (Exception e) {
                System.out.println("TypeOfDocumentModel deleteRecord() Error: " + e);
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
            public int updateRecord(TypeOfDocumentBean typeOfDocumentBean) {
                String query = " UPDATE type_of_document SET type_of_document=?, description = ? WHERE type_of_document_id = ? ";
                int rowsAffected = 0;
                try {
                    PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
                    pstmt.setString(1, krutiToUnicode.convert_to_unicode(typeOfDocumentBean.getType_of_document()));
                    pstmt.setString(2, krutiToUnicode.convert_to_unicode(typeOfDocumentBean.getDescription()));
                    pstmt.setInt(3, typeOfDocumentBean.getType_of_document_id());
                    rowsAffected = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("TypeOfDocumentModel updateRecord() Error: " + e);
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

    public int insertRecord(TypeOfDocumentBean typeOfDocumentBean) {
        int rowsAffected = 0;
        String query = "INSERT INTO type_of_document (type_of_document, description) VALUES (?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(typeOfDocumentBean.getType_of_document()));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(typeOfDocumentBean.getDescription()));
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

    public List<TypeOfDocumentBean> showData(int lowerLimit, int noOfRowsToDisplay , String searchRemarke) {
        List<TypeOfDocumentBean> list = new ArrayList<TypeOfDocumentBean>();
        searchRemarke = krutiToUnicode.convert_to_unicode(searchRemarke);
        String query = " SELECT type_of_document_id, type_of_document, description "
                + " FROM type_of_document "
                + " WHERE IF('" + searchRemarke + "' = '', type_of_document LIKE '%%', type_of_document =?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchRemarke);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TypeOfDocumentBean typeOfDocumentBean = new TypeOfDocumentBean();
                typeOfDocumentBean.setType_of_document_id(rset.getInt("type_of_document_id"));
                typeOfDocumentBean.setType_of_document(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("type_of_document")));
                typeOfDocumentBean.setDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("description")));
                list.add(typeOfDocumentBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
    
        public List<TypeOfDocumentBean> showAllData() {
        List<TypeOfDocumentBean> list = new ArrayList<TypeOfDocumentBean>();

        String query = " SELECT type_of_document_id, type_of_document, description "
                + " FROM type_of_document ";
                //+ " WHERE IF('" + searchtypeOfDocumentBean + "' = '', status LIKE '%%', status =?) "
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, searchtypeOfDocumentBean);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TypeOfDocumentBean typeOfDocumentBean = new TypeOfDocumentBean();
                typeOfDocumentBean.setType_of_document_id(rset.getInt("type_of_document_id"));
                typeOfDocumentBean.setType_of_document(rset.getString("type_of_document"));
                typeOfDocumentBean.setDescription(rset.getString("description"));
                list.add(typeOfDocumentBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
        
    public int getNoOfRows(String searchRemarke) {
        searchRemarke = krutiToUnicode.convert_to_unicode(searchRemarke);
         String query = " SELECT count(type_of_document_id) "
                + " FROM type_of_document "
                + " WHERE IF('" + searchRemarke + "' = '', type_of_document LIKE '%%', type_of_document =?) ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchRemarke);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows TypeOfDocumentModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }
    
    public List<String> gettypeOfDocumentList(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT type_of_document_id, type_of_document FROM type_of_document GROUP BY type_of_document ORDER BY type_of_document";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String type_of_document = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("type_of_document"));
                if (type_of_document.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(type_of_document);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Type exists.......");
            }
        } catch (Exception e) {
            System.out.println("gettypeOfDocumentList ERROR inside TypeOfDocumentModel - " + e);
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
            System.out.println("TypeOfDocumentModel closeConnection() Error: " + e);
        }
    }
}

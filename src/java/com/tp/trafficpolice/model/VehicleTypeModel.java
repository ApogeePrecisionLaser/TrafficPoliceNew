/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import java.sql.Connection;

import com.tp.tableClasses.VehicleTypeBean;
import java.io.ByteArrayOutputStream;
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
 * @author JPSS
 */
public class VehicleTypeModel {

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
                connection= (Connection) DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8",db_username,db_password);            System.out.println("connected - " + connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (Exception e) {
            System.out.println("OfficerBookModel generatReport() JRException: " + e);
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

    public int deleteRecord(int vehicle_type_id) {
        String query = " DELETE FROM vehicle_type WHERE vehicle_type_id = " + vehicle_type_id;
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
            message = "Cannot delete the record, it is used in another GUI";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int insertRecord(VehicleTypeBean vehicleTypeBean) {
        int rowsAffected = 0;
        String query = "INSERT INTO vehicle_type (vehicle_type, remark) VALUES (?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, vehicleTypeBean.getVehicle_type());
       //     pstmt.setString(2, vehicleTypeBean.getCommercial_type());
            pstmt.setString(2, vehicleTypeBean.getRemark());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: vehicle inserting: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
            message = "Cannot Save the record, some error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not inserted");
        }
        return rowsAffected;
    }

     public List<String> geVehicleType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT vehicle_type_id, vehicle_type FROM vehicle_type GROUP BY vehicle_type ORDER BY vehicle_type";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String make = rset.getString("vehicle_type");
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

    public List<VehicleTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchVehicleType) {
        List<VehicleTypeBean> list = new ArrayList<VehicleTypeBean>();

        String query = " SELECT vehicle_type_id, vehicle_type, remark "
                + " FROM vehicle_type "
                + " WHERE IF('" + searchVehicleType + "' = '', vehicle_type LIKE '%%', vehicle_type=?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchVehicleType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                VehicleTypeBean vehicleTypeBean = new VehicleTypeBean();
                vehicleTypeBean.setVehicle_type_id(rset.getInt("vehicle_type_id"));
                vehicleTypeBean.setVehicle_type(rset.getString("vehicle_type"));
            //   vehicleTypeBean.setCommercial_type(rset.getString("commercial_type"));
                vehicleTypeBean.setRemark(rset.getString("remark"));
                list.add(vehicleTypeBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public List<VehicleTypeBean> showAllData() {
        List<VehicleTypeBean> list = new ArrayList<VehicleTypeBean>();

        String query = " SELECT vehicle_type_id, vehicle_type, remark "
                + " FROM vehicle_type ";
        // + " WHERE IF('" + searchVehicleType + "' = '', vehicle_type LIKE '%%', vehicle_type=?) "
        //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,searchVehicleType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                VehicleTypeBean vehicleTypeBean = new VehicleTypeBean();
                vehicleTypeBean.setVehicle_type_id(rset.getInt("vehicle_type_id"));
                vehicleTypeBean.setVehicle_type(rset.getString("vehicle_type"));
                vehicleTypeBean.setRemark(rset.getString("remark"));
                list.add(vehicleTypeBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchVehicleType) {
        String query = " SELECT Count(*) "
                + " FROM vehicle_type "
                + " WHERE IF('" + searchVehicleType + "' = '',vehicle_type LIKE '%%',vehicle_type =?) "
                + " ORDER BY vehicle_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchVehicleType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Vehicle type model" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

            public int updateRecord(VehicleTypeBean vehicleTypeBean) {
                String query = " UPDATE vehicle_type SET  vehicle_type=?, remark=? WHERE vehicle_type_id = ? ";
                int rowsAffected = 0;
                try {
                    PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

                    pstmt.setString(1, vehicleTypeBean.getVehicle_type());
                 //   pstmt.setString(2,vehicleTypeBean.getCommercial_type());
                    pstmt.setString(2, vehicleTypeBean.getRemark());
                     pstmt.setInt(3, vehicleTypeBean.getVehicle_type_id());
                     rowsAffected = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("VehicleTypeModel updateRecord() Error: " + e);
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
            System.out.println("keypersonModel closeConnection() Error: " + e);
        }
    }
}

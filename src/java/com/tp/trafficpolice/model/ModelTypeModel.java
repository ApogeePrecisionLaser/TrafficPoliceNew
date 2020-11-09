/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import java.sql.Connection;

import com.tp.tableClasses.ModelTypeBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class ModelTypeModel {

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
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            System.out.println("connected - " + connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public Map<Integer, String> getModelTypeList() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
//        String statusTypeList = "SELECT model_id, model FROM model GROUP BY model ORDER BY model";
          String statusTypeList = "SELECT make_id, make FROM make GROUP BY make ORDER BY make";

        try {
            PreparedStatement ps = connection.prepareStatement(statusTypeList);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getInt("make_id"), rs.getString("make"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel statusTypeList (" + e);
        }

        return map;
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

    public int deleteRecord(int model_id) {
        String query = " DELETE FROM model WHERE model_id = " + model_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("modelType Model deleteRecord() Error: " + e);
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

    public int updateRecord(ModelTypeBean modelTypeBean) {
        String query = " UPDATE model SET  model=?, remark=?,make_id=? WHERE model_id = ? ";//,vehicle_type_id=?
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

//            pstmt.setString(1, krutiToUnicode.convert_to_unicode(modelTypeBean.getModel()));
//            pstmt.setString(2, krutiToUnicode.convert_to_unicode(modelTypeBean.getRemark()));
            pstmt.setString(1, modelTypeBean.getModel());
            pstmt.setString(2, modelTypeBean.getRemark());
            pstmt.setInt(3,getmakeTypeId(modelTypeBean.getMake()));
            //pstmt.setInt(4, getVehicleeTypeId(modelTypeBean.getVehicle_type()));
            pstmt.setInt(4, modelTypeBean.getModel_id());

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

    public int insertRecord(ModelTypeBean modelTypeBean) {
        int rowsAffected = 0;
        String query = "INSERT INTO model (model,make_id, remark) VALUES (?,?,?) ";//,vehicle_type_id

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, krutiToUnicode.convert_to_unicode(modelTypeBean.getModel()));
              pstmt.setString(1, modelTypeBean.getModel());
            pstmt.setInt(2, getmakeTypeId(modelTypeBean.getMake()));
            //pstmt.setInt(3, getVehicleeTypeId(modelTypeBean.getVehicle_type()));
//            pstmt.setString(3, krutiToUnicode.convert_to_unicode(modelTypeBean.getRemark()));
              pstmt.setString(3, modelTypeBean.getRemark());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: Record inserting: " + e);
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

    public List<ModelTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchmodelType) {
        List<ModelTypeBean> list = new ArrayList<ModelTypeBean>();

        String query = " SELECT md.model_id, md.model,md.remark,m.make "//,vt.vehicle_type
                + " FROM model as md,make as m"//,vehicle_type as vt
                + " WHERE md.make_id=m.make_id  "//and md.vehicle_type_id=vt.vehicle_type_id
                + "AND IF('" + searchmodelType + "' = '', model LIKE '%%', model =?) "
                + "group by md.model_id order by md.model_id"
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchmodelType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ModelTypeBean modelType = new ModelTypeBean();
                modelType.setModel_id(rset.getInt("model_id"));
                modelType.setModel(rset.getString("model"));
                modelType.setRemark(rset.getString("remark"));
                modelType.setMake(rset.getString("make"));
                //modelType.setVehicle_type(rset.getString("vehicle_type"));
                list.add(modelType);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public List<ModelTypeBean> showAllData() {
        List<ModelTypeBean> list = new ArrayList<ModelTypeBean>();

        String query = "SELECT md.model_id, md.model,md.remark,make "//,vehicle_type
                + " FROM model as md,make as m"//,vehicle_type as vt
                + " WHERE md.make_id=m.make_id "// and md.vehicle_type_id=vt.vehicle_type_id
                + "group by md.model_id order by md.model";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, searchmodelType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ModelTypeBean modelType = new ModelTypeBean();
                modelType.setModel_id(rset.getInt("model_id"));
                modelType.setModel(rset.getString("model"));
                modelType.setRemark(rset.getString("remark"));
                modelType.setMake(rset.getString("make"));
                //modelType.setVehicle_type(rset.getString("vehicle_type"));
                list.add(modelType);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchmodelType) {
        String query = " SELECT Count(*) "
                + " FROM model as md,make as m"//,vehicle_type as vt
                + " WHERE md.make_id=m.make_id "//and md.vehicle_type_id=vt.vehicle_type_id
                + "AND IF('" + searchmodelType + "' = '', model LIKE '%%', model =?) ";
                //+ "group by md.model_id order by md.model_id";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchmodelType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows State type model" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public List<String> getmodelType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT model_id, model FROM model GROUP BY model ORDER BY model";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String model = rset.getString("model");
                if (model.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(model);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getmodelType ERROR inside ModelTYpeModel() - " + e);
        }
        return list;
    }

    public int getmakeTypeId(String make_type) {
        int make_id = 0;
        String query = " SELECT make_id FROM make where make='" + make_type + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            while (rset.next()) {    // move cursor from BOR to valid record.
                make_id = rset.getInt("make_id");

            }

        } catch (Exception e) {
            System.out.println("getmodelType ERROR inside ModelTYpeModel() - " + e);
        }
        return make_id;
    }

    public int getVehicleeTypeId(String vehicle_type) {
        int vehicle_type_id = 0;
        String query = " SELECT vehicle_type_id FROM vehicle_type where vehicle_type='" + vehicle_type + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            while (rset.next()) {    // move cursor from BOR to valid record.
                vehicle_type_id = rset.getInt("vehicle_type_id");

            }

        } catch (Exception e) {
            System.out.println("getmodelType ERROR inside ModelTYpeModel() - " + e);
        }
        return vehicle_type_id;
    }
 public List<String> getmakeType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT  make FROM make GROUP BY make ORDER BY make";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String make = rset.getString("make");
                if (make.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(make);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getmakeType ERROR inside ModelTYpeModel() - " + e);
        }
        return list;
    }

  public List<String> getVehicleType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT  vehicle_type FROM vehicle_type GROUP BY vehicle_type ORDER BY vehicle_type";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String vehicle_type = rset.getString("vehicle_type");
                if (vehicle_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(vehicle_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getVehicleType ERROR inside ModelTYpeModel() - " + e);
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
            System.out.println("modelTypemodel closeConnection() Error: " + e);
        }
    }
}

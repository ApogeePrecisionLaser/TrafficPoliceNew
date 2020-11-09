/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import com.tp.tableClasses.CameraInfoBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
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
public class CameraInfoModel {
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

    public void setConnection(Connection conn) {
        connection = conn;
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
            //reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
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

    public int deleteRecord(int camera_Info_id) {
        String query = " DELETE FROM camera__info WHERE camera_info_id = " + camera_Info_id;
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

    public int updateRecord(CameraInfoBean cameraInfoBean) {
        
        int camera_type_id=getCameraTypeId(cameraInfoBean.getCamera_type());
        int camera_facing_id=getCameraFacingId(cameraInfoBean.getCamera_facing());
        
        
        String query = " update camera_info set channel_no=?,camera_no=?,camera_type_id=?,camera_facing_id=?,\n" +
                        "city_location_id=?,lattitude=?,longitude=?,remark=? where camera_info_id=?";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1,cameraInfoBean.getChannel_no());
            pstmt.setString(2,cameraInfoBean.getCamera_no());
            pstmt.setInt(3,camera_type_id );
            pstmt.setInt(4,camera_facing_id);
            pstmt.setInt(5, getCityLocationId(cameraInfoBean.getCity_location()));
//            System.out.println(getCityLocationId(cameraInfoBean.getCity_location()));
            pstmt.setString(6, cameraInfoBean.getLattitude());
            pstmt.setString(7, cameraInfoBean.getLongitude());
            pstmt.setString(8, cameraInfoBean.getRemark());
            
            pstmt.setInt(9,cameraInfoBean.getCamera_info_id());
            
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("CityLocationModel updateRecord() Error: " + e);
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

    public int insertRecord(CameraInfoBean cameraInfoBean) {
        int rowsAffected = 0;
        int city_location_id = getCityLocationId(cameraInfoBean.getCity_location());
        int camera_type_id=getCameraTypeId(cameraInfoBean.getCamera_type());
        int camera_facing_id=getCameraFacingId(cameraInfoBean.getCamera_facing());
         
        String query = "insert into camera_info(channel_no,camera_no,camera_type_id,camera_facing_id,city_location_id,lattitude,longitude,remark)\n" +
                       "values(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, cameraInfoBean.getChannel_no());
            pstmt.setString(2, cameraInfoBean.getCamera_no());
            pstmt.setInt(3,camera_type_id);
            pstmt.setInt(4,camera_facing_id);
            pstmt.setInt(5, city_location_id);  
            pstmt.setString(6, cameraInfoBean.getLattitude());
            pstmt.setString(7, cameraInfoBean.getLongitude());
            pstmt.setString(8, cameraInfoBean.getRemark());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: Data inserting: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
            message = "Record Not saved ......";
            msgBgColor = COLOR_OK;
            System.out.println("not inserted");
        }
        return rowsAffected;
    }
    public int getCameraTypeId(String camera_type) {      
        int camera_type_id = 0;
        String query = "select camera_type_id\n" +
                       "from camera_type ct\n" +
                       "where ct.camera_type='"+camera_type+"'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            
            while (rset.next()) {
                camera_type_id = rset.getInt("camera_type_id");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside getCityLocationId - " + e);
        }
        return camera_type_id;
    }
    public int getCameraFacingId(String camera_facing) {      
        int camera_facing_id = 0;
        String query = "select camera_facing_id\n" +
                       "from camera_facing ct\n" +
                       "where ct.camera_facing='"+camera_facing+"'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                camera_facing_id = rset.getInt("camera_facing_id");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside getCityLocationId - " + e);
        }
        return camera_facing_id;
    }
    
    
    
    public int getCityLocationId(String city_location_eng) {      
        int city_location_id = 0;
        String query = "select city_location_id from city_location where location_eng='" + city_location_eng + "'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                city_location_id = rset.getInt("city_location_id");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside getCityLocationId - " + e);
        }
        return city_location_id;
    }
    
    
    
    public int getZoneId(String zone) {
        //List<String> list = new ArrayList<String>();
        int zone_new_id = 0;
        zone = krutiToUnicode.convert_to_unicode(zone);
        String query = "select zone_new_id from zone_new where zone='" + zone + "'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                zone_new_id = rset.getInt("zone_new_id");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside CityLocationModel - " + e);
        }
        return zone_new_id;
    }


    public List<CameraInfoBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchCameraNo, String searchCameraType, String searchLocation) {
        List<CameraInfoBean> list = new ArrayList<CameraInfoBean>();
        

//        String query = " select cl.city_location_id,z.zone,w.ward_name,a.area_name,cl.location,cl.location_eng,cl.location_no,cl.remark,cl.latitude,cl.longitude "
//                + " from city_location as cl,zone_new as z, ward as w,area as a where "
//                + " cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_new_id=z.zone_new_id "
//                + " And IF('" + searchCityName + "' = '', cl.location LIKE '%%', cl.location  =?) "
//                + " And IF('" + searchZoneName + "' = '', z.zone LIKE '%%', z.zone=?) "
//                + " And IF('" + searchWardName + "' = '', w.ward_name LIKE '%%', w.ward_name =?) "
//                + " And IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =?) order by zone, ward_name, area_name, location_no "
//                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        
        String query="select camera_info_id,channel_no,camera_no,camera_type,camera_facing,cl.location_eng,\n" +
                     "ci.lattitude,ci.longitude,ci.remark\n" +
                     "from camera_info ci,city_location cl,camera_type ct,camera_facing cf\n" +
                     "where ci.city_location_id =cl.city_location_id\n" +
                     "and ci.camera_type_id = ct.camera_type_id\n" +
                     "and ci.camera_facing_id = cf.camera_facing_id "
                + " And IF('" + searchCameraNo + "' = '', ci.camera_no LIKE '%%', ci.camera_no  =?) "
                + "And IF('" + searchCameraType + "' = '', ct.camera_type LIKE '%%', ct.camera_type=?) "
                + "And IF('" + searchLocation + "' = '', cl.location_eng LIKE '%%', cl.location_eng =?) "
              + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCameraNo);
            pstmt.setString(2, searchCameraType);
            pstmt.setString(3, searchLocation);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CameraInfoBean cameraInfoBean = new CameraInfoBean();
                cameraInfoBean.setCamera_info_id(rset.getInt("camera_info_id"));
                cameraInfoBean.setChannel_no(rset.getString("channel_no"));  
                cameraInfoBean.setCamera_no(rset.getString("camera_no"));  
                cameraInfoBean.setCamera_type(rset.getString("camera_type"));
                cameraInfoBean.setCamera_facing(rset.getString("camera_facing"));
                cameraInfoBean.setCity_location(rset.getString("location_eng"));
                cameraInfoBean.setLattitude(rset.getString("lattitude"));
                cameraInfoBean.setLongitude(rset.getString("longitude"));
                cameraInfoBean.setRemark(rset.getString("remark"));
                 
                list.add(cameraInfoBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public List<CameraInfoBean> showAllData(String searchCameraNo, String searchCameraType, String searchLocation) {
        List<CameraInfoBean> list = new ArrayList<CameraInfoBean>();
        

        String query = " select camera_info_id,channel_no,camera_no,camera_type,camera_facing,location_eng,\n" +
                     "lattitude,longitude,remark\n" +
                     "from camera_info ci,city_location cl\n" +
                     "where ci.city_location_id =cl.city_location_id "
                + " And IF('" + searchCameraNo + "' = '', cl.camera_no LIKE '%%', cl.camera_no  =?) "
                + "And IF('" + searchCameraType + "' = '', z.camera_type LIKE '%%', z.camera_type=?) "
                + "And IF('" + searchLocation + "' = '', w.location_eng LIKE '%%', w.location_eng =?) ";
              
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCameraNo);
            pstmt.setString(2, searchCameraType);
            pstmt.setString(3, searchLocation);
           
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CameraInfoBean cameraInfoBean = new CameraInfoBean();
                cameraInfoBean.setCamera_info_id(rset.getInt("camera_info_id"));
                cameraInfoBean.setChannel_no(rset.getString("channel_no"));  
                cameraInfoBean.setCamera_no(rset.getString("camera_no"));  
                cameraInfoBean.setCamera_type(rset.getString("camera_type"));
                cameraInfoBean.setCamera_facing(rset.getString("camera_facing"));
                cameraInfoBean.setCity_location(rset.getString("location_eng"));
                cameraInfoBean.setLattitude(rset.getString("lattitude"));
                cameraInfoBean.setLongitude(rset.getString("longitude"));
                cameraInfoBean.setRemark(rset.getString("remark"));
                list.add(cameraInfoBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchCameraNo, String searchCameraType, String searchLocation) {
//        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
//        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
//        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
//        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
        String query = " select count(*) \n "+
                       " from camera_info ci,city_location cl,camera_type ct,camera_facing cf\n" +
                       "where ci.city_location_id =cl.city_location_id\n" +
                       "and ci.camera_type_id = ct.camera_type_id\n" +
                      "and ci.camera_facing_id = cf.camera_facing_id;"
                + " And IF('" + searchCameraNo + "' = '', cl.camera_no LIKE '%%', cl.camera_no  =?) "
                + "And IF('" + searchCameraType + "' = '', ct.camera_type LIKE '%%', ct.camera_type=?) "
                + "And IF('" + searchLocation + "' = '', cl.location_eng LIKE '%%', cl.location_eng =?) ";
              
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchCameraNo);
            stmt.setString(2, searchCameraType);
            stmt.setString(3, searchLocation);
            
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CityLoactionModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public List<String> searchCityName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT cl.location FROM city_location as cl "
                + " GROUP BY cl.location ORDER BY cl.location";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String location = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location"));
                if (location.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(location);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("searchCityName ERROR inside CityLocationModell - " + e);
        }
        return list;
    }

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select location_eng\n" +
                       "from city_location\n" +
                       "Group By location_eng\n" +
                       "order By city_location_id desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("location_eng");
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCityName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }
    
    
    
    


    public List<String> getZone(String q) {
        List<String> list = new ArrayList<String>();

        String query = " select z.zone from zone_new as z "
                       +" GROUP BY z.zone  ORDER BY z.zone ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("zone"));
                if (zone_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCityName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }

    public List<String> getWardName(String q, String zone_name) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        zone_name = krutiToUnicode.convert_to_unicode(zone_name);
        String query = " SELECT w.ward_name  FROM ward AS w, zone_new AS z "
                + " WHERE   w.zone_new_id = z.zone_new_id "
                + " AND IF('" + zone_name + "'='', zone like '%%', zone ='" + zone_name + "') "
                + " Group by ward_name ";

        
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(query);
            // pstmt.setString(1, zone_name);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String ward_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("ward_name"));
                if (ward_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Ward No exists.");
            }
        } catch (Exception e) {
            System.out.println("getWardType ERROR inside WardTypeModel - " + e);
        }
        return list;
    }


    public List<String> getAreaName(String q, String ward_name, String zone_name) {
        List<String> list = new ArrayList<String>();
        zone_name = krutiToUnicode.convert_to_unicode(zone_name);
        ward_name = krutiToUnicode.convert_to_unicode(ward_name);
//        String query = " SELECT a.area_name "
//                + "FROM area AS a ,ward AS w, zone AS z "
//                + "WHERE a.ward_id = w.ward_id "
//                + "AND w.zone_id = z.zone_id "
//                + "AND IF('" + ward_name + "'='', ward_name like '%%', ward_name ='" + ward_name + "') "
//                + "AND IF('" + zone_name + "'='', zone_name like '%%', zone_name ='" + zone_name + "') "
//                + "Group by area_name";
        String query = " SELECT a.area_name "
                + "FROM area AS a ,ward AS w, zone_new AS z "
                + "WHERE a.ward_id = w.ward_id "
                + "AND w.zone_new_id = z.zone_new_id "
                + "AND IF('" + ward_name + "'='', ward_name like '%%', ward_name ='" + ward_name + "') "
                + "AND IF('" + zone_name + "'='', zone like '%%', zone ='" + zone_name + "') "
                + "Group by area_name";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String areaName = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("area_name"));
                if (areaName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(areaName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Area Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:AreaType1Model-" + e);
        }
        return list;
    }
    public List<String> getSearchCameraNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select camera_no\n" +
                       "from camera_info\n" +
                       "Group By camera_no\n" +
                       "Order By camera_info_id";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String camera_no = rset.getString("camera_no");
                if (camera_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(camera_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getSearchCameraNo ERROR inside CityLocationModel - " + e);
        }
        return list;
    }
    
    public List<String> getSearchCameraType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select camera_type\n" +
                       "from camera_info ci,camera_type ct\n" +
                       "where ci.camera_type_id = ct.camera_type_id\n" +
                       "Group By camera_type\n" +
                       "Order By camera_type";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String camera_type = rset.getString("camera_type");
                if (camera_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(camera_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getSearchCameraNo ERROR inside CityLocationModel - " + e);
        }
        return list;
    }
    
    public List<String> getSearchCityLocation(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select location_eng\n" +
                       "from camera_info ci,city_location cl\n" +
                       "where ci.city_location_id = cl.city_location_id\n" +
                       "Group By location_eng\n" +
                       "order By camera_info_id desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String location_eng = rset.getString("location_eng");
                if (location_eng.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(location_eng);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getSearchCameraNo ERROR inside CityLocationModel - " + e);
        }
        return list;
    }

    
    

    public List<String> getZoneName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select zone from city_location as cl ,zone_new as z where cl.zone_new_id=z.zone_new_id"
                + " GROUP BY zone ORDER BY zone";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("zone"));
                if (zone.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }

    public List<String> getLocationName(String q, String location_code) {
        List<String> list = new ArrayList<String>();
        String query = "select location from city_location "
                + " WHERE IF('" + location_code + "'='', location_code LIKE '%%', location_code='" + location_code + "') "
                + " GROUP BY location ORDER BY location";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String location = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location"));
                if (location.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(location);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }

    public List<String> getLocationCode(String q, String location_name) {
        List<String> list = new ArrayList<String>();
        location_name = krutiToUnicode.convert_to_unicode(location_name);
        String query = "select location_code from city_location "
                + " WHERE IF('" + location_name + "'='', location LIKE '%%', location='" + location_name + "') "
                + " GROUP BY location ORDER BY location";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String location = rset.getString("location_code");
                if (location.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(location);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }

    public int getAreaeId(String area) {
        //List<String> list = new ArrayList<String>();
        int area_id = 0;
        area = krutiToUnicode.convert_to_unicode(area);
        String query = "select area_id from area where area_name='" + area + "'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                area_id = rset.getInt("area_id");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside CityLocationModel - " + e);
        }
        return area_id;
    }
    
    public List<String> getCameraType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select camera_type\n" +
                       "from camera_type ct\n" +
                       "group by camera_type";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("camera_type");
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCityName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }
    public List<String> getCameraFacing(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select camera_facing\n" +
                       "from camera_facing ct\n" +
                       "group by camera_facing";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("camera_facing");
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCityName ERROR inside CityLocationModel - " + e);
        }
        return list;
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
            System.out.println("statusTypemodel closeConnection() Error: " + e);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import com.tp.tableClasses.CityLocationBean;
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
 * @author Administrator
 */
public class CityLocationModel {

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

    public int deleteRecord(int status_type_id) {
        String query = " DELETE FROM city_location WHERE city_location_id = " + status_type_id;
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

    public int updateRecord(CityLocationBean cityLocationTypeBean) {
        String query = " UPDATE city_location SET city_location_id=?,  zone_new_id=?,location=?, remark=? WHERE city_location_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            pstmt.setInt(1, cityLocationTypeBean.getCity_location_id());
            pstmt.setInt(2, cityLocationTypeBean.getZone_id());
           // pstmt.setString(3, cityLocationTypeBean.getZone());
            pstmt.setString(3, krutiToUnicode.convert_to_unicode(cityLocationTypeBean.getLocation()));
            pstmt.setString(4, krutiToUnicode.convert_to_unicode(cityLocationTypeBean.getRemark()));
            pstmt.setInt(5, cityLocationTypeBean.getCity_location_id());
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

    public int insertRecord(CityLocationBean cityLocationeBean) {
        int rowsAffected = 0;
        String query = "INSERT INTO city_location (zone_new_id,location,remark) VALUES (?,?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, cityLocationeBean.getZone_id());
           // pstmt.setString(2, cityLocationeBean.getZone());
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(cityLocationeBean.getLocation()));
            pstmt.setString(3, krutiToUnicode.convert_to_unicode(cityLocationeBean.getRemark()));
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: Data inserting: " + e);
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

    public List<CityLocationBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchCityName, String searchZoneName, String searchLocationName) {
        List<CityLocationBean> list = new ArrayList<CityLocationBean>();
        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
        searchLocationName = krutiToUnicode.convert_to_unicode(searchLocationName);

        String query = " SELECT cl.city_location_id,z.zone_new_id,c.city_id,c.city_name,z.zone,cl.location,cl. remark "
                + "FROM city_location as cl,city as c,zone_new as z where cl.zone_new_id=z.zone_new_id and z.city_id=c.city_id "
                + "And IF('" + searchCityName + "' = '', c.city_name LIKE '%%', c.city_name =?) "
                + "And IF('" + searchZoneName + "' = '', z.zone LIKE '%%', z.zone =?) "
                + "And IF('" + searchLocationName + "' = '', cl.location LIKE '%%', cl.location =?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCityName);
            pstmt.setString(2, searchZoneName);
            pstmt.setString(3, searchLocationName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CityLocationBean cityLocationType = new CityLocationBean();
                cityLocationType.setCity_location_id(rset.getInt("city_location_id"));
                cityLocationType.setCity(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name")));
                cityLocationType.setZone(rset.getString("zone"));
                cityLocationType.setLocation(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
                cityLocationType.setRemark(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("remark")));
                list.add(cityLocationType);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public List<CityLocationBean> showAllData(String searchCityName, String searchZoneName, String searchLocationName) {
        List<CityLocationBean> list = new ArrayList<CityLocationBean>();
        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
        searchLocationName = krutiToUnicode.convert_to_unicode(searchLocationName);

        String query = " SELECT cl.city_location_id,z.zone_new_id,c.city_id,c.city_name,z.zone,cl.location,cl. remark "
                + "FROM city_location as cl,city as c,zone_new as z where cl.zone_new_id=z.zone_new_id and z.city_id=c.city_id  "
                + "And IF('" + searchCityName + "' = '', c.city_name LIKE '%%', c.city_name =?) "
                + "And IF('" + searchZoneName + "' = '', z.zone LIKE '%%', z.zone =?) "
                + "And IF('" + searchLocationName + "' = '', cl.location LIKE '%%', cl.location =?) ";
        //+ " WHERE IF('" + searchStatusType + "' = '', status_type LIKE '%%', status_type =?) "
        //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCityName);
            pstmt.setString(2, searchZoneName);
            pstmt.setString(3, searchLocationName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CityLocationBean cityLocationType = new CityLocationBean();
                cityLocationType.setCity_location_id(rset.getInt("city_location_id"));
                cityLocationType.setCity(rset.getString("city_name"));
                cityLocationType.setZone(rset.getString("zone"));
                cityLocationType.setLocation(rset.getString("location"));
                cityLocationType.setRemark(rset.getString("remark"));
                list.add(cityLocationType);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchCityName, String searchZoneName, String searchLocationName) {
        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
        searchLocationName = krutiToUnicode.convert_to_unicode(searchLocationName);
        String query = " SELECT Count(*) "
                 + "FROM city_location as cl,city as c,zone_new as z where cl.zone_new_id=z.zone_new_id and z.city_id=c.city_id "
                + "And IF('" + searchCityName + "' = '', c.city_name LIKE '%%', c.city_name =?) "
                + "And IF('" + searchZoneName + "' = '', z.zone LIKE '%%', z.zone =?) "
                + "And IF('" + searchLocationName + "' = '', cl.location LIKE '%%', cl.location =?) ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchCityName);
            stmt.setString(2, searchZoneName);
            stmt.setString(3, searchLocationName);
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
        String query = " SELECT c.city_name FROM city_location as cl,city as c,zone_new as z where c.city_id=z.city_id and cl.zone_new_id=z.zone_new_id"
                + " GROUP BY c.city_name ORDER BY c.city_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name"));
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
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
        String query = "select city_name from city "
                + " GROUP BY city_name ORDER BY city_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name"));
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

    public List<String> getZone(String q,String city) {
        List<String> list = new ArrayList<String>();
        city = krutiToUnicode.convert_to_unicode(city);
        String query = "select z.zone from zone_new as z ,city as c where z.city_id=c.city_id and c.city_name='"+city+"'"
                + " GROUP BY z.zone  ORDER BY z.zone ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("zone");
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

    public List<String> getZoneName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select zone from city_location as cl ,zone_new as z where cl.zone_new_id=z.zone_new_id"
                + " GROUP BY zone ORDER BY zone";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone = rset.getString("zone");
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

    public List<String> getLocationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select location from city_location "
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

    public int getZoneId(String cityName, String zone) {
        //List<String> list = new ArrayList<String>();
        int city_id = 0;
        cityName = krutiToUnicode.convert_to_unicode(cityName);
        String query = "select zone_new_id from zone_new zn, city c where zn.city_id = c.city_id AND city_name='"+ cityName +"' AND zone='"+zone+"'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                city_id = rset.getInt("zone_new_id");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside CityLocationModel - " + e);
        }
        return city_id;
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.dataEntry.model;

import com.tp.tableClasses.ZoneNewBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.*;
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
public class ZoneNewModel {

    private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public byte[] generateMapReport(String jrxmlFilePath, List<ZoneNewBean> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in ZoneModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateZoneXlsRecordList(String jrxmlFilePath, List list) {
        ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
        //  HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("CityStatusModel generatReport() JRException: " + e);
        }
        return bytArray;
    }

    public List<ZoneNewBean> showAllData(String searchZone) {
        searchZone = krutiToUnicode.convert_to_unicode(searchZone);
        ArrayList<ZoneNewBean> list = new ArrayList<ZoneNewBean>();
        String query = "SELECT z.zone_new_id,z.zone,z.description,c.city_name "
                + "FROM zone_new as z,city as c where z.city_id=c.city_id "
                + "And IF('" + searchZone + "'='',z.zone LIKE '%%',z.zone=?)  ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchZone);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ZoneNewBean zoneBean = new ZoneNewBean();
                zoneBean.setZoneId(rset.getInt(1));
                zoneBean.setZoneName(rset.getString(2));
                zoneBean.setZoneDescription(rset.getString(3));
                zoneBean.setCityName(rset.getString(4));


                list.add(zoneBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData --- ZoneModel : " + e);
        }

        return list;
    }

    public List<String> getZone(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT zone_new_id, zone FROM zone_new GROUP BY zone ORDER BY zone ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("zone"));
                if (zone_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Zone exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZone ERROR inside ZoneModel - " + e);
        }
        return list;
    }

    public List<String> getCity(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT city_name FROM city GROUP BY city_name ORDER BY city_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name"));
                if (zone_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such City exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCity ERROR inside ZoneModel - " + e);
        }
        return list;
    }

    public List<String> getZoneName(String q, String cityName) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT zone_name FROM zone where zone.city_id=(select country.city_id from country where country.city_name='" + cityName + "') GROUP BY zone_name ORDER BY  zone_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_type = rset.getString("zone_name");
                if (zone_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Zone exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside ZoneModel - " + e);
        }
        return list;
    }

    public void updateRecord(String cityName, String zoneId, String zoneName, String zone_no, String zoneDescription) {
        PreparedStatement presta = null;
        try {
            cityName = krutiToUnicode.convert_to_unicode(cityName);
            zoneName = krutiToUnicode.convert_to_unicode(zoneName);
            zoneDescription = unicodeToKruti.Convert_to_Kritidev_010(zoneDescription);
            String query = "update zone_new set zone='" + zoneName.trim() + "', zone_no='" + zone_no + "', description='" + zoneDescription.trim() + "',"
                    + "city_id=(select city_id from city where '" + cityName.trim() + "'=city.city_name)"
                    + "where zone_new_id=" + Integer.parseInt(zoneId);
            presta = connection.prepareStatement(query);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = i + " Record updated successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not updated successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in updateRecord ---- ZoneModel : " + e);
        }
    }

    public void deleteRecord(int zoneId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("delete from zone_new where zone_new_id=?");
            presta.setInt(1, zoneId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            message = "Record not deleted due to dependency";
            messageBGColor = "red";
            System.out.println("Error in deleteRecord ---- ZoneModel - " + e);
        }
    }

    public ArrayList<ZoneNewBean> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String searchZone) {
        ArrayList<ZoneNewBean> list = new ArrayList<ZoneNewBean>();
        searchZone = krutiToUnicode.convert_to_unicode(searchZone);
        String query = "SELECT z.zone_new_id, z.zone, z.zone_no, z.description, c.city_name "
                + "FROM zone_new as z,city as c where z.city_id=c.city_id "
                + "And IF('" + searchZone + "'='',z.zone LIKE '%%',z.zone=?)  "
                + "order by z.zone limit " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchZone);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ZoneNewBean zoneBean = new ZoneNewBean();
                zoneBean.setZoneId(rset.getInt(1));
                zoneBean.setZoneName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(2)));
                zoneBean.setZone_no(rset.getString(3));
                zoneBean.setZoneDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(4)));
                zoneBean.setCityName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(5)));
                list.add(zoneBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod in ZoneModel " + e);
        }
        return list;
    }

    public int getTotalRowsInTable(String searchZone) {
        searchZone = krutiToUnicode.convert_to_unicode(searchZone);
        String query = " SELECT Count(*) "
                + "FROM zone_new as z,city as c where z.city_id=c.city_id "
                + "And IF('" + searchZone + "'='',z.zone LIKE '%%',z.zone=?)  "
                + "order by z.zone ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchZone);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows ZoneModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public void insertRecord(String[] cityName, String[] zone_id, String[] zoneName, String[] zone_no, String[] zoneDescription) {
        int rowAffected = 0;
        int rowNotAffected = 0;
        message = "";
        for (int i = 0; i < zone_id.length; i++) {
            if (zone_id[i].equals("1")) {
                PreparedStatement presta;
                try {
                    cityName[i] = krutiToUnicode.convert_to_unicode(cityName[i]);
                    zoneName[i] = krutiToUnicode.convert_to_unicode(zoneName[i]);
                    zoneDescription[i] = unicodeToKruti.Convert_to_Kritidev_010(zoneDescription[i]);
                    String query2 = "insert into zone_new(zone, zone_no, description, city_id) "
                            + " values('" + zoneName[i].trim() + "', '" + zone_no[i].trim() + "', "
                            + " '" + zoneDescription[i].trim() + "', (select city_id from city where city_name='" + cityName[i].trim() + "'))";
                    PreparedStatement pstmt = connection.prepareStatement(query2);
                    rowAffected = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Error in addAllRecords inside ZoneModel---- " + e);
                }
            }
        }
        if (rowAffected > 0) {
            message = " Record inserted... ";
            messageBGColor = "yellow";
        }
        if (rowAffected == 0) {
            message = message + "(" + rowAffected + " : Record not inserted some error!)";
            messageBGColor = "red";
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ZoneModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", user, password);
        } catch (Exception e) {
            System.out.println("ZoneModel setConnection error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageBGColor() {
        return messageBGColor;
    }
}

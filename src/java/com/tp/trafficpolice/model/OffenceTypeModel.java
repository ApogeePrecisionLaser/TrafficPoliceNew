/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import java.sql.Connection;
import com.tp.tableClasses.OffenceTypeBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author JPSS
 */
public class OffenceTypeModel {

    private Connection connection;
    private String driverClass, connectionString, db_username, db_password, message, msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

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

    public int deleteRecord(int offence_type_id) {
        String query = " DELETE FROM offence_type WHERE offence_type_id = " + offence_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("OffenceModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

      public ByteArrayOutputStream generateAlertReportInExcel(String jrxmlFilePath, List list) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();

        try {

          //  mymap.put("feeder_filter1", feeder_filter1);


            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, beanColDataSource);
            JRXlsExporter exporter = new JRXlsExporter();

            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("OffenceTypeModel Error: in generateExel() JRException: " + e);
        }
        return baos;
    }


    /*  public int updateRecord(OffenceTypeBean offenceTypeBean) {
    String query = " UPDATE status_type SET  status_type, remark=? WHERE status_type_id = ? ";
    int rowsAffected = 0;
    try {
    PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

    pstmt.setString(1, offenceTypeBean.getStatus_type());
    pstmt.setString(2, offenceTypeBean.getRemark());
    pstmt.setInt(5, offenceTypeBean.getStatus_type_id());
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
    }*/

    public int insertRecord(OffenceTypeBean offenceTypeBean) {
        int rowsAffected = 0;
         SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd ");
        String query = "INSERT INTO offence_type (offence_code,act_origin_id,vehicle_type_id,offence_type,"
                + " remark,act,penalty_amount,from_date,to_date,active,commercial_type_id,have_second_offence,second_offence_penalty) "
                + "VALUES (?,?,?,?, ?, ?, ?, ?, ?,(SELECT IF(CURDATE() <?,'Y','N') ),?,?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, offenceTypeBean.getOffence_code());
            pstmt.setInt(2, getAct_origin_id(offenceTypeBean.getAct_origin()));

            pstmt.setInt(3, getVehicle_type_id(offenceTypeBean.getVehicle_type()));

            pstmt.setString(4, krutiToUnicode.convert_to_unicode(offenceTypeBean.getOffence_type()));
            pstmt.setString(5, offenceTypeBean.getRemark());
            pstmt.setString(6, offenceTypeBean.getAct());
            pstmt.setString(7, offenceTypeBean.getPenalty_amount());
            pstmt.setDate(8, convertToSqlDate(offenceTypeBean.getFrom_date()));
            pstmt.setDate(9, convertToSqlDate(offenceTypeBean.getTo_date()));
            pstmt.setDate(10, convertToSqlDate(offenceTypeBean.getTo_date()));
            pstmt.setInt(11, getCommercialTypeId(offenceTypeBean.getTarnsport_type()));
            pstmt.setString(12, offenceTypeBean.getHave_second_offence());
            pstmt.setString(13, offenceTypeBean.getSecond_offence_penalty());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {

            System.out.println("Error: Offence inserting: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inseRted");
        } else {
            message = "Record not  saved successfully......Some error";
            msgBgColor = COLOR_OK;
            System.out.println("not inserted");
        }
        return rowsAffected;
    }

    public List<OffenceTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchOffenceType, String searchOffenceCode,String searchAct,String  searchActOrigin) {
        List<OffenceTypeBean> list = new ArrayList<OffenceTypeBean>();

        /*String query = " SELECT o.offence_type_id,o.offence_code, o.offence_type, o.remark,act,o.penalty_amount,make,model,"
                + " o.from_date,o.to_date,o.active ,v.vehicle_type,com.commercial_type,a.act_origin "
                + " FROM offence_type as o left  join  commercial_type com on  o.commercial_type_id =com.commercial_type_id "
                + " left  join  model md on o.model_id=md.model_id left  join vehicle_type as v on md.vehicle_type_id=v.vehicle_type_id left  join make  m  on m.make_id=md.make_id ,act_origin as a "
                + "where o.act_origin_id=a.act_origin_id  "
                + " AND IF('" + searchOffenceType + "' = '', o.offence_type LIKE '%%',o.offence_type =?) "
                + " AND IF('" + searchOffenceCode + "' = '', o.offence_code LIKE '%%',o.offence_code =?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;*/

        String query = " SELECT o.offence_type_id,o.offence_code, o.offence_type, o.remark,act,o.penalty_amount,"
                + " o.from_date,o.to_date,o.active ,v.vehicle_type,com.commercial_type,a.act_origin,have_second_offence,second_offence_penalty "
                + " FROM offence_type as o left  join  commercial_type com on  o.commercial_type_id =com.commercial_type_id "


                + " left  join  vehicle_type as v on o.vehicle_type_id=v.vehicle_type_id, act_origin as a "


                + "where o.act_origin_id=a.act_origin_id  "
                + " AND IF('" + searchOffenceType.trim() + "' = '', o.offence_type LIKE '%%',o.offence_type =?) "
                + " AND IF('" + searchOffenceCode + "' = '', o.offence_code LIKE '%%',o.offence_code =?) "

                + " AND IF('" + searchAct.trim() + "' = '', o.act LIKE '%%',o.act ='" + searchAct.trim() + "') "
                + " AND IF('" + searchActOrigin + "' = '', a.act_origin LIKE '%%', a.act_origin ='" + searchActOrigin + "') "

                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOffenceType.trim());
            pstmt.setString(2, searchOffenceCode);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OffenceTypeBean offenceTypeBean = new OffenceTypeBean();
                offenceTypeBean.setOffence_type_id(rset.getInt("offence_type_id"));
                offenceTypeBean.setOffence_code(rset.getString("offence_code"));
                offenceTypeBean.setOffence_type(rset.getString("offence_type"));
                offenceTypeBean.setAct(rset.getString("act"));
                offenceTypeBean.setPenalty_amount(rset.getString("penalty_amount"));
                offenceTypeBean.setFrom_date(rset.getString("from_date"));
                offenceTypeBean.setTo_date(rset.getString("to_date"));
                offenceTypeBean.setActive(rset.getString("active"));
                offenceTypeBean.setRemark(rset.getString("remark"));
                offenceTypeBean.setVehicle_type(rset.getString("vehicle_type"));
                offenceTypeBean.setAct_origin(rset.getString("act_origin"));
                offenceTypeBean.setTarnsport_type(rset.getString("commercial_type"));
                offenceTypeBean.setHave_second_offence(rset.getString("have_second_offence"));
                offenceTypeBean.setSecond_offence_penalty(rset.getString("second_offence_penalty"));
                //offenceTypeBean.setMake(rset.getString("make"));
               // offenceTypeBean.setModel(rset.getString("model"));

                list.add(offenceTypeBean);

            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public List<OffenceTypeBean> showAllData(String searchOffenceType, String searchOffenceCode,String  searchAct,String  searchActOrigin) {
        List<OffenceTypeBean> list = new ArrayList<OffenceTypeBean>();

        /*String query = " SELECT o.offence_type_id,o.offence_code, o.offence_type, o.remark,act,o.penalty_amount,"
                + "  o.from_date,o.to_date,o.active ,v.vehicle_type,com.commercial_type,a.act_origin "
                + " FROM offence_type as o left  join  commercial_type com on  o.commercial_type_id =com.commercial_type_id "
                + " left  join  model md on o.model_id=md.model_id left  join vehicle_type as v on md.vehicle_type_id=v.vehicle_type_id,act_origin as a "
                + "  where  o.act_origin_id=a.act_origin_id "
                + " AND IF('" + searchOffenceType + "' = '', o.offence_type LIKE '%%',o.offence_type =?) "
                + " AND IF('" + searchOffenceCode + "' = '', o.offence_code LIKE '%%',o.offence_code =?) ";*/
        String query = " SELECT o.offence_type_id,o.offence_code, o.offence_type, o.remark,act,o.penalty_amount,"
                + "   o.from_date,o.to_date,o.active ,v.vehicle_type,com.commercial_type,a.act_origin "
                + "  FROM offence_type as o left  join  commercial_type com on  o.commercial_type_id =com.commercial_type_id "
                + "  left  join  vehicle_type as v on o.vehicle_type_id=v.vehicle_type_id,act_origin as a "
                + "  where  o.act_origin_id=a.act_origin_id AND a.act_origin not Like '%Dummy%' "

                + " AND IF('" + searchOffenceType.trim() + "' = '', o.offence_type LIKE '%%',o.offence_type =?) "
                + " AND IF('" + searchOffenceCode + "' = '', o.offence_code LIKE '%%',o.offence_code =?) "
                + " AND IF('" + searchAct.trim() + "' = '', o.act LIKE '%%',o.act ='" + searchAct.trim() + "') "
                + " AND IF('" + searchActOrigin + "' = '', a.act_origin LIKE '%%', a.act_origin ='" + searchActOrigin + "') ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOffenceType.trim());
            pstmt.setString(2, searchOffenceCode);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OffenceTypeBean offenceTypeBean = new OffenceTypeBean();
                offenceTypeBean.setOffence_type_id(rset.getInt("offence_type_id"));
                offenceTypeBean.setOffence_code(rset.getString("offence_code"));
                offenceTypeBean.setOffence_type(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offence_type")));
//                offenceTypeBean.setAct(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("act")));
                offenceTypeBean.setAct(rset.getString("act"));
                offenceTypeBean.setPenalty_amount(rset.getString("penalty_amount"));
                offenceTypeBean.setFrom_date(rset.getString("from_date"));
                offenceTypeBean.setTo_date(rset.getString("to_date"));
                offenceTypeBean.setActive(rset.getString("active"));
                offenceTypeBean.setRemark(rset.getString("remark"));
                offenceTypeBean.setVehicle_type(rset.getString("vehicle_type"));
                offenceTypeBean.setAct_origin(rset.getString("act_origin"));
                offenceTypeBean.setTarnsport_type(rset.getString("commercial_type"));
                list.add(offenceTypeBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }



    public int getNoOfRows(String searchOffenceType, String searchOffenceCode,String searchAct,String  searchActOrigin) {
        /*String query = " SELECT Count(*) "
                + " FROM offence_type as o left  join  commercial_type com on  o.commercial_type_id =com.commercial_type_id "
                + " left  join  model md on o.model_id=md.model_id left  join vehicle_type as v on md.vehicle_type_id=v.vehicle_type_id,act_origin as a "
                + "  where  o.act_origin_id=a.act_origin_id "
                + " and  IF('" + searchOffenceType + "' = '', offence_type LIKE '%%',offence_type =?) "
                + " AND IF('" + searchOffenceCode + "' = '', o.offence_code LIKE '%%',o.offence_code =?) "
                + " ORDER BY offence_type ";*/
        String query = " SELECT Count(*) "
                + " FROM offence_type as o left  join  commercial_type com on  o.commercial_type_id =com.commercial_type_id "
                + " left  join  vehicle_type as v on o.vehicle_type_id=v.vehicle_type_id,act_origin as a "
                + "  where  o.act_origin_id=a.act_origin_id "
                + " and  IF('" + searchOffenceType + "' = '', offence_type LIKE '%%',offence_type =?) "
                + " AND IF('" + searchOffenceCode + "' = '', o.offence_code LIKE '%%',o.offence_code =?) "

                + " AND IF('" + searchAct + "' = '', o.act LIKE '%%',o.act ='" + searchAct + "') "
                + " AND IF('" + searchActOrigin + "' = '', a.act_origin LIKE '%%', a.act_origin ='" + searchActOrigin + "') "

                + " ORDER BY offence_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchOffenceType);
            stmt.setString(2, searchOffenceCode);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows State type model" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public List<String> getOffenceType(String q,String act_origin) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT offence_type_id, offence_type FROM offence_type  o left  join act_origin as a on  o.act_origin_id=a.act_origin_id "
                + " where   if('"+ act_origin + "'='' , act_origin like '%%' , act_origin='"+ act_origin +"') "
                + "  GROUP BY offence_type ORDER BY offence_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String offence_type = rset.getString("offence_type");
                if (offence_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(offence_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Offence exists.......");
            }
        } catch (Exception e) {
            System.out.println("getOffenceType ERROR inside OffenceTypeModel - " + e);
        }
        return list;
    }

    public List<String> getOffenceCode(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT offence_type_id, offence_code FROM offence_type GROUP BY offence_code ORDER BY offence_code";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String offence_code = rset.getString("offence_code");
                if (offence_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(offence_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Offence exists.......");
            }
        } catch (Exception e) {
            System.out.println("getOffenceType ERROR inside OffenceTypeModel - " + e);
        }
        return list;
    }

    public List<String> getModelType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT model FROM model GROUP BY model ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String vehicle_type = rset.getString("model");
                if (vehicle_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(vehicle_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Vehicle exists.......");
            }
        } catch (Exception e) {
            System.out.println("getVehicleType ERROR inside OffenceTypeModel - " + e);
        }
        return list;
    }

    public List<String> getVehicleType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT vehicle_type FROM vehicle_type GROUP BY vehicle_type ";
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
                list.add("No such Vehicle exists.......");
            }
        } catch (Exception e) {
            System.out.println("getVehicleType ERROR inside OffenceTypeModel - " + e);
        }
        return list;
    }

    public int getVehicle_type_id(String vehicle_type) {
        int model_id = 0;
        try {

            String query = "Select vehicle_type_id from vehicle_type where vehicle_type='" + vehicle_type + "'  ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                model_id = rset.getInt("vehicle_type_id");

            }
        } catch (Exception e) {
        }
        return model_id;
    }

    public int getModel_type_id(String model_type) {
        int model_id = 0;
        try {

            String query = "Select model_id from model where model='" + model_type + "'  ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                model_id = rset.getInt("model_id");

            }
        } catch (Exception e) {
        }
        return model_id;
    }

    public int getCommercialTypeId(String commercial_type) {
        int commercial_type_id = 0;
        try {

            String query = "Select commercial_type_id from commercial_type where commercial_type='" + commercial_type + "'  ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                commercial_type_id = rset.getInt("commercial_type_id");

            }
        } catch (Exception e) {
        }
        return commercial_type_id;
    }

    public List<String> getActOrigin(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT act_origin FROM act_origin GROUP BY act_origin ";
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
                list.add("No such Act Origin exists.......");
            }
        } catch (Exception e) {
            System.out.println("getActOrigin ERROR inside OffenceTypeModel - " + e);
        }
        return list;
    }
public List<String> getSearchActOrigin(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT act_origin FROM offence_type of left  join  act_origin ac on  ac.act_origin_id=of.act_origin_id group by  act_origin ";
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
                list.add("No such Act Origin exists.......");
            }
        } catch (Exception e) {
            System.out.println("getActOrigin ERROR inside OffenceTypeModel - " + e);
        }
        return list;
    }



  public List<String> getSearchAct(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT act FROM offence_type  group by  act order  by  act";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String act_origin = rset.getString("act");
                if (act_origin.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(act_origin);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Act Origin exists.......");
            }
        } catch (Exception e) {
            System.out.println("getSearchAct ERROR inside OffenceTypeModel - " + e);
        }
        return list;
    }



public List<String> getTransportationType(String q, String vehicle_type) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT commercial_type FROM commercial_type "
                + "GROUP BY commercial_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String commercial_type = rset.getString("commercial_type");
                if (commercial_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(commercial_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Act Origin exists.......");
            }
        } catch (Exception e) {
            System.out.println("getActOrigin ERROR inside OffenceTypeModel - " + e);
        }
        return list;
    }

    public int getAct_origin_id(String act_origin) {
        int act_origin_id = 0;
        try {

            String query = "Select act_origin_id from act_origin where act_origin='" + act_origin + "' ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            System.out.println(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                act_origin_id = rset.getInt("act_origin_id");

            }
        } catch (Exception e) {
        }
        return act_origin_id;
    }

   public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    } 

 /* public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }
*/

    public Connection getConnection() {
        return connection;
    }

    public int updateRecord(OffenceTypeBean offenceTypeBean) {
        String query = " UPDATE offence_type SET  offence_type=?, remark=?,act=?,penalty_amount=?,"
                + "from_date=?,to_date=?,vehicle_type_id=?,act_origin_id=?,offence_code=?, "
                + "commercial_type_id=?,have_second_offence=?,second_offence_penalty=? WHERE offence_type_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            pstmt.setString(1, offenceTypeBean.getOffence_type());
            pstmt.setString(2, offenceTypeBean.getRemark());
            pstmt.setString(3, offenceTypeBean.getAct());
            pstmt.setString(4, offenceTypeBean.getPenalty_amount());
            pstmt.setString(5, offenceTypeBean.getFrom_date());
            pstmt.setString(6, offenceTypeBean.getTo_date());
            pstmt.setInt(7, getVehicle_type_id(offenceTypeBean.getVehicle_type()));
            pstmt.setInt(8, getAct_origin_id(offenceTypeBean.getAct_origin()));
            pstmt.setString(9, offenceTypeBean.getOffence_code());
            pstmt.setInt(10, getCommercialTypeId(offenceTypeBean.getTarnsport_type()));
            pstmt.setString(11,offenceTypeBean.getHave_second_offence());
            pstmt.setString(12,offenceTypeBean.getSecond_offence_penalty());
            
            pstmt.setInt(13, offenceTypeBean.getOffence_type_id());

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

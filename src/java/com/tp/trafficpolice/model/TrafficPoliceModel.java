/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

//import com.sor.dataEntry.model.*;
import com.tp.general.model.ReceiptBookModel;
import com.tp.licenseVehicle.model.VehicleDetailModel;
import com.tp.tableClasses.TrafficPolice;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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
public class TrafficPoliceModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    private String msg;
    UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
    KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_userName, db_password);
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
            System.out.println("TafficPoliceModel generatReport() JRException: " + e);
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

    public int deleteRecord(int traffic_police_id) {
        String query = " DELETE FROM traffic_police WHERE traffic_police_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, traffic_police_id);
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("TrafficPoliceModel deleteRecord() Error: " + e);
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

    public int getNoOfRows(String searchOfficerName, String searchBookNo, String searchOffenceType, String searchActType, String searchVehicleType, String searchVehicleNo, String searchFromDate, String searchToDate, String searchJarayamNo, String searchOffenceCode, String searchOffenderLicenseNo, String receipt_book_no) {
        int noOfRows = 0;
        searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
        searchOffenceType = krutiToUnicode.convert_to_unicode(searchOffenceType);
        try {
            if (!searchFromDate.equals("")) {
                //String da1[] = searchFromDate.split("-");
                //String da2[] = searchToDate.split("-");
                //searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                //searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }
            

            String query = "SELECT sum(if(count=1,1,1)) FROM (SELECT count(tp.traffic_police_id) AS count from traffic_police as tp "
                    + "left join officer_book as ob on ob.book_no=tp.book_no AND ob.book_revision_no = tp.book_revision_no "
                    + "left join (trafficpolice_receipt_map trm, receipt_book rb) ON rb.receipt_book_id = trm.receipt_book_id AND trm.traffic_police_id = tp.traffic_police_id "
                    + "left join key_person as kp on ob.key_person_id=kp.key_person_id "
                    + "left join city_location as cl on cl.city_location_id=tp.city_location_id "
                    + "left join zone_new as z on z.zone_new_id=cl.zone_new_id "
                    + "left join city as c on c.city_id=z.city_id "
                    + "left join city as ofc on ofc.city_id=tp.city_id "
                    + "left join processing_type as pt on pt.processing_type_id=tp.processing_type_id "
                    + "LEFT JOIN (traffic_offence_map tom, offence_type as ot) ON tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                    + "LEFT JOIN vehicle_type vt ON vt.vehicle_type_id=ot.vehicle_type_id "
                    + " WHERE IF ('" + searchOfficerName + "'='',key_person_name LIKE '%%' OR key_person_name IS NULL, key_person_name= ?) " //tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id " + " AND vt.vehicle_type_id=ot.vehicle_type_id AND
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%' OR tp.book_no IS NULL, tp.book_no = ?) "
                    + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%' OR ot.offence_type IS NULL, ot.offence_type = ?) "
                    + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%' OR ot.act IS NULL, ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%' OR vt.vehicle_type IS NULL, vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%' OR tp.vehicle_no IS NULL, tp.vehicle_no = ?) "
                    + " AND IF ('" + searchJarayamNo + "'='',tp.jarayam_no LIKE '%%' OR tp.jarayam_no IS NULL, tp.jarayam_no = ?) "
                    + " AND IF ('" + searchOffenceCode + "'='',ot.offence_code LIKE '%%' OR ot.offence_code IS NULL, ot.offence_code = ?) "
                    + " AND IF ('" + searchOffenderLicenseNo + "'='', tp.offender_license_no LIKE '%%' OR tp.offender_license_no IS NULL, tp.offender_license_no = ?) "
                    + " AND IF ('" + receipt_book_no + "'='',rb.receipt_book_no LIKE '%%' OR rb.receipt_book_no IS NULL,rb.receipt_book_no = '" + receipt_book_no + "') "
                    + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,( (STR_TO_DATE(tp.offence_date, '%Y-%m-%d')) Between STR_TO_DATE('" + searchFromDate + "', '%d-%m-%Y') AND STR_TO_DATE('" + searchToDate + "', '%d-%m-%Y'))) "
                    //+ " AND IF( '" + searchFromDate + "' = '' , tp.offence_date LIKE '%%' OR tp.offence_date IS NULL , STR_TO_DATE(tp.offence_date, '%Y-%m-%d') = ? ) "//'"+  +"' ) "
                    //+ " AND IF(  '" + searchToDate + "' = '' , tp.offence_date LIKE '%%' OR tp.offence_date IS NULL , STR_TO_DATE(tp.offence_date, '%Y-%m-%d') <= ? )"//'"+ convertToSqlDate(searchToDate) +"' ) "
                    //+ " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))";
                    + " GROUP BY tp.traffic_police_id ORDER BY tp.traffic_police_id) AS noOfRows";//group by tp.traffic_police_id
           /* String query = "select count(*) "
                    + "from traffic_police as tp "
                    + "left join officer_book as ob on ob.book_no=tp.book_no "
                    + "left join key_person as kp on ob.key_person_id=kp.key_person_id "
                    + "left join city_location as cl on cl.city_location_id=tp.city_location_id "
                    + "left join zone_new as z on z.zone_new_id=cl.zone_new_id "
                    + "left join city as c on c.city_id=z.city_id "
                    + "left join processing_type as pt on pt.processing_type_id=tp.processing_type_id"
                    + ", vehicle_type vt, offence_type as ot, traffic_offence_map tom"
                    + " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                    + " AND vt.vehicle_type_id=ot.vehicle_type_id AND IF ('" + searchOfficerName + "'='',key_person_name LIKE '%%',key_person_name= ?) "
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%',tp.book_no = ?) "
                    // + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%',ot.offence_type = ?) "
                    // + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%',ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%',vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%',tp.vehicle_no = ?) "
                    // + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"
                    + " group by tp.traffic_police_id ORDER BY tp.traffic_police_id";
            //  + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"
            // + " group by tp.traffic_police_id ORDER BY tp.traffic_police_id";*/
            PreparedStatement pstmt = connection.prepareStatement(query);
            //System.out.println(query);
            pstmt.setString(1, searchOfficerName);
            pstmt.setString(2, searchBookNo);
            pstmt.setString(3, searchOffenceType);
            pstmt.setString(4, searchActType);
            pstmt.setString(5, searchVehicleType);
            pstmt.setString(6, searchVehicleNo);
            pstmt.setString(7, searchJarayamNo);
            pstmt.setString(8, searchOffenceCode);
            pstmt.setString(9, searchOffenderLicenseNo);
//            if(searchFromDate != null && !searchFromDate.isEmpty())
//                pstmt.setDate(10, convertToSqlDate(searchFromDate));
//            else
//                pstmt.setString(10, "");
//            if(searchToDate != null && !searchToDate.isEmpty())
//                pstmt.setDate(11, convertToSqlDate(searchToDate));
//            else
//                pstmt.setString(11, "");

            ResultSet rset = pstmt.executeQuery();
            rset.next();
            if(rset.getString(1) == null)
                noOfRows = 0;
            else
                noOfRows = Integer.parseInt(rset.getString(1));

        } catch (Exception e) {
            System.out.println("Error:keypersonModel-getNoOfRows-- " + e);
        }
        return noOfRows;
    }

    public List<TrafficPolice> showData(int lowerLimit, int noOfRowsToDisplay, String searchOfficerName, String searchBookNo, String searchOffenceType, String searchActType, String searchVehicleType, String searchVehicleNo, String searchFromDate, String searchToDate, String searchJarayamNo, String searchOffenceCode, String searchOffenderLicenseNo, String receipt_book_no) {
        List<TrafficPolice> list = new ArrayList<TrafficPolice>();
        searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
        searchOffenceType = krutiToUnicode.convert_to_unicode(searchOffenceType);
        String query;
        try {
            if (!searchFromDate.equals("")) {
                //String da1[] = searchFromDate.split("-");
                //String da2[] = searchToDate.split("-");
                //searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                //searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }
            query = "select tp.traffic_police_id, vehicle_no, offender_name, offender_license_no, "
                    + "deposited_amount, ob.book_no,ob.book_revision_no,kp.key_person_name, reciept_no,"
                    + "IF(STR_TO_DATE(tp.offence_date, '%m/%d/%Y'),STR_TO_DATE(tp.offence_date, '%m/%d/%Y'),tp.offence_date) AS offence_date, "
                    + "tp.city_location_id,cl.location,c.city_name,z.zone,pt.processing_type_id,"//STR_TO_DATE(tp.offence_date, '%m/%d/%Y') AS
                    + "relation_type_id, case_no, IF(STR_TO_DATE(tp.case_date, '%m/%d/%Y'),STR_TO_DATE(tp.case_date, '%m/%d/%Y'),tp.case_date) AS  case_date, "
                    + "offender_age, jarayam_no, offender_address,"// STR_TO_DATE(tp.case_date, '%m/%d/%Y') AS
                    + "tp.city_id,pt.processing_type, ofc.city_name AS offender_city, tp.relative_name, tp.relative_salutation, "
                    + "tp.lattitude, tp.longitude, rb.receipt_book_id, rb.receipt_book_no, rb.book_revision, rb.page_no, tp.father_name, "
                    + "tp.remark, tp.vehicle_no_state, tp.vehicle_no_city_code, tp.vehicle_no_series, tp.vehicle_no_digits "
                    + "FROM traffic_police as tp "
                    + "left join officer_book as ob on ob.book_no=tp.book_no AND ob.book_revision_no = tp.book_revision_no "
                    + "left join (trafficpolice_receipt_map trm, receipt_book rb) ON rb.receipt_book_id = trm.receipt_book_id AND trm.traffic_police_id = tp.traffic_police_id "
                    + "left join key_person as kp on kp.key_person_id=kp.key_person_id "
                    + "left join city_location as cl on cl.city_location_id=tp.city_location_id "
                    + "left join zone_new as z on z.zone_new_id=cl.zone_new_id "
                    + "left join city as c on c.city_id=z.city_id "
                    + "left join city as ofc on ofc.city_id=tp.city_id "
                    + "left join processing_type as pt on pt.processing_type_id=tp.processing_type_id "
                    + "LEFT JOIN (traffic_offence_map tom, offence_type as ot) ON tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                    + "LEFT JOIN vehicle_type vt ON vt.vehicle_type_id=ot.vehicle_type_id "
                    + " WHERE IF ('" + searchOfficerName + "'='',key_person_name LIKE '%%' OR key_person_name IS NULL, key_person_name= ?) "// tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id " + " AND vt.vehicle_type_id=ot.vehicle_type_id AND
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%' OR tp.book_no IS NULL,tp.book_no = ?) "
                    + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%' OR ot.offence_type IS NULL,ot.offence_type = ?) "
                    + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%' OR ot.act IS NULL,ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%' OR vt.vehicle_type IS NULL,vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%' OR tp.vehicle_no IS NULL,tp.vehicle_no = ?) "
                    + " AND IF ('" + searchJarayamNo + "'='',tp.jarayam_no LIKE '%%' OR tp.jarayam_no IS NULL,tp.jarayam_no = ?) "
                    + " AND IF ('" + searchOffenceCode + "'='',ot.offence_code LIKE '%%' OR ot.offence_code IS NULL,ot.offence_code = ?) "
                    + " AND IF ('" + searchOffenderLicenseNo + "'='', tp.offender_license_no LIKE '%%' OR tp.offender_license_no IS NULL, tp.offender_license_no = ?) "
                    + " AND IF ('" + receipt_book_no + "'='',rb.receipt_book_no LIKE '%%' OR rb.receipt_book_no IS NULL,rb.receipt_book_no = '" + receipt_book_no + "') "
                    + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,( (STR_TO_DATE(tp.offence_date, '%Y-%m-%d')) Between STR_TO_DATE('" + searchFromDate + "', '%d-%m-%Y') AND STR_TO_DATE('" + searchToDate + "', '%d-%m-%Y'))) "
                    // + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"//%m/%d/%Y
                    + " GROUP BY tp.traffic_police_id ORDER BY tp.traffic_police_id DESC"
                    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOfficerName);
            pstmt.setString(2, searchBookNo);
            pstmt.setString(3, searchOffenceType);
            pstmt.setString(4, searchActType);
            pstmt.setString(5, searchVehicleType);
            pstmt.setString(6, searchVehicleNo);
            pstmt.setString(7, searchJarayamNo);
            pstmt.setString(8, searchOffenceCode);
            pstmt.setString(9, searchOffenderLicenseNo);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TrafficPolice trafficpolice = new TrafficPolice();
                trafficpolice.setTraffic_police_id(rset.getInt("traffic_police_id"));
                trafficpolice.setBook_no(rset.getString("book_no"));
                trafficpolice.setBook_revision_no(rset.getString("book_revision_no"));
                trafficpolice.setKey_person_name(rset.getString("key_person_name"));
                // trafficpolice.setVehicle_type(rset.getString("vehicle_type"));
                trafficpolice.setVehicle_no(rset.getString("vehicle_no"));
                trafficpolice.setOffender_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offender_name")));
                trafficpolice.setOffender_license_no(rset.getString("offender_license_no"));
                trafficpolice.setOffence_place(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location")));
                // trafficpolice.setAct(rset.getString("act"));
                trafficpolice.setReceipt_no(rset.getString("reciept_no"));
                trafficpolice.setDeposited_amount(rset.getString("deposited_amount"));
                // trafficpolice.setOffence_type(rset.getString("offence_type"));
                // trafficpolice.setPenalty_amount(rset.getString("penalty_amount"));

                String ofnc_date = rset.getString("offence_date");
                if(ofnc_date != null && !ofnc_date.isEmpty()){
                //String[] ofnc_date_array  = ofnc_date.split("-");
                //ofnc_date = ofnc_date_array[2] + "-" +  ofnc_date_array[1] + "-" +  ofnc_date_array[0];
                
                String[] ofnc_date_array  = ofnc_date.split(" ");
                String date=ofnc_date_array[0];
                String time="";
                try{
                time=ofnc_date_array[1];
                }catch(Exception e){
                    System.out.println(e);
                }
                String[] setDate =date.split("-"); 
                String myDate_format = setDate[2]+"-"+setDate[1]+"-"+setDate[0];
                ofnc_date=myDate_format+" "+time;
                
                //ofnc_date = ofnc_date_array[2] + "-" +  ofnc_date_array[1] + "-" +  ofnc_date_array[0];
                
                }
                trafficpolice.setOffence_date(ofnc_date);
                // trafficpolice.setAct_origin(rset.getString("act_origin"));
                trafficpolice.setCity(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name")));
                trafficpolice.setZone(rset.getString("zone"));
                trafficpolice.setJarayam_no(rset.getString("jarayam_no"));
                trafficpolice.setProcessing_type(rset.getString("processing_type"));
                // trafficpolice.setRelation_type(rset.getString("relation_type"));
                trafficpolice.setOffender_age(rset.getString("offender_age"));

                String case_date = rset.getString("case_date");
                if(case_date != null && !case_date.isEmpty()){
                String[] case_date_array  = case_date.split("-");
                case_date = case_date_array[2] + "-" +  case_date_array[1] + "-" +  case_date_array[0];
                }
                trafficpolice.setCase_date(case_date);
                trafficpolice.setCase_no(rset.getString("case_no"));
                trafficpolice.setOffender_address(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offender_address")));
                trafficpolice.setSalutation(rset.getString("relative_salutation"));
                trafficpolice.setRelative_name(rset.getString("relative_name"));
                trafficpolice.setOffender_city(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("offender_city")));
                trafficpolice.setLattitude(rset.getString("lattitude"));
                trafficpolice.setLongitude(rset.getString("longitude"));
                trafficpolice.setReceipt_book_id(rset.getInt("receipt_book_id"));
                trafficpolice.setReceipt_book_no(rset.getInt("receipt_book_no"));
                trafficpolice.setReceipt_book_rev_no(rset.getInt("book_revision"));
                trafficpolice.setReceipt_page_no(rset.getInt("page_no"));
                trafficpolice.setFather_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("father_name")));
                trafficpolice.setRemark(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("remark")));
//                trafficpolice.setVehicle_no_state(rset.getString("vehicle_no_state").toUpperCase());
                trafficpolice.setVehicle_no_state(rset.getString("vehicle_no_state"));
                trafficpolice.setVehicle_no_city(rset.getString("vehicle_no_city_code"));
//                trafficpolice.setVehicle_no_series(rset.getString("vehicle_no_series").toUpperCase());
                trafficpolice.setVehicle_no_series(rset.getString("vehicle_no_series"));
                trafficpolice.setVehicle_no_digits(rset.getString("vehicle_no_digits"));
                List<TrafficPolice> offenceTypeList = showOffenceData(trafficpolice , rset.getInt("traffic_police_id"));
                trafficpolice.setOffenceList(offenceTypeList);//
                list.add(trafficpolice);
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
    }

    public List<TrafficPolice> showOffenceData(TrafficPolice trafficpolice1, int traffic_police_id) {
        List<TrafficPolice> list = new ArrayList<TrafficPolice>();
        String query;
        try {
            query = "Select offence_type ,act, if(tom.is_second_offence='YES',o.second_offence_penalty,o.penalty_amount) as penalty_amount, "
                    + "vehicle_type, act_origin, offence_code "
                    + "from traffic_offence_map as tom,traffic_police as tp, offence_type as o,act_origin as a ,"
                    + "vehicle_type as v where o.act_origin_id=a.act_origin_id and o.vehicle_type_id=v.vehicle_type_id "
                    + "and tom.traffic_police_id=tp.traffic_police_id and tom.offence_type_id=o.offence_type_id and tp.traffic_police_id='" + traffic_police_id + "'";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TrafficPolice trafficpolice = new TrafficPolice();
                trafficpolice.setVehicle_type(rset.getString("vehicle_type"));
                trafficpolice.setAct(rset.getString("act"));
                trafficpolice.setOffence_type(rset.getString("offence_type"));
                trafficpolice.setPenalty_amount(rset.getString("penalty_amount"));
                trafficpolice.setAct_origin(rset.getString("act_origin"));
                trafficpolice.setOffence_code(rset.getString("offence_code"));
                list.add(trafficpolice);
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel--showOffenceData " + e);
        }
        return list;
    }

    public List<TrafficPolice> showAllData(String searchOfficerName, String searchBookNo, String searchOffenceType, String searchActType, String searchVehicleType, String searchVehicleNo, String searchFromDate, String searchToDate, String searchJarayamNo, String searchOffenceCode, String searchOffenderLicenseNo, String receipt_book_no) {
        List<TrafficPolice> list = new ArrayList<TrafficPolice>();
        searchOfficerName = krutiToUnicode.convert_to_unicode(searchOfficerName);
        searchOffenceType = krutiToUnicode.convert_to_unicode(searchOffenceType);
        String query;
        try {
            if (!searchFromDate.equals("")) {
                //String da1[] = searchFromDate.split("-");
                //String da2[] = searchToDate.split("-");
                //searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                //searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);


            }

            /*query = "SELECT tp.traffic_police_id, tp.vehicle_no,tp.offender_name,tp.offender_license_no,cl.location,z.zone,tp.reciept_no,"
                    + "tp.deposited_amount,tp.offence_date,ot.offence_type,ot.act,ot.penalty_amount,ob.book_no,ob.book_revision_no, c.city_name, "
                    + "kp.key_person_name ,vt.vehicle_type ,a.act_origin "
                    + "FROM traffic_police as tp,offence_type as ot,officer_book as ob ,vehicle_type as vt,key_person as kp,"
                    + "city_location as cl ,act_origin as a , city as c,zone_new as z"
                    + " where tp.offence_type_id=ot.offence_type_id "
                    + " AND tp.book_no=ob.book_no And ot.act_origin_id=a.act_origin_id"
                    + " AND tp.vehicle_type_id=vt.vehicle_type_id "
                    + " AND tp.book_revision_no=ob.book_revision_no "
                    + " AND ob.key_person_id=kp.key_person_id "
                    + " AND cl.city_location_id=tp.city_location_id and c.city_id=z.city_id  and z.zone_new_id=cl.zone_new_id"
                    + " AND IF ('" + searchOfficerName + "'='',key_person_name LIKE '%%',key_person_name= ?) "
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%',tp.book_no = ?) "
                    + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%',ot.offence_type = ?) "
                    + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%',ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%',vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%',tp.vehicle_no = ?) "
                    + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"
                    + " ORDER BY key_person_name,book_no,offence_type,act,vehicle_type,vehicle_no";*/

            query = "select tp.traffic_police_id, vehicle_no, offender_name, offender_license_no, "
                    + "deposited_amount, ob.book_no,ob.book_revision_no,kp.key_person_name, reciept_no,"
                    + "offence_date, tp.city_location_id,cl.location,c.city_name,z.zone,pt.processing_type_id,"
                    + " relation_type_id, case_no,case_date, offender_age, jarayam_no, offender_address,"
                    + "tp.city_id,pt.processing_type, vt.vehicle_type,ot.offence_type, ot.act, ot.penalty_amount, tp.offence_date, "
                    + " ao.act_origin, ofc.city_name AS offender_city, tp.relative_name, tp.relative_salutation, rb.receipt_book_id, "
                    + "rb.receipt_book_no, rb.book_revision, rb.page_no, tp.father_name, tp.remark "
                    + " FROM traffic_police as tp "
                    + "left join officer_book as ob on ob.book_no=tp.book_no AND ob.book_revision_no = tp.book_revision_no "
                    + "left join (trafficpolice_receipt_map trm, receipt_book rb) ON rb.receipt_book_id = trm.receipt_book_id AND trm.traffic_police_id = tp.traffic_police_id "
                    + "left join key_person as kp on kp.key_person_id=kp.key_person_id "
                    + "left join city_location as cl on cl.city_location_id=tp.city_location_id "
                    + "left join zone_new as z on z.zone_new_id=cl.zone_new_id "
                    + "left join city as c on c.city_id=z.city_id "
                    + "left join city as ofc on ofc.city_id=tp.city_id "
                    + "left join processing_type as pt on pt.processing_type_id=tp.processing_type_id "
                    + "LEFT JOIN (traffic_offence_map tom, offence_type as ot) ON tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                    + "LEFT JOIN vehicle_type vt ON vt.vehicle_type_id=ot.vehicle_type_id "
                    + "LEFT JOIN act_origin ao ON ao.act_origin_id=ot.act_origin_id "
                    + " WHERE IF ('" + searchOfficerName + "'='',key_person_name LIKE '%%' OR key_person_name IS NULL, key_person_name= ?) "// tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id " + " AND vt.vehicle_type_id=ot.vehicle_type_id AND
                    + " AND IF ('" + searchBookNo + "'='',tp.book_no LIKE '%%' OR tp.book_no IS NULL,tp.book_no = ?) "
                    + " AND IF ('" + searchOffenceType + "'='',ot.offence_type LIKE '%%' OR ot.offence_type IS NULL,ot.offence_type = ?) "
                    + " AND IF ('" + searchActType + "'='',ot.act LIKE '%%' OR ot.act IS NULL,ot.act = ?) "
                    + " AND IF ('" + searchVehicleType + "'='',vt.vehicle_type LIKE '%%' OR vt.vehicle_type IS NULL,vt.vehicle_type = ?) "
                    + " AND IF ('" + searchVehicleNo + "'='',tp.vehicle_no LIKE '%%' OR tp.vehicle_no IS NULL,tp.vehicle_no = ?) "
                    + " AND IF ('" + searchJarayamNo + "'='',tp.jarayam_no LIKE '%%' OR tp.jarayam_no IS NULL,tp.jarayam_no = ?) "
                    + " AND IF ('" + searchOffenceCode + "'='',ot.offence_code LIKE '%%' OR ot.offence_code IS NULL,ot.offence_code = ?) "
                    + " AND IF ('" + searchOffenderLicenseNo + "'='', tp.offender_license_no LIKE '%%' OR tp.offender_license_no IS NULL, tp.offender_license_no = ?) "
                    + " AND IF ('" + receipt_book_no + "'='',rb.receipt_book_no LIKE '%%' OR rb.receipt_book_no IS NULL, rb.receipt_book_no = '" + receipt_book_no + "') "
                    + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%' OR tp.offence_date IS NULL,( (STR_TO_DATE(tp.offence_date, '%Y-%m-%d')) Between STR_TO_DATE('" + searchFromDate + "', '%d-%m-%Y') AND STR_TO_DATE('" + searchToDate + "', '%d-%m-%Y'))) "
                    // + " AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"
                    + " GROUP BY tp.traffic_police_id ORDER BY tp.traffic_police_id";//GROUP BY tp.traffic_police_id
                    //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOfficerName);
            pstmt.setString(2, searchBookNo);
            pstmt.setString(3, searchOffenceType);
            pstmt.setString(4, searchActType);
            pstmt.setString(5, searchVehicleType);
            pstmt.setString(6, searchVehicleNo);
            pstmt.setString(7, searchJarayamNo);
            pstmt.setString(8, searchOffenceCode);
            pstmt.setString(9, searchOffenderLicenseNo);

            /*PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOfficerName);
            pstmt.setString(2, searchBookNo);
            pstmt.setString(3, searchOffenceType);
            pstmt.setString(4, searchActType);
            pstmt.setString(5, searchVehicleType);
            pstmt.setString(6, searchVehicleNo);*/


            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TrafficPolice trafficpolice = new TrafficPolice();
                trafficpolice.setTraffic_police_id(rset.getInt("traffic_police_id"));
                trafficpolice.setJarayam_no(rset.getString("jarayam_no"));
                trafficpolice.setBook_no(rset.getString("book_no"));
                trafficpolice.setBook_revision_no(rset.getString("book_revision_no"));
                trafficpolice.setKey_person_name(rset.getString("key_person_name"));
                trafficpolice.setVehicle_type(rset.getString("vehicle_type"));
                trafficpolice.setVehicle_no(rset.getString("vehicle_no"));
                trafficpolice.setOffender_name(rset.getString("offender_name"));
                trafficpolice.setOffender_license_no(rset.getString("offender_license_no"));
                trafficpolice.setOffence_place(rset.getString("location"));
                trafficpolice.setAct(rset.getString("act"));
                trafficpolice.setReceipt_no(rset.getString("reciept_no"));
                trafficpolice.setDeposited_amount(rset.getString("deposited_amount"));
                trafficpolice.setOffence_type(rset.getString("offence_type"));
                trafficpolice.setPenalty_amount(rset.getString("penalty_amount"));
                trafficpolice.setOffence_date(rset.getString("offence_date"));
                trafficpolice.setAct_origin(rset.getString("act_origin"));
                trafficpolice.setCity(rset.getString("city_name"));
                trafficpolice.setZone(rset.getString("zone"));
                trafficpolice.setReceipt_book_no(rset.getInt("receipt_book_no"));
                trafficpolice.setReceipt_book_rev_no(rset.getInt("book_revision"));
                trafficpolice.setReceipt_page_no(rset.getInt("page_no"));
                trafficpolice.setFather_name(rset.getString("father_name"));
                trafficpolice.setRemark(rset.getString("remark"));
                list.add(trafficpolice);
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel--showData " + e);
        }
        return list;
    }

    public int getOffence_type_id(String offence_type, String act_origin) {
        int offence_type_id = 0;
        try {
            String query = "SELECT o.offence_type_id from offence_type as o , act_origin as a  "
                    + "Where o.act_origin_id=a.act_origin_id and o.offence_type= ? "
                    + " And a.act_origin=?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, offence_type);
            pstmt.setString(2, act_origin);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                offence_type_id = rset.getInt("offence_type_id");
            }
        } catch (Exception e) {
        }
        return offence_type_id;
    }

    public String getOfficerBookNo(String officer_name_selected, String book_no) {
        String officer_book_no = "";
        try {

            String query = "SELECT ob.book_no from officer_book AS ob,key_person as kp "
                    + "Where kp.key_person_name= ?"
                    + "AND ob.book_no=?"
                    + "AND ob.key_person_id=kp.key_person_id";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, officer_name_selected);
            pstmt.setString(2, book_no);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                officer_book_no = rset.getString("book_no");

            }
        } catch (Exception e) {
        }
        return officer_book_no;
    }

    public int getVehicle_type_id(String vehicle_type) {
        int offence_type_id = 0;
        try {

            String query = "SELECT vehicle_type_id from vehicle_type "
                    + "Where vehicle_type= ? ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, vehicle_type);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                offence_type_id = rset.getInt("vehicle_type_id");

            }
        } catch (Exception e) {
        }
        return offence_type_id;
    }

    public int insertRecord(TrafficPolice trafficPolice, int no_of_offence) {
        VehicleDetailModel vdm = new VehicleDetailModel();
        String query = "INSERT INTO traffic_police(offender_name,vehicle_no,offender_license_no,"
                + "city_location_id,deposited_amount,offence_date,reciept_no,book_no,book_revision_no,"
                + "processing_type_id,relation_type_id,case_no,case_date,jarayam_no,offender_age,"
                + "relative_name,relative_salutation,offender_address,"
                + " vehicle_no_state,vehicle_no_city_code,vehicle_no_series,vehicle_no_digits,father_name,remark) "
                + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?, ?, ?, ?, ?,?)";
        String insert_query = "INSERT INTO traffic_offence_map(traffic_police_id,offence_type_id,is_second_offence)"
                + "VALUES(?, ?,?)";
        String insert_case = "INSERT INTO case_processing (traffic_police_id, case_status_id, case_date) VALUES(?,(SELECT case_status_id FROM case_status WHERE case_status = 'Initial'),?)";
        int rowsAffected = 0;
        try {
            String[] traffic_police_id = trafficPolice.getTraffic_police_idM();
            int trafficPoliceId = traffic_police_id.length;
            for (int i = 0; i < trafficPoliceId; i++) {
                int tpi = 0;
                if (traffic_police_id[i].equals("1")) {
                    tpi = (Integer.parseInt(traffic_police_id[i]));

                    PreparedStatement pstmt = connection.prepareStatement(query);
                    //pstmt.setString(1, krutiToUnicode.convert_to_unicode(trafficPolice.getOffender_nameM()[i]));
                    pstmt.setString(1,trafficPolice.getOffender_nameM()[i]);
                    String vehicle_no = trafficPolice.getVehicle_no();//.trim().toUpperCase();
                    pstmt.setString(2, vehicle_no);
                    //vehicle_no = vdm.checkVehicleNo(vehicle_no);
                    pstmt.setString(3, trafficPolice.getOffender_license_noM()[i]);
                    pstmt.setInt(4, getCity_location_id(krutiToUnicode.convert_to_unicode(trafficPolice.getCityM()[i]), krutiToUnicode.convert_to_unicode(trafficPolice.getZoneM()[i]), krutiToUnicode.convert_to_unicode(trafficPolice.getOffence_placeM()[i])));
                    pstmt.setDouble(5, Double.parseDouble(trafficPolice.getDeposited_amountM()[i]));
                    String date = convertToSqlDate(trafficPolice.getOffence_dateM()[i]).toString();
                    String time = trafficPolice.getOffenceTime_h_m();
                    String offenceDateTime = date+" "+time;
                    pstmt.setString(6, offenceDateTime);
                    //pstmt.setDate(6, convertToSqlDate(trafficPolice.getOffence_dateM()[i]));
                    pstmt.setString(7, trafficPolice.getReceipt_noM()[i]);
                    pstmt.setString(8, getOfficerBookNo(trafficPolice.getOfficer_nameM()[i], trafficPolice.getBook_noM()[i]));
                    //pstmt.setString(9, trafficPolice.getBook_revision_noM()[i]);
                    String book_revision_no = trafficPolice.getBook_revision_noM()[i];
                    if(book_revision_no.isEmpty())
                        book_revision_no = trafficPolice.getBook_revision_noM()[i+1];
                    pstmt.setString(9, book_revision_no);
                    int processing_type = getProcessTypeId(trafficPolice.getProcessing_typeM()[i]);
                    if(processing_type == 0)
                        pstmt.setNull(10, java.sql.Types.NULL);
                    else
                        pstmt.setInt(10, processing_type);

                    //pstmt.setString(19, krutiToUnicode.convert_to_unicode(trafficPolice.getOffender_address_M()[i]));
                    pstmt.setString(18,trafficPolice.getOffender_address_M()[i]);
                    pstmt.setString(15, trafficPolice.getOffender_ageM()[i]);
                    //int city_id = getCityId(krutiToUnicode.convert_to_unicode(trafficPolice.getOffender_cityM()[0]));
                    //pstmt.setString(18,trafficPolice.getOffender_cityM()[0]);
//                        if(city_id == 0)
//                            pstmt.setNull(18, java.sql.Types.NULL);
//                        else
//                            pstmt.setInt(18, city_id);
                    /*pstmt.setString(12, trafficPolice.getCase_noM()[i]);
                    pstmt.setString(13, trafficPolice.getCase_dateM()[i]);
                    pstmt.setString(14, trafficPolice.getJarayam_noM()[i]);
                    pstmt.setString(15, trafficPolice.getOffender_ageM()[i]);
                    pstmt.setString(16, trafficPolice.getRelative_nameM()[i]);
                    pstmt.setString(17, trafficPolice.getSalutationM()[i]);*/
                    if(trafficPolice.getProcessing_typeM()[i].equals("Court")){
                        int relation_id = getRelationTypeId(trafficPolice.getRelation_typeM()[0]);
                        if(relation_id == 0)
                            pstmt.setNull(11, java.sql.Types.NULL);
                        else
                            pstmt.setInt(11, relation_id);                     
                        
                        pstmt.setString(12, trafficPolice.getCase_noM()[i]);
                        pstmt.setDate(13, convertToSqlDate(trafficPolice.getCase_dateM()[i].toString()));
//                        pstmt.setString(19, trafficPolice.getOffender_address_M()[i]);
//                        pstmt.setString(15, trafficPolice.getOffender_ageM()[i]);
                        pstmt.setString(16, trafficPolice.getRelative_nameM()[i]);
                        pstmt.setString(17, trafficPolice.getSalutationM()[i]);
                    }else {
                        pstmt.setNull(11, java.sql.Types.NULL);
//                        pstmt.setNull(18, java.sql.Types.NULL);
                        pstmt.setNull(12, java.sql.Types.NULL);
                        pstmt.setNull(13, java.sql.Types.NULL);
//                        pstmt.setNull(19, java.sql.Types.NULL);
//                        pstmt.setNull(15, java.sql.Types.NULL);
                        pstmt.setNull(16, java.sql.Types.NULL);
                        pstmt.setNull(17, java.sql.Types.NULL);
                    }
                    pstmt.setString(14, trafficPolice.getJarayam_noM()[i]);
                    //String[] vehicleParts = vehicle_no.split("_");
                    //if(vehicleParts.length == 5){
                        pstmt.setString(19, trafficPolice.getVehicle_no_state());
                        pstmt.setString(20, trafficPolice.getVehicle_no_city());
                        pstmt.setString(21, trafficPolice.getVehicle_no_series());
                        pstmt.setString(22, trafficPolice.getVehicle_no_digits());
                    /*}else{
                        pstmt.setNull(20, java.sql.Types.NULL);
                        pstmt.setNull(21, java.sql.Types.NULL);
                        pstmt.setNull(22, java.sql.Types.NULL);
                        pstmt.setNull(23, java.sql.Types.NULL);
                    }*/
                    //pstmt.setString(24, krutiToUnicode.convert_to_unicode(trafficPolice.getFather_nameM()[i]));
                    pstmt.setString(23,trafficPolice.getFather_nameM()[i]);
                    pstmt.setString(24, krutiToUnicode.convert_to_unicode(trafficPolice.getRemarkM()[i]));
                    
                    rowsAffected = pstmt.executeUpdate();
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if(rs.next())
                        tpi = rs.getInt(1);
                    System.out.println("Trafic_police_id = "+tpi);
                }
                if (rowsAffected > 0) {
                    int trafficPolice_id = getTraffic_polise_id();
                    if(trafficPolice.getProcessing_typeM()[i].equals("Court")){
                        PreparedStatement pst = connection.prepareStatement(insert_case);
                        pst.setInt(1, trafficPolice_id);
                        pst.setDate(2, convertToSqlDate(trafficPolice.getCase_dateM()[i].toString()));
                        rowsAffected = pst.executeUpdate();
                    }
                    String[] offence_type = trafficPolice.getOffence_typeM()[i].split(",");
                    //String[] offence_type = trafficPolice.getOffence_type().split("_");
                    int offence_type_length = offence_type.length;
                    for (int j = 1; j < offence_type_length; j++) { //offence_type.length - 1
                        String offenceId = offence_type[j];//.split("offence")[1]
                        String offenceArray[] = offenceId.split("_");
                        
                        PreparedStatement pstmt1 = connection.prepareStatement(insert_query);
                        pstmt1.setInt(1, trafficPolice_id);
                        pstmt1.setInt(2, Integer.parseInt(offenceArray[0]));  // getOffence_type_id(trafficPolice.getOffence_typeM()[j], trafficPolice.getAct_originM()[i])
                        pstmt1.setString(3, offenceArray[1]);
                        rowsAffected = pstmt1.executeUpdate();
                    }
                }
                if(rowsAffected > 0 && !trafficPolice.getProcessing_typeM()[i].equals("Court")){
                    rowsAffected = insertReceiptRecord(trafficPolice.getReceipt_book_no(), trafficPolice.getReceipt_book_rev_no(), trafficPolice.getReceipt_page_no(), Double.parseDouble(trafficPolice.getDeposited_amountM()[i]), tpi);
                }
            }

        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel--insertRecord-- " + e);
            //rowsAffected = 0;
        }
             if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int insertReceiptRecord(int receipt_book_no, int receipt_revision_no, int page_no, double amount, int tp_id) {
        int rowsAffected = 0;
        String query = "INSERT INTO receipt_book (receipt_book_no, page_no, book_revision, amount)"
                + " VALUES(?, ?, ?, ?)";
        String insert_query = "INSERT INTO trafficpolice_receipt_map(traffic_police_id, receipt_book_id)"
                + "VALUES(?, ?)";
        try {
            //connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, receipt_book_no);
            pstmt.setInt(2, page_no);
            pstmt.setInt(3, receipt_revision_no);
            pstmt.setDouble(4, amount);

            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                int key = 0;
                if(rs.next())
                    key = rs.getInt(1);
                int receiptBookId = getReceiptBookId();
                PreparedStatement pstmt1 = connection.prepareStatement(insert_query);
                pstmt1.setInt(1,tp_id);
                pstmt1.setInt(2, receiptBookId);
                rowsAffected = pstmt1.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error: Receipt inserting: " + e);
        }        
        return rowsAffected;
    }

    public int getReceiptBookId() {
        int receipt_book_id = 0;
        try {
            String query = "SELECT MAX(receipt_book_id) as rb_id from receipt_book ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next())
                receipt_book_id = rset.getInt("rb_id");
        } catch (Exception e) {
            System.out.println("Error: in getReceiptBookId of ReceiptBookModel" + e);
        }
        return receipt_book_id;
    }

    public int updateRecord(TrafficPolice trafficPolice, int no_of_offence) {
        VehicleDetailModel vdm = new VehicleDetailModel();
        String query = " UPDATE traffic_police SET offender_name=?,vehicle_no=?,offender_license_no=?,"
                + "city_location_id=?,deposited_amount=?,offence_date=?,reciept_no=?,book_no=?,book_revision_no=?,"
                + "processing_type_id=?,relation_type_id=?,case_no=?,case_date=?,jarayam_no=?,offender_age=?,"
                + "relative_name=?,relative_salutation=?,offender_address=?,"
                + " vehicle_no_state=?, vehicle_no_city_code=?, vehicle_no_series=?, vehicle_no_digits=?, father_name=?, remark=? "
                + " WHERE traffic_police_id = ? ";
        String insert_traffic_offence_query = "INSERT INTO traffic_offence_map(traffic_police_id,offence_type_id)"
                + "VALUES(?, ?)";
        String delete_traffic_offence_query = "DELETE FROM traffic_offence_map WHERE traffic_police_id = ?";
        String updateReceiptBookQuery = "UPDATE receipt_book SET receipt_book_no = ?, book_revision = ?, page_no = ?, amount = ? WHERE receipt_book_id = ?";
//                + "VALUES(?, ?)";
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query);
           pstmt.setString(1, krutiToUnicode.convert_to_unicode(trafficPolice.getOffender_nameM()[0]));
           String vehicle_no = trafficPolice.getVehicle_no();//.trim().toUpperCase();
                    pstmt.setString(2, vehicle_no);
                    //vehicle_no = vdm.checkVehicleNo(vehicle_no);
                    pstmt.setString(3, trafficPolice.getOffender_license_noM()[0]);
                    pstmt.setInt(4, getCity_location_id(krutiToUnicode.convert_to_unicode(trafficPolice.getCityM()[0]), krutiToUnicode.convert_to_unicode(trafficPolice.getZoneM()[0]), krutiToUnicode.convert_to_unicode(trafficPolice.getOffence_placeM()[0])));
                    pstmt.setDouble(5, Double.parseDouble(trafficPolice.getDeposited_amountM()[0]));
                    //pstmt.setDate(6, convertToSqlDate(trafficPolice.getOffence_dateM()[0]));
                    
                    String date = convertToSqlDate(trafficPolice.getOffence_dateM()[0]).toString();
                    String time = trafficPolice.getOffenceTime_h_m();
                    String offenceDateTime = date+" "+time;
                    pstmt.setString(6, offenceDateTime);
                    
                    pstmt.setString(7, trafficPolice.getReceipt_noM()[0]);
                    pstmt.setString(8, getOfficerBookNo(trafficPolice.getOfficer_nameM()[0], trafficPolice.getBook_noM()[0]));
                    pstmt.setString(9, trafficPolice.getBook_revision_noM()[0]);
                    pstmt.setString(14, trafficPolice.getJarayam_noM()[0]);
                    int processing_type = getProcessTypeId(trafficPolice.getProcessing_typeM()[0]);
                    if(processing_type == 0)
                        pstmt.setNull(10, java.sql.Types.NULL);
                    else
                        pstmt.setInt(10, getProcessTypeId(trafficPolice.getProcessing_typeM()[0]));

                    pstmt.setString(15, trafficPolice.getOffender_ageM()[0]);
//                    int city_id = getCityId(krutiToUnicode.convert_to_unicode(trafficPolice.getOffender_cityM()[0]));
//                        if(city_id == 0)
//                            pstmt.setNull(18, java.sql.Types.NULL);
//                        else
//                            pstmt.setInt(18, city_id);
                    pstmt.setString(18, krutiToUnicode.convert_to_unicode(trafficPolice.getOffender_address_M()[0]));
                    
                    if(trafficPolice.getProcessing_typeM()[0].equals("Court")){
                        int relation_id = getRelationTypeId(trafficPolice.getRelation_typeM()[0]);
                        if(relation_id == 0)
                            pstmt.setNull(11, java.sql.Types.NULL);
                        else
                            pstmt.setInt(11, relation_id);                        
                        pstmt.setString(12, trafficPolice.getCase_noM()[0]);
                        pstmt.setDate(13, convertToSqlDate(trafficPolice.getCase_dateM()[0].toString()));
                        //pstmt.setString(13, trafficPolice.getCase_dateM()[0]);                       
                        pstmt.setString(16, trafficPolice.getRelative_nameM()[0]);
                        pstmt.setString(17, trafficPolice.getSalutationM()[0]);
                    }else {
                        pstmt.setNull(11, java.sql.Types.NULL);                        
                        pstmt.setNull(12, java.sql.Types.NULL);
                        pstmt.setNull(13, java.sql.Types.NULL);                       
                        pstmt.setNull(16, java.sql.Types.NULL);
                        pstmt.setNull(17, java.sql.Types.NULL);
                    }
//                    String[] vehicleParts = vehicle_no.split("_");
//                    if(vehicleParts.length >= 5){
                    pstmt.setString(19, trafficPolice.getVehicle_no_state());
                    pstmt.setString(20, trafficPolice.getVehicle_no_city());
                    pstmt.setString(21, trafficPolice.getVehicle_no_series());
                    pstmt.setString(22, trafficPolice.getVehicle_no_digits());
                    /*}else{
                        pstmt.setNull(20, java.sql.Types.NULL);
                        pstmt.setNull(21, java.sql.Types.NULL);
                        pstmt.setNull(22, java.sql.Types.NULL);
                        pstmt.setNull(23, java.sql.Types.NULL);
                    }*/
                    pstmt.setString(23, krutiToUnicode.convert_to_unicode(trafficPolice.getFather_nameM()[0]));
                    pstmt.setString(24, krutiToUnicode.convert_to_unicode(trafficPolice.getRemarkM()[0]));
                    int traffic_police_id = Integer.parseInt(trafficPolice.getTraffic_police_idM()[0]);
                    pstmt.setInt(25, traffic_police_id);

            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                    //String[] offence_type = trafficPolice.getOffence_typeM()[i].split(",");
                PreparedStatement delpstmt = connection.prepareStatement(delete_traffic_offence_query);
                delpstmt.setInt(1, Integer.parseInt(trafficPolice.getTraffic_police_idM()[0]));
                rowsAffected = delpstmt.executeUpdate();
                //if(rowsAffected > 0)
                    String[] offence_type = trafficPolice.getOffence_typeM()[0].split("_");
                    //String[] offence_type = trafficPolice.getOffence_type().split("_");
                    int offence_type_length = offence_type.length;
                    for (int j = 1; j < offence_type_length; j++) { //offence_type.length - 1
                        int offenceId = Integer.parseInt(offence_type[j]);//.split("offence")[1]
                        PreparedStatement pstmt1 = connection.prepareStatement(insert_traffic_offence_query);
                        pstmt1.setInt(1, traffic_police_id);
                        pstmt1.setInt(2, offenceId);  // getOffence_type_id(trafficPolice.getOffence_typeM()[j], trafficPolice.getAct_originM()[i])
                        rowsAffected = pstmt1.executeUpdate();
                    }
                if(rowsAffected > 0)
                    connection.commit();
                else
                    connection.rollback();
                }
            connection.setAutoCommit(true);
            if(rowsAffected > 0){
                PreparedStatement pstmt1 = connection.prepareStatement(updateReceiptBookQuery);
                pstmt1.setInt(1, trafficPolice.getReceipt_book_no());
                pstmt1.setInt(2, trafficPolice.getReceipt_book_rev_no());
                pstmt1.setInt(3, trafficPolice.getReceipt_page_no());
                pstmt1.setDouble(4, Double.parseDouble(trafficPolice.getDeposited_amountM()[0]));
                pstmt1.setInt(5, trafficPolice.getReceipt_book_id());
                pstmt1.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("TrafficPoliceModel updateRecord() Error : " + e);
            try{
                    connection.rollback();
                    connection.setAutoCommit(true);
                    rowsAffected = 0;
            } catch (Exception ex) {
                    System.out.println("ERROR: in rollback in updateRecord() of TrafficPoliceModel : " + ex);
            }
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    /*public int updateRecord(TrafficPolice trafficPolice) {

        String query = " UPDATE trafficpolice SET offender_name=?,vehicle_no=?,offender_license_no=?,"
                + "city_location_id=?,deposited_amount=?,offence_date=?,reciept_no=?,book_no=?,book_revision_no=?,"
                + "processing_type_id=?,relation_type_id=?,case_no=?,case_date=?,jarayam_no=?,offender_age=?,"
                + "relative_name=?,relative_salutation=?,city_id=?,offender_address=?"
                + " WHERE traffic_police_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           pstmt.setString(1, trafficPolice.getOffender_nameM()[0]);
                    pstmt.setString(2, trafficPolice.getVehicle_noM()[0]);
                    pstmt.setString(3, trafficPolice.getOffender_license_noM()[0]);
                    pstmt.setInt(4, getCity_location_id(trafficPolice.getCityM()[0], trafficPolice.getZoneM()[0], trafficPolice.getOffence_placeM()[0]));
                    pstmt.setDouble(5, Double.parseDouble(trafficPolice.getDeposited_amountM()[0]));
                    pstmt.setDate(6, convertToSqlDate(trafficPolice.getOffence_dateM()[0]));
                    pstmt.setString(7, trafficPolice.getReceipt_noM()[0]);
                    pstmt.setString(8, getOfficerBookNo(trafficPolice.getOfficer_nameM()[0], trafficPolice.getBook_noM()[0]));
                    pstmt.setString(9, trafficPolice.getBook_revision_noM()[0]);
                    pstmt.setInt(10, getProcessTypeId(trafficPolice.getProcessing_typeM()[0]));
                    pstmt.setInt(11, getRelationTypeId(trafficPolice.getRelation_typeM()[0]));
                    pstmt.setString(12, trafficPolice.getCase_noM()[0]);
                    pstmt.setString(13, trafficPolice.getCase_dateM()[0]);
                    pstmt.setString(14, trafficPolice.getJarayam_noM()[0]);
                    pstmt.setString(15, trafficPolice.getOffender_ageM()[0]);
                    pstmt.setString(16, trafficPolice.getRelative_nameM()[0]);
                    pstmt.setString(17, trafficPolice.getSalutationM()[0]);
                    pstmt.setInt(18,getCityId(trafficPolice.getOffender_cityM()[0]));
                    pstmt.setString(19, trafficPolice.getOffender_address_M()[0]);

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ZOne Model updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }*/

    public List<String> vehicleTypeList() {
        List<String> list = new ArrayList<String>();
        String vehicleTypeList = "select v.vehicle_type from offence_type as o, vehicle_type as v "
                + "where  o.vehicle_type_id=v.vehicle_type_id";

        try {
            PreparedStatement ps = connection.prepareStatement(vehicleTypeList);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String vehicle_type = rs.getString("vehicle_type");
                list.add(vehicle_type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel vehicleTypeList(" + e);
        }

        return list;
    }

    public List<String> processingTypeList() {
        List<String> list = new ArrayList<String>();
        String query = "select processing_type from processing_type ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String processing_type = rs.getString("processing_type");
                list.add(processing_type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }

        return list;
    }

    public List<String> relationTypeList() {
        List<String> list = new ArrayList<String>();
        String query = "select relation_type from relation_type ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String relation_type = rs.getString("relation_type");
                list.add(relation_type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel relationTypeList(" + e);
        }

        return list;
    }

    public List<String> getofficerNameList() {
        List<String> officer_name_list = new ArrayList<String>();
        try {
//            String query = " select ob.book_no ,kp.key_person_name from officer_book as ob,key_person as kp "
//                    + "WHERE kp.key_person_id=ob.key_person_id";
//            String query = " select ob.book_no ,kp.key_person_name from officer_book as ob,key_person as kp, status_type st "
//                    + " WHERE kp.key_person_id=kp.key_person_id AND ob.book_type = 'C' AND ob.status_type_id = st.status_type_id AND ob.active = 'Y' AND st.status_type = 'Issue'";
       
              String query = "SELECT  kp.key_person_name from key_person as kp "// ,traffic_police as tp, traffic_offence_map tom "
                //+ " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                //+ " AND tp.book_no = tp.book_no"
                + " GROUP BY key_person_name"
                + " ORDER BY key_person_name";


          ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                officer_name_list.add(rst.getString("key_person_name"));
            }
        } catch (Exception e) {
              e.printStackTrace();
            System.out.println("error in TrafficPoliceModel OfficerNameList(" + e);
        }
        return officer_name_list;
    }

    public List<String> getBookNoList() {
        List<String> list = new ArrayList<String>();
        //q = q.trim();
        int count = 0;
        // String query = "SELECT complaint_no FROM complaint_feedback ";
        String query = " select book_no FROM officer_book ob, status_type st "
                + " WHERE ob.status_type_id = st.status_type_id AND ob.book_type = 'C' AND ob.active = 'Y' "
                + " AND st.status_type = 'Issue' GROUP BY book_no  ORDER BY book_no ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                int book_no = rset.getInt("book_no");
                String book_no_list = Integer.toString(book_no);
                //if(book_no_list.startsWith(q))
                list.add(book_no_list);
                count++;
                //    }
            }
            if (count == 0) {
                list.add("No Such Type Of Book Number  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel --getBookNoList-- " + e);
        }
        return list;
    }

    public String getOfficerBooklist(String officer_name) {

        String book_no_list = "";
        try {

            String query = "SELECT ob.book_no from officer_book as ob ,key_person as kp, status_type AS st "
                    + " Where ob.active = 'Y' AND ob.status_type_id = st.status_type_id AND st.status_type = 'Issue' "
                    + " AND ob.book_type = 'C' AND ob.key_person_id  = kp.key_person_id AND key_person_name = ? ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, officer_name);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                book_no_list = rset.getString("book_no");
            }
            System.out.println("book_no======"+book_no_list);
        } catch (Exception e) {
        }
        return book_no_list;
    }

    public int getTraffic_polise_id() {

        int traffic_police_id = 0;
        try {

            String query = "SELECT MAX(traffic_police_id) as tip from traffic_police ";



            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                traffic_police_id = rset.getInt("tip");
            }
        } catch (Exception e) {
        }
        return traffic_police_id;
    }

    public int getCityId(String city_name) {

        int city_id = 0;
        try {

            String query = "SELECT city_id from city where city_name='" + city_name + "' ";



            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                city_id = rset.getInt("city_id");
            }
        } catch (Exception e) {
        }
        return city_id;
    }

    public String getBookOfficerlist(int book_no) {

        String officer_name_list = "";
        try {

            String query = "SELECT kp.key_person_name from key_person as kp,officer_book AS ob "
                    + " Where ob.book_no= ?  "
                    + " AND active='Y' "
                    + "AND ob.key_person_id=kp.key_person_id";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, book_no);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                officer_name_list = rset.getString("key_person_name");
            }
        } catch (Exception e) {
        }
        return officer_name_list;
    }

    public String getVehicleTypelist(String vehicle_type) {

        String vehicle_type_list = "";
        try {

            String query = "SELECT vehicle_typeId from vehicle_type "
                    + " Where vehicle_type= ? ";


            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, vehicle_type);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                vehicle_type_list = vehicle_type_list + "," + rset.getString("vehicle_type_id");
            }
        } catch (Exception e) {
        }
        return vehicle_type_list;
    }

    public List<String> getOffenceTypeList() {

        List<String> offence_type_list = new ArrayList<String>();
        try {



            String query = "select offence_type from offence_type "
                    + "WHERE active='Y' group by offence_type";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                offence_type_list.add(rst.getString("offence_type"));
            }
        } catch (Exception e) {
        }
        return offence_type_list;
    }

    public List<String> getActTypeList() {

        List<String> act_type_list = new ArrayList<String>();
        try {
            String query = "select act from offence_type group buy act ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                act_type_list.add(rst.getString("act"));
            }
        } catch (Exception e) {
        }
        return act_type_list;
    }

    public String getOffenceActTypeList(String offence_type) {

        String jSON_format = "";
        try {

            String query = "Select act,penalty_amount,vehicle_type from offence_type as o,vehicle_type v "
                    + "where  o.vehicle_type_id=v.vehicle_type_id and offence_type='" + offence_type + "'";

            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, offence_type);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                jSON_format = "{ ";
                jSON_format = jSON_format + "\"act\"" + ": " + "\"" + rset.getString("act") + "\", ";
                jSON_format = jSON_format + "\"penalty_amount\"" + ": " + "\"" + rset.getString("penalty_amount") + "\", ";
                jSON_format = jSON_format + "\"vehicle_type\"" + ": " + "\"" + rset.getString("vehicle_type") + "\" ";

                jSON_format = jSON_format + "}";
            }
//            else {
//                jSON_format = "{ ";
//                jSON_format = jSON_format + "\"org_office_code\"" + ": " + "\"" + rset.getString("org_office_code") + "\", ";
//                jSON_format = jSON_format + "\"org_office_name\"" + ": " + "\"" + "" + "\", ";
//                jSON_format = jSON_format + "\"office_type\"" + ": " + "\"" + "" + "\", ";
//                jSON_format = jSON_format + "\"officeCity\"" + ": " + "\"" + "" + "\" ";
//
//                jSON_format = jSON_format + "}";
//            }
        } catch (Exception e) {
        }
        return jSON_format;
    }

    public String getActPenaltyAmountList(String act) {
        String response_data = "";
        try {
            String query = "Select  penalty_amount from offence_type " + " WHERE act=? ";
            //Select  IF(CURDATE() <= to_date, penalty_amount,'N/A') penalty_amount from offence_type "+ " WHERE act=?;
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, act);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                response_data = rset.getString("penalty_amount");
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintFeedbackModel --getComplaintNoList-- " + e);
        }
        return response_data;
    }

    public String getRecieptNo(String book_no) {
        String response_data = "";
        try {
            String query = " Select from_no,to_no from officer_book "
                    + " WHERE book_no=? ";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, book_no);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                response_data = "Challan no should be from " + rset.getString("from_no") + "to" + rset.getString("to_no") + "_" + getChalanNo(book_no);
                msg = response_data;
                msgBgColor = COLOR_OK;
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintFeedbackModel --getComplaintNoList-- " + e);
        }
        return response_data;
    }

    public int getChalanNo(String book_no){
        int receipt_no = 0;
        String query = "SELECT MAX(reciept_no) FROM traffic_police WHERE book_no = "+ book_no;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next())
                receipt_no = rs.getInt(1) + 1;
        }catch(Exception ex){
        }
        return receipt_no;
    }

    public String getOffenceTypeIdlist(String offence_type) {

        String offence_type_id_list = "";
        try {

            String query = "SELECT offence_type_id from officer_book "
                    + "Where offence_type= ? ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, offence_type);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                offence_type_id_list = offence_type_id_list + "," + rset.getString("offence_type_id");
            }
        } catch (Exception e) {
        }
        return offence_type_id_list;
    }

    public String getActTypeIdlist(String act) {

        String book_no_list = "";
        try {

            String query = "SELECT book_no from officer_book "
                    + "Where officer_name= ? ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, act);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                book_no_list = book_no_list + "," + rset.getString("book_no");
            }
        } catch (Exception e) {
        }
        return book_no_list;
    }

    public int getBookRevisionNoList(String book_no) {

        int book_revision_no_list = 0;
        try {

            String query = "SELECT max(book_revision_no) as book_revision_no  from officer_book "
                    + "Where active = 'Y' AND book_type='C' AND book_no=?";
            //+"where active='y'"
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, book_no);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                book_revision_no_list = rset.getInt("book_revision_no");
            }
        } catch (Exception e) {
            System.out.println("Exception is" + e);
        }
        return book_revision_no_list;
    }

    public int getOfficerBookRevisionNoList(String officer_name) {

        int revision_no_list = 0;
        try {

            String query = "SELECT max(book_revision_no) as book_revision_no  from officer_book AS ob, key_person AS kp "
                    + "Where book_type='C' AND ob.key_person_id = kp.key_person_id AND key_person_name=?";
            //+"where active='y'"
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, officer_name);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision_no_list = rset.getInt("book_revision_no");
            }
        } catch (Exception e) {
            System.out.println("Exception is" + e);
        }
        return revision_no_list;
    }

    public List<String> getOfficerSearchList(String q) {
        List<String> list = new ArrayList<String>();
        
//        String query = "SELECT tp.book_no, kp.key_person_name FROM traffic_police as tp,key_person as kp  "
//                + " Where kp.key_person_id=kp.key_person_id "
//                + "AND tp.book_no=tp.book_no AND tp.book_revision_no = tp.book_revision_no "
//                + " GROUP BY key_person_name"
//                + " ORDER BY key_person_name ";
//        
 String query = "SELECT  kp.key_person_name from key_person as kp "// ,traffic_police as tp, traffic_offence_map tom "
                //+ " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                //+ " AND tp.book_no = tp.book_no"
                + " GROUP BY key_person_name"
                + " ORDER BY key_person_name";






try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String officer_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
               // String officer_name = rset.getString("key_person_name");
                
                if (officer_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(officer_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such officer exists.......");
            }
        } catch (Exception e) {
            System.out.println("getOfficerSearchListERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

    public List<String> getBookSearchList(String q) {
        List<String> list = new ArrayList<String>();
//
        String query = " SELECT tp.book_no from traffic_police as tp ,key_person as kp ,officer_book as ob "
                + " WHERE ob.key_person_id  =kp.key_person_id "
                + "AND tp.book_no=ob.book_no "
                + " GROUP BY book_no"
                + " ORDER BY book_no ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //   pstmt.setString(1,officer_name);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String book_no = rs.getString("book_no");
                if(book_no.startsWith(q)){
                    list.add(book_no);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No book no is exist");
            }
        } catch (Exception e) {
            System.out.println("getBookSearchListERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

    public List<String> getOffenceSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  ot.offence_type from offence_type as ot "// ,traffic_police as tp, traffic_offence_map tom "
                //+ " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                //+ " AND tp.book_no = tp.book_no"
                + " GROUP BY offence_type"
                + " ORDER BY offence_type";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1,book_no);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
//                String offence_type = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("offence_type"));
                  String offence_type = rs.getString("offence_type");
                if(offence_type.startsWith(q)){
                    list.add(offence_type);
                    count++;
                }
            }

            if (count == 0) {
                list.add("No Offnece Type is exist");
            }
        } catch (Exception e) {
            System.out.println(" getOffenceSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

    public List<String> getActSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  ot.offence_type_id,ot.act from offence_type as ot"//,traffic_police as tp, traffic_offence_map tom"
                //+ " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id"
                + " GROUP BY act"
                + " ORDER BY act";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1,offence_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String act = rs.getString("act");
                list.add(act);
                count++;
            }

            if (count == 0) {
                list.add("No Act Type is exist");
            }
        } catch (Exception e) {
            System.out.println(" getActSearchList ERROR inside TrafficPoliceModel - " + e); 
        }
        return list;
    }

    public List<String> getVehicleTypeSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  vt.vehicle_type from vehicle_type as vt "//, traffic_police as tp, offence_type as ot, traffic_offence_map tom "
                //+ " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                //+ " AND vt.vehicle_type_id=ot.vehicle_type_id "
                //+ "AND ot.offence_type_id=tp.offence_type_id"
                + " GROUP BY vehicle_type"
                + " ORDER BY vehicle_type";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,act);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String vehicle_type = rs.getString("vehicle_type");
                if(vehicle_type.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(vehicle_type);
                count++;
                }
            }

            if (count == 0) {
                list.add("No vehicle Type is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleTypeSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

    public List<String> getVehicleNoSearchList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  tp.vehicle_no from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " WHERE vehicle_no IS NOT NULL"
                + " GROUP BY vehicle_no"
                + " ORDER BY vehicle_no ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String vehicle_no = rs.getString("vehicle_no");
                if(vehicle_no.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(vehicle_no);
                count++;
                }
            }

            if (count == 0) {
                list.add("No vehicle Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getVehicleNoSearchList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

    public List<String> getOffenderLicenseNo(String q){
        List<String> list = new ArrayList<String>();
        String query = "SELECT  tp.offender_license_no from traffic_police as tp " //vehicle_type as vt,
                +" WHERE tp.offender_license_no IS NOT NULL "
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                + " GROUP BY offender_license_no"
                + " ORDER BY offender_license_no ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String offender_license_no = rs.getString("offender_license_no");
                if(offender_license_no.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(offender_license_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Offender License No. Number is exist");
            }
        } catch (Exception e) {
            System.out.println(" getOffenderLicenseNo ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

public List<String> getJarayamNoList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  tp.jarayam_no from traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tp.vehicle_type_id=vt.vehicle_type_id "
                //+ " GROUP BY vehicle_no"
                + " ORDER BY jarayam_no ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String jarayam_no = rs.getString("jarayam_no");
                if(jarayam_no.startsWith(q))
                    list.add(jarayam_no);
                count++;
            }

            if (count == 0) {
                list.add("Jarayam Number is not exist");
            }
        } catch (Exception e) {
            System.out.println(" getJarayamNoList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

public List<String> getOffenceCodeList(String q) {
        List<String> list = new ArrayList<String>();

        String query = "SELECT  ot.offence_code from offence_type ot "//, traffic_offence_map tom, traffic_police as tp " //vehicle_type as vt,
                //+ " WHERE tom.traffic_police_id = tp. traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                + " GROUP BY offence_code"
                + " ORDER BY offence_code ";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,vehicle_type);

            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String offence_code = rs.getString("offence_code");
                if(offence_code.startsWith(q))
                    list.add(offence_code);
                count++;
            }

            if (count == 0) {
                list.add("Offence Code is not exist");
            }
        } catch (Exception e) {
            System.out.println(" getOffenceCodeList ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select c.city_name from city_location as cl,city as c,zone_new as z where c.city_id=z.city_id "
                + "and z.zone_new_id=cl.zone_new_id GROUP BY c.city_name";        
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("city_name");
                city_name = unicodeToKruti.Convert_to_Kritidev_010(city_name);
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
                    count++;
                }
            }
            if (count == 0) 
                list.add("No such City exists.......");            
        } catch (Exception e) {
            System.out.println("getCityName ERROR inside TrafficPoliceModel - " + e);
        }
        return list;
    }

   public List<String> getOffenderCityName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select city_name from city group by city_name";        
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("city_name");
                city_name = unicodeToKruti.Convert_to_Kritidev_010(city_name);
                  
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
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
    public List<String> getZoneName(String q, String city_name) {
        List<String> list = new ArrayList<String>();
        String cityN = krutiToUnicode.convert_to_unicode(city_name);
        
        String query = "select z.zone from city_location as cl,city as c,zone_new as z where c.city_id=z.city_id and z.zone_new_id=cl.zone_new_id "
                + "and c.city_name='" + cityN + "' group by z.zone";
        try {
            PreparedStatement pstsmt = connection.prepareStatement(query);
            ResultSet rs = pstsmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String zone = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("zone"));
                  
                if (zone.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Zone exists.");
            }

        } catch (Exception e) {
            System.out.println("Error inside getZoneName " + e);
        }
        return list;
    }

    public List<String> getLocationName(String q, String city, String zone_name) {
        List<String> list = new ArrayList<String>();
        String cityN = krutiToUnicode.convert_to_unicode(city);
        zone_name = krutiToUnicode.convert_to_unicode(zone_name);
//        String query = "select cl.location from city_location as cl,zone_new as z where z.zone_new_id=cl.zone_new_id "
//                + "and z.zone='" + zone_name + "' group by cl.location";
        String query = "select cl.location from city_location as cl, city as c, zone_new as z where c.city_id = z.city_id AND z.zone_new_id=cl.zone_new_id "
                + " AND c.city_name = '"+ cityN +"' "
                + "and z.zone='" + zone_name + "' group by cl.location";
        try {
            PreparedStatement pstsmt = connection.prepareStatement(query);
            ResultSet rs = pstsmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
                String location = rs.getString("location");
                location = unicodeToKruti.Convert_to_Kritidev_010(location);
                  
                if (location.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(location);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Location exists.");
            }
        } catch (Exception e) {
            System.out.println("Error inside getLocationName " + e);
        }
        return list;
    }

    public int[] getCityId(String[] city_name, String[] zone, String[] location) {
        String query = "select city_location_id from city_location as cl,city as c "
                + "where c.city_id=cl.city_id and  c.city_name=? and cl.zone=? and cl.location=? ";

        int length = location.length;
        int division_id[] = new int[length];
        String[] location_name = location;
        try {
            for (int i = 0; i < location_name.length; i++) {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, location_name[i]);
                pstmt.setString(2, zone[i]);
                pstmt.setString(3, city_name[i]);
                ResultSet rset = pstmt.executeQuery();
                rset.next();    // move cursor from BOR to valid record.
                System.out.println(rset.getInt(1));
                division_id[i] = rset.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("TrafficModelModel getCityId : " + e);
        }
        return division_id;
    }

    public int getCity_location_id(String city_name, String zone_name, String location) {
        int city_location_id = 0;
        try {

            String query = "select city_location_id from city_location as cl,city as c,zone_new as z "
                    + "where c.city_id=z.city_id and z.zone_new_id=cl.zone_new_id "
                    + "and  c.city_name=? and z.zone=? and cl.location=? ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            pstmt.setString(2, zone_name);
            pstmt.setString(3, location);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                city_location_id = rset.getInt("city_location_id");

            }
        } catch (Exception e) {
        }
        return city_location_id;
    }

    public int getProcessTypeId(String processing_type) {
        int processing_type_id = 0;
        try {

            String query = "SELECT processing_type_id from processing_type where processing_type='" + processing_type + "'";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                processing_type_id = rset.getInt("processing_type_id");

            }
        } catch (Exception e) {
        }
        return processing_type_id;
    }

    public int getRelationTypeId(String relation_type) {
        int relation_type_id = 0;
        try {

            String query = "SELECT relation_type_id from relation_type where relation_type='" + relation_type + "'";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                relation_type_id = rset.getInt("relation_type_id");

            }
        } catch (Exception e) {
        }
        return relation_type_id;
    }

    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public String getVehicleType(String offence_type) {
        String response_data = "";
        try {
            String query = "select v.vehicle_type from offence_type as o, vehicle_type as v"
                    + " where o.vehicle_type_id=v.vehicle_type_id and o.offence_type='" + offence_type + "'";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                response_data = rset.getString("vehicle_type");
                message = response_data;
                msgBgColor = COLOR_OK;
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintFeedbackModel --getComplaintNoList-- " + e);
        }
        return response_data;
    }

    public String getActOrigin(String act) {
        String response_data = "";
        try {
            String query = "select a.act_origin from offence_type as o, act_origin as a"
                    + " where o.act_origin_id=a.act_origin_id and o.act='" + act + "'";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                response_data = rset.getString("act_origin");
                message = response_data;
                msgBgColor = COLOR_OK;
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintFeedbackModel --getComplaintNoList-- " + e);
        }
        return response_data;
    }

    public String getOffenceList(String offence) {
        String response_data = "";
        try {
            String query = "select o.offence_type from offence_type as o ,act_origin as a "
                    + "where o.act_origin_id=a.act_origin_id "
                    + "and a.act_origin='" + offence + "' "
                    + "Group by o.offence_type";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                response_data = response_data + "," + rset.getString("offence_type").trim();
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintFeedbackModel --getComplaintNoList-- " + e);
        }
        return response_data;
    }

    public List<TrafficPolice> getOffenceRecordList(String act_origin, String vehicle_type, String commercial_type) {
        List<TrafficPolice> list = new ArrayList<TrafficPolice>();
        try {
            String query = "SELECT o.offence_type, o.offence_code, o.act, o.penalty_amount, "
                    + " IF(ct.commercial_type = 'Commercial', 'Yes', 'No') AS is_commercial, vt.vehicle_type "
                    + " FROM offence_type as o ,act_origin as a, commercial_type AS ct, vehicle_type AS vt "
                    + " WHERE o.act_origin_id=a.act_origin_id AND o.commercial_type_id = ct.commercial_type_id "
                    + " AND vt.vehicle_type_id = o.vehicle_type_id "
                    + " AND a.act_origin='" + act_origin + "' "
                    + " AND IF('"+ vehicle_type +"'='', vt.vehicle_type LIKE '%%', vt.vehicle_type = '"+ vehicle_type +"' OR vt.vehicle_type = 'All') "
                    + " AND IF('"+ commercial_type +"'='', ct.commercial_type LIKE '%%', ct.commercial_type = '"+ commercial_type +"') "
                    + " ORDER BY offence_type_id";//GROUP BY o.offence_type
                      
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                TrafficPolice tp = new TrafficPolice();
                tp.setOffence_type(rset.getString("offence_type"));
                tp.setOffence_code(rset.getString("offence_code"));
                tp.setAct(rset.getString("act"));
                tp.setVehicle_type(rset.getString("vehicle_type"));
                tp.setIs_commercial(rset.getString("is_commercial"));
                double penalty_amount = rset.getDouble("penalty_amount");
                if(penalty_amount == 100000 )
                    tp.setPenalty_amount("Court");
                else
                    tp.setPenalty_amount((int)penalty_amount + "");

                list.add(tp);
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel --getOffenceRecordList-- " + e);
        }
        return list;
    }

    public List<String> getActOriginList(String q){
        List<String> list = new ArrayList<String>();
        String query = "SELECT act_origin FROM act_origin";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                list.add(rs.getString("act_origin"));
            }
        }catch(Exception ex){
            System.out.println("Error:TrafficPoliceModel --getActOriginList-- " + ex);
        }
        return list;
    }

    public List<TrafficPolice> showData(String[] offence_type) {
        List<TrafficPolice> list = new ArrayList<TrafficPolice>();
        String query;
        try {
            for (int i = 0; i <= offence_type.length; i++) {
                String offence = offence_type[i];
                query = "select act,penalty_amount, vehicle_type from offence_type as o ,vehicle_type as v "
                        + "where o.vehicle_type_id=v.vehicle_type_id and offence_type='" + offence + "' ";
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rset = pstmt.executeQuery();
                while (rset.next()) {
                    TrafficPolice trafficpolice = new TrafficPolice();

                    trafficpolice.setVehicle_type(rset.getString("vehicle_type"));
                    trafficpolice.setOffence_type(rset.getString("offence_type"));
                    trafficpolice.setPenalty_amount(rset.getString("penalty_amount"));
                    trafficpolice.setAct(rset.getString("act"));
                    list.add(trafficpolice);
                }
            }

        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
    }

    public List<TrafficPolice> showMISData(){
        List<TrafficPolice> list = new ArrayList<TrafficPolice>();
        Map<String, String> map = new HashMap<String, String>();
        String query = "SELECT kp.key_person_name, ot.act, COUNT(tp.traffic_police_id) AS no_of_challan "
                + " FROM key_person kp LEFT JOIN officer_book ob ON ob.key_person_id=kp.key_person_id AND ob.active='Y' AND book_type='R' "
                + " LEFT JOIN (receipt_book rb, trafficpolice_receipt_map trm, traffic_police tp) "
                + " ON rb.receipt_book_no=ob.book_no AND rb.book_revision=ob.book_revision_no "
                + " AND trm.receipt_book_id=rb.receipt_book_id AND trm.traffic_police_id=tp.traffic_police_id "
                + " LEFT JOIN (traffic_offence_map tom, offence_type ot) ON tom.traffic_police_id=tp.traffic_police_id AND ot.offence_type_id=tom.offence_type_id "
                + " WHERE kp.key_person_name != 'क ख ग'   GROUP BY ot.act, kp.key_person_name  ORDER BY key_person_name ";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                TrafficPolice tpBean = new TrafficPolice();
                String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("key_person_name"));
                String act = rs.getString("act");
                int no_of_challan = rs.getInt("no_of_challan");
                tpBean.setKey_person_name(rs.getString("key_person_name"));
            }
        }catch(Exception ex){
            System.out.println("ERROR : in showMISData() in TrafficPoliceModel : " + query);
        }
        return list;
    }

    public byte[] generateMISRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (Exception e) {
            System.out.println("TafficPoliceModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("keypersonModel closeConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_userName() {
        return db_userName;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public String getDb_userPasswrod() {
        return db_password;
    }

    public void setDb_userPasswrod(String db_password) {
        this.db_password = db_password;
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
}

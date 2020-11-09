/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import java.sql.Connection;
import com.tp.tableClasses.Designation;
import com.tp.tableClasses.OfficerBookBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class OfficerBookModel {

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

    public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            //connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
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

    public List<String> getOrganisation_Name(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org.organisation_name FROM organisation_name AS org ORDER BY organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = rset.getString("organisation_name");
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<String> searchOfficeCode(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org_office_code from officer_book as ob ,key_person as kp, org_office as oo"
                + " Where ob.key_person_id =kp.key_person_id"
                + " AND oo.org_office_id=kp.org_office_id";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                if (org_office_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeCode(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT of.org_office_code FROM org_office_type AS oft ,org_office AS of ,organisation_name AS o "
                + "WHERE oft.office_type_id = of.office_type_id  "
                + " AND o.organisation_id=of.organisation_id "
                + "ORDER BY org_office_code";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                if (org_office_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Office exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:KeypersonModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT of.org_office_name FROM org_office AS of,officer_book as ob,key_person as kp "
                + "where of.org_office_id=kp.org_office_id and ob.key_person_id=ob.key_person_id Group BY org_office_name";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, org_office_code);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("org_office_name"));
                if (org_office_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Office exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:KeypersonModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<String> getKeyPerson(String q, String emp_id) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT kp.key_person_name from key_person AS kp where IF('" + emp_id + "'='', emp_code LIKE '%%' ,emp_code='" + emp_id + "')";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,emp_code);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key person exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }
    public String getKeyPerson(String emp_id) {

        String key_person_name = "";
        try {
            String query = "SELECT kp.key_person_name from key_person AS kp where emp_code='" + emp_id + "' group by key_person_name ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
            }
        } catch (Exception e) {
        }
        return key_person_name;
    }

    public List<String> getKeyPersonList(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT kp.key_person_name from officer_book as ob ,key_person AS kp "
                + " where ob.key_person_id=kp.key_person_id GROUP BY key_person_name ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key person exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }

//    public List<String> getDesignation(String q, String emp_code) {
//        List<String> list = new ArrayList<String>();
//        String query = "SELECT d.designation from key_person AS kp ,designation AS d "
//                + " where kp.emp_code='" + emp_code + "' AND d.designation_id=kp.designation_id "
//                + "group by d.designation order by d.designation ";
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rset = pstmt.executeQuery();
//            int count = 0;
//            q = q.trim();
//            while (rset.next()) {    // move cursor from BOR to valid record.
//                String designation = rset.getString("designation");
//                if (designation.toUpperCase().startsWith(q.toUpperCase())) {
//                    list.add(designation);
//                    count++;
//                }
//            }
//            if (count == 0) {
//                list.add("No such key person exists.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
//        }
//        return list;
//    }
    public String getDesignation(String emp_code) {

        String designation = "";
        try {
            String query = "SELECT d.designation from key_person AS kp ,designation AS d "
                    + " where kp.emp_code='" + emp_code + "' AND d.designation_id=kp.designation_id "
                    + "group by d.designation order by d.designation ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                //key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
               // designation = rset.getString("designation");
                designation = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("designation"));
            }
        } catch (Exception e) {
            
        }
        return designation;
    }

    public int getKey_person_Id(String key_person_name, String emp_code, String designation) {
        int key_person_id = 0;
        key_person_name = krutiToUnicode.convert_to_unicode(key_person_name);
        designation = krutiToUnicode.convert_to_unicode(designation);
        try {

//            String query = "SELECT kp.key_person_id from key_person as kp ,designation as d "
//                    + " Where kp.emp_code=? AND kp.key_person_name=? AND d.designation=? "
//                    + "  AND kp.designation_id=d.designation_id ";
//            String query = "SELECT kp.key_person_id from key_person as kp ,designation as d "
//                    + " Where kp.emp_code='"+emp_code+"' OR kp.key_person_name='"+key_person_name+"' OR d.designation='"+designation+"' "
//                    + "  AND kp.designation_id=d.designation_id ";
   String query = "SELECT kp.key_person_id from key_person as kp ,designation as d "
                    + " Where kp.emp_code='"+emp_code+"' "
                    + "  AND kp.designation_id=d.designation_id ";

            PreparedStatement pstmt = connection.prepareStatement(query);
           // pstmt.setString(1, emp_code);
            //pstmt.setString(2, key_person_name.trim());
            //pstmt.setString(3, designation.trim());
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                key_person_id = rset.getInt("key_person_id");

            }
        } catch (Exception e) {
        }
        return key_person_id;
    }

    public List<String> getEmployeeCode(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT kp.emp_code from  key_person AS kp group by emp_code ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1,key_person_name);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String emp_code = rset.getString("emp_code");
                if (emp_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(emp_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Employee Code  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OfficerBookModel--getEmployeeCode()-- " + e);
        }
        return list;
    }

    public List<String> getBookType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT book_type from  book_type  group by book_type ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1,key_person_name);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String book_type = rset.getString("book_type");
                //String book_type =krutiToUnicode.convert_to_unicode(rset.getString("book_type"));
                if (book_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(book_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Employee Code  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OfficerBookModel--getEmployeeCode()-- " + e);
        }
        return list;
    }


    public int checkBookAvailability(int book_no, String status){
        int availability = 0;
        String query = "";
        if(status.equals("Generate"))
            query = "SELECT book_no FROM officer_book ob, status_type st "
                + " WHERE ob.status_type_id = st.status_type_id AND ob.active = 'Y' AND ob.book_no = " + book_no;
        if(status.equals("Issue"))
            query = "SELECT book_no FROM officer_book ob, status_type st "
                + " WHERE ob.status_type_id = st.status_type_id AND ob.active = 'Y' AND (st.status_type = 'Submit' OR st.status_type = 'Generate') AND ob.book_no = " + book_no;
        if(status.equals("Submit"))
            query = "SELECT book_no FROM officer_book ob, status_type st "
                + " WHERE ob.status_type_id = st.status_type_id AND ob.active = 'Y' AND st.status_type = 'Issue' AND st.status_type != 'Submit' AND ob.book_no = " + book_no;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next())
                availability = rs.getInt("book_no");
        }catch(Exception ex){
            System.out.println("ERROR: in checkBookAvailability in OfficerBookModel.java" + ex);
        }
        return availability;
    }
    
    public int insertRecord(OfficerBookBean officerBookBean) {
        int rowsAffected = 0;
        int book_no = officerBookBean.getBook_no();
        int book_no_availability = 0;
        String status = officerBookBean.getStatus_type();

        if(status.equals("Generate"))
            book_no_availability = checkBookAvailability(book_no, status);
        else
            book_no_availability = 1;

        String query = "INSERT INTO officer_book (book_no,from_no,to_no,remark,status_type_id,issue_date,key_person_id, book_type) VALUES (?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            if(book_no_availability == 0 ){//&& officerBookBean.getStatus_type().equals("Issued")
            pstmt.setInt(1, book_no);
            pstmt.setString(2, officerBookBean.getFrom_no());
            pstmt.setString(3, officerBookBean.getTo_no());
            pstmt.setString(4, krutiToUnicode.convert_to_unicode(officerBookBean.getRemark()));
            pstmt.setString(5, getStatusTypeId(status));
            pstmt.setDate(6, convertToSqlDate(officerBookBean.getIssue_date()));
//            pstmt.setInt(7, getKey_person_Id(krutiToUnicode.convert_to_unicode(officerBookBean.getKey_person_name()), officerBookBean.getEmp_code(), krutiToUnicode.convert_to_unicode(officerBookBean.getDesignation())));
            pstmt.setInt(7, getKey_person_Id(officerBookBean.getKey_person_name(), officerBookBean.getEmp_code(),officerBookBean.getDesignation()));
            pstmt.setString(8, officerBookBean.getBook_type());

            rowsAffected = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error:Detail inserting: " + e);
        }
        if (rowsAffected > 0) {

            message = "Record saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
            message = "Record not saved successfully";
            msgBgColor = COLOR_ERROR;

            System.out.println("not inserted");
        }
        if(book_no_availability > 0){
            message = "Book is Already Exist !";
            msgBgColor = COLOR_ERROR;
            System.out.println("not inserted");
        }
        return rowsAffected;
    }

    public int reviseRecord(OfficerBookBean officerBookBean) {
        int rowsAffected = 0, count = 0;
        int book_no = officerBookBean.getBook_no();
        int book_no_availability = 0;
        String status = officerBookBean.getStatus_type();

        if(!status.equals("Generate"))
            book_no_availability = checkBookAvailability(book_no, status);
        
        String query = "INSERT INTO officer_book (key_person_id, book_no, from_no, to_no, remark, status_type_id,issue_date, book_type, book_revision_no)"
                + "(select ?,?,?,?,?,?,?,?,(select max(book_revision_no)+1 from officer_book where book_no = ?)as ss)";
        
        String updatequery = " update officer_book set active='N'"
                + " where book_no =? and "
                + " book_revision_no = ? ";
        try {
            if(book_no_availability > 0 ){
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, getKey_person_Id(krutiToUnicode.convert_to_unicode(officerBookBean.getKey_person_name()), officerBookBean.getEmp_code(), officerBookBean.getDesignation()));
            pstmt.setInt(2, officerBookBean.getBook_no());
            pstmt.setString(3, officerBookBean.getFrom_no());
            pstmt.setString(4, officerBookBean.getTo_no());
            pstmt.setString(5, krutiToUnicode.convert_to_unicode(officerBookBean.getRemark()));
            pstmt.setString(6, getStatusTypeId(status));
            pstmt.setDate(7, convertToSqlDate(officerBookBean.getIssue_date()));
            pstmt.setString(8, officerBookBean.getBook_type());
            pstmt.setInt(9, book_no);

            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {                
                rowsAffected = 0;
                pstmt.close();
                pstmt = connection.prepareStatement(updatequery);
                pstmt.setInt(1, officerBookBean.getBook_no());
                pstmt.setInt(2, officerBookBean.getBook_revision_no());
                rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) 
                    connection.commit();                    
                 else 
                    connection.rollback();                
            } else 
                connection.rollback();            
            }
        } catch (Exception e) {
            System.out.println("Error:Detail inserting: " + e);
            try {
                connection.rollback();
            } catch (SQLException sql) {
                System.out.println("SQLException occured during Updation is: " + sql);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(OfficerBookModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
            message = "Cannot save the record, some error......";
            msgBgColor = COLOR_ERROR;
            System.out.println("not inserted");
        }
        if(book_no_availability == 0){
            message = "Book is Already "+ status +" !";
            msgBgColor = COLOR_ERROR;
            System.out.println("not inserted");
        }
        return rowsAffected;
    }

    public List<OfficerBookBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchOfficeCode, String searchOfficeName, String searchOfficerName, String searchFromDate, String searchToDate, String searchBookType, String searchStatusType, String searchActive, String searchBookNo) {
        List<OfficerBookBean> list = new ArrayList<OfficerBookBean>();
        if(searchBookType.equals("All"))
            searchBookType = "";
        if (!searchFromDate.equals("")) {
            String da1[] = searchFromDate.split("-");
            String da2[] = searchToDate.split("-");
            searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
            searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
            System.out.println(searchFromDate);
            System.out.println(searchToDate);
        }
        String query = "select org.organisation_name,oft.office_type ,oo.org_office_code,oo.org_office_name ,kp.key_person_id,kp.emp_code,kp.key_person_name,"
                + "st.status_type,st.status_type_id,d.designation ,ob.book_no,ob.from_no,ob.to_no,ob.book_revision_no,ob.active,ob.remark, DATE_FORMAT(ob.issue_date, '%d-%m-%Y') AS issue_date, ob.book_type from officer_book as ob "
                + "left join key_person as kp on ob.key_person_id=kp.key_person_id  "
                + " left join org_office as oo on oo.org_office_id=kp.org_office_id "
                + " left join designation as d on kp.designation_id=d.designation_id "
                + " left join org_office_type as oft on oo.office_type_id=oft.office_type_id "
                + " left join organisation_name as org on oo.organisation_id=org.organisation_id "
                + "left join status_type as st on ob.status_type_id=st.status_type_id "
                + " WHERE IF('" + searchOfficeCode + "'='' ,oo.org_office_id is null OR oo.org_office_id=0 OR oo.org_office_code LIKE '%%',oo.org_office_code LIKE '" + searchOfficeCode + ".%' OR oo.org_office_code like ?)"
                //+ " AND IF ('" + searchOfficeCode+ "' = '', org.org_office_code LIKE '%%',org.org_office_code =?) "
                + " AND IF ('" + searchOfficeName + "' = '', oo.org_office_id is null OR oo.org_office_id=0 OR oo.org_office_name LIKE '%%',oo.org_office_name =?) "
                + " AND IF ('" + searchOfficerName + "' = '',ob.key_person_id is null OR ob.key_person_id=0 OR kp.key_person_name LIKE '%%',kp.key_person_name=?) "
                + " AND IF ('" + searchFromDate + "'='',ob.issue_date LIKE '%%',( (ob.issue_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"
                + " AND IF ('" + searchBookType + "'='',ob.book_type LIKE '%%', ob.book_type = '" + searchBookType + "')"
                + " AND IF ('" + searchStatusType + "'='',st.status_type LIKE '%%', st.status_type = '" + searchStatusType + "')"
                + " AND IF ('" + searchActive + "'='',ob.active LIKE '%%', ob.active = '" + searchActive + "')"
                + " AND IF ('" + searchBookNo + "'='',ob.book_no LIKE '%%', ob.book_no = '" + searchBookNo + "')"
                //+ " GROUP BY oo.org_office_code,oo.org_office_name,kp.key_person_name"
                //  + " ORDER BY oo.org_office_code,oo.org_office_name,kp.key_person_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOfficeCode);
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(searchOfficeName));
            pstmt.setString(3, krutiToUnicode.convert_to_unicode(searchOfficerName));

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                OfficerBookBean officerBookBean = new OfficerBookBean();
                //officerBookBean.setStatus_type_id(rset.getInt("status_type_id"));
                // officerBookBean.setOfficer_name(rset.getString("officer_name"));
                officerBookBean.setBook_no(rset.getInt("book_no"));
                officerBookBean.setFrom_no(rset.getString("from_no"));
                officerBookBean.setTo_no(rset.getString("to_no"));
                officerBookBean.setStatus_type(rset.getString("status_type"));
                //officerBookBean.setStatus_type_id(rset.getInt("status_type_id"));
                officerBookBean.setRemark(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("remark")));
                officerBookBean.setBook_revision_no(rset.getInt("book_revision_no"));
                officerBookBean.setIssue_date(rset.getString("issue_date"));
                officerBookBean.setKey_person_id(rset.getInt("key_person_id"));
                officerBookBean.setKey_person_name(rset.getString("key_person_name"));
                officerBookBean.setOrg_office_code(rset.getString("org_office_code"));
                officerBookBean.setOrg_office_name(rset.getString("org_office_name"));
                officerBookBean.setOrganisation_name(rset.getString("organisation_name"));
                officerBookBean.setOffice_type(rset.getString("office_type"));
                officerBookBean.setDesignation(rset.getString("designation"));
                officerBookBean.setActive(rset.getString("active"));
                officerBookBean.setEmp_code(rset.getString("emp_code"));
                String book_type = rset.getString("book_type");
                //book_type = book_type.equals("C")? "Challan" : "Receipt";
                officerBookBean.setBook_type(book_type.equals("C")? "Challan" : "Receipt");
                list.add(officerBookBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public List<OfficerBookBean> showAllData() {
        List<OfficerBookBean> list = new ArrayList<OfficerBookBean>();

        String query = "select org.organisation_name,oft.office_type ,oo.org_office_code,oo.org_office_name ,kp.key_person_id,kp.emp_code,kp.key_person_name,"
                + "st.status_type,d.designation ,ob.book_no,ob.from_no,ob.to_no,ob.book_revision_no,ob.active,ob.remark,ob.issue_date, ob.book_type from officer_book as ob "
                + "left join key_person as kp on ob.key_person_id=kp.key_person_id  "
                + " left join org_office as oo on oo.org_office_id=kp.org_office_id "
                + " left join designation as d on kp.designation_id=d.designation_id "
                + " left join org_office_type as oft on oo.office_type_id=oft.office_type_id "
                + " left join organisation_name as org on oo.organisation_id=org.organisation_id "
                + "left join status_type as st on ob.status_type_id=st.status_type_id";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                OfficerBookBean officerBookBean = new OfficerBookBean();
                //officerBookBean.setStatus_type_id(rset.getInt("status_type_id"));
                // officerBookBean.setOfficer_name(rset.getString("officer_name"));
                officerBookBean.setBook_no(rset.getInt("book_no"));
                officerBookBean.setFrom_no(rset.getString("from_no"));
                officerBookBean.setTo_no(rset.getString("to_no"));
                officerBookBean.setStatus_type(rset.getString("status_type"));
                officerBookBean.setRemark(rset.getString("remark"));
                officerBookBean.setBook_revision_no(rset.getInt("book_revision_no"));
                officerBookBean.setIssue_date(rset.getString("issue_date"));
                officerBookBean.setKey_person_id(rset.getInt("key_person_id"));
                officerBookBean.setKey_person_name(rset.getString("key_person_name"));
                officerBookBean.setOrg_office_code(rset.getString("org_office_code"));
                officerBookBean.setOrg_office_name(rset.getString("org_office_name"));
                officerBookBean.setOrganisation_name(rset.getString("organisation_name"));
                officerBookBean.setOffice_type(rset.getString("office_type"));
                officerBookBean.setDesignation(rset.getString("designation"));
                officerBookBean.setActive(rset.getString("active"));
                officerBookBean.setEmp_code(rset.getString("emp_code"));
                String book_type = rset.getString("book_type");
                officerBookBean.setBook_type(book_type.equals("C")? "Challan" : "Receipt");
                list.add(officerBookBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchOfficeName,String searchOfficeCode, String searchOfficerName,  String searchFromDate, String searchToDate, String searchBookType, String searchStatusType, String searchActive, String searchBookNo) {
        int noOfRows = 0;
        if(searchBookType.equals("All"))
            searchBookType = "";
        try {
            if (!searchFromDate.equals("")) {
                String da1[] = searchFromDate.split("-");
                String da2[] = searchToDate.split("-");
                searchFromDate = da1[2] + "-" + da1[1] + "-" + da1[0];
                searchToDate = da2[2] + "-" + da2[1] + "-" + da2[0];
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }
            String query = "select Count(*) from officer_book as ob "
                    + "left join key_person as kp on ob.key_person_id=kp.key_person_id  "
                    + " left join org_office as oo on oo.org_office_id=kp.org_office_id "
                    + " left join designation as d on kp.designation_id=d.designation_id "
                    + " left join org_office_type as oft on oo.office_type_id=oft.office_type_id "
                    + " left join organisation_name as org on oo.organisation_id=org.organisation_id "
                    + "left join status_type as st on ob.status_type_id=st.status_type_id "
                    + " WHERE IF('" + searchOfficeCode + "'='' ,oo.org_office_id is null OR oo.org_office_id=0 OR oo.org_office_code LIKE '%%',oo.org_office_code LIKE '" + searchOfficeCode + ".%' OR oo.org_office_code like ?)"
                    + " AND IF ('" + searchOfficeName + "' = '', oo.org_office_id is null OR oo.org_office_id=0 OR oo.org_office_name LIKE '%%',oo.org_office_name =?) "
                    + " AND IF ('" + searchOfficerName + "' = '',ob.key_person_id is null OR ob.key_person_id=0 OR kp.key_person_name LIKE '%%',kp.key_person_name=?) "
                    + " AND IF ('" + searchFromDate + "'='',ob.issue_date LIKE '%%',( (ob.issue_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))"
                    + " AND IF ('" + searchBookType + "'='',ob.book_type LIKE '%%', ob.book_type = '" + searchBookType + "')"
                    + " AND IF ('" + searchStatusType + "'='',st.status_type LIKE '%%', st.status_type = '" + searchStatusType + "')"
                    + " AND IF ('" + searchActive + "'='',ob.active LIKE '%%', ob.active = '" + searchActive + "')"
                    + " AND IF ('" + searchBookNo + "'='',ob.book_no LIKE '%%', ob.book_no = '" + searchBookNo + "')";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchOfficeCode);
            stmt.setString(2, krutiToUnicode.convert_to_unicode(searchOfficeName));
            stmt.setString(3, krutiToUnicode.convert_to_unicode(searchOfficerName));

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows State type model" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public Map<Integer, String> getStatusTypeList() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        String statusTypeList = "SELECT status_type_id,status_type FROM status_type";

        try {
            PreparedStatement ps = connection.prepareStatement(statusTypeList);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getInt("status_type_id"), rs.getString("status_type"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel statusTypeList (" + e);
        }

        return map;
    }

    public String getStatusTypeId(String status_type) {
        String list = "";
        String query = "select status_type_id from status_type where status_type=?";
        try {
            PreparedStatement pstsmt = connection.prepareStatement(query);
            pstsmt.setString(1, status_type);
            ResultSet rs = pstsmt.executeQuery();
            while (rs.next()) {
                list = rs.getString("status_type_id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getItemNosList " + e);
        }
        return list;
    }

    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public int deleteRecord(String book_no) {
        //String query = "DELETE FROM officer_book WHERE book_no=" + book_no;
        String query = "UPDATE officer_book SET active = 'N' WHERE book_no=" + book_no;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:keypersonModel-deleteRecord-- " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getBookNoList(String q){
        List<String> list = new ArrayList<String>();
        int count = 0;
        String query = "SELECT book_no FROM officer_book GROUP BY book_no";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            q = q.trim();
            while(rs.next()){
                String book_no = rs.getString("book_no");
                if(book_no.startsWith(q)){
                    list.add(book_no);
                    count++;
                }
            }
//            if(count == 0)
//                list.add("");
        }catch(Exception ex){
        }
        return list;
    }

    public String getOfficerData(String officer_name){
        officer_name = krutiToUnicode.convert_to_unicode(officer_name);
        String officer_data = "";
        String query = "SELECT emp_code, d.designation FROM key_person kp, designation d "
                + " WHERE d.designation_id = kp.designation_id AND key_person_name='"+officer_name+"'";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                
                officer_data = rs.getString("emp_code") + "_" + unicodeToKruti.Convert_to_Kritidev_010(rs.getString("designation"));
            }
        }catch(Exception ex){
            System.out.println("ERROR : getOfficerData() in OfficerBookModel : " + ex);
        }
        return officer_data;
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
}

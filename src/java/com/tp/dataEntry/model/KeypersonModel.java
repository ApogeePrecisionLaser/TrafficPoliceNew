/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.dataEntry.model;

import com.tp.tableClasses.KeyPerson;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author SoftTechinsert
 */
public class KeypersonModel {

    private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    private String connectionString;
    private String db_username;
    private String db_password;
    private String driverClass;
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
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

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
        } catch (Exception e) {
            System.out.println("KeypersonModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String searchOrganisation, String searchKeyPerson, String searchOfficeCode, String searchEmpCode, String searchDesignation,String searchMobileNo) {
        int noOfRows = 0;
        try {
            String query = "select count(*)  "
                    + "FROM key_person AS k, organisation_name AS org, city AS c, state AS s, org_office AS of, designation as d, org_office_type as oft, district AS dtc, division AS dv "
                    + "WHERE k.org_office_id=of.org_office_id AND oft.office_type_id = of.office_type_id AND of.organisation_id = org.organisation_id "
                    + "AND k.city_id = c.city_id AND c.district_id = dtc.district_id AND dtc.division_id = dv.division_id AND dv.state_id = s.state_id And k.designation_id = d.designation_id "
                    + " AND if('" + searchOrganisation + "' = '' , organisation_name like '%%' , organisation_name = ? )  "
                    + "AND if('" + searchKeyPerson + "' = '' , key_person_name like '%%' , key_person_name = ? )  "
                    + "AND if('" + searchOfficeCode + "' = '' , of.org_office_code like '%%' , of.org_office_code LIKE  '" + searchOfficeCode + "%' OR of.org_office_code like ? )  "
                    + "AND if('" + searchEmpCode + "' = '' , emp_code like '%%' , emp_code = ? )  "
                    + "AND if('" + searchDesignation + "' = '' ,d.designation like '%%' ,d.designation = ? ) "
                    + "AND if('" + searchMobileNo + "' = '' ,k.mobile_no1 like '%%' ,k.mobile_no1 = ? ) ";
                   

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOrganisation);
            pstmt.setString(3, krutiToUnicode.convert_to_unicode(searchKeyPerson));
            pstmt.setString(2, searchOfficeCode);
            pstmt.setString(4, searchEmpCode);
            pstmt.setString(5, krutiToUnicode.convert_to_unicode(searchDesignation));
            pstmt.setString(6, searchMobileNo);

            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));

        } catch (Exception e) {
            System.out.println("Error:keypersonModel-getNoOfRows-- " + e);
        }
        return noOfRows;
    }

    public List<KeyPerson> showData(int lowerLimit, int noOfRowsToDisplay, String searchOrganisation, String searchKeyPerson, String searchOfficeCode, String searchEmpCode, String searchDesignation,String searchMobileNo) {
        List<KeyPerson> list = new ArrayList<KeyPerson>();
        String query;
        byte[] imgData = null;
        //InputStream binaryStream = null;
        try {
            // Use DESC or ASC for descending or ascending order respectively of fetched data.
            query = "SELECT k.key_person_id,k.general_image_details_id, off.org_office_code, oft.office_type ,org.organisation_name, off.org_office_name , k.key_person_name, d.designation, "
                    + " s.state_name, c.city_name, k.address_line1, k.address_line2,k.address_line3,k.image_path, "
                    + "k.mobile_no1, k.mobile_no2, k.landline_no1, k.landline_no2, k.email_id1, k.email_id2, k.salutation,k.emp_code "
                    + "FROM key_person AS k, organisation_name AS org, city AS c, state AS s, org_office AS off, designation as d, org_office_type as oft, district AS dtc, division AS dv "
                    + "WHERE k.org_office_id=off.org_office_id AND oft.office_type_id = off.office_type_id AND off.organisation_id = org.organisation_id "
                    + "AND k.city_id = c.city_id AND c.district_id = dtc.district_id AND dtc.division_id = dv.division_id AND dv.state_id = s.state_id And k.designation_id = d.designation_id "
                    + " AND if('" + searchOrganisation + "' = '' , organisation_name like '%%' , organisation_name = ? )  "
                    + "AND if('" + searchKeyPerson + "' = '' , key_person_name like '%%' , key_person_name = ? )  "
                    + "AND if('" + searchOfficeCode + "' = '' , off.org_office_code like '%%' , off.org_office_code LIKE  '" + searchOfficeCode + ".%' OR off.org_office_code like ? )  "
                    + "AND if('" + searchEmpCode + "' = '' , emp_code like '%%' , emp_code = ? )  "
                    + "AND if('" + searchDesignation + "' = '' ,d.designation like '%%' ,d.designation = ? ) "
                    + "AND if('" + searchMobileNo + "' = '' , k.mobile_no1 like '%%' ,k.mobile_no1 = ? )  "
                    + " GROUP BY k.key_person_id  "
                    + "ORDER BY org.organisation_name, off.org_office_name, k.key_person_name  "
                    + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOrganisation);
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(searchKeyPerson));
            pstmt.setString(3, searchOfficeCode);
            pstmt.setString(4, searchEmpCode);
            pstmt.setString(5, krutiToUnicode.convert_to_unicode(searchDesignation));
            pstmt.setString(6, searchMobileNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                KeyPerson key = new KeyPerson();
                key.setKey_person_id(rset.getInt("key_person_id"));
                key.setOrg_office_code(rset.getString("org_office_code"));
                key.setOffice_type(rset.getString("office_type"));
                key.setOrganisation_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("organisation_name")));
                key.setOrg_office_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("org_office_name")));
                key.setKey_person_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name")));
                key.setImage_path(rset.getString("image_path"));
                key.setDesignation(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("designation")));
                key.setState_name(rset.getString("state_name"));
                key.setCity_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name")));
                key.setAddress_line1(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("address_line1")));
                key.setAddress_line2(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("address_line2")));
                key.setAddress_line3(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("address_line3")));
                key.setMobile_no1(rset.getString("mobile_no1"));
                key.setMobile_no2(rset.getString("mobile_no2"));
                key.setLandline_no1(rset.getString("landline_no1"));
                key.setLandline_no2(rset.getString("landline_no2"));
                key.setEmail_id1(rset.getString("email_id1"));
                key.setEmail_id2(rset.getString("email_id2"));
                key.setSalutation(rset.getString("salutation"));
                key.setEmp_code(rset.getString("emp_code"));
                key.setGeneral_image_details_id(rset.getInt("general_image_details_id"));
                //key.setImage_name(rset.getString("image_name"));
                list.add(key);
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
    }

    public byte[] generateKeyperSonList(String jrxmlFilePath, int organisation_id) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        mymap.put("organisation_id", organisation_id);
        Connection con = connection;
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
        } catch (Exception e) {
            System.out.println("Error: in KeypersonModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateKeyperSonExcelList(String jrxmlFilePath, int organisation_id) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        mymap.put("organisation_id", organisation_id);
        Connection con = connection;
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            //reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, con);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("Error: in KeypersonModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public int getOrganisation_id(String organisation_name) {
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: keypersonModel--" + e);
        }
        return organisation_id;
    }

    public int insertRecord(KeyPerson key, Iterator itr, String destination) {
        int rowsAffected = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String image_uploaded_for = "key_person";
        String query = "", imageQuery = "", updateQuery = "";
        PreparedStatement pstmt = null;

        int count = 0;
        try {
            String tempExt = key.getImage_path();
            int maxId = 0;
            String query1 = " select max(key_person_id) as  key_person_id from key_person ";
            PreparedStatement pstmt1 = connection.prepareStatement(query1);
            ResultSet rset = pstmt1.executeQuery();
            while (rset.next()) {
                maxId = rset.getInt("key_person_id");
                System.out.println(maxId);
            }
            int check = 0;
            if (tempExt.isEmpty()) {
                tempExt = key.getImage_path();
                maxId = key.getKey_person_id();
                check = 1;
            }
            int index = tempExt.lastIndexOf(".");
            int index1 = tempExt.length();
            String Extention = tempExt.substring(index + 1, index1);
            tempExt = "." + Extention;
            String imageName = key.getDesignation() + "_" + "pic" + "_" + maxId + tempExt;
            key.setImage_name(imageName);
            makeDirectory(destination);
            if (check == 1) {
                File file = new File(key.getImage_path());
                File file1 = new File(imageName);
                if (file.renameTo(file1)) {
                    System.out.println("Rename succesful");
                } else {
                    System.out.println("Rename failed");
                }
                //  updateImageName(imageName,maxId);
            } else {
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    try {
                        if (!item.isFormField()) {
                            File file = new File(destination + imageName);
                            String image = item.getName();
                            if (image.isEmpty() || image.equals(destination)) {
                            } else {
                                item.write(file);
                                message = "Image Uploaded Successfully.";
                                //  updateImageName(imageName,id);
                                count++;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Image upload error: " + e);
                    }
                }
            }
            query = "INSERT INTO key_person( salutation, key_person_name, designation_id, org_office_id, city_id, address_line1, "
                    + "address_line2, address_line3,"
                    + " mobile_no1, mobile_no2, landline_no1, landline_no2, email_id1, email_id2,emp_code,image_path) "
                    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

            imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description,key_person_id) "
                    + " VALUES(?, ?, ?, ?,?)";

            updateQuery = "UPDATE key_person set general_image_details_id=? where key_person_id=?";

            pstmt = connection.prepareStatement(query);
            pstmt.setString(16, destination + imageName);
            // pstmt.setInt(17,Integer.parseInt(key.getImage_id()));
            //}




            pstmt.setString(1, key.getSalutation());
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(key.getKey_person_name()));
            pstmt.setInt(3, key.getDesignation_id());
            pstmt.setInt(4, key.getOrg_office_id());
            pstmt.setInt(5, key.getCity_id());
            pstmt.setString(6, krutiToUnicode.convert_to_unicode(key.getAddress_line1()));
            pstmt.setString(7, krutiToUnicode.convert_to_unicode(key.getAddress_line2()));
            pstmt.setString(8, krutiToUnicode.convert_to_unicode(key.getAddress_line3()));
            pstmt.setString(9, key.getMobile_no1());
            pstmt.setString(10, key.getMobile_no2());
            pstmt.setString(11, key.getLandline_no1());
            pstmt.setString(12, key.getLandline_no2());
            pstmt.setString(13, key.getEmail_id1());
            pstmt.setString(14, key.getEmail_id2());
            pstmt.setString(15, key.getEmp_code());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--insertRecord-- " + e);
        }
        if (rowsAffected > 0) {
            try {
                pstmt = connection.prepareStatement(imageQuery);
                pstmt.setString(1, key.getImage_name());
                pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
                pstmt.setString(3, current_date);
                pstmt.setString(4, "this image is for site");
                pstmt.setInt(5, getkey_person_id(key.getEmp_code()));
                rowsAffected = pstmt.executeUpdate();
                pstmt.close();
            } catch (Exception e) {
                System.out.println("Error:keypersonModel--insertRecord-- " + e);
            }
            if (rowsAffected > 0) {
                try {
                    pstmt = connection.prepareStatement(updateQuery);
                    pstmt.setInt(1, getgeneral_image_details_id(getkey_person_id(key.getEmp_code())));
                    pstmt.setInt(2, getkey_person_id(key.getEmp_code()));
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Exception :" + e);
                }
            }
            // rowsAffected = writeImage(key, itr, destination);
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
    /*   public int writeImage(KeyPerson key, Iterator itr, String destination) {
    int count = 0;
    try {
    String tempExt =  key.getImage_path();
    int maxId=0;
    String query1=" select max(key_person_id) as  key_person_id from key_person ";
    PreparedStatement pstmt1 = connection.prepareStatement(query1);
    ResultSet rset=pstmt1.executeQuery();
    while(rset.next())
    {
    maxId=rset.getInt("key_person_id");
    System.out.println(maxId);
    }
    int check=0;
    if(tempExt.isEmpty()){
    tempExt=key.getImage_path();
    maxId= key.getKey_person_id();
    check=1;
    }
    int index = tempExt.lastIndexOf(".");
    int index1 = tempExt.length();
    String Extention = tempExt.substring(index + 1, index1);
    tempExt = "." + Extention;
    String imageName = key.getDesignation()+"_"+"pic"+"_"+ maxId + tempExt;
    makeDirectory(destination);
    if(check==1){
    File file = new File(key.getImage_path());
    File file1 = new File(imageName);
    if(file.renameTo(file1)){
    System.out.println("Rename succesful");
    }else{
    System.out.println("Rename failed");
    }
    //  updateImageName(imageName,maxId);
    }else{
    while (itr.hasNext()) {
    FileItem item = (FileItem) itr.next();
    try {
    if (!item.isFormField()) {
    File file = new File(destination+ imageName);
    String image = item.getName();
    if (image.isEmpty() || image.equals(destination)) {
    } else {
    item.write(file);
    message = "Image Uploaded Successfully.";
    //  updateImageName(imageName,id);
    count++;
    }
    }
    } catch (Exception e) {
    System.out.println("Image upload error: " + e);
    }
    }
    }
    } catch (Exception ex) {
    System.out.println("File write error" + ex);
    message = "Cannot upload the image, some error.";
    msgBgColor = COLOR_ERROR;
    }
    return 1;
    }*/

    public int getimage_destination_id(String image_uploaded_for) {
        String query;
        int image_destination_id = 0;
        query = " SELECT image_destination_id, destination_path from image_destination AS id , image_uploaded_for As i "
                + " WHERE id.image_uploaded_for_id=i.image_uploaded_for_id AND i.image_uploaded_for= ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, image_uploaded_for);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                String destination_path = rset.getString("destination_path");
                System.out.println(destination_path);
                rset.getInt("image_destination_id");
                System.out.println("id = " + rset.getInt("image_destination_id"));
                image_destination_id = rset.getInt("image_destination_id");
                System.out.println(image_destination_id);
            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return image_destination_id;
    }

    public int getkey_person_id(String emp_code) {
        String query;
        int key_person_id = 0;
        query = "select key_person_id from key_person where emp_code='" + emp_code + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("key_person_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return key_person_id;
    }

    public int getgeneral_image_details_id(int id) {
        String query;
        int key_person_id = 0;
        query = "select general_image_details_id from general_image_details where key_person_id='" + id + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("general_image_details_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return key_person_id;
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        System.out.println("dirPathName---" + dirPathName);
        //dirPathName = "C:/ssadvt/sor/organisation" ;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("Error - " + e);
            }
        }
        return result;
    }

    public int updateRecord(KeyPerson key, Iterator itr, String destination) {

        String image_uploaded_for = "key_person";
        String imageQuery = "", updateQuery = "";
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String tempExt="";
        int count = 0;
        try {
//            if (key.getGeneral_image_details_id() != 0) {
//                String image_name = key.getImage_path();
//                File file1 = new File(destination + File.separator + image_name);
//                if (!file1.exists()) {
//                }
//                while (itr.hasNext()) {
//                    FileItem item = (FileItem) itr.next();
//                    try {
//                        if (!item.isFormField()) {
//                            File file = new File(destination + image_name);
//                            String image = item.getName();
//                            if (image.isEmpty() || image.equals(destination)) {
//                            } else {
//                                item.write(file);
//                                message = "Image Uploaded Successfully.";
//                                //  updateImageName(imageName,id);
//                                count++;
//                            }
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Image upload error: " + e);
//                    }
//                }
//            } else {
             if (key.getGeneral_image_details_id() != 0) {
             String query="select image_name from general_image_details where general_image_details_id='"+key.getGeneral_image_details_id()+"'";
             PreparedStatement pstmt1 = connection.prepareStatement(query);
                ResultSet rset = pstmt1.executeQuery();
                while (rset.next()) {
                    tempExt=rset.getString("image_name");
                }
             }else{
                tempExt = key.getImage_path();}
                int maxId = 0;
                 String imageName="";
                String query1 = " select max(key_person_id) as  key_person_id from key_person ";
                PreparedStatement pstmt1 = connection.prepareStatement(query1);
                ResultSet rset = pstmt1.executeQuery();
                while (rset.next()) {
                    maxId = rset.getInt("key_person_id");
                    System.out.println(maxId);
                }
                int check = 0;
                if (tempExt.isEmpty()) {
                    tempExt = key.getImage_path();
                    maxId = key.getKey_person_id();
                    check = 1;
                }
                File file1 = new File(destination + File.separator + tempExt);
                if (file1.exists()) {
                  imageName=tempExt;
                  key.setImage_name(imageName);
                } else {
                    int index = tempExt.lastIndexOf(".");
                    int index1 = tempExt.length();
                    String Extention = tempExt.substring(index + 1, index1);
                    tempExt = "." + Extention;

                     imageName = key.getDesignation() + "_" + "pic" + "_" + maxId + tempExt;
                    key.setImage_name(imageName);
                }
                makeDirectory(destination);
                if (check == 1) {
                    File file = new File(key.getImage_path());
                    File file2 = new File(imageName);
                    if (file.renameTo(file2)) {
                        System.out.println("Rename succesful");
                    } else {
                        System.out.println("Rename failed");
                    }
                    // updateImageName(imageName,maxId);
                } else {
                    while (itr.hasNext()) {
                        FileItem item = (FileItem) itr.next();
                        try {
                            if (!item.isFormField()) {
                                File file = new File(destination + imageName);
                                String image = item.getName();
                                if (image.isEmpty() || image.equals(destination)) {
                                } else {
                                    item.write(file);
                                    message = "Image Uploaded Successfully.";
                                    //  updateImageName(imageName,id);
                                    count++;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Image upload error: " + e);
                        }
                    }
                }
         //   }
        } catch (SQLException e) {
            System.out.println("Exception is :" + e);
        }

        String query = "UPDATE key_person SET  salutation=?, key_person_name=?, designation_id=?, org_office_id=?, city_id=?, address_line1=?, "
                + "address_line2=?, address_line3=?,"
                + " mobile_no1=?, mobile_no2=?, landline_no1=?, landline_no2=?, email_id1=?, email_id2=?,emp_code=? "
                + "WHERE key_person_id=?";
        updateQuery = "update general_image_details set image_name=?,image_destination_id=?, date_time=?,key_person_id=? where general_image_details_id=?";
        imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description,key_person_id) "
                + " VALUES(?, ?, ?, ?,?)";
        String updateQuery1 = "UPDATE key_person set general_image_details_id=? where key_person_id=?";

        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, key.getSalutation());
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(key.getKey_person_name()));
            pstmt.setInt(3, key.getDesignation_id());
            pstmt.setInt(4, key.getOrg_office_id());
            pstmt.setInt(5, key.getCity_id());
            pstmt.setString(6, krutiToUnicode.convert_to_unicode(key.getAddress_line1()));
            pstmt.setString(7, krutiToUnicode.convert_to_unicode(key.getAddress_line2()));
            pstmt.setString(8, krutiToUnicode.convert_to_unicode(key.getAddress_line3()));
            pstmt.setString(9, key.getMobile_no1());
            pstmt.setString(10, key.getMobile_no2());
            pstmt.setString(11, key.getLandline_no1());
            pstmt.setString(12, key.getLandline_no2());
            pstmt.setString(13, key.getEmail_id1());
            pstmt.setString(14, key.getEmail_id2());
            pstmt.setString(15,key.getEmp_code() );
            pstmt.setInt(16, key.getKey_person_id());
            rowsAffected = pstmt.executeUpdate();
            if(rowsAffected>0){
            if (key.getGeneral_image_details_id() != 0) {
                PreparedStatement pstmt1 = connection.prepareStatement(updateQuery);
               // pstmt.setString(1, key.getImage_path());
                pstmt1.setString(1, key.getImage_name());
                pstmt1.setInt(2, getimage_destination_id(image_uploaded_for));
                pstmt1.setString(3, current_date);
                pstmt1.setInt(4, getkey_person_id(key.getEmp_code()));
                pstmt1.setInt(5,key.getGeneral_image_details_id());
                rowsAffected = pstmt1.executeUpdate();
            } else {
                try {
                     PreparedStatement pstmt2 = connection.prepareStatement(imageQuery);
                    pstmt2.setString(1, key.getImage_name());
                    pstmt2.setInt(2, getimage_destination_id(image_uploaded_for));
                    pstmt2.setString(3, current_date);
                    pstmt2.setString(4, "this image is for site");
                    pstmt2.setInt(5, getkey_person_id(key.getEmp_code()));
                    rowsAffected = pstmt2.executeUpdate();
                    pstmt.close();
                    if (rowsAffected > 0) {
                        try {
                             PreparedStatement pstmt3 = connection.prepareStatement(updateQuery1);
                            pstmt3.setInt(1, getgeneral_image_details_id(getkey_person_id(key.getEmp_code())));
                            pstmt3.setInt(2, getkey_person_id(key.getEmp_code()));
                            pstmt3.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Exception :" + e);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error:keypersonModel--insertRecord-- " + e);
                }
            }
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel-updateRecord-- " + e);
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

    public int deleteRecord(int key_person_id) {
        String query = "DELETE FROM key_person WHERE key_person_id=" + key_person_id;
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

    public int getCity_id(String city_name) {
        String query = "SELECT city_id FROM city WHERE city_name = ?";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("city_id");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getCity_id-- " + e);
        }
        return city_id;
    }

    public List<String> getOffice_type(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oft.office_type FROM org_office_type AS oft ORDER BY office_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_type = rset.getString("office_type");
                if (office_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_type);
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

    public List<String> getOrgPersonNameList(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT key_person_name FROM key_person  As k, org_office AS of "
                + " WHERE k.org_office_id = of.org_office_id"
                + " GROUP BY key_person_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, emp_code);
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
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrgPersonNameList()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeName(String q, String office_code) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT of.org_office_name FROM org_office AS of , org_office_type as oft,organisation_name AS o "
//                + "WHERE oft.office_type_id = of.office_type_id  AND o.organisation_id=of.organisation_id AND of.org_office_code =?"
//                + " GROUP BY org_office_name ";
// "AND if('" + searchEmpCode + "' = '' , emp_code like '%%' , emp_code = ? )  "

          String query = "SELECT off.org_office_name FROM org_office AS off , org_office_type as oft,organisation_name AS o\n" +
           "WHERE oft.office_type_id = off.office_type_id  AND o.organisation_id=off.organisation_id\n" +
           "AND if('"+office_code+"' = '' , off.org_office_code like '%%' , off.org_office_code = ? )\n" +
           "GROUP BY org_office_name  ";
        try {
            int count = 0;
            q = q.trim();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, office_code);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("org_office_name"));
                if (office_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_name);
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

    public int getOrgOffice_id(String org_office_name, String office_code) {
        String query = "SELECT off.org_office_id "
                + " FROM org_office AS off , "
                + " organisation_name AS o "
                + " WHERE o.organisation_id=off.organisation_id AND off.org_office_name = ? AND off.org_office_code = ?";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, org_office_name);
            pstmt.setString(2, office_code);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel-- getOrganization_id--" + e);
        }
        return organisation_id;
    }

    public List<String> getCityName(String q, String state_name) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String query = "SELECT city_name FROM city AS c, district AS d, division AS dv, state AS st "
                + " WHERE c.district_id = d.district_id AND d.division_id = dv.division_id "
                + " AND dv.state_id = st.state_id AND st.state_name=? ORDER BY city_name";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String AdvertiseName = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name"));
                if (AdvertiseName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(AdvertiseName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such City Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: KeypersonModel-" + e);
        }
        return list;
    }

    public List<String> getStateName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT state_name FROM state s where s.active='Y' ORDER BY state_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name"));
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such State Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:KeypersonModel- " + e);
        }
        return list;
    }

    public int getDesignation_id(String designation) {
        String query = "SELECT designation_id FROM designation WHERE designation = ?";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, designation);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getdesignation_id-- " + e);
        }
        return designation_id;
    }

    public List<String> getDesignation(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT designation from designation order by designation ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String designation = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("designation"));
                if (designation.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(designation);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeCode(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org.org_office_code FROM org_office AS org , org_office_type as oft "
                + " WHERE org.office_type_id=oft.office_type_id group by org.org_office_code ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,office_type);
            ResultSet rset = pstmt.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                if (org_office_code.startsWith(q)) {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }
    public List<String> searchMobileNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select mobile_no1\n" +
                       "from key_person kp; ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1,office_type);
            ResultSet rset = pstmt.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String mobile_no1 = rset.getString("mobile_no1");
                if (mobile_no1.startsWith(q)) {
                    list.add(mobile_no1);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public List<String> getsearchOrganisation(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org.org_office_code FROM org_office AS org , org_office_type as oft "
                + " WHERE org.office_type_id=oft.office_type_id group by org.org_office_code ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                if (org_office_code.startsWith(q)) {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Person of such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }

    public List<String> getSearchEmpCode(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select kp.emp_code from key_person kp "
                + "left join org_office as of on kp.org_office_id = of.org_office_id "
                + " GROUP BY kp.emp_code ";


        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String emp_code = rset.getString("emp_code");
                if (emp_code.startsWith(q)) {
                    list.add(emp_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Person of such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }

    public byte[] generateKeyperSonList(String jrxmlFilePath, List list) {
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

    public String exist_image(String emp_code) {
        String image_name = null;
        try {
            String query = " SELECT image_path FROM key_person "
                    + " where emp_code = ? ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, emp_code);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                image_name = rst.getString("image_path");
            }
        } catch (Exception e) {
            System.out.println("ERROR keyPerson Model exist_image" + e);
        }
        return image_name;
    }

    public String getImagePath(String emp_code){
     String img_name = "";
     String destination_path = "";
     String query = "SELECT image_name, destination_path FROM general_image_details gid, key_person kp, image_destination dp "
             + " WHERE dp.image_destination_id=gid.image_destination_id "
             + " AND gid.general_image_details_id = kp.general_image_details_id "
             + " AND kp.emp_code = '" + emp_code + "'";
     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next()){
             img_name = rs.getString("image_name");
             destination_path = rs.getString("destination_path");
         }
         //String[] img_path = img_name.split("-");
         destination_path = destination_path + img_name;
     }catch(Exception ex){
         System.out.println("ERROR: in getImageName in TrafficPoliceSearchModel : " + ex);
     }
     return destination_path;
 }

    public String getDestinationPath(String key_person_name) {
        String image_path = null;
        try {
            String query = " SELECT image_path FROM key_person "
                    + " where key_person_name = ? ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, key_person_name);
            ResultSet rst = psmt.executeQuery();
            if (rst.next()) {
                image_path = rst.getString("image_path");
            }
        } catch (Exception e) {
            System.out.println("ERROR keyPerson Model exist_image" + e);
        }
        return image_path;
    }

    public String getDestination_Path(String image_uploaded_for){
     String destination_path = "";
     String query = " SELECT destination_path FROM image_destination id, image_uploaded_for  iuf "
             + " WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id "
             + " AND iuf.image_uploaded_for = '" + image_uploaded_for + "' ";//traffic_police
     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next())
             destination_path = rs.getString("destination_path");
     }catch(Exception ex){
         System.out.println("ERROR: in getTrafficPoliceId in TraffiPoliceSearchModel : " + ex);
     }
     return destination_path;
  }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("keypersonModel closeConnection() Error: " + e);
        }
    }
}

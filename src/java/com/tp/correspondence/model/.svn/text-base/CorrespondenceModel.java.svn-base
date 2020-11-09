/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.correspondence.model;

import com.tp.correspondence.tableClasses.Correspondence;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
 * @author Shruti
 */
public class CorrespondenceModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public int getNoOfRows(String correspondence_no, String search_subject, String type_of_corr, String org_from, String search_office, String corr_date, String type_of_doc, String dealing_person, String searchreply_forward, String search_Other_person, String search_key_person) {
        int noOfRows = 0;
        search_subject = krutiToUnicode.convert_to_unicode(search_subject);
        org_from = krutiToUnicode.convert_to_unicode(org_from);
        String add_query = "";
        //String add_field = "";
        String add_table = "";
        if(search_office.equals("ALL"))
            search_office = "";
        if(type_of_corr.equals("Incoming"))
            if(searchreply_forward.equals("Reply"))
                add_query = "AND c.own_ref_no IS NOT NULL AND c.ref_corres_id IS NOT NULL AND c.ref_corres_date IS NOT NULL";
        if(type_of_corr.equals("Outgoing"))
            if(searchreply_forward.equals("Reply"))
                add_query = "AND c.ref_no IS NOT NULL AND c.ref_corres_id IS NOT NULL AND c.ref_corres_date IS NOT NULL";
            else if(searchreply_forward.equals("Forward")){
                //add_field = ", corr.ref_no AS incoming_ref_no";
                //add_table = ", correspondence AS corr";
                add_query = "AND c.ref_no IS NULL AND c.ref_corres_id IS NOT NULL AND c.ref_corres_date IS NULL";//AND corr.correspondence_id = c.ref_corres_id AND corr.active='Y'
            }

        try {
            String query = "SELECT count(c.correspondence_id) FROM correspondence c LEFT JOIN correspondence_priority cp "
                    + " ON cp.correspondence_priority_id = c.correspondence_priority_id"
                    + " LEFT JOIN correspondence_status cs on cs.correspondence_status_id = c.correspondence_status_id, org_office oc1, organisation_name on1, "
                    + " key_person k, key_person k1, key_person k2, type_of_document td, correspondence_type ct " + add_table
                    //+ " correspondence_status cs "//correspondence_priority cp,
                    + " where c.office_id=oc1.org_office_id and on1.organisation_id=oc1.organisation_id "
                    + " and k.key_person_id=c.dealing_person_id and k1.key_person_id=c.other_person_id "
                    + " and k2.key_person_id=c.key_person_id and c.type_of_document_id= td.type_of_document_id "
                    + " AND ct.correspondence_type_id = c.correspondence_type_id AND c.active = 'Y'"
                    //+ " AND cp.correspondence_priority_id = c.correspondence_priority_id "
                    //+ " AND cs.correspondence_status_id = c.correspondence_status_id "
                    + " and if('" + correspondence_no + "'='', c.correspondence_no like '%%', c.correspondence_no='" + correspondence_no + "') "
                    + " and if('" + search_subject + "'='', c.subject like '%%', c.subject='" + search_subject + "') "
                    + " and if('" + type_of_corr + "'='', ct.correspondence_type like '%%', ct.correspondence_type='" + type_of_corr + "') "
                    // + " and if('" + corr_date + "'='',  corr_date , corr_date " + corr_date + ") "
                    + " and if('" + type_of_doc + "'='', type_of_document like '%%', type_of_document='" + type_of_doc + "') "
                    + " and if('" + dealing_person + "'='', k.key_person_name like '%%', k.key_person_name='" + dealing_person + "') "
                    + " and if('" + search_Other_person + "'='', k1.key_person_name like '%%', k1.key_person_name='" + search_Other_person + "') "
                    + " and if('" + search_key_person + "'='', k2.key_person_name like '%%', k2.key_person_name='" + search_key_person + "') "
                    + " and if('" + org_from + "'='', on1.organisation_name like '%%', on1.organisation_name='" + org_from + "') "
                    + " and if('" + search_office + "'='', oc1.org_office_name like '%%', oc1.org_office_name='" + search_office + "') " + add_query;
                    //+ " and if('" + org_to + "'='', on2.organisation_name like '%%', on2.organisation_name='" + org_to + "')  ";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("ReceivedBillModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<Correspondence> showData(String correspondence_no, String search_subject, String type_of_corr, String org_from, String search_office, String corr_date, String type_of_doc, String dealing_person, int lowerLimit, int noOfRowsToDisplay, String searchreply_forward, String search_Other_person, String search_key_person) {
        List<Correspondence> list = new ArrayList<Correspondence>();
        search_subject = krutiToUnicode.convert_to_unicode(search_subject);
        org_from = krutiToUnicode.convert_to_unicode(org_from);
        String add_query = "";
        String add_field = "";
        String add_table = "";
        String addLimit = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if(lowerLimit == -1 && noOfRowsToDisplay == -1)
            addLimit = "";
        if(search_office.equals("ALL"))
            search_office = "";
        if(type_of_corr.equals("Incoming"))
            if(searchreply_forward.equals("Reply"))
                add_query = "AND c.own_ref_no IS NOT NULL AND c.ref_corres_id IS NOT NULL AND c.ref_corres_date IS NOT NULL";
        if(type_of_corr.equals("Outgoing"))
            if(searchreply_forward.equals("Reply"))
                add_query = "AND c.ref_no IS NOT NULL AND c.ref_corres_id IS NOT NULL AND c.ref_corres_date IS NOT NULL";
            else if(searchreply_forward.equals("Forward")){
                //add_field = ", corr.ref_no AS incoming_ref_no";
                //add_table = ", correspondence AS corr";
                add_query = "AND c.ref_no IS NULL AND c.ref_corres_id IS NOT NULL AND c.ref_corres_date IS NULL";//AND corr.correspondence_id = c.ref_corres_id AND corr.active='Y'  
            }
        try {
            String query = "SELECT c.correspondence_id, c.revision, c.active, c.correspondence_no, c.subject, ct.correspondence_type, c.office_id, "
                    + " c.description, c.image_name, date_format(c.corr_date,'%d-%m-%Y') as corr_date, "
                    + " oc1.org_office_name as source_office,  on1.organisation_id as source_org_id, "
                    + " on1.organisation_name as source_organisation, "
                    + " k.key_person_id as dealing_perosn_id, k.key_person_name as dealing_person, "
                    + " k1.key_person_name AS other_person, c.ref_no, k2.key_person_name As key_person, "
                    + " c.own_ref_no, c.ref_corres_id, c.ref_corres_date, "
                    + " td.type_of_document_id, type_of_document, cp.priority, cs.status, "
                    + " date_format(c.register_date,'%d-%m-%Y') as register_date, c.reason, c.is_cc " + add_field
                    + " FROM correspondence c LEFT JOIN correspondence_priority cp "
                    + " on cp.correspondence_priority_id = c.correspondence_priority_id"
                    + " LEFT JOIN correspondence_status cs on cs.correspondence_status_id = c.correspondence_status_id,"
                    + " org_office oc1 , organisation_name on1  , "
                    + " key_person k, key_person k1, key_person k2, type_of_document td, correspondence_type ct " + add_table
                    //+ " correspondence_status cs "//correspondence_priority cp,
                    + " where c.office_id=oc1.org_office_id and on1.organisation_id=oc1.organisation_id "
                    + " and k.key_person_id=c.dealing_person_id and k1.key_person_id=c.other_person_id "
                    + " and k2.key_person_id=c.key_person_id and c.type_of_document_id= td.type_of_document_id "
                    + " AND ct.correspondence_type_id = c.correspondence_type_id AND c.active = 'Y' "
                    //+ " AND cp.correspondence_priority_id = c.correspondence_priority_id "
                    //+ " AND cs.correspondence_status_id = c.correspondence_status_id "
                    + " and if('" + correspondence_no + "'='', c.correspondence_no like '%%', c.correspondence_no='" + correspondence_no + "') "
                    + " and if('" + search_subject + "'='', c.subject like '%%', c.subject='" + search_subject + "') "
                    + " and if('" + type_of_corr + "'='', ct.correspondence_type like '%%', ct.correspondence_type='" + type_of_corr + "') "
                    // + " and if('" + corr_date + "'='',  corr_date , corr_date " + corr_date + ") "
                    + " and if('" + type_of_doc + "'='', type_of_document like '%%', type_of_document='" + type_of_doc + "') "
                    + " and if('" + dealing_person + "'='', k.key_person_name like '%%', k.key_person_name='" + dealing_person + "') "
                    + " and if('" + search_Other_person + "'='', k1.key_person_name like '%%', k1.key_person_name='" + search_Other_person + "') "
                    + " and if('" + search_key_person + "'='', k2.key_person_name like '%%', k2.key_person_name='" + search_key_person + "') "
                    + " and if('" + org_from + "'='', on1.organisation_name like '%%', on1.organisation_name='" + org_from + "') "
                    + " and if('" + search_office + "'='', oc1.org_office_name like '%%', oc1.org_office_name='" + search_office + "') " + add_query
                    //+ " and if('" + org_to + "'='', on2.organisation_name like '%%', on2.organisation_name='" + org_to + "')  "
                    + addLimit;
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Correspondence corr = new Correspondence();
                corr.setCorrecpondence_id(rset.getInt("correspondence_id"));
                corr.setCorrespondece_no(rset.getString("correspondence_no"));
                corr.setRevision(rset.getInt("revision"));
                corr.setActive(rset.getString("active"));
                corr.setSubject(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("subject")));

                String correspondence_type = rset.getString("correspondence_type");
                String ref_no = rset.getString("ref_no");
                String own_ref_no = rset.getString("own_ref_no");
                String ref_corres_id = rset.getString("ref_corres_id");
                int ref_corres_id1 = rset.getInt("ref_corres_id");
                String ref_corres_date = rset.getString("ref_corres_date");

                corr.setType_of_cop(correspondence_type);
                corr.setDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("description")));
                corr.setCorr_date(rset.getString("corr_date"));
                corr.setSource_office_id(rset.getInt("office_id"));
                corr.setSource_office(rset.getString("source_office"));
                corr.setSource_organisation(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("source_organisation")));
                corr.setImage_name(rset.getString("image_name"));
//                corr.setDestination_office_id(rset.getInt("des_office_id"));
//                corr.setDestination_office(rset.getString("des_office"));
//                corr.setDestination_organisation(rset.getString("des_organisation"));
                corr.setType_of_document(rset.getString("type_of_document"));
                corr.setDealing_person(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("dealing_person")));
                corr.setOther_person(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("other_person")));
                corr.setRefrence_no(ref_no);
                corr.setKey_person(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person")));
                corr.setOwner_ref_no(own_ref_no);              
                corr.setRef_corres_id(ref_corres_id);
                corr.setRef_corres_date(ref_corres_date);
                corr.setRegister_date(rset.getString("register_date"));
                corr.setIs_cc(rset.getString("is_cc"));
                corr.setStatus(rset.getString("status"));
                corr.setPriority(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("priority")));
                corr.setReason(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("reason")));
                if(!add_query.isEmpty())
                    corr.setReply_forward(searchreply_forward);
                if(correspondence_type.equals("Outgoing") && (ref_no == null || ref_no.isEmpty()) && (ref_corres_date == null || ref_corres_date.isEmpty()) && (ref_corres_id != null && !ref_corres_id.isEmpty())){
                    corr.setIs_forward("Y");
                    corr.setRefrence_no(getRefrenceNoFromCorrespondenceId(ref_corres_id1));
                } else
                if(correspondence_type.equals("Outgoing") && (ref_no != null && !ref_no.isEmpty()) && (ref_corres_date != null && !ref_corres_date.isEmpty()) && (ref_corres_id != null && !ref_corres_id.isEmpty())){
                    corr.setIs_reply("Y");
                } else
                if(correspondence_type.equals("Incoming") && (own_ref_no != null && !own_ref_no.isEmpty()) && (ref_corres_date != null && !ref_corres_date.isEmpty()) && (ref_corres_id != null && !ref_corres_id.isEmpty())){
                    corr.setIs_reply("Y");
                }

                list.add(corr);
            }
        } catch (Exception e) {
            System.out.println("Error:corrModel-showData--- " + e);
        }
        return list;
    }

    public String getRefrenceNoFromCorrespondenceId(int correspondence_id){
        String ref_no = "";
        String query = "SELECT c.ref_no FROM correspondence AS c, correspondence_type AS ct "
                + " WHERE c.active='Y' AND c.correspondence_type_id=ct.correspondence_type_id "
                + " AND ct.correspondence_type = 'Incoming' AND c.correspondence_id = " + correspondence_id;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next()){
                ref_no = rs.getString("ref_no");
            }
        }catch(Exception ex){
        }
        return ref_no;
    }

    public int[] getUserdetail(String user_name) {
        int[] user_deatil = new int[3];
        try {
            String query = "select user_name , user_id , k.key_person_id, o.org_office_id  , location_name from user u , "
                    + "key_person k , org_office o "
                    + "where  u.key_person_id= k.key_person_id  and o.org_office_id= k.org_office_id and  user_name='jpss' ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            if (rst.next()) {
                user_deatil[0] = rst.getInt("user_id");
                user_deatil[1] = rst.getInt("key_person_id");
                user_deatil[2] = rst.getInt("org_office_id");
            }
        } catch (Exception e) {
            System.out.println("Error in metehod " + e);
        }
        return user_deatil;

    }

    public String getOfficeList(String organisation_name) {
        organisation_name = krutiToUnicode.convert_to_unicode(organisation_name);
        String office_name = "";
        try {

            String query = " select org_office_name from org_office as oc , organisation_name om "
                    + "where oc.organisation_id=om.organisation_id and organisation_name='" + organisation_name + "' ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                office_name = office_name + rst.getString("org_office_name") + "&#";
            }


        } catch (Exception e) {
        }
        return office_name;
    }

    public int insertRecord(List<Correspondence> correspondenceList, String is_task_reply, int corres_task_id) {//, int home_person_id
        /*String query = "INSERT INTO correspondence( correspondence_no, subject, type_of_correspondence, "
                + " dealing_person_id, source_office_id, description,  "
                + " image_name, destination_office_id, type_of_document_id , corr_date)"
                + " VALUES(?, ?, ?, ?, ?, ? ,?,?,?,? ) ";*/
        String query = "INSERT INTO correspondence( correspondence_no, subject, correspondence_type_id, "
                + " dealing_person_id, office_id, description,  "
                + " type_of_document_id , corr_date, ref_no, other_person_id, register_date, is_cc, "
                + " correspondence_priority_id, correspondence_status_id, key_person_id, own_ref_no, "
                + " ref_corres_id, ref_corres_date, reason )"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";

        String reviseQuery = "INSERT INTO correspondence( correspondence_no, subject, correspondence_type_id, "
                + " dealing_person_id, office_id, description,  "
                + " type_of_document_id , corr_date, ref_no, other_person_id, register_date, is_cc, "
                + " correspondence_priority_id, correspondence_status_id, key_person_id, own_ref_no, "
                + " ref_corres_id, ref_corres_date, reason, correspondence_id, revision )"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
        String taskMapQuery = "INSERT INTO correspondence_task_map (correspondence_id, correspondence_task_id) VALUES(?, ?)";
        int rowsAffected = 0;
        int count = 0;
        boolean autoCommit = false;
        boolean isSaved = false;
        try {
            connection.setAutoCommit(false);
            autoCommit = connection.getAutoCommit();
            PreparedStatement pstmt = null;
            int correspondence_id = 0;
            try {
                for (Correspondence cop : correspondenceList) {
                    correspondence_id = cop.getCorrecpondence_id();
                    if (correspondence_id == 0)
                        pstmt = connection.prepareStatement(query);
                    else{
                        pstmt = connection.prepareStatement(reviseQuery);
                        pstmt.setInt(20, correspondence_id);
                        pstmt.setInt(21, cop.getRevision());
                    }
                        String correspondence_type = cop.getType_of_cop();
                        String reply = cop.getReply();
                        String reply_forward = cop.getReply_forward();
                        String correspondence_no = cop.getCorrespondece_no();
                        if(correspondence_id > 0 || checkCorrespondenceNoValid(correspondence_no, correspondence_type))
                            pstmt.setString(1, correspondence_no);
                        else
                            pstmt.setNull(1, java.sql.Types.NULL);
                        pstmt.setString(2, krutiToUnicode.convert_to_unicode(cop.getSubject()));
                        pstmt.setInt(3, getCorrespondenceTypeId(correspondence_type));
                        pstmt.setInt(4, getkey_person_id(krutiToUnicode.convert_to_unicode(cop.getDealing_person())));
                        //if (cop.getType_of_cop().equalsIgnoreCase("InComing")) {
                        pstmt.setDouble(5, getoffice_id(krutiToUnicode.convert_to_unicode(cop.getSource_organisation()), krutiToUnicode.convert_to_unicode(cop.getSource_office())));
                        //} else {
//                            pstmt.setInt(5, home_person_id);
                        //}
                        pstmt.setString(6, krutiToUnicode.convert_to_unicode(cop.getDescription()));
                        //  pstmt.setInt(7, cop.getCreated_by());
                        //pstmt.setString(7, cop.getImage_name());
//                        if (cop.getType_of_cop().equalsIgnoreCase("Outgoing")) {
//                            pstmt.setInt(8, getoffice_id(cop.getDestination_organisation(), cop.getDestination_office()));
//                        } else {
//                            pstmt.setInt(8, home_person_id);
//                        }
                        pstmt.setInt(7, getDocumentId(krutiToUnicode.convert_to_unicode(cop.getType_of_document())));
                        pstmt.setDate(8, setDate(cop.getCorr_date()));
                        if(correspondence_type.equals("Incoming") || (correspondence_type.equals("Outgoing") && reply_forward.equals("Reply") && reply.equals("Y")))
                            pstmt.setString(9, cop.getRefrence_no());
                        else
                            pstmt.setNull(9, java.sql.Types.NULL);
                        String otherPerson = krutiToUnicode.convert_to_unicode(cop.getOther_person());
                        pstmt.setInt(10, getOtherPersonId(otherPerson));
                        pstmt.setDate(11, setDate(cop.getRegister_date()));
                        pstmt.setString(12, cop.getIs_cc());
                        String priority = krutiToUnicode.convert_to_unicode(cop.getPriority());
                        if(priority != null && !priority.trim().isEmpty())
                            pstmt.setInt(13, getCorrespondencePriorityId(priority.trim()));
                        else
                            pstmt.setNull(13, java.sql.Types.NULL);
                        String status = cop.getStatus();
                        if(status != null && !status.trim().isEmpty())
                            pstmt.setInt(14, getCorrespondenceStatusId(status.trim()));
                        else
                            pstmt.setNull(14, java.sql.Types.NULL);
                        String keyPerson = krutiToUnicode.convert_to_unicode(cop.getKey_person());
                        pstmt.setInt(15, getkey_person_id(keyPerson));
                        
                        if((correspondence_type.equals("Incoming") && reply.equals("Y")) || correspondence_type.equals("Outgoing"))
                            pstmt.setString(16, cop.getOwner_ref_no());
                        else
                            pstmt.setNull(16, java.sql.Types.NULL);
                        
                        if((correspondence_type.equals("Incoming") || (correspondence_type.equals("Outgoing") && (reply_forward != null && !reply_forward.isEmpty()))) && reply.equals("Y"))//for reply or forward
                            pstmt.setInt(17, getRefCorrespondenceId(keyPerson, cop.getOwner_ref_no(), otherPerson, cop.getRefrence_no(), correspondence_type, reply_forward, reply));
                        else
                            pstmt.setNull(17, java.sql.Types.NULL);
                        
                        if((correspondence_type.equals("Incoming") || (correspondence_type.equals("Outgoing") && reply_forward.equals("Reply"))) && reply.equals("Y")){//for Incoming with reply
                            if(cop.getRef_corres_date() != null && !cop.getRef_corres_date().isEmpty())
                                pstmt.setDate(18, setDate(cop.getRef_corres_date()));
                            else
                                pstmt.setDate(18, null);
                        }else
                            pstmt.setDate(18, null);
                        pstmt.setString(19, krutiToUnicode.convert_to_unicode(cop.getReason()));
                  
                        rowsAffected = pstmt.executeUpdate();
                    
                    count++;
                }                
                if (rowsAffected > 0 && count == correspondenceList.size()) {
                    connection.commit();
                    connection.setAutoCommit(true);
                    isSaved = true;

                }
                if(is_task_reply != null && !is_task_reply.isEmpty())
                    if(rowsAffected > 0){
                        pstmt = connection.prepareStatement(taskMapQuery);
                        pstmt.setInt(1, getCorrespondenceId());
                        pstmt.setInt(2, corres_task_id);
                        rowsAffected = pstmt.executeUpdate();
                    }
            } catch (SQLException s) {
                System.out.println("Sql Error in  sqlException " + s);
                for (Correspondence cop : correspondenceList) {
                    String image_name = cop.getImage_name();
                    if (image_name != null && !image_name.isEmpty()) {
                        File file = new File(image_name);
                        file.delete();
                    }
                }
                connection.setAutoCommit(true);
                connection.rollback();
            }
        } catch (Exception e) {
            System.out.println("CorrespondenceModel insertRecord() Error: " + e);

        }
        if (isSaved) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int getCorrespondenceId(){
        int corr_id = 0;
        String query = "SELECT MAX(correspondence_id) FROM correspondence WHERE active = 'Y'";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next())
                corr_id = rs.getInt(1);
        }catch(Exception ex){
            System.out.println("Error: getCorrespondenceId() in CorrespondenceModel : " + ex);
        }
        return corr_id;
    }

    public int reviseRecord(Correspondence cop) {//, int home_person_id        
        int rowsAffected = 0;
        PreparedStatement pstmt = null;
        int correspondence_id = 0;
        int revision = 0;
        int new_revision = 0;
        String outgoing_cc = "";
        String correspondence_type = "";
        String is_cc = "";
        String correspondence_no = "";
        boolean corr_no_valid = true;
        try {
            correspondence_id = cop.getCorrecpondence_id();
            revision = cop.getRevision();
            correspondence_no = cop.getCorrespondece_no();
            correspondence_type = cop.getType_of_cop();
            is_cc = cop.getIs_cc();
            if(correspondence_type.equals("Outgoing") && is_cc.equals("Y") ){
                String checkIsCC = getIsCC(correspondence_id, revision);
                if(checkIsCC.equals("N"))
                    outgoing_cc = "Y";
            }
            if(outgoing_cc.equals("Y"))
                if(checkCorrespondenceNoValid(correspondence_no, correspondence_type));
                else
                    corr_no_valid = false;

            //cop.setRevision(revision + 1);
            new_revision = getMaxRevision(correspondence_id);
            cop.setRevision(new_revision + 1);
            List<Correspondence> correspondenceList = new ArrayList<Correspondence>();
            correspondenceList.add(cop);
            if(corr_no_valid)
                rowsAffected = insertRecord(correspondenceList, "", 0);
            if(rowsAffected > 0 && !outgoing_cc.equals("Y"))
                rowsAffected = deleteRecord(cop.getCorrecpondence_id(), revision);
        } catch (Exception e) {
            System.out.println("CorrespondenceModel reviseRecord() Error: " + e);
//            String image_name = cop.getImage_name();
//            if (image_name != null && !image_name.isEmpty()) {
//                File file = new File(image_name);
//                file.delete();
//            }
        }
        if (rowsAffected > 0) {
            message = "Record Revise successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot Revise the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if(!corr_no_valid)
            message = "Correspondence No. must be unique";

        return rowsAffected;
    }

    public String getIsCC(int correspondence_id, int revision){
        String is_cc = "";
        String query = "SELECT is_cc FROM correspondence WHERE correspondence_id = " + correspondence_id + " AND revision = "+ revision;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next())
                is_cc = rs.getString("is_cc");
        }catch(Exception ex){}
        return is_cc;
    }

    public int getMaxRevision(int correspondence_id){
        int max_revision = 0;
        String query = "SELECT MAX(revision) AS revision FROM correspondence Where correspondence_id = " + correspondence_id;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next()){
                max_revision = rs.getInt("revision");
            }
        }catch(Exception ex){
            System.out.println("Error : in getMaxRevision() in CorrespondenceModel : " + ex);
        }
        return max_revision;
    }

    public int deleteRecord(int correspondence_id, int revision) {

        String query = " UPDATE correspondence set active = 'N' WHERE correspondence_id = " + correspondence_id + " AND revision = " + revision;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:Delete:CorrespondenceModel-- " + e);
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

    public java.sql.Date setDate(String date) {
        java.sql.Date finalDate = null;
        try {
            String strD = date;
            String[] str1 = strD.split("-");
            strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
            finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        } catch (Exception e) {
            System.out.println("CorrespondenceModel setDate() -- error " + e);
        }
        return finalDate;
    }

    public int getoffice_id(String org_name, String office_name) {
        int org_office_id = 0;
        try {
            String query = "select org_office_id from org_office oc, organisation_name om "
                    + "where oc.organisation_id= om.organisation_id "
                    + "and organisation_name= ? and org_office_name= ? ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_name);
            pstmt.setString(2, office_name);
            ResultSet rst = pstmt.executeQuery();
            if (rst.next()) {
                org_office_id = rst.getInt("org_office_id");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return org_office_id;
    }

    public int getDocumentId(String type_of_document) {
        int type_of_document_id = 0;
        try {
            String query = "select type_of_document_id  from  type_of_document where type_of_document =? ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, type_of_document);
            ResultSet rst = pstmt.executeQuery();
            if (rst.next()) {
                type_of_document_id = rst.getInt("type_of_document_id");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return type_of_document_id;
    }

    public int getkey_person_id(String key_person) {
        int key_person_id = 0;
        try {
            String query = "select key_person_id  from  key_person where key_person_name =? ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, key_person);
            ResultSet rst = pstmt.executeQuery();
            if (rst.next()) {
                key_person_id = rst.getInt("key_person_id");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return key_person_id;
    }

    public String createAppropriateDirectories(String destination_path, String corr_no, String corr_date) {
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};
        Calendar calendar = Calendar.getInstance();
        String[] date = corr_date.split("-");
        calendar.set(new Integer(date[2]), new Integer(date[1]), new Integer(date[0]));
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        String month = monthName[calendar.get(Calendar.MONTH)];
        String imageUploadedForFolder = destination_path + "/" + year + "/" + month + "/" + day_of_month + "/" + corr_no;
        System.out.println("imageUploadedForFolder--" + imageUploadedForFolder);
        makeDirectory(imageUploadedForFolder);
        return imageUploadedForFolder;
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        System.out.println("dirPathName---" + dirPathName);
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("Error - " + e);
            }
        } else {
            System.out.println("Path is Already Exist-" + dirPathName);
        }
        return result;
    }

    public String getReportPath() {
        String image_path = "";
        try {
            String query = "select  repository_path from organisation_name om "
                    + "where  is_home='Y' ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            if (rst.next()) {
                image_path = rst.getString("repository_path");

            }
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return image_path;
    }

    public List<String> getDoclist() {
        List<String> list = new ArrayList<String>();
        try {
            String query = "select type_of_document  from  type_of_document ";
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {
                list.add(rst.getString("type_of_document"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<String> getKeyPersonList(String q) {
        List<String> keyPersonList = new ArrayList<String>();
        try {
            String query = " select key_person_name from key_person k, org_office o, organisation_name om "
                    + "where k.org_office_id= o.org_office_id "
                    + "and o.organisation_id = om.organisation_id and is_home='Y' ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            q = q.trim();
            while (rst.next()) {
                String person_name = unicodeToKruti.Convert_to_Kritidev_010(rst.getString("key_person_name"));
                if(person_name.toUpperCase().startsWith(q.toUpperCase()))
                    keyPersonList.add(person_name);
            }
        } catch (Exception e) {
        }
        return keyPersonList;
    }

    public int getCorrespondenceTypeId(String correspondence_type){
        int correspondence_type_id = 0;
        String query = "SELECT correspondence_type_id FROM correspondence_type WHERE correspondence_type = '" + correspondence_type + "'";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next()){
                correspondence_type_id = rs.getInt("correspondence_type_id");
            }
        }catch(Exception ex){
            System.out.println("Error: in getCorrespondenceTypeId() in CorrespondenceModdel : " + ex);
        }
        return correspondence_type_id;
    }

    public int getCorrespondenceStatusId(String correspondence_status){
        int correspondence_status_id = 0;
        String query = "SELECT correspondence_status_id FROM correspondence_status WHERE status = '" + correspondence_status + "'";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next()){
                correspondence_status_id = rs.getInt("correspondence_status_id");
            }
        }catch(Exception ex){
            System.out.println("Error: in getCorrespondenceStatusId() in CorrespondenceModdel : " + ex);
        }
        return correspondence_status_id;
    }

    public int getCorrespondencePriorityId(String correspondence_priority){
        int correspondence_priority_id = 0;
        String query = "SELECT correspondence_priority_id FROM correspondence_priority WHERE priority = '" + correspondence_priority + "'";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next()){
                correspondence_priority_id = rs.getInt("correspondence_priority_id");
            }
        }catch(Exception ex){
            System.out.println("Error: in getCorrespondencePriorityId() in CorrespondenceModdel : " + ex);
        }
        return correspondence_priority_id;
    }

    public int getRefCorrespondenceId(String key_person, String own_ref_no, String other_person, String ref_no, String correspondence_type, String reply_forward, String reply){
        int ref_correspondence_id = 0;
        String query = "";
        if(correspondence_type.equals("Incoming") && reply.equals("Y"))
            query = "SELECT correspondence_id FROM correspondence c, key_person k, correspondence_type ct "
                + " WHERE c.correspondence_type_id = ct.correspondence_type_id AND c.key_person_id = k.key_person_id AND c.active = 'Y' "
                + " AND ct.correspondence_type = 'Outgoing' AND k.key_person_name = '" + key_person + "' AND c.own_ref_no = '" + own_ref_no + "'";
        if(correspondence_type.equals("Outgoing")){
            if(reply_forward.equals("Reply") && reply.equals("Y"))
                query = "SELECT correspondence_id FROM correspondence c, key_person k, correspondence_type ct "
                        + " WHERE c.correspondence_type_id = ct.correspondence_type_id AND c.other_person_id = k.key_person_id  AND c.active = 'Y' "
                        + " AND ct.correspondence_type = 'Incoming' AND k.key_person_name = '" + other_person + "' AND c.ref_no = '" + ref_no + "'";
            else if(reply_forward.equals("Forward") && reply.equals("Y"))
                query = "SELECT correspondence_id FROM correspondence c, key_person k, correspondence_type ct "
                        + " WHERE c.correspondence_type_id = ct.correspondence_type_id AND c.key_person_id = k.key_person_id  AND c.active = 'Y' "
                        + " AND ct.correspondence_type = 'Incoming' AND k.key_person_name = '" + key_person + "' AND c.ref_no = '" + ref_no + "'";
        }
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next()){
                ref_correspondence_id = rs.getInt("correspondence_id");
            }
        }catch(Exception ex){
            System.out.println("Error: in getRefCorrespondenceId() in CorrespondenceModdel : " + ex);
        }
        return ref_correspondence_id;
    }

    public boolean checkCorrespondenceNoValid(String correspondence_no, String correspondence_type){
        boolean is_valid = true;
        String query = "SELECT correspondence_id, correspondence_no FROM correspondence c, correspondence_type ct "
                + " WHERE c.correspondence_type_id = ct.correspondence_type_id "
                + " AND ct.correspondence_type = '" + correspondence_type + "' "
                + " AND c.correspondence_no = '"+ correspondence_no +"' ";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next())
                is_valid = false;
        }catch(Exception ex){
            System.out.println("Error : in checkCorrespondenceNoValid() in CorrespondenceModel : " + ex);
        }
        return is_valid;
    }

    public int getOtherPersonId(String other_person){
        int other_person_id = 0;
        String query = "SELECT key_person_id FROM key_person WHERE key_person_name = '" + other_person + "'";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next()){
                other_person_id = rs.getInt("key_person_id");
            }
        }catch(Exception ex){
            System.out.println("Error: in getOtherPersonId() in CorrespondenceModdel : " + ex);
        }
        return other_person_id;
    }

    public List<String> getCorrespondenceNo(String q){
        List<String> corresNo_list = new ArrayList<String>();
        String correspondence_no = "";
        int count = 0;
        String query = "SELECT c.correspondence_no FROM correspondence c, correspondence_type ct "
                + " WHERE c.active = 'Y' AND c.correspondence_type_id = ct.correspondence_type_id AND ct.correspondence_type = 'Incoming' ";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            q = q.trim();
            while(rs.next()){
                correspondence_no = rs.getString("correspondence_no");
                if(correspondence_no.toUpperCase().startsWith(q.toUpperCase())){
                    corresNo_list.add(correspondence_no);
                    count++;
                }
            }
            if(count == 0)
                corresNo_list.add("No Correspondence No. Found");
        }catch(Exception ex){
            System.out.println("ERROR: in getCorrespondenceNo() in TaskSchedulerModel" + ex);
        }
        return corresNo_list;
    }

    public List<String> getSubjectList(String q){
        List<String> corresSubject_list = new ArrayList<String>();
        String subject = "";
        int count = 0;
        String query = "SELECT subject FROM correspondence"
                + " WHERE active = 'Y' GROUP BY subject ORDER BY subject ";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            q = q.trim();
            while(rs.next()){
                subject = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("subject"));
                if(subject.toUpperCase().startsWith(q.toUpperCase())){
                    corresSubject_list.add(subject);
                    count++;
                }
            }
            if(count == 0)
                corresSubject_list.add("No Subject Found");
        }catch(Exception ex){
            System.out.println("ERROR: in getSubjectList() in TaskSchedulerModel" + ex);
        }
        return corresSubject_list;
    }

    public List<String> getOtherPersonList(String q, String organization, String office){
        List<String> corresSubject_list = new ArrayList<String>();
        organization = krutiToUnicode.convert_to_unicode(organization);
        String key_person_name = "";
        int count = 0;
        String query = "SELECT kp.key_person_name FROM key_person AS kp, org_office AS of, organisation_name AS org "
                + " WHERE kp.org_office_id = of.org_office_id AND of.organisation_id = org.organisation_id "
                + " AND IF('"+ organization +"'='', org.organisation_name LIKE '%%', org.organisation_name = '"+ organization +"') "
                + " AND IF('"+ office +"'='', of.org_office_name LIKE '%%', of.org_office_name = '"+ office +"')";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            q = q.trim();
            while(rs.next()){
                key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("key_person_name"));
                if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
                    corresSubject_list.add(key_person_name);
                    count++;
                }
            }
            if(count == 0)
                corresSubject_list.add("Not Found");
        }catch(Exception ex){
            System.out.println("ERROR: in getOtherPersonList() in TaskSchedulerModel" + ex);
        }
        return corresSubject_list;
    }

    public byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (Exception e) {
            System.out.println("CorrespondenceModel generateRecordList() JRException: " + e);
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
            System.out.println("CorrespondenceModel generateExcelList() JRException: " + e);
        }
        return reportInbytes;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("keypersonModel closeConnection() Error: " + e);
        }
    }

    public String getDb_userName() {
        return db_userName;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public String getDb_userPasswrod() {
        return db_userPasswrod;
    }

    public void setDb_userPasswrod(String db_userPasswrod) {
        this.db_userPasswrod = db_userPasswrod;
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

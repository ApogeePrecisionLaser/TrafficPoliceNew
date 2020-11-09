/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.correspondence.model;

import com.tp.correspondence.tableClasses.Correspondence;
import com.tp.correspondence.tableClasses.TaskSchedulerBean;
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
public class OutgoingTaskModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    private int showData_size = 0;

    public int getNoOfRows(String correspondence_no, String search_task, String org_from, String org_to, String corr_date, String type_of_doc, String dealing_person, String searchreply_forward, String search_Other_person, String search_key_person) {
        int noOfRows = 0;       
        try {
            String query = "SELECT COUNT(ctm.correspondence_task_map_id) "
                    + " FROM correspondence AS c, correspondence_task AS ct, correspondence_task_map AS ctm, correspondence_status AS cs "
                    + " WHERE cs.correspondence_status_id = ct.status_id AND ctm.correspondence_id = c.correspondence_id AND c.active = 'Y' "
                    + " AND ctm.correspondence_task_id = ct.correspondence_task_id AND ct.active = 'Y' "
                    + " and if('" + correspondence_no + "'='', c.correspondence_no like '%%', c.correspondence_no='" + correspondence_no + "') "
                    + " and if('" + search_task + "'='', ct.task like '%%', ct.task='" + search_task + "') ";
            PreparedStatement pst = connection.prepareStatement(query);
            System.out.println(query);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));            
        } catch (Exception e) {
            System.out.println("ReceivedBillModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<TaskSchedulerBean> showData(String correspondence_no, String search_task, String org_from, String org_to, String corr_date, String type_of_doc, String dealing_person, int lowerLimit, int noOfRowsToDisplay, String searchreply_forward, String search_Other_person, String search_key_person) {
        List<TaskSchedulerBean> list = new ArrayList<TaskSchedulerBean>();
        String addLimit = "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if(lowerLimit == -1 && noOfRowsToDisplay == -1)
            addLimit = "";
        String query = "SELECT c.correspondence_id, c.correspondence_no, c.subject, date_format(c.corr_date,'%d-%m-%Y') AS corr_date, ct.correspondence_task_id, ct.task, "
                    + " date_format(ct.start_date,'%d-%m-%Y') as start_date, cs.status, ct.no_of_days, ct.remark "
                    + " FROM correspondence AS c, correspondence_task AS ct, correspondence_task_map AS ctm, correspondence_status AS cs "
                    + " WHERE cs.correspondence_status_id = ct.status_id AND ctm.correspondence_id = c.correspondence_id AND c.active = 'Y' "
                    + " AND ctm.correspondence_task_id = ct.correspondence_task_id AND ct.active = 'Y' "
                    + " and if('" + correspondence_no + "'='', c.correspondence_no like '%%', c.correspondence_no='" + correspondence_no + "') "
                    + " and if('" + search_task + "'='', ct.task like '%%', ct.task='" + search_task + "') " + addLimit;
                  
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TaskSchedulerBean taskBean = new TaskSchedulerBean();
                taskBean.setCorrespondence_id(rset.getInt("correspondence_id"));
                taskBean.setCorrespondence_no(rset.getString("correspondence_no"));
                taskBean.setSubject(rset.getString("subject"));
                taskBean.setCorr_date(rset.getString("corr_date"));
                taskBean.setCorrespondence_task_id(rset.getInt("correspondence_task_id"));
                taskBean.setTask(rset.getString("task"));
                taskBean.setStart_date(rset.getString("start_date"));
                taskBean.setNo_of_days(rset.getInt("no_of_days"));
                taskBean.setRemark(rset.getString("remark"));
                taskBean.setStatus(rset.getString("status"));
                list.add(taskBean);
            }
        } catch (Exception e) {
            System.out.println("Error:corrModel-showData--- " + e);
        }
        return list;
    }

    public int getNoOfRowsFromTask(String correspondence_no, String search_task, String type_of_corr, String org_from, String org_to, String corr_date, String type_of_doc, String dealing_person, String searchreply_forward, String search_Other_person, String search_key_person) {
        int noOfRows = 0;        
         String query = "SELECT COUNT(ctk.correspondence_task_id) "
                + " FROM correspondence_task ctk LEFT JOIN correspondence c ON c.correspondence_id = ctk.correspondence_id AND c.active='Y', correspondence_status cs "
                + " WHERE cs.correspondence_status_id = ctk.status_id AND ctk.active = 'Y' "
                + " AND IF('" + correspondence_no + "'='', c.correspondence_no like '%%', c.correspondence_no='" + correspondence_no + "') "
                + " AND IF('" + search_task + "'='', ctk.task like '%%', ctk.task='" + search_task + "') "
                + " ORDER BY c.correspondence_id, ctk.correspondence_task_id ";
        try{
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

    public List<Correspondence> showTaskData(String correspondence_no, String search_task, String type_of_corr, String org_from, String org_to, String corr_date, String type_of_doc, String dealing_person, int lowerLimit, int noOfRowsToDisplay, String searchreply_forward, String search_Other_person, String search_key_person) {
        List<Correspondence> list = new ArrayList<Correspondence>();
        List<TaskSchedulerBean> taskList = new ArrayList<TaskSchedulerBean>();        
        int temp_correspondence_id = 0;
        int temp_correspondence_revision = 0;
        String query = "SELECT c.correspondence_id, c.revision, c.correspondence_no, c.subject, ctk.correspondence_task_id, ctk.revision AS task_revision, ctk.task, date_format(ctk.start_date,'%d-%m-%Y') as start_date, ctk.no_of_days, ctk.remark, cs.status "
                + " FROM correspondence_task ctk LEFT JOIN correspondence c ON c.correspondence_id = ctk.correspondence_id AND c.active='Y', correspondence_status cs "
                + " WHERE cs.correspondence_status_id = ctk.status_id AND ctk.active = 'Y' "
                + " AND IF('" + correspondence_no + "'='', c.correspondence_no like '%%', c.correspondence_no='" + correspondence_no + "') "
                + " AND IF('" + search_task + "'='', ctk.task like '%%', ctk.task='" + search_task + "') "
                + " ORDER BY c.correspondence_id, ctk.correspondence_task_id "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            Correspondence corr = new Correspondence();
            while (rset.next()) {
                TaskSchedulerBean taskBean = new TaskSchedulerBean();
                int correspondence_id = rset.getInt("correspondence_id");
                int revision = rset.getInt("revision");                
                taskBean.setCorrespondence_task_id(rset.getInt("correspondence_task_id"));
                taskBean.setRevision(rset.getInt("task_revision"));
                taskBean.setTask(rset.getString("task"));
                taskBean.setStart_date(rset.getString("start_date"));
                taskBean.setNo_of_days(rset.getInt("no_of_days"));
                taskBean.setRemark(rset.getString("remark"));
                taskBean.setStatus(rset.getString("status"));
                if(temp_correspondence_id == 0){
                    temp_correspondence_id = correspondence_id;
                    temp_correspondence_revision = revision;
                    corr.setCorrecpondence_id(correspondence_id);
                    corr.setCorrespondece_no(rset.getString("correspondence_no"));
                    corr.setRevision(revision);
                    corr.setSubject(rset.getString("subject"));
                }
                if(correspondence_id == temp_correspondence_id && revision == temp_correspondence_revision) {
                    taskList.add(taskBean);
                } else {
                    corr.setTaskList(taskList);
                    list.add(corr);
                    taskList = new ArrayList<TaskSchedulerBean>();
                    corr = new Correspondence();
                    taskList.add(taskBean);
                    temp_correspondence_id = correspondence_id;
                    temp_correspondence_revision = revision;
                    corr.setCorrecpondence_id(correspondence_id);
                    corr.setCorrespondece_no(rset.getString("correspondence_no"));
                    corr.setRevision(revision);
                    corr.setSubject(rset.getString("subject"));
                }
                showData_size++;
            }
            corr.setTaskList(taskList);
            list.add(corr);
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

    public int insertRecord(TaskSchedulerBean taskBean) {//, int home_person_id
        /*String query = "INSERT INTO correspondence( correspondence_no, subject, type_of_correspondence, "
                + " dealing_person_id, source_office_id, description,  "
                + " image_name, destination_office_id, type_of_document_id , corr_date)"
                + " VALUES(?, ?, ?, ?, ?, ? ,?,?,?,? ) ";*/
        String query = "INSERT INTO correspondence_task( task, start_date, correspondence_id, "
                + " remark, no_of_days, status_id) "
                + " VALUES(?, ?, ?, ?, ?, ?) ";

        String reviseQuery = "INSERT INTO correspondence_task( task, start_date, correspondence_id, "
                + " remark, no_of_days, status_id, correspondence_task_id, revision) "
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";


        /*String reviseQuery = "INSERT INTO correspondence( correspondence_no, subject, correspondence_type_id, "
                + " dealing_person_id, office_id, description,  "
                + " type_of_document_id , corr_date, ref_no, other_person_id, register_date, is_cc, "
                + " correspondence_priority_id, correspondence_status_id, key_person_id, own_ref_no, "
                + " ref_corres_id, ref_corres_date, reason, correspondence_id, revision )"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";*/
        int rowsAffected = 0;
        int count = 0;
        boolean autoCommit = false;
        boolean isSaved = false;
        try {
            connection.setAutoCommit(false);
            autoCommit = connection.getAutoCommit();
            PreparedStatement pstmt = null;
            try {
                //for (Correspondence cop : correspondenceList) {
                    int correspondence_task_id = taskBean.getCorrespondence_task_id();
                    if (correspondence_task_id == 0)
                        pstmt = connection.prepareStatement(query);
                    else{
                        pstmt = connection.prepareStatement(reviseQuery);
                        pstmt.setInt(7, correspondence_task_id);
                        pstmt.setInt(8, taskBean.getRevision());
                    }
                    pstmt.setString(1, taskBean.getTask());
                    String start_date = taskBean.getStart_date();
                    if(start_date != null && !start_date.isEmpty())
                        pstmt.setDate(2, setDate(taskBean.getStart_date()));
                    else
                        pstmt.setNull(2, java.sql.Types.NULL);
                    pstmt.setInt(3, taskBean.getCorrespondence_id());
                    pstmt.setString(4, taskBean.getRemark());
                    int no_of_days = taskBean.getNo_of_days();
                    if(no_of_days != 0)
                        pstmt.setInt(5, no_of_days);
                    else
                        pstmt.setNull(5, java.sql.Types.NULL);
                    String status = taskBean.getStatus();
                    if(status != null && !status.isEmpty())
                        pstmt.setInt(6, getCorrespondenceStatusId(status));
                    else
                        pstmt.setNull(6, java.sql.Types.NULL);
                    rowsAffected = pstmt.executeUpdate();                    
                    //count++;
                //}
                if (rowsAffected > 0) {
                    connection.commit();
                    connection.setAutoCommit(true);
                    isSaved = true;
                }
            } catch (SQLException s) {
                System.out.println("Sql Error in  sqlException " + s);
                connection.rollback();
                connection.setAutoCommit(true);                
            }
        } catch (Exception e) {
            System.out.println("TaskSchedulerModel insertRecord() Error: " + e);
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

    public int reviseRecord(TaskSchedulerBean taskBean) {//, int home_person_id
        int rowsAffected = 0;
        PreparedStatement pstmt = null;
        int correspondence_task_id = 0;
        int revision = 0;
        int new_revision = 0;                
        try {
            correspondence_task_id = taskBean.getCorrespondence_task_id();
            revision = taskBean.getRevision();
            new_revision = getMaxRevision(correspondence_task_id);
            taskBean.setRevision(new_revision + 1);            
                rowsAffected = insertRecord(taskBean);
            if(rowsAffected > 0)
                rowsAffected = deleteRecord(correspondence_task_id, revision);
        } catch (Exception e) {
            System.out.println("TaskSchedulerModel reviseRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Revise successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot Revise the record, some error.";
            msgBgColor = COLOR_ERROR;
        }       

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

    public int getMaxRevision(int correspondence_task_id){
        int max_revision = 0;
        String query = "SELECT MAX(revision) AS revision FROM correspondence_task Where correspondence_task_id = " + correspondence_task_id;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next()){
                max_revision = rs.getInt("revision");
            }
        }catch(Exception ex){
            System.out.println("Error : in getMaxRevision() in TaskSchedulerModel : " + ex);
        }
        return max_revision;
    }

    public int deleteRecord(int correspondence_task_id, int revision) {

        String query = " UPDATE correspondence_task set active = 'N' WHERE correspondence_task_id = " + correspondence_task_id + " AND revision = " + revision;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:Delete:TaskSchedulerModel-- " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Canceled successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot Canceled the record, some error.";
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
            System.out.println("TaskSchedulerModel setDate() -- error " + e);
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
                String person_name = rst.getString("key_person_name");
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
            System.out.println("Error : in checkCorrespondenceNoValid() in TaskSchedulerModel : " + ex);
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
        String query = "SELECT c.correspondence_no FROM correspondence c, correspondence_task_map ctm "
                + " WHERE c.active = 'Y' AND ctm.correspondence_id = c.correspondence_id ";
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

    public List<String> getCorrespondenceTask(String q){
        List<String> corresTask_list = new ArrayList<String>();
        String task = "";
        int count = 0;
        String query = "SELECT task FROM correspondence_task"
                + " WHERE active = 'Y' GROUP BY task ORDER BY task ";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            q = q.trim();
            while(rs.next()){
                task = rs.getString("task");
                if(task.toUpperCase().startsWith(q.toUpperCase())){
                    corresTask_list.add(task);
                    count++;
                }
            }
            if(count == 0)
                corresTask_list.add("No Correspondence No. Found");
        }catch(Exception ex){
            System.out.println("ERROR: in getCorrespondenceNo() in TaskSchedulerModel" + ex);
        }
        return corresTask_list;
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

    public int getShowData_size() {
        return showData_size;
    }   

}

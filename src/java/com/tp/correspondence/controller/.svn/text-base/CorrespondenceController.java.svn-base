/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.correspondence.controller;

import com.tp.correspondence.tableClasses.Correspondence;
import com.tp.correspondence.model.CorrespondenceModel;
import com.tp.correspondence.tableClasses.TaskSchedulerBean;
import com.tp.dbcon.DBConnection;
import com.tp.util.UniqueIDGenerator;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Shruti
 */
public class CorrespondenceController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        CorrespondenceModel correspon_model = new CorrespondenceModel();
        HttpSession session = request.getSession(false);
//        if (session == null || (String) session.getAttribute("user_name") == null) {
//            response.sendRedirect("beforeLoginPage");
//            return;
//        }
        try {
            correspon_model.setConnection(DBConnection.getConnection(ctx));
        } catch (Exception e) {
            System.out.println("error in DivisionController setConnection() calling try block" + e);
        }
        try {
            //String user_name = (String) session.getAttribute("user_name");
            //int[] userdetail = correspon_model.getUserdetail(user_name);
            //int user_id = userdetail[0];
            //int created_by_id = userdetail[1];
            //int home_offce_id = userdetail[2];
            String task = request.getParameter("task");
//            if(task == null)
//                task = "";
            String JQString = request.getParameter("action1");
            if(JQString != null){
                List<String> list = null;
                String q = request.getParameter("q");
                PrintWriter out = response.getWriter();
                if (JQString.equals("getOfficeList")) {
                    String organisation_name = request.getParameter("organisation_name");
                    String offices = correspon_model.getOfficeList(organisation_name);
                    if (offices == null)
                        out.print("");
                    else
                        out.println(offices);
                    return;
                } else if (JQString.equals("getkey_personlist"))
                    list = correspon_model.getKeyPersonList(q);
                else if (JQString.equals("getCorrespondenceNo"))
                    list = correspon_model.getCorrespondenceNo(q);
                else if(JQString.equals("getSubjectList"))
                    list = correspon_model.getSubjectList(q);
                else if(JQString.equals("getOtherPersonList")){
                    String organization = request.getParameter("org_name");
                    String office = request.getParameter("office_name");
                    if(office == null || office.equals("null"))
                        office = "";
                    list = correspon_model.getOtherPersonList(q, organization, office);
                }

                Iterator itr = list.iterator();
                while(itr.hasNext())
                    out.println((String)itr.next());
                out.close();
                return;
            }
            String search_corr_no = "";
            String search_subject = "";
            String search_type_corr = "";
            String search_org_from = "";
            String search_office = "";
            String search_corr_date = "";
            String type_of_doc = "";
            String dealing_person = "";
            String search_Other_person = "";
            String search_key_person = "";
            String searchreply_forward = "";
            String task_reply = "";
            search_corr_no = request.getParameter("search_corr_no");
            search_subject = request.getParameter("search_subject");
            search_type_corr = request.getParameter("search_type_corr");
            search_org_from = request.getParameter("search_org_from");
            search_office = request.getParameter("search_office");
            search_corr_date = request.getParameter("search_corr_date");
            type_of_doc = request.getParameter("type_of_doc");
            dealing_person = request.getParameter("dealing_person");
            search_Other_person = request.getParameter("search_Other_person");
            search_key_person = request.getParameter("search_key_person");
            searchreply_forward = request.getParameter("searchreply_forward");
            try {
                if (search_corr_no == null) {
                    search_corr_no = "";
                }
                if (search_subject == null) {
                    search_subject = "";
                }
                if (search_type_corr == null) {
                    search_type_corr = "";
                }
                if (search_org_from == null) {
                    search_org_from = "";
                }
                if (search_office == null) {
                    search_office = "";
                }
                if (search_corr_date == null) {
                    search_corr_date = "";
                }
                if (dealing_person == null) {
                    dealing_person = "";
                }
                if (search_Other_person == null) {
                    search_Other_person = "";
                }
                if (search_key_person == null) {
                    search_key_person = "";
                }
                if (type_of_doc == null) {
                    type_of_doc = "";
                }
                if (searchreply_forward == null) {
                    searchreply_forward = "";
                }
            } catch (Exception e) {
            }
            if (task != null && task.equals("PRINTRecordList")) {
                String jrxmlFilePath;
                List list = null;
                try {
                    response.setContentType("application/pdf");
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    jrxmlFilePath = ctx.getRealPath("/report/correspondence/correspondenceReport.jrxml");
                    list = correspon_model.showData(search_corr_no, search_subject, search_type_corr, search_org_from, search_office, search_corr_date, type_of_doc, dealing_person, -1, -1, searchreply_forward, search_Other_person, search_key_person);
                    byte[] reportInbytes = correspon_model.generateRecordList(jrxmlFilePath, list);
                    response.setContentLength(reportInbytes.length);
                    servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                    servletOutputStream.flush();
                    servletOutputStream.close();
                    correspon_model.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("Exception is:" + e);
                }
            } else if (task != null && task.equals("PrintExcelList")) {
                String jrxmlFilePath;
                List list = null;
                try {
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename=CorrespondenceReport.xls");
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    jrxmlFilePath = ctx.getRealPath("/report/correspondence/correspondenceReport.jrxml");
                    list = correspon_model.showData(search_corr_no, search_subject, search_type_corr, search_org_from, search_office, search_corr_date, type_of_doc, dealing_person, -1, -1, searchreply_forward, search_Other_person, search_key_person);
                    ByteArrayOutputStream reportInbytes = correspon_model.generateExcelList(jrxmlFilePath, list);
                    response.setContentLength(reportInbytes.size());
                    servletOutputStream.write(reportInbytes.toByteArray());
                    servletOutputStream.flush();
                    servletOutputStream.close();
                    correspon_model.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("Exception is:" + e);
                }
            }
            if (task == null) {
                List items = null;
                Iterator itr = null;
                // UploadImage list = new UploadImage();
                String fileExt = "";
                String folder = "";
                FileItem item = null;
                List co_List = new ArrayList();
                File tmpDir = null;
                String is_task_reply = "";
                int corres_task_id = 0;
                DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
                fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
                fileItemFactory.setRepository(tmpDir);
                ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
                try {
                    if (ServletFileUpload.isMultipartContent(request)) {
                        items = uploadHandler.parseRequest(request);
                        int size = items.size();
                        System.out.println("size of item list=" + size);
                        itr = items.iterator();
                        Correspondence correspond = new Correspondence();
                        int row_count = 0;
                        while (itr.hasNext()) {
                            item = (FileItem) itr.next();
                            if (row_count == 0) {
                                
                                System.out.println("************************************************** =" + item.getFieldName());
                            }
                            if (item.isFormField()) {
                                System.out.println("&&&&&&&&&&&&&&&&&&&&name the fields =" + item.getFieldName());
                                if (item.getFieldName().equals("correspondence_id")) {
                                    correspond.setCorrecpondence_id(Integer.parseInt(item.getString()));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("revision")) {
                                    correspond.setRevision(Integer.parseInt(item.getString()));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("correspondece_no")) {
                                    correspond.setCorrespondece_no((item.getString()));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("subject")) {
                                    correspond.setSubject(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("type_of_correspondence")) {
                                    correspond.setType_of_cop(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("type_of_document")) {
                                    correspond.setType_of_document(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }

                                if (item.getFieldName().equals("source_organisation")) {
                                    correspond.setSource_organisation(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());

                                }
                                if (item.getFieldName().equals("src_office")) {
                                    correspond.setSource_office(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("destination_organisation")) {
                                    correspond.setDestination_organisation(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("des_office")) {
                                    correspond.setDestination_office(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("refrence_no")) {
                                    correspond.setRefrence_no(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("dealing_person")) {
                                    correspond.setDealing_person(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("description")) {
                                    correspond.setDescription(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("corr_date")) {
                                    correspond.setCorr_date(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("other_person")) {
                                    correspond.setOther_person(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("key_person")) {
                                    correspond.setKey_person(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("own_ref_no")) {
                                    correspond.setOwner_ref_no(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("ref_corr_date")) {
                                    correspond.setRef_corres_date(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("priority")) {
                                    correspond.setPriority(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("reason")) {
                                    correspond.setReason(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("is_cc")) {
                                    correspond.setIs_cc(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("register_date")) {
                                    correspond.setRegister_date(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("status")) {
                                    correspond.setStatus(item.getString("UTF-8"));
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("reply")) {
                                    correspond.setReply(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("reply_forward")) {
                                    correspond.setReply_forward(item.getString());
                                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString());
                                }
                                if (item.getFieldName().equals("task")) {
                                    task = item.getString();
                                    System.out.println("name of task" + task);
                                }
                                if (item.getFieldName().equals("is_task_reply")) {
                                    is_task_reply = item.getString();                                   
                                }
                                if (item.getFieldName().equals("corres_task_id")) {
                                    String temp = item.getString();
                                    if(!temp.isEmpty())
                                    corres_task_id = Integer.parseInt(temp);
                                }

                            }
                            if (!item.isFormField()) {
                                fileExt = item.getName();
                                System.out.println("name of file =" + fileExt);
                                if (!item.getString().equals("")) {
                                    String billImagePath = correspon_model.getReportPath();
                                    billImagePath = billImagePath + "Correspondence/" + correspond.getType_of_document();
                                    int index = fileExt.indexOf(".");
                                    String fileExtendsion = fileExt.substring(index + 1);
                                    String appfolder = correspon_model.createAppropriateDirectories(billImagePath, correspond.getCorrespondece_no(), correspond.getCorr_date());
                                    String bill_image = appfolder + "/" + correspond.getCorrespondece_no() + "." + fileExtendsion;
                                    File file = new File(bill_image);
                                    //if(!file.exists()){
                                    correspond.setImage_name(bill_image);
                                    item.write(file);
                                    // }
                                } else {
                                    correspond.setImage_name("");
                                }

                            }
//                            row_count++;
//                            if (row_count == 31) {
//                               co_List.add(correspond);
//                                System.out.println("no of object has created =" + co_List.size());
//                                row_count = 0;
//
//                            }
                        }
                        co_List.add(correspond);
                        //correspond = (Correspondence) co_List.get(0);
                        if (task.equals("Add All Records")) {
                            correspon_model.insertRecord(co_List, is_task_reply, corres_task_id);//home_offce_id
                            task = "Add All Records";
                        }
                        if (task.equals("Save") && correspond.getCorrecpondence_id() == 0) {
                            correspon_model.insertRecord(co_List, is_task_reply, corres_task_id);//home_offce_id                            
                        }
                        if (task.equals("Delete")) {
                            //correspond = (Correspondence) co_List.get(0);
                            int correcpondence_id = correspond.getCorrecpondence_id();
                            int revision = correspond.getRevision();
                            correspon_model.deleteRecord(correcpondence_id, revision);
                            task = "Delete";
                        }
                        if (task.equals("Save") && correspond.getCorrecpondence_id() > 0) {
                            //correspond = (Correspondence) co_List.get(0);
                            correspon_model.reviseRecord(correspond );//home_offce_id
                            task = "Save";
                        }
                        if (task.equals("Save As New")) {
                            //correspond = (Correspondence) co_List.get(0);
                            correspond.setCorrecpondence_id(0);
                            correspon_model.insertRecord(co_List, is_task_reply, corres_task_id);//home_offce_id
                            task = "Save As New";
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error in uploading and Insert Image" + e);
                }
            }
            List<String> list = null;
            if (task == null) {
                task = "";
            } else if (task.equals("CorrespondenceImage")) {
                //  String ssadvtRepositoryPath = ctx.getInitParameter("ssadvtRepositoryPath");  // E.g. "E:\ssadvt_repository"
                String image_path = request.getParameter("image_path");
                OutputStream os = response.getOutputStream();
                InputStream is = new FileInputStream(new File(image_path));
                byte[] buf = new byte[32 * 1024];
                int nRead = 0;
                while ((nRead = is.read(buf)) != -1) {
                    os.write(buf, 0, nRead);
                }
                os.flush();
                os.close();
                return;


            } else if (task.equals("Show All Records")) {
                search_corr_no = "";
                search_subject = "";
                search_type_corr = "";
                search_org_from = "";
                search_office = "";
                search_corr_date = "";
                type_of_doc = "";
                dealing_person = "";
                searchreply_forward = "";
            }else if(task.equals("Reply")){
                task_reply = (String)request.getAttribute("task_reply");
                String corr_id = (String) request.getAttribute("correspondence_id");
                search_corr_no = (String) request.getAttribute("correspondence_no");
                search_type_corr = (String) request.getAttribute("correspondence_type");
                String corr_task_id = (String) request.getAttribute("correspondence_task_id");
                request.setAttribute("task_reply", task_reply);
                request.setAttribute("corres_task_id", corr_task_id);
                //TaskSchedulerBean taskBean = (TaskSchedulerBean) request.getAttribute("taskBean");
                //String taskBean = request.getParameter("taskBean");
            }
            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            }
            noOfRowsInTable = correspon_model.getNoOfRows(search_corr_no, search_subject, search_type_corr, search_org_from, search_office, search_corr_date, type_of_doc, dealing_person, searchreply_forward, search_Other_person, search_key_person);                  // get the number of records (rows) in the table.
            if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
            else if (buttonAction.equals("Previous")) {
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0) {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else {
                    lowerLimit = temp;
                }
            } else if (buttonAction.equals("First")) {
                lowerLimit = 0;
            } else if (buttonAction.equals("Last")) {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save As New") || task.equals("Reply")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }
            List<Correspondence> list1 = correspon_model.showData(search_corr_no, search_subject, search_type_corr, search_org_from, search_office, search_corr_date, type_of_doc, dealing_person, lowerLimit, noOfRowsToDisplay, searchreply_forward, search_Other_person, search_key_person);
            lowerLimit = lowerLimit + list1.size();
            noOfRowsTraversed = list1.size();


            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("search_corr_no", search_corr_no);
            request.setAttribute("search_subject", search_subject);
            request.setAttribute("search_type_corr", search_type_corr);
            request.setAttribute("searchreply_forward", searchreply_forward);
            request.setAttribute("search_org_from", search_org_from);
            request.setAttribute("search_office", search_office);
            request.setAttribute("search_corr_date", search_corr_date);
            request.setAttribute("type_of_doc", type_of_doc);
            request.setAttribute("dealing_person", dealing_person);
            request.setAttribute("correcpondencelist", list1);
            request.setAttribute("doclist", correspon_model.getDoclist());
            request.setAttribute("message", correspon_model.getMessage());
            request.setAttribute("msgBgColor", correspon_model.getMsgBgColor());
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            // correspon_model.closeConnection();
            String is_from_task = request.getParameter("is_from_task");
            if(is_from_task != null && is_from_task.equals("Y"))
                request.getRequestDispatcher("/taskSchedulerView").forward(request, response);
            else
                request.getRequestDispatcher("/correspondenceView").forward(request, response);
        } catch (Exception e) {
            System.out.println("Error in correspondence Controller" + e);
            ctx.getRequestDispatcher("/correspondenceView").forward(request, response);
        }


    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }
}

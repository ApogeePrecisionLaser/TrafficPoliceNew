/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.correspondence.controller;

import com.tp.correspondence.tableClasses.Correspondence;
import com.tp.correspondence.model.CorrespondenceModel;
import com.tp.correspondence.model.TaskSchedulerModel;
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
public class TaskSchedulerController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 4, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        TaskSchedulerModel taskSchedulerModel = new TaskSchedulerModel();
        HttpSession session = request.getSession(false);
//        if (session == null || (String) session.getAttribute("user_name") == null) {
//            response.sendRedirect("beforeLoginPage");
//            return;
//        }
        try {
            taskSchedulerModel.setConnection(DBConnection.getConnection(ctx));
        } catch (Exception e) {
            System.out.println("error in DivisionController setConnection() calling try block" + e);
        }
        try {
            //String user_name = (String) session.getAttribute("user_name");
            //int[] userdetail = taskSchedulerModel.getUserdetail(user_name);
            //int user_id = userdetail[0];
            //int created_by_id = userdetail[1];
            //int home_offce_id = userdetail[2];
            String task = request.getParameter("task");
            if(task == null)
                task = "";
            String JQString = request.getParameter("action1");
            if(JQString != null){
                List<String> list = null;
                String q = request.getParameter("q");
                PrintWriter out = response.getWriter();
                if (JQString.equals("getOfficeList")) {
                    String organisation_name = request.getParameter("organisation_name");
                    String offices = taskSchedulerModel.getOfficeList(organisation_name);
                    if (offices == null)
                        out.print("");
                    else
                        out.println(offices);
                    return;
                } else if (JQString.equals("getkey_personlist"))                     
                    list = taskSchedulerModel.getKeyPersonList(q);
                else if (JQString.equals("getCorrespondenceNo"))                     
                    list = taskSchedulerModel.getCorrespondenceNo(q);
                else if(JQString.equals("getCorrespondenceTask"))
                    list = taskSchedulerModel.getCorrespondenceTask(q);
                else if(JQString.equals("getSubjectList"))
                    list = taskSchedulerModel.getSubjectList(q);
                
                Iterator itr = list.iterator();
                while(itr.hasNext())
                    out.println((String)itr.next());
                out.close();
                return;
            }

            String search_corr_no = "";
            String search_subject = "";
            String search_task = "";
            String search_type_corr = "";
            String search_org_from = "";
            String search_org_to = "";
            String search_corr_date = "";
            String type_of_doc = "";
            String dealing_person = "";
            String search_Other_person = "";
            String search_key_person = "";
            String searchreply_forward = "";
            search_corr_no = request.getParameter("search_corr_no");
            search_subject = request.getParameter("search_subject");
            search_task = request.getParameter("search_task");
            search_type_corr = request.getParameter("search_type_corr");
            search_org_from = request.getParameter("search_org_from");
            search_org_to = request.getParameter("search_org_to");
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
                if (search_task == null) {
                    search_task = "";
                }
                if (search_type_corr == null) {
                    search_type_corr = "";
                }
                if (search_org_from == null) {
                    search_org_from = "";
                }
                if (search_org_to == null) {
                    search_org_to = "";
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
                    jrxmlFilePath = ctx.getRealPath("/report/correspondence/correspondenceTaskReport.jrxml");
                    list = taskSchedulerModel.showTaskDataReport(search_corr_no, search_task, search_type_corr, search_org_from, search_org_to, search_corr_date, type_of_doc, dealing_person, -1, -1, searchreply_forward, search_Other_person, search_key_person);
                    byte[] reportInbytes = taskSchedulerModel.generateRecordList(jrxmlFilePath, list);
                    response.setContentLength(reportInbytes.length);
                    servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                    servletOutputStream.flush();
                    servletOutputStream.close();
                    taskSchedulerModel.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("Exception is:" + e);
                }
            } else if (task != null && task.equals("PrintExcelList")) {
                String jrxmlFilePath;
                List list = null;
                try {
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename=OutgoingReport.xls");
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    jrxmlFilePath = ctx.getRealPath("/report/correspondence/correspondenceTaskReport.jrxml");
                    list = taskSchedulerModel.showTaskDataReport(search_corr_no, search_task, search_type_corr, search_org_from, search_org_to, search_corr_date, type_of_doc, dealing_person, -1, -1, searchreply_forward, search_Other_person, search_key_person);
                    ByteArrayOutputStream reportInbytes = taskSchedulerModel.generateExcelList(jrxmlFilePath, list);
                    response.setContentLength(reportInbytes.size());
                    servletOutputStream.write(reportInbytes.toByteArray());
                    servletOutputStream.flush();
                    servletOutputStream.close();
                    taskSchedulerModel.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("Exception is:" + e);
                }
            }

            try {
                if (task.equals("Cancel"))
                    taskSchedulerModel.deleteRecord(Integer.parseInt(request.getParameter("correspondence_task_id")), Integer.parseInt(request.getParameter("revision")));
                else if (task.equals("Save") || task.equals("Save As New") || task.equals("Add All Records") || task.equals("Reply")) {
                //Correspondence correspond = new Correspondence();
                    TaskSchedulerBean taskBean = new TaskSchedulerBean();
                    String correspondence_task_id = request.getParameter("correspondence_task_id");
                    String revision = request.getParameter("revision");
                    String correspondence_id = request.getParameter("correspondence_id_task");
                    String correspondence_no = request.getParameter("task_correspondence_no");
                    String correspondence_task = request.getParameter("correspondence_task");
                    String start_date = request.getParameter("start_date");
                    String no_of_days = request.getParameter("no_of_days");
                    String remark = request.getParameter("remark");
                    String status = request.getParameter("task_status");
                    if(correspondence_task_id != null && !correspondence_task_id.isEmpty())
                        taskBean.setCorrespondence_task_id(Integer.parseInt(correspondence_task_id));
                    if(revision != null && !revision.isEmpty())
                        taskBean.setRevision(Integer.parseInt(revision));
                    if(correspondence_id != null && !correspondence_id.isEmpty())
                        taskBean.setCorrespondence_id(Integer.parseInt(correspondence_id));
                    if(correspondence_task != null && !correspondence_task.isEmpty())
                        taskBean.setTask(correspondence_task);
                    if(start_date != null && !start_date.isEmpty())
                        taskBean.setStart_date(start_date);
                    if(no_of_days != null && !no_of_days.isEmpty())
                        taskBean.setNo_of_days(Integer.parseInt(no_of_days));
                    if(remark != null)
                        taskBean.setRemark(remark);
                    if(status != null && !status.isEmpty())
                        taskBean.setStatus(status);

                    if (task.equals("Reply")) {
                        request.setAttribute("task", "taskReply");
                        request.setAttribute("task_reply", "Y");
                        request.setAttribute("correspondence_id", correspondence_id);
                        request.setAttribute("correspondence_no", correspondence_no);
                        request.setAttribute("correspondence_type", "Incoming");
                        request.setAttribute("correspondence_task_id", correspondence_task_id);
                        //request.setAttribute("taskBean", taskBean);
                        request.getRequestDispatcher("correspondenceCont.do").forward(request, response);
                        return;
                    } else if (task.equals("Add All Records")) {
                        taskSchedulerModel.insertRecord(taskBean);//home_offce_id
                    }else if (task.equals("Save") && taskBean.getCorrespondence_task_id() == 0) {
                        taskSchedulerModel.insertRecord(taskBean);//home_offce_id
                    }else if (task.equals("Save") && taskBean.getCorrespondence_task_id() > 0) {
                        //correspond = (Correspondence) co_List.get(0);
                        taskSchedulerModel.reviseRecord(taskBean );//home_offce_id
                    }else if (task.equals("Save As New")) {
                        //correspond = (Correspondence) co_List.get(0);
                        taskBean.setCorrespondence_id(0);
                        taskSchedulerModel.insertRecord(taskBean);//home_offce_id
                    }
                }
            } catch (Exception e) {
                    System.out.println("Error in uploading and Insert Image" + e);
            }
                        
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
                search_task = "";
                search_type_corr = "";
                search_org_from = "";
                search_org_to = "";
                search_corr_date = "";
                type_of_doc = "";
                dealing_person = "";
                searchreply_forward = "";
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
            search_type_corr = "Incoming";
            String from = request.getParameter("from");
            if(from == null)
                from = "";
            if(from.equals("Schedule"))
                noOfRowsInTable = taskSchedulerModel.getNoOfRows(search_corr_no, search_subject, search_type_corr, search_org_from, search_org_to, search_corr_date, type_of_doc, dealing_person, searchreply_forward, search_Other_person, search_key_person);                  // get the number of records (rows) in the table.
            else
                noOfRowsInTable = taskSchedulerModel.getNoOfRowsFromTask(search_corr_no, search_task, search_type_corr, search_org_from, search_org_to, search_corr_date, type_of_doc, dealing_person, searchreply_forward, search_Other_person, search_key_person);                  // get the number of records (rows) in the table.
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

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save As New")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }
            List<Correspondence> list1 = null;
            int size = 0;
            if(from.equals("Schedule")){
                list1 = taskSchedulerModel.showData(search_corr_no, search_subject, search_type_corr, search_org_from, search_org_to, search_corr_date, type_of_doc, dealing_person, lowerLimit, noOfRowsToDisplay, searchreply_forward, search_Other_person, search_key_person);
                size = list1.size();
            }
            else{
                list1 = taskSchedulerModel.showTaskData(search_corr_no, search_task, search_type_corr, search_org_from, search_org_to, search_corr_date, type_of_doc, dealing_person, lowerLimit, noOfRowsToDisplay, searchreply_forward, search_Other_person, search_key_person);
                size = taskSchedulerModel.getShowData_size();
            }
            lowerLimit = lowerLimit + size;
            noOfRowsTraversed = size;


            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("correspondence_status", "initial");
            request.setAttribute("search_corr_no", search_corr_no);
            request.setAttribute("search_subject", search_subject);
            request.setAttribute("search_task", search_task);
            request.setAttribute("search_type_corr", search_type_corr);
            request.setAttribute("searchreply_forward", searchreply_forward);
            request.setAttribute("search_org_from", search_org_from);
            request.setAttribute("search_org_to", search_org_to);
            request.setAttribute("search_corr_date", search_corr_date);
            request.setAttribute("type_of_doc", type_of_doc);
            request.setAttribute("dealing_person", dealing_person);
            request.setAttribute("correcpondencelist", list1);
            request.setAttribute("doclist", taskSchedulerModel.getDoclist());
            request.setAttribute("message", taskSchedulerModel.getMessage());
            request.setAttribute("msgBgColor", taskSchedulerModel.getMsgBgColor());
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            // taskSchedulerModel.closeConnection();
            if(from.equals("Schedule"))
                request.getRequestDispatcher("/taskSchedulerView").forward(request, response);
            else
                request.getRequestDispatcher("/manageTaskView").forward(request, response);
        } catch (Exception e) {
            System.out.println("Error in correspondence Controller" + e);
            ctx.getRequestDispatcher("/taskSchedulerView").forward(request, response);
        }


    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }
}

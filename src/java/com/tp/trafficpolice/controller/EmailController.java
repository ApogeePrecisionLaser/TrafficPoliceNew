/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.trafficpolice.controller;

import com.tp.tableClasses.EmailBean;
import com.tp.tableClasses.StatusTypeBean;
import com.tp.trafficpolice.model.EmailModel;
import com.tp.trafficpolice.model.StatusTypeModel;
import com.tp.util.UniqueIDGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JPSS
 */
public class EmailController extends HttpServlet {
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is email Controller....");
        ServletContext ctx = getServletContext();
        EmailModel emailmodel = new EmailModel();
         emailmodel .setDriverClass(ctx.getInitParameter("driverClass"));
        emailmodel.setDb_username(ctx.getInitParameter("db_userName"));
        emailmodel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        emailmodel.setConnectionString(ctx.getInitParameter("connectionString"));
        emailmodel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");
        String searchEmail= request.getParameter("searchEmail");

        
        
            String mountingType = "";
        Map<String, String> map = new HashMap<String, String>();
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getEmail")) {
                    list = emailmodel.getEmail(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                emailmodel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

         if (task == null) {
            task = "";
        }
        if (task.equals("generateMapReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/statusReport.jrxml");
                        list=emailmodel.showAllData();
                        byte[] reportInbytes =emailmodel.generateRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        emailmodel.closeConnection();
                        return;
            }else if (task.equals("generateExcelReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/vnd.ms-excel");
                        response.addHeader("Content-Disposition", "attachment; filename=status_report.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/statusReport.jrxml");
                        list=emailmodel.showAllData();
                        ByteArrayOutputStream reportInbytes =emailmodel.generateExcelList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.size());
                        servletOutputStream.write(reportInbytes.toByteArray());
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        emailmodel.closeConnection();
                        return;
            }
        if (task.equals("Delete")) {
           emailmodel.deleteRecord(Integer.parseInt(request.getParameter("id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int id;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (Exception e) {
                id = 0;
            }
            if (task.equals("Save AS New")) {
                id = 0;
            }
           EmailBean emailbean= new EmailBean();
           emailbean.setId(id);
           emailbean.setUser_name(request.getParameter("user_name"));
           emailbean.setUser_password(request.getParameter("user_password"));
           emailbean.setSendto(request.getParameter("sendto"));
            if (id == 0) {
                System.out.println("Inserting values by model......");
                emailmodel.insertRecord(emailbean);
            } else {
                System.out.println("Update values by model........");
                emailmodel.updateRecord(emailbean);
            }
        }

       
        try {
            if ( searchEmail== null) {
                searchEmail = "";
            }
        } catch (Exception e) {
        }
        // Start of Search Table
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
        if (task.equals("Show All Records")) {
             searchEmail = "";
        }
        System.out.println("searching.......... " +  searchEmail);
        noOfRowsInTable = emailmodel.getNoOfRows( searchEmail);                  // get the number of records (rows) in the table.

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

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        
         List<EmailBean> EmailList = emailmodel.showData(lowerLimit,noOfRowsToDisplay,searchEmail);
        lowerLimit = lowerLimit + EmailList.size();
        noOfRowsTraversed = EmailList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

        request.setAttribute("emailList", EmailList);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("searchEmail", searchEmail);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
         request.setAttribute("message", emailmodel.getMessage());
    request.getRequestDispatcher("/view/trafficPolice/email.jsp").forward(request, response);
    }
          @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);}
}

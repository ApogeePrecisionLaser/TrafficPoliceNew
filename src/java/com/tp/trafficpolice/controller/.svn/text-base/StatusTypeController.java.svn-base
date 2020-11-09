/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.trafficpolice.controller;

import com.tp.tableClasses.StatusTypeBean;
import com.tp.trafficpolice.model.StatusTypeModel;
import com.tp.util.UniqueIDGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
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
public class StatusTypeController extends HttpServlet {
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is state Controller....");
        ServletContext ctx = getServletContext();
        StatusTypeModel statusTypeModel = new StatusTypeModel();
         statusTypeModel .setDriverClass(ctx.getInitParameter("driverClass"));
        statusTypeModel.setDb_username(ctx.getInitParameter("db_userName"));
        statusTypeModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        statusTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        statusTypeModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");
        String searchStatusType= request.getParameter("searchStatusType");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getStatusType")) {
                    list = statusTypeModel.getStatusType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                statusTypeModel.closeConnection();
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
                        list=statusTypeModel.showAllData();
                        byte[] reportInbytes =statusTypeModel.generateRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        statusTypeModel.closeConnection();
                        return;
            }else if (task.equals("generateExcelReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/vnd.ms-excel");
                        response.addHeader("Content-Disposition", "attachment; filename=status_report.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/statusReport.jrxml");
                        list=statusTypeModel.showAllData();
                        ByteArrayOutputStream reportInbytes =statusTypeModel.generateExcelList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.size());
                        servletOutputStream.write(reportInbytes.toByteArray());
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        statusTypeModel.closeConnection();
                        return;
            }
        if (task.equals("Delete")) {
           statusTypeModel.deleteRecord(Integer.parseInt(request.getParameter("status_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int status_type_id;
            try {
                status_type_id = Integer.parseInt(request.getParameter("status_type_id"));
            } catch (Exception e) {
                status_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                status_type_id = 0;
            }
           StatusTypeBean statusTypeBean= new StatusTypeBean();
            statusTypeBean.setStatus_type_id(status_type_id);
           statusTypeBean.setStatus_type(request.getParameter("status_type"));
           statusTypeBean.setRemark(request.getParameter("remark"));
            if (status_type_id == 0) {
                System.out.println("Inserting values by model......");
                statusTypeModel.insertRecord(statusTypeBean);
            } else {
                System.out.println("Update values by model........");
                statusTypeModel.updateRecord(statusTypeBean);
            }
        }

       
        try {
            if ( searchStatusType == null) {
                searchStatusType = "";
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
             searchStatusType = "";
        }
        System.out.println("searching.......... " +  searchStatusType);
        noOfRowsInTable = statusTypeModel.getNoOfRows( searchStatusType);                  // get the number of records (rows) in the table.

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
        
         List<StatusTypeBean> statusTypeList = statusTypeModel.showData(lowerLimit,noOfRowsToDisplay,searchStatusType);
        lowerLimit = lowerLimit + statusTypeList.size();
        noOfRowsTraversed = statusTypeList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

        request.setAttribute("statusTypeList", statusTypeList);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("searchStatusType", searchStatusType);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
         request.setAttribute("message", statusTypeModel.getMessage());
    request.getRequestDispatcher("/statusType").forward(request, response);
    }
          @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);}
}

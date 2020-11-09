/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

import com.tp.tableClasses.ActOriginBean;
import com.tp.trafficpolice.model.ActOriginModel;
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
 * @author Administrator
 */
public class ActOriginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is state Controller....");
        ServletContext ctx = getServletContext();
        ActOriginModel actOriginModel = new ActOriginModel();
        actOriginModel.setDriverClass(ctx.getInitParameter("driverClass"));
        actOriginModel.setDb_username(ctx.getInitParameter("db_userName"));
        actOriginModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        actOriginModel.setConnectionString(ctx.getInitParameter("connectionString"));
        actOriginModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

    

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getActOrigin")) {
                    list = actOriginModel.getActOrigin(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                actOriginModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         String task = request.getParameter("task");
        String searchActOrigin = request.getParameter("searchActOrigin");
        if (task == null) {
            task = "";
        }
        try {
            if (searchActOrigin == null) {
                searchActOrigin = "";
            }
        } catch (Exception e) {
        }
         if(task.equals("generateMapReport"))//start from here
         {
            List listAll = null;
            String jrxmlFilePath;
        
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll=actOriginModel.showAllData(searchActOrigin);
            jrxmlFilePath = ctx.getRealPath("/report/ActOrignType.jrxml");
            byte[] reportInbytes = actOriginModel.generateOrignReport(jrxmlFilePath,listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
         }else if(task.equals("generateMapXlsReport"))
         {     String jrxmlFilePath;
              List listAll=null;
          
                       response.setContentType("application/vnd.ms-excel");
                       response.addHeader("Content-Disposition", "attachment; filename=ActOrignReport.xls");
                       ServletOutputStream servletOutputStream = response.getOutputStream();
                       jrxmlFilePath = ctx.getRealPath("/report/ActOrignType.jrxml");
                       listAll=actOriginModel.showAllData(searchActOrigin);
                       ByteArrayOutputStream reportInbytes =actOriginModel.generateOrignXlsRecordList(jrxmlFilePath, listAll);
                       response.setContentLength(reportInbytes.size());
                       servletOutputStream.write(reportInbytes.toByteArray());
                       servletOutputStream.flush();
                       servletOutputStream.close();
                       return;
         }


        if (task.equals("Delete")) {
           actOriginModel.deleteRecord(Integer.parseInt(request.getParameter("act_origin_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int act_origin_id;
            try {
                act_origin_id = Integer.parseInt(request.getParameter("act_origin_id"));
            } catch (Exception e) {
                act_origin_id = 0;
            }
            if (task.equals("Save AS New")) {
                act_origin_id = 0;
            }
           ActOriginBean actOrigin=new ActOriginBean();
            actOrigin.setAct_origin_id(act_origin_id);
            actOrigin.setAct_origin(request.getParameter("act_origin"));
            actOrigin.setRemark(request.getParameter("remark"));
            if (act_origin_id == 0) {
                System.out.println("Inserting values by model......");
                actOriginModel.insertRecord(actOrigin);
            } else {
                System.out.println("Update values by model........");
                actOriginModel.updateRecord(actOrigin);
            }
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
            searchActOrigin = "";
        }
        System.out.println("searching.......... " + searchActOrigin);
        noOfRowsInTable = actOriginModel.getNoOfRows(searchActOrigin);                  // get the number of records (rows) in the table.

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

        List<ActOriginBean> actoriginList = actOriginModel.showData(lowerLimit, noOfRowsToDisplay, searchActOrigin);
        lowerLimit = lowerLimit + actoriginList.size();
        noOfRowsTraversed =actoriginList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        request.setAttribute("actoriginList", actoriginList);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("searchActOrigin",searchActOrigin);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", actOriginModel.getMessage());
        request.getRequestDispatcher("/actOrigin").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

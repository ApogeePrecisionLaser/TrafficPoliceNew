/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.trafficpolice.controller;

import com.tp.tableClasses.ReportsType;
import com.tp.trafficpolice.model.ReportsTypeModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class ReportsTypeController extends HttpServlet {
      @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is state Controller....");
        ServletContext ctx = getServletContext();
        ReportsTypeModel reportsModel=new ReportsTypeModel();
        reportsModel.setDriverClass(ctx.getInitParameter("driverClass"));
        reportsModel.setDb_userName(ctx.getInitParameter("db_userName"));
        reportsModel.setDb_userPasswrod(ctx.getInitParameter("db_userPasswrod"));
        reportsModel.setConnectionString(ctx.getInitParameter("connectionString"));
        reportsModel.setConnection();
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
                if (JQstring.equals("getReportsFor")) {
                    list =reportsModel.getReportsFor(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                reportsModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

         if (task == null) {
            task = "";
        }

        if (task.equals("Delete")) {
                  } else if (task.equals("Save") || task.equals("Save AS New")) {
                      try{
                         ReportsType reportsType=new ReportsType();
            int report_type_id;
            try {
                report_type_id = Integer.parseInt(request.getParameter("report_type_id"));
            } catch (Exception e) {
               report_type_id =0;
            }
            if (task.equals("Save AS New")) {
                  report_type_id= 0;
                         
            }
       
         reportsType.setReports_idM(request.getParameterValues("report_type_id"));
         reportsType.setReports_for(request.getParameter("reports_for"));
         reportsType.setHeader(request.getParameter("header_name"));
         reportsType.setNo_of_columns(Integer.parseInt(request.getParameter("no_of_columns")));
         reportsType.setReport_column_nameM(request.getParameterValues("report_column_name"));
        if (report_type_id==0) {
                System.out.println("Inserting values by model......");
                reportsModel.insertRecord(reportsType);
            } else {
                System.out.println("Update values by model........");
                //statusTypeModel.updateRecord(statusTypeBean);
            }

                      }catch(Exception e){
            System.out.println("Exception: "+e);
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
        System.out.println("searching.......... " +  searchStatusType);
       // noOfRowsInTable = statusTypeModel.getNoOfRows( searchStatusType);                  // get the number of records (rows) in the table.

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
           // lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
             searchStatusType = "";
        }

       //  List<StatusTypeBean> statusTypeList = statusTypeModel.showData(lowerLimit,noOfRowsToDisplay,searchStatusType);
       // lowerLimit = lowerLimit + statusTypeList.size();
      //  noOfRowsTraversed = statusTypeList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
//            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
//                request.setAttribute("showNext", "false");
//                request.setAttribute("showLast", "false");
//            }

      //  request.setAttribute("statusTypeList", statusTypeList);
       request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("searchStatusType", searchStatusType);
       
 request.getRequestDispatcher("/reportType").forward(request, response);
    }
          @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);}
}

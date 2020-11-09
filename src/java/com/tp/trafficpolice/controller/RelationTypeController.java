/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

//import com.meter.dataEntry.controller.*;
//import com.meter.dbcon.DBConnection;
import com.tp.tableClasses.CaseStatus;
import com.tp.trafficpolice.model.RelationTypeModel;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
public class RelationTypeController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();

        
        RelationTypeModel relationTypeModel = new RelationTypeModel();

        relationTypeModel .setDriverClass(ctx.getInitParameter("driverClass"));
        relationTypeModel.setDb_username(ctx.getInitParameter("db_userName"));
        relationTypeModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        relationTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        relationTypeModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        

          try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getRelationtype")) {
                    list = relationTypeModel.getRelationtype(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                relationTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }


        //  HttpSession session = request.getSession(false);
       

        // String role = (String) session.getAttribute("user_role");
     /*   int ctreat_by= ((Integer)session.getAttribute("user_id")).intValue();

      if (session == null || (String) session.getAttribute("user_name")==null ) {
            response.sendRedirect("beforeLoginPage");             return;
        }
        try {
            queryTypeModel.setConnection(DBConnection.getConnection(ctx, session));
        } catch (Exception e) {
            System.out.println("error in QueryTypeController setConnection() calling try block" + e);
        } */
        try{
        String task = request.getParameter("task");

        String searchRelationtype = request.getParameter("searchRelationtype");

       if (searchRelationtype ==null) {
            searchRelationtype ="";
        }
        if (task == null) {
            task = "";
        }
       
        if (task.equals("generateMapReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/RelationTypeReport.jrxml");
                        list=relationTypeModel.showAllData();
                        byte[] reportInbytes =relationTypeModel.generateRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        relationTypeModel.closeConnection();
                        return;
            }else if (task.equals("generateExcelReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment; filename=RelationType_report.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/RelationTypeReport.jrxml");
                        list=relationTypeModel.showAllData();
                        ByteArrayOutputStream reportInbytes =relationTypeModel.generateExcelList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.size());
                        servletOutputStream.write(reportInbytes.toByteArray());
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        relationTypeModel.closeConnection();
                        return;
            }

        if (task.equals("Delete")) {
            relationTypeModel.deleteRecord(Integer.parseInt(request.getParameter("query_type_id")));  // Pretty sure that query_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save As New")||task.equals("Update")|| task.equals("Add All Records")) {
            int query_type_id = 0;
            try {
                query_type_id = Integer.parseInt(request.getParameter("query_type_id").trim());
            } catch (Exception e) {
                query_type_id = 0;
            }
             CaseStatus queryTypeDetail = new CaseStatus();
            if (task.equals("Save As New")) {
                    query_type_id = 0;

                            String[] id = new String[1];
                            String z_id = "1";
                            id[0] = z_id;
                  queryTypeDetail.setQuery_type_idM(id);
            }
           if (task.equals("Add All Records") ) {
               queryTypeDetail.setQuery_type_idM(request.getParameterValues("query_type_id"));
           }

            queryTypeDetail.setQuery_typeM(request.getParameterValues("query_type"));
            queryTypeDetail.setDescriptionM(request.getParameterValues("description"));
            if (query_type_id == 0  || task.equals("Add All Records")) {
                relationTypeModel.insertRecord(queryTypeDetail);
            } else if(task.equals("Update")){
              queryTypeDetail.setQuery_type_id(Integer.parseInt(request.getParameter("query_type_id").trim()));
                relationTypeModel.updateRecord(queryTypeDetail);
            }
        }

  // if (request.getAttribute("isSelectPriv").equals("Y")) {
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
             searchRelationtype = "";
        }
        noOfRowsInTable = relationTypeModel.getNoOfRows(searchRelationtype);                  // get the number of records (rows) in the table.
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

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save As New")||task.equals("Update")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        
        // Logic to show data in the table.
        List<CaseStatus> alertSheet = relationTypeModel.showData(lowerLimit, noOfRowsToDisplay,searchRelationtype);
        lowerLimit = lowerLimit + alertSheet.size();
        noOfRowsTraversed = alertSheet.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("queryTypeList", alertSheet);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
   //   }
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", relationTypeModel.getMessage());
        request.setAttribute("msgBgColor", relationTypeModel.getMsgBgColor());
        request.setAttribute("searchRelationtype", searchRelationtype);
        relationTypeModel.closeConnection();
        request.getRequestDispatcher("relation_type").forward(request, response);
        }catch(Exception e){
            System.out.println("RelationTypeController main thread " + e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}

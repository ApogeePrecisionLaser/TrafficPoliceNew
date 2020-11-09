/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

//import com.meter.dataEntry.controller.*;
import com.tp.trafficpolice.model.CommercialTypeModel;
//import com.meter.dbcon.DBConnection;
import com.tp.tableClasses.CommercialType;
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
public class CommercialTypeController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();

        
        CommercialTypeModel commercialTypeModel = new CommercialTypeModel();

        commercialTypeModel .setDriverClass(ctx.getInitParameter("driverClass"));
        commercialTypeModel.setDb_username(ctx.getInitParameter("db_userName"));
        commercialTypeModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        commercialTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        commercialTypeModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        
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
                
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("q");
                if(JQstring!=null)
                {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if(JQstring.equals("getCommercialType"))
                {
                 list = commercialTypeModel.getCommercial(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                commercialTypeModel.closeConnection();
                return;
                }

                String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
                String nameCommercial = request.getParameter("searchCommercial");
                if(nameCommercial == null)
                {
                    nameCommercial="";
                }

           if(task.equals("generateMapReport"))//start from here
          {
            String jrxmlFilePath;            
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            List  listAll=commercialTypeModel.showAllData(nameCommercial);
            jrxmlFilePath = ctx.getRealPath("/report/CommercialReport.jrxml");
            byte[] reportInbytes = commercialTypeModel.generateMapReport(jrxmlFilePath,listAll,nameCommercial);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
         }else if(task.equals("generateMapXlsReport"))
         {     String jrxmlFilePath;
                           
                       response.setContentType("application/vnd.ms-excel");
                       response.addHeader("Content-Disposition", "attachment; filename=commercial.xls");
                       ServletOutputStream servletOutputStream = response.getOutputStream();
                       jrxmlFilePath = ctx.getRealPath("/report/CommercialReport.jrxml");
                       List listAll=commercialTypeModel.showAllData(nameCommercial);
                       ByteArrayOutputStream reportInbytes =commercialTypeModel.generateCityXlsRecordList(jrxmlFilePath, listAll,nameCommercial);
                       response.setContentLength(reportInbytes.size());
                       servletOutputStream.write(reportInbytes.toByteArray());
                       servletOutputStream.flush();
                       servletOutputStream.close();
                       return;
         }


        if (task.equals("Delete")) {
            commercialTypeModel.deleteRecord(Integer.parseInt(request.getParameter("query_type_id")));  // Pretty sure that office_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save As New")||task.equals("Update")|| task.equals("Add All Records")) {
            int query_type_id = 0;
            try {
                query_type_id = Integer.parseInt(request.getParameter("query_type_id").trim());
            } catch (Exception e) {
                query_type_id = 0;
            }
             CommercialType queryTypeDetail = new CommercialType();
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
                commercialTypeModel.insertRecord(queryTypeDetail);
            } else if(task.equals("Update")){
              queryTypeDetail.setQuery_type_id(Integer.parseInt(request.getParameter("query_type_id").trim()));
                commercialTypeModel.updateRecord(queryTypeDetail);
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
        noOfRowsInTable = commercialTypeModel.getNoOfRows();                  // get the number of records (rows) in the table.
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
        }else if(task.equals("Search All Records"))
        { nameCommercial="";}

        // Logic to show data in the table.
        List<CommercialType> alertSheet = commercialTypeModel.showData(lowerLimit, noOfRowsToDisplay,nameCommercial);
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
        request.setAttribute("searchCommercialType", nameCommercial);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", commercialTypeModel.getMessage());
        request.setAttribute("msgBgColor", commercialTypeModel.getMsgBgColor());
        commercialTypeModel.closeConnection();
        request.getRequestDispatcher("commercialTypeView").forward(request, response);
        }catch(Exception e){
            System.out.println("commercialTypeController main thread " + e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
